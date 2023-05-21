import React, { useEffect, useState } from "react";
import { Post } from "./Post";
import { setPosts, setUserId } from "../../store/actions";
import { useDispatch, useSelector } from "react-redux";
import { decodeToken } from "./decodeToken";
import CircularProgress from "@mui/material/CircularProgress";

export const PostsDisplaying = () => {
    const userPosts = useSelector(state => state.Posts.posts);
    return (
        userPosts ? (<div style={{ height: "100vh" }}>
            {userPosts.map((post) => (
                <Post key={post.postId} userName={post.username}
                      name={post.name} text={post.writtenText}
                      photo={post.photoFileByteArray}
                      postComments={post.postComments}
                      dataTime={post.sentDateTime}
                      postId={post.postId}
                />
            ))}
        </div>) : (<CircularProgress sx={{marginTop:"20%"}}/>)
    );
};