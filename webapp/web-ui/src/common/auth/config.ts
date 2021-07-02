const providerConfig = {
    domain: process.env.REACT_APP_AUTH0_DOMAIN!,
    clientId: process.env.REACT_APP_AUTH0_CLIENT_ID!,
    scope: process.env.REACT_APP_AUTH0_SCOPE!,
    // response_type: 'id_token',
    // response_mode: 'fragment',
    ...(process.env.REACT_APP_AUTH0_AUDIENCE ? {audience: process.env.REACT_APP_AUTH0_AUDIENCE} : null),
    redirectUri: window.location.origin
}

export default providerConfig;