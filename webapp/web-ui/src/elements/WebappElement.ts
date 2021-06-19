import { getBoolean } from '../common/jsUtils';
import { renderComponent, renderUnavailable } from '../components/webapp/renderer';

export default class WebappElement extends HTMLElement {
    appContainer: HTMLDivElement;

    constructor() {
        super();
        this.appContainer = document.createElement('div');
        this.appContainer.setAttribute('id', 'webapp');
        this.appendChild(this.appContainer);
    }

    connectedCallback() {
        const renderAttribute = this.getAttribute('render');
        if (!renderAttribute) {
            renderUnavailable(this.appContainer);
        } else {
            const render = getBoolean(renderAttribute);
            renderComponent(this.appContainer, render);
        }
    }
}
