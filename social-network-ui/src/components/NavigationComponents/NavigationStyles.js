


export const Header = {
    position: "sticky",
    maxHeight: "80px",
    height: "70px",
    width:"100%",
    maxWidth: "600px",
    top: 0,
    backgroundColor: "rgba(255, 255, 255, 0.85)",
    flexBasis: "600px",
    boxShadow: "none",
    borderBottom: "1px solid rgba(0, 0, 0, 0.1)",
    backdropFilter: "blur(15px)",
};
export const DarkHeader = {
    position: "sticky",
    maxHeight: "80px",
    height: "70px",
    width:"100%",
    maxWidth: "600px",
    top: 0,
    backgroundColor: "rgba(21, 32, 43, 0.85)",
    flexBasis: "600px",
    boxShadow: "none",
    borderBottom: "1px solid rgba(0, 0, 0, 0.1)",
    backdropFilter: "blur(15px)",
    border: "1px solid rgb(56, 68, 77)",
    "& .MuiTypography-root": {
        color: "rgb(247, 249, 249)"
    },
};

export const SidebarBox = {
    "& > :not(style)": { m: 1 },
    width: "300px",
    display: "flex",
    textAlign: "start",
    alignContent: "start",
    flexDirection: "column",
    borderRight: "1px solid rgba(0, 0, 0, 0.1)",
    height: "100vh",
    overflow: "hidden",
    backgroundColor: "#ffff",
    position: "sticky",
    top: 0,
};

export const SidebarFabActive = {
    position: "relative",
    fontFamily: "'Lato', sans-serif",
    display: "flex",
    justifyContent: "flex-start",
    borderRadius: "40px",
    backgroundColor: "#ffffff",
    boxShadow: "none",
    padding: "0",
    height: "40px",
    "&:hover": {
        backgroundColor: " #ffffff",
    },
    "&::before": {
        left: "-4px",
        position: "absolute",
        content: "\"\"",
        display: "inline-block",
        width: "7px",
        borderRadius: "90%",
        height: "7px",
        backgroundColor: "black",
    }
};

export const SidebarFab = {
    fontFamily: "'Lato', sans-serif",
    display: "flex",
    justifyContent: "flex-start",
    borderRadius: "40px",
    backgroundColor: "#ffffff",
    boxShadow: "none",
    padding: "25px 0",
    height: "40px",
    "&:hover": {
        transition: "0.7s",
        padding: "25px 0",
        backgroundColor: "rgba(15, 20, 25, 0.15)",
    },
};
export const DarkSidebarFab = {
    fontFamily: "'Lato', sans-serif",
    display: "flex",
    justifyContent: "flex-start",
    borderRadius: "40px",
    color: "rgb(247, 249, 249)",
    backgroundColor: "rgb(21, 32, 43)",
    boxShadow: "none",
    padding: "25px 0",
    height: "40px",
    "&:hover": {
        transition: "0.7s",
        padding: "25px 0",
        backgroundColor: "rgba(247, 249, 249, 0.1)",
    },
};

export const SidebarTypography = {
    fontFamily: "'Lato', sans-serif",
    flexGrow: 1,
    fontWeight: "700",
    fontSize: "22px",
    display: "flex",
    alignItems: "center",
    padding: "0 20px 0 10px",
    textTransform:"none",

};

export const SidebarLogOutButton = {
    fontFamily: "'Lato', sans-serif",
    fontSize: "15px",
    lineHeight: "23px",
    fontStyle: "normal",
    height: "45px",
    background: "#000000",
    transition: "0.7s", "&:hover": {
        transition: "0.7s",
        backgroundColor: "#ffffff",
        color: "#000000"
    },
    fontWeight: 700,
    borderRadius: "20px",
    maxWidth: "140px",
    marginTop: "50px",
    alignSelf:"start",
};

export const SidebarIconBackground = {
    maxWidth: "60px",
    marginTop: "5%",
    display: "flex",
    justifyContent: "flex-start",
    backgroundColor: "#ffffff",
    boxShadow: "none",
    padding: "0",
    width: "150px",
    height: "50px",
    "&:hover": {
        backgroundColor: "#ffffff",
    },
}
export const DarkSidebarIconBackground = {
    maxWidth: "60px",
    marginTop: "5%",
    display: "flex",
    justifyContent: "flex-start",
    backgroundColor: "rgb(21, 32, 43)",
    boxShadow: "none",
    padding: "0",
    width: "150px",
    height: "50px",
    "&:hover": {
        backgroundColor: "rgb(21, 32, 43)",
    },
}

export const UserSearchWrapper = {
    display: "flex",
    flexDirection: "column",
    position: "sticky",
    top: 0,
    zIndex: "2",
    borderLeft: "1px solid rgba(0, 0, 0, 0.1)"
};
export const DarkUserSearchWrapper = {
    display: "flex",
    flexDirection: "column",
    position: "sticky",
    top: 0,
    zIndex: "2",
    borderLeft: "1px solid rgb(56, 68, 77)"
};

export const UserSearchAppBar = {
    top: "0",
    flexBasis: "400px",
    right: 0,
    boxShadow: "none",
    height: "70px",
    backgroundColor: "rgba(255, 255, 255, 0.85)",
};

export const UserSearchTextField = {
    width: "300px",
    height: "30px",
    marginTop: "10px",
    borderRadius: "10px",
    marginLeft: "20px",
    "& .MuiOutlinedInput-root": {
        borderRadius: "40px",
    },
    "& .MuiOutlinedInput-notchedOutline": {
        borderRadius: "40px",
    },
};
export const DarkUserSearchTextField = {
    width: "300px",
    height: "30px",
    marginTop: "10px",
    borderRadius: "10px",
    marginLeft: "20px",
    "& .MuiOutlinedInput-root": {
        borderRadius: "40px",
        background: "rgb(39, 51, 64)",
    },
    "& .MuiOutlinedInput-notchedOutline": {
        borderRadius: "40px",
    },
    "& .MuiOutlinedInput-root .MuiAutocomplete-input": {
        color: "rgb(247, 249, 249)",
    },
    "& .MuiInputLabel-root": {
        color: "rgb(247, 249, 249)",
    },
    "& .MuiAutocomplete-popupIndicator": {
        color: "rgb(247, 249, 249)",
    },
    "& .MuiAutocomplete-clearIndicator": {
        color: "rgb(247, 249, 249)",
    },
};

export const UserSearchContentWrapper = {
    height: "100vh",
    width: "300px",
};

export const HeaderInformationParagraph ={
    fontFamily: "'Lato', sans-serif", color: "#000000"
}

export const SvgIconStyles={ width: "25px", height: "25px", marginLeft: "10px" }