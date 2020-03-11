import React from 'react';
import {Button, useCreate} from 'react-admin';
import PropTypes from "prop-types";
import IconEvent from '@material-ui/icons/Event';


const CreateButton = ({label, resource, path, data}) => {
    const [execute, {loading}] = useCreate(resource + (path || ''), data);
    return <Button label={label} onClick={execute} disabled={loading}><IconEvent/></Button>;
};

CreateButton.propTypes = {
    label: PropTypes.string.isRequired,
    resource: PropTypes.string.isRequired,
    path: PropTypes.string,
    data: PropTypes.object,
    icon: PropTypes.element,
};

CreateButton.defaultProps = {};

export default CreateButton;