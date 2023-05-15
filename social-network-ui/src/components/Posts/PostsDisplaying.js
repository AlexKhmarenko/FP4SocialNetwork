import React, { useEffect } from "react";
import { Post } from "./Post";
import { setPosts, setUserId } from "../../store/actions";
import { useDispatch, useSelector } from "react-redux";
import { decodeToken } from "./decodeToken";

export const PostsDisplaying = () => {
        const userPosts = useSelector(state => state.Posts.posts);
        const dispatch = useDispatch();

        const newPosts = async () => {
            const decodedToken = decodeToken();
            if (decodedToken) {
                const userId = decodedToken.sub;
                dispatch(setUserId(userId));
                const userDataPosts = await fetch(`http://localhost:8080/posts?userId=${userId}`);
                const usersPosts = await userDataPosts.json();
                dispatch(setPosts(usersPosts));
            } else {
                const usersDataPosts = await fetch(`http://localhost:8080/posts`);
                const usersPosts = await usersDataPosts.json();
                dispatch(setPosts(usersPosts));
            }
        };

        useEffect(() => {
            const fetchPosts = async () => {
                try {
                    await newPosts();
                } catch (error) {
                    console.error("Ошибка получения постов:", error);
                }
            };
            fetchPosts();
        }, []);

        return (
            <>
                {userPosts.map((post) => (
                    <Post key={post.postId} userName={post.username}
                          name={post.name} text={post.writtenText}
                          photo={post.photoFileByteArray}
                          postComments={post.postComments}
                          postLikes={post.postLikes}
                    />
                ))}
            </>
        );
    }
;