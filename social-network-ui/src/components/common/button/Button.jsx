import * as React from 'react';
import Button from '@mui/material/Button';
import PropTypes from 'prop-types';

const BasicButton = ({text, onClick, type}) => {
    return (
        <Button variant="git" type={type} onClick={onClick}>{text}</Button>
    )
}

BasicButton.propTypes = {
    text: PropTypes.string,
    onClick: PropTypes.func,
    type: PropTypes.string
  };
export default BasicButton