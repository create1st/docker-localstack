import React, {useEffect, useState} from 'react';
import {useAuth0} from '@auth0/auth0-react';
import {GetAccessTokenSilently} from '../../common/auth/auth0';
import {Orders} from '../../model/Order';
import OrdersTable from "./OrdersTable";
import axios, {AxiosResponse} from "axios";

const ordersUrl = `http://localhost:8080/orders`;

interface OrdersViewState {
    orders: Orders
}

type SetOrdersViewState = (value: (((prevState: OrdersViewState) => OrdersViewState) | OrdersViewState)) => void;

// https://auth0.github.io/auth0-react/
const OrdersView = () => {
    const {getAccessTokenSilently} = useAuth0();
    const [ordersViewState, setOrdersViewState] = useState<OrdersViewState>({
        orders: []
    });
    OrdersViewStateUpdater(getAccessTokenSilently, setOrdersViewState);
    return (
        <div>
            <OrdersTable orders={ordersViewState.orders}/>
        </div>
    )
}

const OrdersViewStateUpdater = (getAccessTokenSilently: GetAccessTokenSilently, setOrdersViewState: SetOrdersViewState) => useEffect(() => {
    getAccessTokenSilently()
        .then(fetchOrders)
        .then(response => response.data)
        .then(toOrderViewState)
        .then(setOrdersViewState)
        .catch(errorHandler);
}, [getAccessTokenSilently, setOrdersViewState])

const fetchOrders = (accessToken: String): Promise<AxiosResponse<Orders>> =>
    axios.get(ordersUrl, {
        headers: {
            Authorization: `Bearer ${accessToken}`,
        },
    });

const toOrderViewState = (resp: Orders): OrdersViewState => ({
    orders: resp
})

const errorHandler = (error: any) => {
    console.error("Failed to get orders" + error);
}

export default OrdersView;