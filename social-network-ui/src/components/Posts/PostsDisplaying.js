import React from "react";
import { Post } from "./Post";
import CircularProgress from "@mui/material/CircularProgress";
import PropTypes from "prop-types";

export const PostsDisplaying = ({userPosts, isLoading}) => {
    console.log(userPosts)
    if (isLoading) {
        return <CircularProgress sx={{ marginTop: "20%" }}/>
    } else if (userPosts.length === 0) {
        return <div style={{ marginTop: "20%",   fontWeight: "400",
            lineHeight: "20px",
            fontSize: "22px",
            fontFamily: "'Lato', sans-serif",}}>Here will be posts from your friends</div>
    } else {
        console.log(userPosts)
        return (
            <div>
                {userPosts.map((post) => (
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
                ))}
            </div>
        );
    }
};

PostsDisplaying.propTypes = {
    userPosts: PropTypes.array.isRequired,
    isLoading:PropTypes.bool,
 }