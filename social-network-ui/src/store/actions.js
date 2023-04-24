import { SET_USER_DATA } from "./types";

export const setUserData = (userData) => ({
    type: SET_USER_DATA,
    payload: userData,
});