import { combineReducers } from "redux";
import { stepModalReducer } from "./Reducers/stepModalReducer";
import { userDataLoginReducer } from "./Reducers/userDataLoginReducer";
import { loginPageReducer } from "./Reducers/loginPageReducer";
import { userTokenSaveReducer } from "./Reducers/userTokenReducer";
import {usersSearchReducer} from "./Reducers/usersSearchReducer";
import { userDataReducer } from "./Reducers/userDataReducer";
import { PostReducer } from "./Reducers/PostsReducer";
import {PageDisplaing} from "./Reducers/PageDisplaing"
import {likeReducer} from  "./Reducers/likeReducer"

const rootReducer = combineReducers({
    loginUserData: userDataLoginReducer,
    modal: loginPageReducer,
    stepModal: stepModalReducer,
    saveUserToken: userTokenSaveReducer,
    usersSearch: usersSearchReducer,
    userData: userDataReducer,
    Posts: PostReducer,
    pageCount:PageDisplaing,
    isLikedPost:likeReducer
});

export default rootReducer;