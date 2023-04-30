import React from 'react';
import PropTypes from 'prop-types';
import CloseIcon from '@mui/icons-material/Close';
import "./Modal.css"

export const Close = ({onClick}) => {
    return (
        <div className="close" >
            <CloseIcon onClick={onClick} />
        </div>
    )
}
Close.propTypes = {
    onClick: PropTypes.func
};