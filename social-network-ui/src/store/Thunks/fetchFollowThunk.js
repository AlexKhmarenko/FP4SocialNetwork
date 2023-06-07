import {userUnfollow} from "../actions";


export function fetchFollow (searchId) {
    return (dispatch, getState) => {

        const state = getState()
        const userId = state.userData.userData.userId
        try {
            fetch(`http://localhost:8080/api/follow`, {
                method: "POST",
                body: JSON.stringify({
                    userFollower: userId,
                    userFollowing: searchId,
                }),
                headers: {"Content-Type": "application/json"}
            })
            .then(r => {
                if (!r.ok) {
                    dispatch(userUnfollow())
                }
            })
        } catch (error) {
            dispatch(userUnfollow())
            console.error("An error occurred:", error);
        }
    }
}