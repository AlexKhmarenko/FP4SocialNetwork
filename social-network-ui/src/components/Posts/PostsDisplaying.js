import React, { useEffect } from "react";
import { Post } from "./Post";
import CircularProgress from "@mui/material/CircularProgress";
import PropTypes from "prop-types";
import {DarkPostDisplayingEmptyPostsText, PostDisplayingEmptyPostsText} from "./PostStyles";
import { useTransition, animated } from "react-spring";
import SockJS from "sockjs-client";
import { apiUrl } from "../../apiConfig";
import {useSelector} from "react-redux";

let stompClient = null;

export const PostsDisplaying = ({ userPosts, isLoading }) => {
    const darkMode = useSelector(state => state.userData.userMode.darkMode);
    const transitions = useTransition(userPosts, {
        from: { opacity: 0, transform: "translate3d(0,50%,0)" },
        enter: { opacity: 1, transform: "translate3d(0%,0%,0)" },
        leave: { opacity: 0, transform: "translate3d(0,50%,0)" },
        keys: post => post.postId,
        config: { duration: 600, delay: 200 },
    });

    useEffect(() => {
        try {
            const socket = new SockJS(`${apiUrl}/websocket`);
            stompClient = Stomp.over(socket);

            return () => {
                if (stompClient && stompClient.connected) {
                    console.info('posts - disconnecting from websocket');
                    try {
                        stompClient.disconnect();
                    } catch (e) {
                        console.warn('posts - failed to disconnect the stomp client', e);
                    }
                } else {
                    console.warn('posts - no websocket to disconnect from');
                }
            };
        } catch (e) {
            console.warn('posts - failed to create the stomp client', e);
        }
    }, []);

    const handleClick = (postId, userId) => {
        if (stompClient) {
            stompClient.send("/app/repost", {}, JSON.stringify({ postId: postId, userId: userId }));
        }
    };

    const handleLikesClick = (postId, userId) => {
        let numUserId = parseInt(userId);
        let numPostId = Number(postId);
        if (stompClient) {
            stompClient.send("/app/post_like", {}, JSON.stringify({ userId: numUserId, postId: numPostId }));
        }
    };

    if (isLoading) {
        return <CircularProgress sx={{ marginTop: "20%" }}/>;
    } else if (userPosts.length === 0) {
        return <div style={darkMode ? DarkPostDisplayingEmptyPostsText : PostDisplayingEmptyPostsText}>Here will be posts from your friends</div>;
    } else {
        return (
            <>
                {transitions((style, item) => (
                    <animated.div style={style} key={item.postId}>
                        <Post
                            profileImage={item.profileImageLink}
                            userName={item.username}
                            name={item.name}
                            text={item.writtenText}
                            photo={item.photoFileLink}
                            postComments={item.postCommentsCount}
                            dataTime={item.sentDateTime}
                            postId={item.postId}
                            postLikes={item.likesCount}
                            userIdWhoSendPost={item.userId}
                            reposted={item.isReposted}
                            repostsCount={item.repostsCount}
                            sendEventToWebsocket={handleClick}
                            viewCount={item.viewCount}
                            handleLikesClick={handleLikesClick}
                        />
                    </animated.div>
                ))}
            </>
        );
    }
};

PostsDisplaying.propTypes = {
    userPosts: PropTypes.array.isRequired,
    isLoading: PropTypes.bool,
};