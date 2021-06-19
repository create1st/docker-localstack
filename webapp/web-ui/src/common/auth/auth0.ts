import { LogoutOptions, RedirectLoginOptions, GetTokenSilentlyOptions } from '@auth0/auth0-spa-js';

export type Logout = (options?: LogoutOptions) => void;
export type LoginWithRedirect = (options?: RedirectLoginOptions) => Promise<void>;
export type GetAccessTokenSilently = (options?: GetTokenSilentlyOptions) => Promise<string>;