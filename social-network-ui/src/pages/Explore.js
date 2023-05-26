import React, { useEffect,  useContext } from "react";
import { PostsDisplaying } from "../components/Posts/PostsDisplaying";
import {
    fetchExplorePosts, setPage,
} from "../store/actions";
import { useDispatch, useSelector } from "react-redux";
import { ScrollContext } from "../components/Layout.js";


export function Explore() {
    const explorePosts = useSelector(state => state.Posts.explorePosts);
    const page = useSelector(state=>state.pageCount.page)
    const {handleParentScroll} = useContext(ScrollContext);

    const dispatch = useDispatch();
    useEffect(() => {
        async function getPosts() {
           dispatch(fetchExplorePosts(page));
        }
        if(explorePosts.length === 0){
            getPosts();
        }

    }, []);

    const handleScroll = (event) => {
        const { scrollTop, clientHeight, scrollHeight } = event.currentTarget;
        if (scrollHeight - scrollTop <= clientHeight + 20) {
            const page2 = page + 1;
            dispatch(setPage(page2))
            dispatch(fetchExplorePosts(page2));
        }
        handleParentScroll(scrollTop, clientHeight, scrollHeight);
    };


    return (
        <div style={{ display: "flex", justifyContent: "center", alignItems: "center" }} onScroll={handleScroll}>
            <PostsDisplaying userPosts={explorePosts}/>
        </div>
    );
}