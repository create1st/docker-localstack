import { withAuth0, WithAuth0Props } from '@auth0/auth0-react';
import { User } from '@auth0/auth0-spa-js';
import React from 'react';
import { LoginWithRedirect, Logout } from '../../common/auth/auth0';
import OrdersView from '../orders/OrdersView';

// For Auth0 hooks see example on
// https://github.com/auth0/auth0-react#use-with-a-class-component
class Webapp extends React.Component<WithAuth0Props> {
  render() {
    const {
      isAuthenticated,
      isLoading,
      loginWithRedirect,
      user,
      logout,
    } = this.props.auth0;

    if (isLoading) return this.renderLoading()
    if (!isAuthenticated) return this.renderLogin(loginWithRedirect)
    return this.renderComponent(user!, logout)
  }

  renderLoading() {
    return (
      <div>Loading...</div>
    )
  }

  renderLogin(loginWithRedirect: LoginWithRedirect) {
    return (
      <button onClick={loginWithRedirect}>Log in</button>
    )
  }

  renderComponent(user: User, logout: Logout) {
    return (
      <div>
        <OrdersView />
        <div>Hello {user ? user.name : "guest"}</div>
        <button onClick={() => logout({ returnTo: window.location.origin })}>Log out</button>
      </div>
    )
  }
}

export default withAuth0(Webapp);