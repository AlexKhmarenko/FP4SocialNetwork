import {SAVE_USER_TOKEN} from "../types"

const initialState = {
        userToken: false,
};

export function userTokenSaveReducer(state = initialState, action) {
    switch (action.type) {
        case SAVE_USER_TOKEN:
            return {
                userToken: action.payload.userToken,
            };
        default:
            return state;
    }
};