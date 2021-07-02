import providerConfig from "../../common/auth/config";
import {Auth0Provider} from "@auth0/auth0-react";
import React from "react";
import {RouteComponentProps, useHistory, withRouter} from "react-router-dom";

const Auth0RouterProvider: React.FC<RouteComponentProps> = ({children}) => {
    const history = useHistory();
    const onRedirectCallback = (appState: any) => {
        history.push(appState?.returnTo || window.location.pathname);
    };
    return (
        <Auth0Provider onRedirectCallback={onRedirectCallback} {...providerConfig}>
            {children}
        </Auth0Provider>
    )
}

export default withRouter(Auth0RouterProvider)