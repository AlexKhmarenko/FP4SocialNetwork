import React, {useState, useCallback, useEffect, useContext, useRef} from "react";
import {useDispatch, useSelector} from "react-redux";
import {Button, Box} from "@mui/material";
import {CloudUploadOutlined} from "@mui/icons-material";
import {Formik, Form, Field} from "formik";
import * as Yup from "yup";

import {
    fetchData,
    fetchPostsByUserId,
    sendPost,
    setPageZero,
    setUserId,
    setUserPostsClear
} from "../store/actions";
import {SidebarLogOutButton} from "../components/NavigationComponents/NavigationStyles";
import {CapybaraSvgPhoto} from "../components/SvgIcons/CapybaraSvgPhoto";
import {
    NameOfUser,
    SvgWrapper,
    WrittenPostWrapper,
    HomeScreenWrapper,
    PostWrapper,
    SendingPostButtonsContainer, imgStyles, textWrapper
} from "./pagesStyles/HomeScreenStyles";
import {PostsDisplaying} from "../components/Posts/PostsDisplaying";
import {SendPostInput} from "../components/Posts/SendPostInput";
import {CharactersTextWrapper, PostImgWrapper, PostsWrapper, SendPostField} from "../components/Posts/PostStyles";
import {decodeToken} from "../components/Posts/decodeToken";
import {apiUrl} from "../apiConfig";

import {ScrollContext} from "../components/Layout.js";


import SockJS from "sockjs-client";

export function HomeScreen() {
    const userData = useSelector(state => state.userData.userData);
    const [postText, setPostText] = useState("");
    const [postImage, setPostImage] = useState(null);
    const handleParentScroll = useContext(ScrollContext);
    const userId = useSelector(state => state.userData.userData.userId);
    const [isLoading, setIsLoading] = useState(false);
    const userPosts = useSelector(state => state.Posts.posts);
    const dispatch = useDispatch();
    const [posts, setPosts] = useState([]);
    const [isFetchingPosts, setIsFetchingPosts] = useState(false);
    const [allPostsLoaded, setAllPostsLoaded] = useState(false);
    const socketRef = useRef(null);

    const handlePostImageChange = useCallback((event) => {
        const file = event.target.files[0];
        setPostImage(file);
    }, []);

    // useEffect(() => {
    //     // Создаем подключение при загрузке компонента
    //     socketRef.current = new WebSocket(`ws://localhost:8080/websocket`);
    //
    //     // Закрываем подключение при размонтировании компонента
    //     return () => {
    //         if (socketRef.current) {
    //             socketRef.current.close();
    //         }
    //     };
    // }, []);
    const socket = new SockJS("http://localhost:8080/websocket"); // Создание экземпляра SockJS и передача адреса сервера
    console.log("connected")
    useEffect(() => {
        // Создаем подключение при загрузке компонента
        // const socket = new SockJS("http://localhost:8080/websocket"); // Создание экземпляра SockJS и передача адреса сервера

        // Закрываем подключение при размонтировании компонента

        return () => {
            socket.send("/app/post", {}, JSON.stringify({userId: userId}));

            if (socket) {
                socket.close();
            }
        };
    }, []);

    const handleClick = () => {
        if (socket) {
            console.log("sending")
            // Отправляем данные на сервер
            socket.send("/app/post", {}, JSON.stringify({userId: userId}));
        }
    };
    // const handleClick = () => {
    //     if (socketRef.current) {
    //         // Отправляем данные на сервер
    //         socketRef.current.send(JSON.stringify({userId: userId}));
    //     }
    // };


    useEffect(() => {
        const fetchPosts = async () => {
            try {
                setIsLoading(true);
                const decodedToken = decodeToken();
                if (decodedToken) {
                    const userId = decodedToken.sub;
                    dispatch(setUserId(userId));
                    dispatch(fetchData(userId));
                    console.log(userId, "userIdHomeScreen")
                    // it's initial loading, we're always starting from the first (0) page
                    await dispatch(fetchPostsByUserId(userId, 0));
                }
            } catch (error) {
                console.error(error);
            } finally {
                setIsLoading(false);
            }
        };
        const initHomeScreen = () => {
            dispatch(setUserPostsClear([]));
            dispatch(setPageZero());
            fetchPosts();
        };
        initHomeScreen();
    }, []);

    const handlePostSubmit = async (values, setSubmitting) => {
        if (values.postText.trim() !== "" || postImage) {
            setSubmitting(true);

            let photoFileByteArray = [];
            if (postImage) {
                const reader = new FileReader();
                reader.readAsArrayBuffer(postImage);
                reader.onloadend = async () => {
                    const imageArrayBuffer = new Uint8Array(reader.result);
                    photoFileByteArray = Array.from(imageArrayBuffer);

                    const postObject = {
                        writtenText: values.postText,
                        photoFileByteArray: photoFileByteArray,
                        userId: userId
                    };

                    await dispatch(sendPost(postObject, setSubmitting));
                };
            } else {
                console.log(values.postText);
                const postObject = {
                    writtenText: values.postText,
                    photoFileByteArray: [],
                    userId: userId
                };
                await dispatch(sendPost(postObject, setSubmitting));
            }

            setPostImage(null);
            setPostText("");
        }
    };

    const handleScroll = async (event) => {
        if (isFetchingPosts || allPostsLoaded) {
            return;
        }
        setIsFetchingPosts(true);
        try {
            const {scrollTop, clientHeight, scrollHeight} = event.currentTarget;
            if (scrollHeight - scrollTop <= clientHeight + 20) {
                let newPosts = [...userPosts];
                if (newPosts.length > 0) {
                    setPosts([...posts, ...newPosts]);
                } else {
                    setAllPostsLoaded(true);
                }
            }
            handleParentScroll(scrollTop, clientHeight, scrollHeight);
        } finally {
            setIsFetchingPosts(false);
        }
    };

    return (
        <div onScroll={handleScroll}>
            <Formik
                initialValues={{postText: ""}}
                validationSchema={
                    Yup.object({
                        postText: Yup.string().max(280, "Must be 280 characters or less"),
                    })}
                onSubmit={(values, {resetForm, setSubmitting}) => {
                    setSubmitting(true);
                    handlePostSubmit(values, setSubmitting);
                    resetForm();
                }}
            >
                {({values, errors, touched, isSubmitting}) => (
                    <Form>
                        <div style={HomeScreenWrapper}>
                            <div style={PostWrapper}>
                                <div style={SvgWrapper}>
                                    {userData.image ? <img src={userData.image}
                                                           style={imgStyles}
                                                           alt=""/> : <CapybaraSvgPhoto/>}
                                </div>
                                <div style={WrittenPostWrapper}>
                                    <div
                                        style={textWrapper}>
                                        <h2 style={NameOfUser}>{userData.name}</h2>
                                        <h2 style={{
                                            ...NameOfUser,
                                            color: "grey",
                                            marginLeft: "10px"
                                        }}>@ {userData.userName}</h2>

                                    </div>
                                    <Field
                                        values={postText}
                                        component={SendPostInput}
                                        name="postText"
                                        className={errors.postText && touched.postText ? "error" : ""}
                                        style={SendPostField}
                                        id="postText"
                                        placeholder="What's happening?"
                                    />
                                    <div style={CharactersTextWrapper}>
                                        {
                                            280 - values.postText.length >= 0 ?
                                                (280 - values.postText.length + " characters") : ("maximum number of characters 280")
                                        }
                                    </div>
                                    <Box sx={PostImgWrapper}>
                                        {postImage && (
                                            <img
                                                src={URL.createObjectURL(postImage)}
                                                alt="Post Image"
                                                style={{maxWidth: "100%", height: "auto"}}
                                            />
                                        )}
                                        <input
                                            type="file"
                                            accept="image/*"
                                            id="post-image-input"
                                            onChange={handlePostImageChange}
                                            style={{display: "none"}}
                                        />
                                        <div style={SendingPostButtonsContainer}>
                                            <label htmlFor="post-image-input"
                                                   style={{height: "30px", borderRadius: "20px",}}>
                                                <Button
                                                    component="span"
                                                    variant="contained"
                                                    color="primary"
                                                    sx={{...SidebarLogOutButton, marginTop: 0}}
                                                    startIcon={<CloudUploadOutlined/>}
                                                    disabled={!!postImage}
                                                >image</Button>
                                            </label>
                                            <label htmlFor="post-image-input"
                                                   style={{height: "30px", borderRadius: "20px",}}>
                                                <Button
                                                    type="submit"
                                                    variant="contained"
                                                    sx={{...SidebarLogOutButton, marginTop: 0, width: "100px"}}
                                                    fullWidth={true}
                                                    disabled={isSubmitting}
                                                    onClick={handleClick}
                                                >
                                                    {isSubmitting ? "Posting..." : "Post"}
                                                </Button>
                                            </label>

                                        </div>
                                    </Box>
                                </div>
                            </div>
                        </div>
                    </Form>
                )}
            </Formik>
            <div style={PostsWrapper}>
                <PostsDisplaying userPosts={userPosts} isLoading={isLoading}/>

            </div>
        </div>
    );
}



