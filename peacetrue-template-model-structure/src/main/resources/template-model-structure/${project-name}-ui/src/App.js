// in src/App.js
import React from 'react';
import {Admin, Resource} from 'react-admin';
import {UserList} from './modules/users/list';
import {UserCreate} from './modules/users/create';
// import jsonServerProvider from 'ra-data-json-server';
import jsonServerProvider from './common/rs-data-spring-rest';

const dataProvider = jsonServerProvider('http://localhost:8082');
const App = () => (
    <Admin dataProvider={dataProvider}>
        <Resource name="users" list={UserList}/>
    </Admin>
);

export default App;