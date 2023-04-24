export const StyledModal = {
    display: "flex",
    justifyContent: "center",
    alignItems: "center",
    outline: "none",
};

export const StyledBox = {
    width: 500,
    height: 600,
    background: "#ffff",
    outline: "none",
    display: "flex",
    justifyContent: "center",
    alignItems: "center",
    flexDirection: "column",
    borderRadius: "10px",
    overflow: "visible",
    position: "relative",
};

export const StyledTwitSvgIcon = {
    width: "40px",
    height: "40px",
    marginBottom: "20px"
};

export const StyledCloseSvgIcon = {
    position: "absolute",
    top: "20px",
    right: "20px",
};

export const StyledHeaderModalText = {
    marginBottom: "30px",
    fontWeight: 700,
};

export const StyledFormControl = {
    display: "flex",
    justifyContent: "center",
    alignItems: "center",
    flexDirection: "column",
};

export const StyledBlackButton = {
    height: "45px",
    marginTop: "40px", width: "400px", background: "#000000",
    transition: "0.7s", "&:hover": {
        transition: "0.7s",
        backgroundColor: "#ffffff",
        color: "#000000"
    },
    fontWeight: 700,
    borderRadius: "20px",
};

export const StyledWhiteButton = {
    height: "45px",
    marginTop: "20px", width: "400px", background: "#ffffff", color: "#000000",
    transition: "0.7s", "&:hover": {
        transition: "0.7s",
        backgroundColor: "#000000",
        color: "#ffffff"
    },
    fontWeight: 700,
    borderRadius: "20px",
};

export const StyledSpanElement = {
    position: "relative",
    marginTop: "30px",
    color: "#808080",
    marginBottom: "30px",
    "&::before": {
        content: "\"\"",
        position: "absolute",
        width: "100px",
        height: "1px",
        backgroundColor: "#808080",
        left: "30px",
        top: "50%",
        transform: "translateY(-50%)",
    },
    "&::after": {
        content: "\"\"",
        position: "absolute",
        width: "100px",
        height: "1px",
        backgroundColor: "#808080",
        right: "30px",
        top: "50%",
        transform: "translateY(-50%)",
    },
};
