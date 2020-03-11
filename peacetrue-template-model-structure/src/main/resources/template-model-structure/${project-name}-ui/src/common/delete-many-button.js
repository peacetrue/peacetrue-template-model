import React from 'react';
import {Button, useDeleteMany} from 'react-admin';
import PropTypes from "prop-types";
import IconEvent from '@material-ui/icons/Event';


const DeleteManyButton = ({label, resource, id}) => {
    const [execute, {loading}] = useDeleteMany(resource, id);
    return <Button label={label} onClick={execute} disabled={loading}><IconEvent/></Button>;
};

DeleteManyButton.propTypes = {
    label: PropTypes.string.isRequired,
    resource: PropTypes.string.isRequired,
    id: PropTypes.arrayOf(PropTypes.any).isRequired,
    icon: PropTypes.element,
};

DeleteManyButton.defaultProps = {};

export default DeleteManyButton;