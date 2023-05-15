import { combineReducers } from "redux";
import { stepModalReducer } from "./Reducers/stepModalReducer";
import { userDataLoginReducer } from "./Reducers/userDataLoginReducer";
import { loginPageReducer } from "./Reducers/loginPageReducer";
import { userTokenSaveReducer } from "./Reducers/userTokenReducer";
import { userDataReducer } from "./Reducers/userDataReducer";
import { PostReducer } from "./Reducers/PostsReducer";

const rootReducer = combineReducers({
    loginUserData: userDataLoginReducer,
    modal: loginPageReducer,
    stepModal: stepModalReducer,
    saveUserToken: userTokenSaveReducer,
    userData: userDataReducer,
    Posts: PostReducer
});

export default rootReducer;