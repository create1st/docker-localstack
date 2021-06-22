import {Order, Orders} from "../../model/Order";
import {
    makeStyles,
    Paper,
    Table, TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    Theme,
    withStyles
} from "@material-ui/core";


const StyledTableCell = withStyles((theme: Theme) => ({
    head: {
        backgroundColor: theme.palette.common.black,
        color: theme.palette.common.white,
    },
    body: {
        fontSize: 14,
    },
}))(TableCell);

const StyledTableRow = withStyles((theme: Theme) => ({
    root: {
        '&:nth-of-type(odd)': {
            backgroundColor: theme.palette.action.hover,
        },
    },
}))(TableRow);

const useStyles = makeStyles({
    table: {
        minWidth: 700,
    },
});

const OrdersTable = ({orders}: { orders: Orders }) => {
    const classes = useStyles();

    return (
        <TableContainer component={Paper}>
            <Table
                id="ordersTable"
                className={classes.table}
                aria-label="customized table"
            >
                <TableHead>
                    <TableRow>
                    <StyledTableCell>User ID</StyledTableCell>
                    <StyledTableCell>Transaction ID</StyledTableCell>
                    <StyledTableCell>Order status</StyledTableCell>
                    <StyledTableCell>Timestamp</StyledTableCell>
                    </TableRow>
                </TableHead>
                <OrdersTableBody orders={orders}/>
            </Table>
        </TableContainer>
    );
}

const OrdersTableBody = ({orders}: { orders: Orders }) => {
    let rows = orders.map((order, index) => orderRow(order, index))
    return (
        <TableBody>
        {rows}
        </TableBody>
    )
}

const orderRow = (order: Order, index: number) => (
    <StyledTableRow key={index}>
        <StyledTableCell>{order.userId}</StyledTableCell>
        <StyledTableCell>{order.transactionId}</StyledTableCell>
        <StyledTableCell>{order.orderStatus}</StyledTableCell>
        <StyledTableCell>{order.timestamp}</StyledTableCell>
    </StyledTableRow>
)

export default OrdersTable;