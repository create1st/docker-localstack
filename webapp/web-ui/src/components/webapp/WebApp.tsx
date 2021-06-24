import {useAuth0} from '@auth0/auth0-react';
import React from 'react';
import OrdersView from '../orders/OrdersView';
import {AppBar, Button, CircularProgress, Container, Toolbar} from "@material-ui/core";
import MenuIcon from '@material-ui/icons/Menu';
import AppBarIconButton from "../ui/AppBarIconButton";
import AppBarTypography from "../ui/AppBarTypography";

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

const WebAppBar = ({userName, loginButtonText, loginButtonAction} : {userName:String, loginButtonText: String, loginButtonAction:() => void}) => {
    return (<AppBar position="static">
        <Toolbar>
            <AppBarIconButton edge="start" color="inherit" aria-label="menu">
                <MenuIcon/>
            </AppBarIconButton>
            <AppBarTypography variant="h6">
                Hello {userName}
            </AppBarTypography>
            <Button color="inherit" onClick={loginButtonAction}>
                {loginButtonText}
            </Button>
        </Toolbar>
    </AppBar>)
}

export default WebApp;