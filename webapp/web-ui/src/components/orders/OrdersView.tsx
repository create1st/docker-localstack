import React from 'react';
import OrdersTable from "./OrdersTable";
import useGetOrders from "../../hooks/useGetOrders";
import {Container, Typography} from "@material-ui/core";

const OrdersView = () => {
    const [ordersViewState] = useGetOrders();
    return (
        <Container>
            <Typography variant="h5" gutterBottom>
                Orders
            </Typography>
            <OrdersTable orders={ordersViewState.orders}/>
        </Container>
    )
}

export default OrdersView;