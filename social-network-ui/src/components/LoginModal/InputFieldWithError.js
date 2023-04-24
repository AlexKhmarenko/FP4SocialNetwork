import React from "react";

import { useFormikContext } from "formik";
import { Input, InputLabel, Typography } from "@mui/material";
import PropTypes from "prop-types";

export function InputFieldWithError({ label, name, ...props }) {
    const { errors, touched } = useFormikContext();
    const hasError = errors[name] && touched[name];

    return (
        <>
            <InputLabel
                htmlFor={name}
                sx={{ color: hasError ? "error.main" : "text.primary" }}
            >
                {label}
            </InputLabel>
            <Input error={hasError} name={name} {...props} />
            {hasError && (
                <Typography color="error" variant="caption">
                    {errors[name]}
                </Typography>
            )}
        </>
    );
}

InputFieldWithError.propTypes = {
    name: PropTypes.string.isRequired,
    label: PropTypes.string.isRequired,
};

