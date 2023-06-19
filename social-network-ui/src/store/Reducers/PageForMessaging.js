const initialState = {
    page: 0
};

export function PageForMessaging(state = initialState, action) {
    switch (action.type) {
        case 'SET_PAGE_FOR_MESSAGING':
            return {
                ...state,
                page: state.page + 1,
            };
        case "SET_PAGE_ZERO_FOR_MESSAGING":
            return {
                ...state,
                page: 0,
            };
        default:
            return state;
    }
};
