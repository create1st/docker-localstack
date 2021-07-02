import axios from "axios";
import {renderHook} from "@testing-library/react-hooks";
import useGetOrders, {Status} from "./useGetOrders";
import {useAuth0} from "@auth0/auth0-react";
import testData from "../tests/testData";
import {Auth0ContextInterface} from "@auth0/auth0-react/dist/auth0-context";
import {Orders} from "../model/Order";

jest.mock("@auth0/auth0-react")
jest.mock("axios");

const mockedUseAuth0 = useAuth0 as jest.Mock;
const mockedAxiosGet = axios.get as jest.Mock;

const mockedGetAccessTokenSilently = jest.fn()
const auth0: Auth0ContextInterface<any> = {
    buildAuthorizeUrl: jest.fn(),
    buildLogoutUrl: jest.fn(),
    handleRedirectCallback: jest.fn(),
    isAuthenticated: true,
    user: testData.user,
    logout: jest.fn(),
    loginWithRedirect: jest.fn(),
    getAccessTokenWithPopup: jest.fn(),
    getAccessTokenSilently: mockedGetAccessTokenSilently,
    getIdTokenClaims: jest.fn(),
    loginWithPopup: jest.fn(),
    isLoading: false
}

describe('useGetOrders', () => {
    beforeEach(() => {
        mockedUseAuth0.mockReturnValue(auth0);
    });

    it('should set in-progress state', async () => {
        givenUseAuth0WillReturnAccessToken()
        givenAxiosGetWillReturnOrders([]);

        const {result} = renderHook(() => useGetOrders());

        const [ordersViewState] = result.current;
        expect(ordersViewState).toEqual({
            status: Status.InProgress,
            message: undefined,
            orders: []
        })
        expect(mockedAxiosGet).not.toBeCalled();
    })

    it('should set orders in ordersViewState', async () => {
        givenUseAuth0WillReturnAccessToken()
        givenAxiosGetWillReturnOrders([testData.order]);

        const {result, waitForNextUpdate} = renderHook(() => useGetOrders());
        await waitForNextUpdate();

        const [ordersViewState] = result.current;
        expect(ordersViewState).toEqual({
            status: Status.Success,
            message: undefined,
            orders: [testData.order]
        })
        expectAxiosGetOrdersCallToBeExecuted();
    })

    it('should set error in ordersViewState', async () => {
        givenUseAuth0WillReturnAccessToken()
        givenAxiosGetWillReturnError();

        const {result, waitForNextUpdate} = renderHook(() => useGetOrders());
        await waitForNextUpdate();

        const [ordersViewState] = result.current;
        expect(ordersViewState).toEqual({
            status: Status.Failed,
            message: testData.errorMessage,
            orders: []
        })
        expectAxiosGetOrdersCallToBeExecuted();
    })

    it('should refresh orders in ordersViewState', async () => {
        givenUseAuth0WillReturnAccessToken()
        givenAxiosGetWillReturnDongleUsersAfterRefresh([testData.order]);

        const {result, waitForNextUpdate} = renderHook(() => useGetOrders());
        await waitForNextUpdate();
        const [, refresh] = result.current;
        refresh();
        await waitForNextUpdate();

        const [ordersViewState] = result.current;
        expect(ordersViewState).toEqual({
            status: Status.Success,
            message: undefined,
            orders: [testData.order]
        })
        expectAxiosGetOrdersCallToBeExecuted();
    })

    const givenUseAuth0WillReturnAccessToken = () => {
        mockedGetAccessTokenSilently.mockResolvedValue(testData.jwtToken)
    }

    const givenAxiosGetWillReturnOrders = (orders: Orders) => {
        mockedAxiosGet.mockResolvedValue({data: orders});
    }

    const givenAxiosGetWillReturnDongleUsersAfterRefresh = (orders: Orders) => {
        mockedAxiosGet
            .mockResolvedValueOnce({data: []})
            .mockResolvedValueOnce({data: orders});
    }

    const givenAxiosGetWillReturnError = () => {
        mockedAxiosGet.mockRejectedValue(testData.axiosError);
    }

    const expectAxiosGetOrdersCallToBeExecuted = () => expect(mockedAxiosGet)
        .toBeCalledWith(`${process.env.REACT_APP_REST_ENDPOINT}/orders`, testData.httpOptions);
})