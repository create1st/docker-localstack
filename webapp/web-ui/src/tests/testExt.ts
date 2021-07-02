import {WaitForNextUpdate} from "@testing-library/react-hooks/src/types/index";

const expectNoMoreUpdates = async (waitForNextUpdate: WaitForNextUpdate) => {
    const timoutError = await waitForNextUpdate()
        .catch(error => error);
    if (!timoutError) {
        throw Error("No more actions expected");
    }
}

export {expectNoMoreUpdates}