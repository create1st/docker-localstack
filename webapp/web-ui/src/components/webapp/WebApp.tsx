import {useAuth0} from '@auth0/auth0-react';
import React from 'react';
import OrdersView from '../orders/OrdersView';
import {CircularProgress, Container, CssBaseline} from "@material-ui/core";
import WebAppBar from "./WebAppBar";
import withRoot from "../../withRoot";

const WebApp = () => {
    const {
        isAuthenticated,
        isLoading,
        loginWithRedirect,
        user,
        logout,
    } = useAuth0();
    const logoutAction = () => logout({returnTo: window.location.origin})
    const loginButtonAction = isAuthenticated ? logoutAction : loginWithRedirect;
    const loginButtonText = isAuthenticated ? "Logout" : "Login";
    const userName = user ? user.name!! : "guest";

    return (
        <Container>
            <WebAppBar
                userName={userName}
                loginButtonText={loginButtonText}
                loginButtonAction={loginButtonAction}
            />
            <Container>
                {isLoading && <CircularProgress/>}
                {isAuthenticated && <OrdersView/>}
            </Container>
        </Container>
    )
}

export default withRoot(WebApp);