import React from "react";
import { Post } from "./Post";
import CircularProgress from "@mui/material/CircularProgress";
import PropTypes from "prop-types";

export const PostsDisplaying = ({userPosts}) => {
    return (
        userPosts.length === 0 ? (<CircularProgress sx={{ marginTop: "20%" }}/>) : (<div style={{ height: "100vh" }}>
            {userPosts.map((post) => (
                <Post key={post.postId} userName={post.username}
                      name={post.name} text={post.writtenText}
                      photo={post.photoFileByteArray}
                      postComments={post.postCommentsCount}
                      dataTime={post.sentDateTime}
                      postId={post.postId}
                      postLikes={post.likesCount}
                />
            ))}
        </div>)
    );
};

PostsDisplaying.propTypes = {
    userPosts: PropTypes.array.isRequired,
 }