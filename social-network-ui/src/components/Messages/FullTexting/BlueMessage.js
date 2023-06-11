import React from "react";
import PropTypes from 'prop-types';

export const GenerateBlueMessage = ({text, timestampText, profileImagePath, username, name}) => {
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
          }}>{name}</div>
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


GenerateBlueMessage.propTypes = {
  text: PropTypes.string.isRequired,
  timestampText: PropTypes.string.isRequired,
  profileImagePath: PropTypes.string.isRequired,
  username: PropTypes.oneOfType([PropTypes.string, PropTypes.number]).isRequired,
  name: PropTypes.oneOfType([PropTypes.string, PropTypes.number]).isRequired,
}
  

{/* <GenerateBlueMessage
                      key="1"
                      text="1Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industryls standard dummy text ever since the 1500s."
                      timestampText="Timestamp"
                      profileImagePath="profile-picture.jpg"
                      username="Username"/>
                    <GenerateBlueMessage
                      key="2"
                      text="2Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industryls standard dummy text ever since the 1500s."
                      timestampText="Timestamp"
                      profileImagePath="profile-picture.jpg"
                      username="Username"/>
                    <GenerateBlueMessage
                      key="3"
                      text="3Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industryls standard dummy text ever since the 1500s."
                      timestampText="Timestamp"
                      profileImagePath="profile-picture.jpg"
                      username="Username"/>
                    <GenerateBlueMessage
                      key="4"
                      text="4Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industryls standard dummy text ever since the 1500s."
                      timestampText="Timestamp"
                      profileImagePath="profile-picture.jpg"
                      username="Username"/>
                    <GenerateBlueMessage
                      key="5"
                      text="5Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industryls standard dummy text ever since the 1500s."
                      timestampText="Timestamp"
                      profileImagePath="profile-picture.jpg"
                      username="Username"/>
                    <GenerateBlueMessage
                      key="6"
                      text="6Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industryls standard dummy text ever since the 1500s."
                      timestampText="Timestamp"
                      profileImagePath="profile-picture.jpg"
                      username="Username"/>
                    <GenerateBlueMessage
                      key="7"
                      text="7Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industryls standard dummy text ever since the 1500s."
                      timestampText="Timestamp"
                      profileImagePath="profile-picture.jpg"
                      username="Username"/>
                    <GenerateWhiteMessage
                      key="8"
                      text="8Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industryls standard dummy text ever since the 1500s."
                      timestampText="Timestamp"
                      profileImagePath="profile-picture.jpg"
                      username="Username"/> */}