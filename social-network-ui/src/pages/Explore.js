import React, { useEffect } from "react";
import { PostsDisplaying } from "../components/Posts/PostsDisplaying";
import { fetchPostsByPage, setPosts, setUserPostsClear } from "../store/actions";
import { useDispatch, useSelector } from "react-redux";

export function Explore() {
    const page = useSelector(state=>state.pageCount.page)
    const dispatch = useDispatch();
    useEffect( ()=>{
       async function getPosts(){
            let  data = await dispatch(fetchPostsByPage(page));
            dispatch(setUserPostsClear(data));
        }
        getPosts()
    }, [])



    return (
        <div style={{display:"flex", justifyContent:"center", alignItems:"center"}}>
            <PostsDisplaying/>
        </div>
    );
}