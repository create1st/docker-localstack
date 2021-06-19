import { Order, Orders } from "../../model/Order";

const OrdersTable = ({ orders }: { orders: Orders}) => (
  <div>
    <table id="ordersTable">
      <thead>
        <tr>
          <th>User ID</th>
          <th>Transaction ID</th>
          <th>Order status</th>
          <th>Timestamp</th>
        </tr>
      </thead>
      <OrdersTableBody orders={orders}/>      
    </table>
  </div>
)

const OrdersTableBody = ({ orders }: { orders: Orders}) => {
  let rows = (orders.length > 0)
    ? orders.map((order,index) => orderRow(order, index))
    : noResultsRow()
  return (
    <tbody>
      {rows}
    </tbody>
  )
}

const orderRow = (order: Order, index: number) => (
  <tr key={index}>
    <td>{order.userId}</td>
    <td>{order.transactionId}</td>
    <td>{order.orderStatus}</td>
    <td>{order.timestamp}</td>
  </tr>
)

const noResultsRow = () => (
  <tr>
    <td className="nodata" colSpan={4}>Nothing found to display.</td>
  </tr>
)

export default OrdersTable;