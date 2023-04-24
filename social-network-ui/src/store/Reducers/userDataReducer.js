import { SET_USER_DATA } from "../types";

const initialState = {
    userData: {}
};

export function userDataReducer(state = initialState, action) {
    switch (action.type) {
        case SET_USER_DATA:
            return {
                ...state,
                userData: action.payload,
            };
        default:
            return state;
    }
};