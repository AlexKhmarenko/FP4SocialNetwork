import React, {useEffect} from 'react';
import {TextField, Autocomplete, Typography, Grid, Avatar} from "@mui/material";
import {UserSearchTextField} from "../NavigationStyles";
import {useDispatch, useSelector} from "react-redux";
import {DeleteUsersSuccess, setSearchData, setSearchId} from "../../../store/actions";

export const InputSearch = ({ ...props }) => {

    const users = useSelector(state => state.usersSearch.users)
    const userId = useSelector(state => state.userData.searchData.userId);
    const dispatch = useDispatch()

    useEffect(() => {
        const fetchData = async () => {

            const response = await fetch(`http://localhost:8080/profile/${userId}`);
            const userData = await response.json();
            dispatch(setSearchData(userData));
        };
        if (userId) {
            fetchData();
        }
    }, [userId]);


    return (
            <Autocomplete
                getOptionLabel={(option) => option.username}
                filterOptions={(x) => x}
                // options={users.map((option) => option)}
                options={users}
                autoComplete
                includeInputInList
                filterSelectedOptions
                noOptionsText="User not found"
                renderInput={(params) => (
                    <TextField{...props} sx={UserSearchTextField} {...params} label="Search in Capitweet"
                              onBlur={(ev) => {
                                  ev.preventDefault()
                                  dispatch(DeleteUsersSuccess())
                              }}
                    />
                )}
                    renderOption={(props, option) => {

                    return (
                        <li {...props} key={option.userId}>
                            <Grid container alignItems="center" onClick={() => {
                                dispatch(setSearchId(option.userId))
                                ////////////////// добавить переход на страничку пользователя   ////////////////////////////
                            }}>
                                <Grid item sx={{ display: 'flex', width: 44 }}>
                                    <Avatar alt={option.username} src={option.profileImageUrl}/>
                                </Grid>
                                <Grid item sx={{ width: 'calc(100% - 44px)', wordWrap: 'break-word' }}>
                                    <Typography variant="body2" color="text.secondary">
                                        {option.username}
                                    </Typography>
                                </Grid>
                            </Grid>
                        </li>
                    );
                }}
            />
    )
}
