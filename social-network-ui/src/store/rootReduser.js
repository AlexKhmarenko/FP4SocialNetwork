import { combineReducers } from "redux";
import { stepModalReducer } from "./Reducers/stepModalReducer";
import { userDataReducer } from "./Reducers/userDataReducer";

const rootReducer = combineReducers({
    loginUserData: userDataReducer,
    stepModal: stepModalReducer
});

export default rootReducer;