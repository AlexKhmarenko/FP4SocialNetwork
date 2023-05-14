import React, { useEffect } from "react";
import jwt_decode from "jwt-decode";

export const PostsDisplaying = () => {
    const localStorageToken = JSON.parse(localStorage.getItem("userToken"));
    const sessionStorageToken = JSON.parse(sessionStorage.getItem("userToken"));

    const newPosts = async () => {
        if (localStorageToken || sessionStorageToken) {
            const decodedToken = jwt_decode(
                localStorageToken.token || sessionStorageToken.token
            );
            const userId = decodedToken.sub;
            const userDataPosts = await fetch(`http://localhost:8080/posts?userId=${userId}`);
            const userPosts = await userDataPosts.json();
        } else {
            const usersDataPosts = await fetch(`http://localhost:8080/posts`);
            const usersPosts = await usersDataPosts.json();
        }
    }

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

    return <></>;
};