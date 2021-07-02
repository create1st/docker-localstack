import React from "react";
import {BrowserRouter, Switch} from "react-router-dom";
import ProtectedRoute from "../auth/ProtectedRoute";
import WebApp from "./WebApp";
import Auth0RouterProvider from "../auth/Auth0RouterProvider";

const WebAppRoot = () => (
    <BrowserRouter>
        <Auth0RouterProvider>
            <Switch>
                <ProtectedRoute exact path="/" component={WebApp}/>
                {/*<Route path="/login" component={Login}/>*/}
            </Switch>
        </Auth0RouterProvider>
    </BrowserRouter>
)

export default WebAppRoot