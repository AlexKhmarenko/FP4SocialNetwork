const initialState = {
    messages: [],
};

export const messageReducer = (state = initialState, action) => {
    switch (action.type) {
        case "SET_MESSAGES":
            return {
                ...state,
                messages: [...state.messages, action.payload],
            };
        case "CLEAR_MESSAGES":
            return {
                ...state,
                messages: [],
            };
        default:
            return state;
    }
};