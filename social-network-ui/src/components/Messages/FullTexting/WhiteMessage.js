import React from "react";
import PropTypes from 'prop-types';

export const GenerateWhiteMessage = ({text, timestampText, profileImagePath, username, name}) => {
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
            {name}
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

GenerateWhiteMessage.propTypes = {
    text: PropTypes.string.isRequired,
    timestampText: PropTypes.string.isRequired,
    profileImagePath: PropTypes.string.isRequired,
    username: PropTypes.oneOfType([PropTypes.string, PropTypes.number]).isRequired,
    name: PropTypes.oneOfType([PropTypes.string, PropTypes.number]).isRequired,
}