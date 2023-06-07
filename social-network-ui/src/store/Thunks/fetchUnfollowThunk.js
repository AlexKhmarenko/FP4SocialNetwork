
import {userFollow} from "../actions";


export function fetchUnfollow (searchId) {
    return (dispatch, getState) => {

        const state = getState()
        const userId = state.userData.userData.userId

        try {
            fetch(`http://localhost:8080/api/unfollow`, {
                method: "POST",
                body: JSON.stringify({
                    userUnfollowed: userId,
                    userUnfollowing: searchId,
                }),
                headers: {"Content-Type": "application/json"}
            })
            .then(r => {
                if (!r.ok) {
                    dispatch(userFollow())
                }
            })
        } catch (error) {
            console.error("An error occurred:", error);
        }

    }
}