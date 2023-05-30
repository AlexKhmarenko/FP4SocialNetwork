import { Avatar, Button, Paper, Typography } from "@mui/material";
import { Link } from "react-router-dom";
import { StyledBlackButton } from "../../LoginModal/loginModalStyles";
import React, { useEffect, useState } from "react";

export function PopularPeopleSidebar() {
    const [mostPopularPeople, setMostPopularPeople] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            const response = await fetch("http://localhost:8080/users/popular?page=0");
            const popularPeople = await response.json();
            setMostPopularPeople(popularPeople);
            console.log(popularPeople);
        };

        fetchData();
    }, []);

    return (
        <Paper elevation={3} sx={{
            minWidth: "200px",
            minHeight: "620px",
            width: "250px",
            marginTop: "20%",
            marginLeft: "9%",
            padding: "10px 20px",
            overflow: "scroll"
        }}>
            {mostPopularPeople.length > 0 ? <ul style={{ listStyle: "none", margin: "0", padding: "0" }}>
                <li style={{
                    display: "flex",
                    flexDirection: "column",
                    alignItems: "center",
                    borderBottom: "1px solid rgba(0, 0, 0, 0.1)",
                    borderTop: "1px solid rgba(0, 0, 0, 0.1)",
                    padding: "20px 0"
                }}>
                    <div style={{
                        listStyle: "none",
                        display: "flex",
                        justifyContent: "start",
                        alignItems: "center",
                        margin: "0",
                        padding: "0"
                    }}>
                        <Avatar alt="username" style={{ width: "50px", height: "50px" }} src="#"/>
                        <div style={{ display: "flex", flexDirection: "column", marginLeft: "20px" }}>
                            <Typography style={{
                                color: "rgb(113, 118, 123)", fontFamily: "'Lato', sans-serif",
                                fontSize: "13px",
                                fontWeight: "400", marginRight: "10px",
                            }}>
                                <Link style={{
                                    color: "rgb(113, 118, 123)", fontFamily: "'Lato', sans-serif",
                                    fontSize: "15px",
                                    fontWeight: "400",
                                }}>username</Link>
                            </Typography>
                            <Typography style={{
                                color: "rgb(113, 118, 123)",
                                fontFamily: "'Lato', sans-serif",
                                fontSize: "15px",
                                fontWeight: "400",
                                marginRight: "10px",
                                textDecoration: "underline",
                                cursor: "pointer"
                            }}>name
                            </Typography>
                        </div>
                    </div>
                    <Button href="#" sx={{
                        ...StyledBlackButton,
                        color: "white",
                        maxWidth: "200px",
                        maxHeight: "30px",
                        marginTop: "10px"
                    }}>follow</Button>
                </li>
            </ul> : <Typography style={{
                color: "rgb(113, 118, 123)",
                fontFamily: "'Lato', sans-serif",
                fontSize: "25px",
                fontWeight: "700",
                textAlign: "center",
                marginRight: "10px",
                cursor: "pointer",
            }}>we have no ideas to show
            </Typography>}
        </Paper>
    );
}


