import React from 'react';
import { WithAuth0Props, withAuth0 } from '@auth0/auth0-react';
import { GetAccessTokenSilently } from '../../common/auth/auth0';
import { Orders } from '../../model/Order';
import OrdersTable from "./OrdersTable";

const ordersUrl = `http://localhost:8080/orders`;

interface OrderViewState {
  orders: Orders
}

class OrdersView extends React.Component<WithAuth0Props, OrderViewState> {
  constructor(props: WithAuth0Props) {
    super(props);
    this.state = { orders: [] };
  }

  componentDidMount() {
    const {
      getAccessTokenSilently,
    } = this.props.auth0;
    this.getOrders(getAccessTokenSilently);
  }

  async getOrders(getAccessTokenSilently: GetAccessTokenSilently) {
    try {
      const accessToken = await getAccessTokenSilently();
      const ordersResponse = await fetch(ordersUrl, {
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
      });
      const orders = await ordersResponse.json();
      this.setState({
        orders: orders
      });
    } catch (e: any) {
      console.log(e.message);
    }
  }

  render() {
    return (
      <div>
        <OrdersTable orders={this.state.orders} />
      </div>
    )
  }
}

export default withAuth0(OrdersView);