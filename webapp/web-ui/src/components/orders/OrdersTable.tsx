import {Order, Orders} from "../../model/Order";
import {Paper, TableBody, TableContainer, TableHead, TableRow} from "@material-ui/core";
import {AlternateColorCell, AlternateColorTable, AlternateColorTableRow} from "../ui/AlternateColorTable"
import {createContext, useContext} from "react";


const OrdersContext = createContext<Orders | undefined>(undefined);

const OrdersTable = ({orders}: { orders: Orders }) => {
    return (
        <TableContainer component={Paper}>
            <AlternateColorTable
                id="ordersTable"
                aria-label="customized table"
            >
                <TableHead>
                    <TableRow>
                        <AlternateColorCell>User ID</AlternateColorCell>
                        <AlternateColorCell>Transaction ID</AlternateColorCell>
                        <AlternateColorCell>Order status</AlternateColorCell>
                        <AlternateColorCell>Timestamp</AlternateColorCell>
                    </TableRow>
                </TableHead>
                <OrdersContext.Provider value={orders}>
                    <OrdersTableBody/>
                </OrdersContext.Provider>
            </AlternateColorTable>
        </TableContainer>
    );
}

const OrdersTableBody = () => {
    const orders = useContext(OrdersContext);
    let rows = orders?.map((order, index) => orderRow(order, index))
    return (
        <TableBody>
            {rows}
        </TableBody>
    )
}

const orderRow = (order: Order, index: number) => (
    <AlternateColorTableRow key={index}>
        <AlternateColorCell>{order.userId}</AlternateColorCell>
        <AlternateColorCell>{order.transactionId}</AlternateColorCell>
        <AlternateColorCell>{order.orderStatus}</AlternateColorCell>
        <AlternateColorCell>{order.timestamp}</AlternateColorCell>
    </AlternateColorTableRow>
)

export default OrdersTable;