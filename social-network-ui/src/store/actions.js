import { UPDATE_USER_DATA_USERNAME, UPDATE_USER_PASSWORD, UPDATE_REMEMBER_ME_ACTION, SAVE_USER_TOKEN } from "./types";

export const setUserName = (userData) => ({
    type: UPDATE_USER_DATA_USERNAME,
    payload: userData,
});

export const setUserPassword = (userData) => ({
    type: UPDATE_USER_PASSWORD,
    payload: userData,
});

export const setRememberMeAction = () => ({
    type: UPDATE_REMEMBER_ME_ACTION,
});

export const setUserToken = (userToken) => ({
    type: SAVE_USER_TOKEN,
    payload: { userToken },
});

