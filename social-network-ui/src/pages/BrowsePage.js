import React, {useEffect} from 'react';
import {useDispatch, useSelector} from "react-redux";
import {Profile} from "../components/Profile/Profile/Profile";
import {setSearchData, userFollow} from "../store/actions";
import {fetchFollow} from "../store/Thunks/fetchFollowThunk";

export function BrowsePage () {

    const searchData = useSelector(state => state.userData.searchData)
    const searchId = useSelector(state => state.userData.searchData.userId);
    const userId = useSelector(state => state.userData.userData.userId);
    const dispatch = useDispatch()

    useEffect(() => {
        const fetchData = async () => {

            const response = await fetch(`http://localhost:8080/profile/${searchId}`);
            const userData = await response.json();
            dispatch(setSearchData(userData));
        };
        if (searchId) {
            fetchData();
        }
    }, [searchId]);

    // const fetchFollow = async () => {
    //
    //         const response = await fetch(`http://localhost:8080/api/follow`, {
    //             method: "POST",
    //             body: JSON.stringify({
    //                 userFollower: userId,
    //                 userFollowing: searchId,
    //             }),
    //             headers: { "Content-Type": "application/json" }
    //         })
    //         const userIsFollow = await response.json();
    //         console.log(userIsFollow)
    //         dispatch(userFollow())
    //
    // };

    return (
        <Profile buttonText="Subscribe"
                 image={searchData.image}
                 name={searchData.name}
                 userName={searchData.userName}
                 date={searchData.date}
                 address={searchData.address}
                 followings={searchData.followings}
                 followers={searchData.followers}
                 userId={searchData.userId}
                 btnClick={() => dispatch(fetchFollow())}
        />
    )
}