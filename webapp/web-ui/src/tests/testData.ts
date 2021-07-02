import {Order} from "../model/Order";

const JWT_TOKEN = "JWT_TOKEN"

const httpOptionsHeaders = {
    'Authorization': `Bearer ${JWT_TOKEN}`
}

const httpOptions = {
    headers: httpOptionsHeaders
}

const user = {
    email: "test@me.com",
    email_verified: true,
    sub: "google-oauth2|12345678901234",
}

const ERROR_MESSAGE = 'Async error';

const axiosError = new Error(ERROR_MESSAGE);

const order: Order = {
    orderStatus: "new",
    timestamp: "123456799",
    transactionId: "transactionId1",
    userId: "userId"
}

const testData = {
    user,
    jwtToken: JWT_TOKEN,
    httpOptions,
    httpOptionsHeaders,
    errorMessage: ERROR_MESSAGE,
    axiosError,

    order,
}

export default testData