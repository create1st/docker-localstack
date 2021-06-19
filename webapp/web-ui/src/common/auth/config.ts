import configJson from '../../config/auth_config.json'

// Check for auth_config.json login details
// https://auth0.com/docs/quickstart/spa/vanillajs/01-login
const getConfig = () => {
  const audience =
    configJson.audience && configJson.audience !== "YOUR_API_IDENTIFIER"
      ? configJson.audience
      : null;

  return {
    domain: configJson.domain,
    clientId: configJson.clientId,
    scope: configJson.scope,
    ...(audience ? { audience } : null),
  };
}

const config = getConfig();

const providerConfig = {
  domain: config.domain,
  clientId: config.clientId,
  scope: config.scope,
  // response_type: 'id_token',
  // response_mode: 'fragment',
  ...(config.audience ? { audience: config.audience } : null),
  redirectUri: window.location.origin,
  // onRedirectCallback,
      // redirectUri={window.location.origin}
};

export default providerConfig;