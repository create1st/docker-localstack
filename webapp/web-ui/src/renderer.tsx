import ReactDOM from 'react-dom';
import WebAppRoot from "./components/webapp/WebAppRoot";

type ContainerType = Element | DocumentFragment | null;

const renderComponent = (container: ContainerType, render: boolean) => {
    if (!render) return;
    ReactDOM.render(
        <WebAppRoot/>,
        container,
    );
}

const renderUnavailable = (container: ContainerType) => {
    const inlineStyles = {
        fontFamily: '"Open Sans", "Helvetica Neue", Helvetica, Arial, sans-serif',
    };
    ReactDOM.render(
        <span style={inlineStyles}>Webapp unavailable.</span>,
        container,
    );
}

export {renderComponent, renderUnavailable};
