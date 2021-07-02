import {useEffect, useState} from "react";
import axios, {AxiosResponse} from "axios";
import {Orders} from "../model/Order";
import {useAuth0} from "@auth0/auth0-react";

const ordersUrl = `${process.env.REACT_APP_REST_ENDPOINT}/orders`;

// const ordersUrl = `data.json`;

export enum Status {
    Initialized,
    InProgress,
    Failed,
    Success
}

interface OrdersViewState {
    orders: Orders,
    status: Status,
    message: String | undefined,
}

const useGetOrders = (): [OrdersViewState, () => void] => {
    const {getAccessTokenSilently} = useAuth0(); // https://auth0.github.io/auth0-react/
    const [ordersViewState, setOrdersViewState] = useState<OrdersViewState>(initState);
    const refresh = () => {
        setOrdersViewState(inProgress);
        getAccessTokenSilently()
            .then(fetchOrders)
            .then(response => response.data)
            .then(toOrderViewState)
            .catch(errorHandler)
            .then(setOrdersViewState);
    }
    useEffect(refresh, [getAccessTokenSilently]);
    return [ordersViewState, refresh]
}

const fetchOrders = (accessToken: String): Promise<AxiosResponse<Orders>> =>
    axios.get(ordersUrl, {
        headers: {
            Authorization: `Bearer ${accessToken}`,
        },
    });

const initState = {
    status: Status.Initialized,
    message: undefined,
    orders: []
}

const inProgress = {
    status: Status.InProgress,
    message: undefined,
    orders: []
}

const errorHandler = (error: any) => {
    console.error("Failed to get orders" + error);
    return toErrorOrderViewState(error);
}

const toErrorOrderViewState = (error: any): OrdersViewState => ({
    status: Status.Failed,
    message: error.message,
    orders: []
})

const toOrderViewState = (orders: Orders): OrdersViewState => ({
    status: Status.Success,
    message: undefined,
    orders: orders
})

export default useGetOrders
export type {OrdersViewState}