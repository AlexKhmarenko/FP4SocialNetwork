import React from "react";
import { Post } from "./Post";
import CircularProgress from "@mui/material/CircularProgress";
import PropTypes from "prop-types";
import { useTransition, animated } from "react-spring";

export const PostsDisplaying = ({ userPosts, isLoading }) => {
    // const transitions = useTransition(userPosts, {
    //     from: { opacity: 0, transform: "translate3d(0%,50%,0)" },
    //     enter: { opacity: 1, transform: "translate3d(0,0,0)" },
    //     leave: { opacity: 0, transform: "translate3d(0,0,,0)" },
    //     keys: post => post.postId
    // });

    if (isLoading) {
        return <CircularProgress sx={{ marginTop: "20%" }}/>;
    } else if (userPosts.length === 0) {
        return <div style={{
            marginTop: "20%", fontWeight: "400",
            lineHeight: "20px",
            fontSize: "22px",
            fontFamily: "'Lato', sans-serif",
        }}>Here will be posts from your friends</div>;
    } else {
        console.log(userPosts);
        return (

            userPosts.map((post) => (
                        <Post key={post.postId} profileImage={post.profileImageByteArray}
                              userName={post.username}
                              name={post.name} text={post.writtenText}
                              photo={post.photoFileByteArray}
                              postComments={post.postCommentsCount}
                              dataTime={post.sentDateTime}
                              postId={post.postId}
                              postLikes={post.likesCount}
                              userIdWhoSendPost={post.userId}
                              reposted={post.isReposted}
                        />
                    ))

    )
        ;
    }
};

PostsDisplaying.propTypes = {
    userPosts: PropTypes.array.isRequired,
    isLoading: PropTypes.bool,
};