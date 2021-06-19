export type Order = {
  userId: string,
  transactionId: string,
  orderStatus: string,
  timestamp: string
}

export type Orders = Array<Order>