
import {userUnfollow} from "../actions";


export function fetchUnfollow () {
    return (dispatch, getState) => {

        const state = getState()
        const searchId = state.userData.searchData.userId
        const userId = state.userData.userData.userId

        fetch(`http://localhost:8080/api/unfollow`, {
            method: "POST",
            body: JSON.stringify({
                userUnfollowed: userId,
                userUnfollowing: searchId,
            }),
            headers: { "Content-Type": "application/json" }
        })
            .then(r => {
                if (r.ok) dispatch(userUnfollow())
            })
        // if (response.ok) {
        //     dispatch(userUnfollow())
        // }
    }
}