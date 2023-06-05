import React, { useEffect, useContext, useState, useCallback } from "react";
import { PostsDisplaying } from "../components/Posts/PostsDisplaying";
import {
    fetchExplorePosts, setPage, setPageZero,
} from "../store/actions";
import { useDispatch, useSelector } from "react-redux";
import { ScrollContext } from "../components/Layout.js";

export function Explore() {
    const explorePosts = useSelector(state => state.Posts.explorePosts);
    const page = useSelector(state => state.pageCount.page);
    const { handleParentScroll } = useContext(ScrollContext);
    const [isLoading, setIsLoading] = useState(false);
    const [posts, setPosts] = useState([]);
    const [isFetchingPosts, setIsFetchingPosts] = useState(false);
    const [allPostsLoaded, setAllPostsLoaded] = useState(false);
    const dispatch = useDispatch();

    useEffect(() => {
        dispatch(setPageZero());

        async function getPosts() {
            try {
                setIsLoading(true);
                const newPosts = await dispatch(fetchExplorePosts(page));
                console.log("newPosts", newPosts);// Сохраните новые посты
                setPosts(newPosts);
                if (newPosts.length === 0) {
                    setAllPostsLoaded(true); // Если массив пуст, значит, все посты загружены
                }
            } catch (error) {
                console.error(error);
            } finally {
                setIsLoading(false);
            }
        }

        if (posts.length === 0 && !allPostsLoaded) {
            setIsLoading(true);
            getPosts();
        }
    }, [location.pathname]);

    useEffect(() => {
        console.log(page);
    });

    const handleScroll = async (event) => {
        if (isFetchingPosts || allPostsLoaded) {
            return;
        }
        setIsFetchingPosts(true);
        try {
            const { scrollTop, clientHeight, scrollHeight } = event.currentTarget;
            if (scrollHeight - scrollTop <= clientHeight + 20) {
                let newPosts = [...explorePosts];
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
        <div style={{ display: "flex", justifyContent: "center", alignItems: "center" }} onScroll={handleScroll}>
            <PostsDisplaying userPosts={explorePosts} isLoading={isLoading}/>
        </div>
    );
}