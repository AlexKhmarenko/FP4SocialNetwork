import * as React from 'react';
import Button from '@mui/material/Button';
import PropTypes from 'prop-types';

const BasicButton = ({text, onClick}) => {
    return (
        <Button variant="contained" onClick={onClick}>{text}</Button>
    )
}

BasicButton.propTypes = {
    text: PropTypes.string,
    onClick: PropTypes.func
  };
export default BasicButton