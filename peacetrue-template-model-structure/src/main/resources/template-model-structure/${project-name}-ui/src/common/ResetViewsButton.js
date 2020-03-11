import React from 'react';
import {Button, useNotify, useRefresh, useUnselectAll, useUpdateMany} from 'react-admin';

const ResetViewsButton = ({selectedIds, resource}) => {
    const refresh = useRefresh();
    const notify = useNotify();
    const unselectAll = useUnselectAll();
    const [updateMany, {loading}] = useUpdateMany(
        'posts',
        selectedIds,
        {views: 0},
        {
            onSuccess: () => {
                refresh();
                notify('Posts updated');
                unselectAll(resource);
            },
            onFailure: error => notify('Error: posts not updated', 'warning'),
        }
    );

    return (
        <Button
            label="simple.action.resetViews"
            disabled={loading}
            onClick={updateMany}
        >
            重置
        </Button>
    );
};

export default ResetViewsButton;