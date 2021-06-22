import {useAuth0} from '@auth0/auth0-react';
import {User} from '@auth0/auth0-spa-js';
import React from 'react';
import {LoginWithRedirect, Logout} from '../../common/auth/auth0';
import OrdersView from '../orders/OrdersView';

const Webapp = () => {
    const {
        isAuthenticated,
        isLoading,
        loginWithRedirect,
        user,
        logout,
    } = useAuth0();

    if (isLoading) return renderLoading()
    if (!isAuthenticated) return renderLogin(loginWithRedirect)
    return renderComponent(user!, logout)
}

const renderLoading = () => (
    <div>Loading...</div>
)

const renderLogin = (loginWithRedirect: LoginWithRedirect) => (
    <button onClick={loginWithRedirect}>Log in</button>
)

const renderComponent = (user: User, logout: Logout) => (
    <div>
        <OrdersView/>
        <div>Hello {user ? user.name : "guest"}</div>
        <button onClick={() => logout({returnTo: window.location.origin})}>Log out</button>
    </div>
)

export default Webapp;