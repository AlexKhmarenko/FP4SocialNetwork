import React from 'react';
import {useDispatch, useSelector} from "react-redux";
import {Profile} from "../components/Profile/Profile/Profile";

export function ProfilePage () {

    const userData = useSelector(state => state.userData.userData)
    const dispatch = useDispatch();



    return (
        <Profile buttonText="Change profile"
                 buttonColor="#ffffff"
                 textColor="#000000"
                 image={userData.image}
                 name={userData.name}
                 userName={userData.userName}
                 date={userData.date}
                 address={userData.address}
                 followings={userData.followings}
                 followers={userData.followers}
                 userId={userData.userId}
        />
    )
}

