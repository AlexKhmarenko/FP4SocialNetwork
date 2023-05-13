import { combineReducers } from "redux";
import { stepModalReducer } from "./Reducers/stepModalReducer";
import { userDataReducer } from "./Reducers/userDataReducer";
import {loginPageReducer} from "./Reducers/loginPageReducer";
import { userTokenSaveReducer } from "./Reducers/userTokenReducer";
import { forgotPasswordReducer } from "./Reducers/forgotPasswordReducer";

const rootReducer = combineReducers({
    loginUserData: userDataReducer,
    modal: loginPageReducer,
    stepModal: stepModalReducer,
    saveUserToken: userTokenSaveReducer,
    forgot: forgotPasswordReducer
});

export default rootReducer;