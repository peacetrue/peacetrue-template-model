import React from 'react';
import {Button, useUpdateMany} from 'react-admin';
import PropTypes from "prop-types";
import IconEvent from '@material-ui/icons/Event';


const UpdateManyButton = ({label, resource, path, id, data}) => {
    const [execute, {loading}] = useUpdateMany(resource + (path || ''), id, data);
    return <Button label={label} onClick={execute} disabled={loading}><IconEvent/></Button>;
};

UpdateManyButton.propTypes = {
    label: PropTypes.string.isRequired,
    resource: PropTypes.string.isRequired,
    path: PropTypes.string,
    id: PropTypes.arrayOf(PropTypes.any).isRequired,
    data: PropTypes.object,
    icon: PropTypes.element,
};

UpdateManyButton.defaultProps = {};

export default UpdateManyButton;