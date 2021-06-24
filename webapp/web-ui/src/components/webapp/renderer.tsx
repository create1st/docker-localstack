import { Auth0Provider } from '@auth0/auth0-react';
import ReactDOM from 'react-dom';
import providerConfig from '../../common/auth/config';
import WebApp from './WebApp';

type ContainerType = Element | DocumentFragment | null;

const renderComponent = (container: ContainerType, render: boolean) => {
  if (!render) return;
  ReactDOM.render(
    <Auth0Provider {...providerConfig}>
      <WebApp />
    </Auth0Provider>,
    container,
  );
};

const renderUnavailable = (container: ContainerType) => {
  const inlineStyles = {
    fontFamily: '"Open Sans", "Helvetica Neue", Helvetica, Arial, sans-serif',
  };
  ReactDOM.render(
    <span style={inlineStyles}>Webapp unavailable.</span>,
    container,
  );

};

export { renderComponent, renderUnavailable };
