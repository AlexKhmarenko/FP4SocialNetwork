import { ThemeProvider, createTheme } from '@mui/material/styles';

export const style = {
    position: 'absolute',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    width: 400,
    bgcolor: 'background.paper',
    borderRadius: '5px',
    boxShadow: 24,
    p: 4,
    color: '#000000',
  };
  
 export const theme = createTheme({
    typography: {
      button: {
        width: "100%",
        fontSize: '1rem',
        padding: "0.75rem",

      },
      primarybtn: {
        backGround: "green"
      },
      secondarybtn: {
        backGround: "pink"
      }
    },
  });
  