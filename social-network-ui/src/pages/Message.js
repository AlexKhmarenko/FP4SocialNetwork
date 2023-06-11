import React, { useEffect, useContext, useState } from "react";
import { PostsDisplaying } from "../components/Posts/PostsDisplaying";
import {
    fetchExplorePosts, setPage, setPageZero,
} from "../store/actions";
import { useDispatch, useSelector } from "react-redux";
import { ScrollContext } from "../components/Layout.js";
import SearchIcon from '@mui/icons-material/Search';
import { apiUrl } from "../apiConfig";


export function Message() {
    function generateBlueMessage(text, timestampText, profileImagePath, username) {
        return (
          <div style={{
            display: "flex",
            flexDirection: "column",
            alignItems: "flex-end",
            marginBottom: "20px",
          }}>
            <div style={{
              display: "flex",
              flexDirection: "row",
              alignItems: "center",
              marginBottom: "10px",
            }}>
              <div style={{
                color: "gray",
                marginRight: "5px",
              }}>{timestampText}</div>
              <div style={{
                color: "gray",
                marginRight: "5px",
              }}>·</div>
              <div style={{
                color: "gray",
                marginRight: "5px",
              }}>@{username}</div>
              <div style={{
                fontWeight: "bold",
                marginRight: "5px",
              }}>{username}</div>
              <img src={profileImagePath} alt="Profile Picture" style={{
                width: "30px",
                height: "30px",
                borderRadius: "50%",
                marginRight: "10px",
              }} />
            </div>
            <div style={{
              background: "blue",
              color: "white",
              padding: "10px",
              borderRadius: "10px",
              maxWidth: "400px",
              textAlign: "right",
            }}>
              <div style={{
                fontSize: "14px",
                lineHeight: "18px",
              }}>
                {text}
              </div>
            </div>
          </div>
        );
      }

      function generateWhiteMessage(text, timestampText, profileImagePath, username) {
        return (
          <div
            style={{
              display: "flex",
              flexDirection: "column",
              alignItems: "flex-start",
              marginBottom: "20px",
            }}
          >
            <div
              style={{
                display: "flex",
                flexDirection: "row",
                alignItems: "center",
                marginBottom: "10px",
              }}
            >
              <img
                src={profileImagePath}
                alt="Profile Picture"
                style={{
                  width: "30px",
                  height: "30px",
                  borderRadius: "50%",
                  marginRight: "10px",
                }}
              />
              <div style={{ fontWeight: "bold", marginRight: "5px" }}>
                {username}
              </div>
              <div style={{ color: "gray", marginRight: "5px" }}>@{username}</div>
              <div style={{ color: "gray", marginRight: "5px" }}>·</div>
              <div style={{ color: "gray" }}>{timestampText}</div>
            </div>
            <div
              style={{
                background: "#f5f8fa",
                padding: "10px",
                borderRadius: "10px",
                maxWidth: "400px",
              }}
            >
              <div
                style={{
                  fontSize: "14px",
                  lineHeight: "18px",
                  color: "#14171a",
                }}
              >
                {text}
              </div>
            </div>
          </div>
        );
      }


      const inboxContainerStyle = {
        width: "100%",
        margin: "0 auto",
      };

      const messageContainerStyle = {
        boxSizing: "border-box",
        display: "flex",
        padding: "10px",
        borderRadius: "8px",
        backgroundColor: "#F5F8FA",
        marginBottom: "10px",
        alignItems: "center"
      };
      
      const avatarStyle = {
        width: "40px",
        height: "40px",
        borderRadius: "50%",
        marginRight: "10px",
      };
      
      const contentStyle = {
        flex: "1",
        height: "40px",
        overflow: "hidden",
      };
      
      const senderStyle = {
        fontWeight: "bold",
        marginBottom: "5px",
      };
      
      const messageStyle = {
        fontSize: "14px",
      };

      const dateStyle = {
        marginLeft: "auto",
        fontSize: "12px",
        color: "#657786",
      };
      
      function InboxMessage(sender, message, date) {
        return (
          <div style={messageContainerStyle}>
            <img src="avatar.jpg" alt="Avatar" style={avatarStyle} />
            <div style={contentStyle}>
              <div style={senderStyle}>{sender}</div>
              <div style={messageStyle}>{message}</div>
            </div>
            <div style={dateStyle}>{date}</div>
          </div>
        );
      }
      
      const sortContainerStyle = {
        display: "flex",
        alignItems: "center",
        padding: "10px",
        marginBottom: "10px",
      };
      
      const searchInputContainerStyle = {
        position: "relative",
        flex: "1",
      };
      
      const searchIconStyle = {
        position: "absolute",
        top: "50%",
        right: "8px",
        transform: "translateY(-50%)",
        color: "#657786",
      };
      
      const searchInputStyle = {
        boxSizing: "border-box",
        width: "100%",
        border: "none",
        borderRadius: "20px",
        padding: "8px 12px",
        paddingLeft: "32px",
        backgroundColor: "#E6ECF0",
        color: "#14171A",
        fontSize: "14px",
      };
      
      function SortBlock() {
        return (
          <div style={sortContainerStyle}>
            <div style={searchInputContainerStyle}>
              <input type="text" placeholder="Search" style={searchInputStyle} />
              <SearchIcon style={searchIconStyle} />
            </div>
          </div>
        );
      }
      
      function LoadAllMessages() {
        const userId = useSelector(state => state.userData.userData.userId);
        const fetchMessages = async () => {
          // const response2 = await fetch(`${apiUrl}/api/inbox`, {
          //   method: "POST",
          //   body: JSON.stringify({
          //       inboxUid: userId,
          //       userId: 64,
          //       writtenMessage: "work"
          //   }),
          //   headers: { "Content-Type": "application/json" }
          // });
          // const userData2 = await response2.json();
          // console.log(userData2);

          const response1 = await fetch(`${apiUrl}/api/inbox?inboxUid=${userId}`);
          if (response1.ok) {
            console.log("Response1 ok")
          } else {
            // Handle response error
            console.error('Error with response1:');
          }
          const userData = await response1.json();
          console.log(userData);
        }
        
        //   try {
        //     const responses = fetch(url)
        //     .then(response => response.json())
        //     .then(data => {
        //       // Обработка полученных данных
        //       console.log(data);
        //     })
        //     .catch(error => {
        //       // Обработка ошибок
        //       console.error(error);
        //     });
      
        //     if (responses.ok) {
        //       console.log("OK");
        //     } else {
        //       console.log("NOT OK");
        //     }
        //   } catch (error) {
        //     console.error("Error:", error);
        //   }
        // };
      
        useEffect(() => {
          fetchMessages();
        }, [userId]);
      
        return (
          <div>Placeholder content</div>
        );
      }

    return (
        <div style={{display: "flex", paddingBottom: "20px",}}>
            <div style={{
                display: "flex",
                flexDirection: "column",
                overflowY: "scroll",
                maxHeight: "calc(100vh - 120px)",
                padding: "20px 20px",
                boxSizing: "border-box",
                width: "600px",
            }}>
              {SortBlock()}
                <div style={inboxContainerStyle}>
                
                  {/* {messages.map((msg, index) => ( */}
                    {/* <InboxMessage key={index} sender={msg.sender} message={msg.message} /> */}
                    {InboxMessage("John Doe", "Hello!", "June 9, 2023")}
                    {InboxMessage("Jane Smith", "Just wanted to say hello!", "June 10, 2023")}
                    <LoadAllMessages />
                  {/* ))} */}
                </div>
                </div>
                <div style={{
                    display: "flex",
                    flexDirection: "column-reverse",
                    overflowY: "scroll",
                    maxHeight: "calc(100vh - 120px)",
                    padding: "20px 20px",
                    boxSizing: "border-box",
                    borderTop: "1px solid rgba(0, 0, 0, 0.1)",
                    width: "600px",
                }}>
                    {generateBlueMessage("1Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industryls standard dummy text ever since the 1500s.", "Timestamp", "profile-picture.jpg", "Username")}
                    {generateBlueMessage("3Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industryls standard dummy text ever since the 1500s.", "Timestamp", "profile-picture.jpg", "Username")}
                    {generateBlueMessage("3Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industryls standard dummy text ever since the 1500s.", "Timestamp", "profile-picture.jpg", "Username")}
                    {generateBlueMessage("3Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industryls standard dummy text ever since the 1500s.", "Timestamp", "profile-picture.jpg", "Username")}
                    {generateBlueMessage("3Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industryls standard dummy text ever since the 1500s.", "Timestamp", "profile-picture.jpg", "Username")}
                    {generateBlueMessage("3Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industryls standard dummy text ever since the 1500s.", "Timestamp", "profile-picture.jpg", "Username")}
                    {generateBlueMessage("3Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industryls standard dummy text ever since the 1500s.", "Timestamp", "profile-picture.jpg", "Username")}
                    {generateBlueMessage("3Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industryls standard dummy text ever since the 1500s.", "Timestamp", "profile-picture.jpg", "Username")}
                    {generateBlueMessage("3Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industryls standard dummy text ever since the 1500s.", "Timestamp", "profile-picture.jpg", "Username")}
                    {generateWhiteMessage("1Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s.", "Timestamp","profile-picture.jpg", "Username")}
                </div>

        </div>
    );
}