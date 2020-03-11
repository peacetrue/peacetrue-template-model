import React from 'react';
import {Create, ReferenceInput, required, SelectInput, SimpleForm, TextInput} from 'react-admin';

export const OrderCreate = (props) => (
    <Create {...props}>
        <SimpleForm>
            <ReferenceInput label="serviceId" source="serviceId" reference="services" validate={required()}>
                <SelectInput optionText="remark"/>
            </ReferenceInput>
            <TextInput source="count" initialValue="1" validate={required()}/>
            <TextInput source="params" fullWidth initialValue='{"url":"https://github.com/"}' validate={required()}/>
            <TextInput source="ownerCode" validate={required()}/>
            <TextInput source="remark" fullWidth multiline/>
        </SimpleForm>
    </Create>
);
