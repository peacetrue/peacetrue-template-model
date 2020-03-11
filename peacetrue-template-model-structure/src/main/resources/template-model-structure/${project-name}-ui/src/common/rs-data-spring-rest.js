import {stringify} from "query-string";
import {
    CREATE,
    DELETE,
    DELETE_MANY,
    fetchUtils,
    GET_LIST,
    GET_MANY,
    GET_MANY_REFERENCE,
    GET_ONE,
    UPDATE,
    UPDATE_MANY
} from "react-admin";

/**
 * Maps react-admin queries to a REST API implemented using Java Spring Boot and Swagger
 *
 * @example
 * GET_LIST     => GET http://my.api.url/posts?page=0&pageSize=10
 * GET_ONE      => GET http://my.api.url/posts/123
 * GET_MANY     => GET http://my.api.url/posts?id=1234&id=5678
 * UPDATE       => PUT http://my.api.url/posts/123
 * CREATE       => POST http://my.api.url/posts
 * DELETE       => DELETE http://my.api.url/posts/123
 */
export default (apiUrl, httpClient = fetchUtils.fetchJson) => {
    /**
     * @param {String} type One of the constants appearing at the top if this file, e.g. 'UPDATE'
     * @param {String} resource Name of the resource to fetch, e.g. 'posts'
     * @param {Object} params The data request params, depending on the type
     * @returns {Object} { url, options } The HTTP request parameters
     */
    const convertDataRequestToHTTP = (type, resource, params) => {
        let url = "";
        const options = {headers: new Headers({'Content-Type': 'application/x-www-form-urlencoded'})};
        switch (type) {
            case GET_LIST: {
                const {page, perPage} = params.pagination;
                let _params = {
                    page: page - 1,
                    size: perPage,
                    ...params.filter,
                };
                let sort = params.sort;
                if (sort) _params.sort = `${sort.field},${sort.order}`;
                url = `${apiUrl}/${resource}?` + stringify(_params);
                break;
            }
            case GET_ONE:
                url = `${apiUrl}/${resource}/${params.id}`;
                break;
            case GET_MANY: {
                url = `${apiUrl}/${resource}?${stringify({id: params.ids})}`;
                break;
            }
            case GET_MANY_REFERENCE: {
                const {page, perPage} = params.pagination;
                let _params = {
                    page: page - 1,
                    size: perPage,
                    ...params.filter,
                    [params.target]: params.id
                };
                let sort = params.sort;
                if (sort) _params.sort = `${sort.field},${sort.order}`;
                url = `${apiUrl}/${resource}?${stringify(_params)}`;
                break;
            }
            case UPDATE:
                url = `${apiUrl}/${resource}/${params.id}`;
                options.method = "PUT";
                options.body = stringify(params.data);
                break;
            case CREATE:
                url = `${apiUrl}/${resource}`;
                options.method = "POST";
                options.body = stringify(params.data);
                break;
            case DELETE:
                url = `${apiUrl}/${resource}/${params.id}`;
                options.method = "DELETE";
                break;
            default:
                throw new Error(`Unsupported fetch action type ${type}`);
        }

        console.info("request:", {resource, type, params, url, options});
        return {url, options};
    };

    /**
     * @param {Object} response HTTP response from fetch()
     * @param {String} type One of the constants appearing at the top if this file, e.g. 'UPDATE'
     * @param {String} resource Name of the resource to fetch, e.g. 'posts'
     * @param {Object} params The data request params, depending on the type
     * @returns {Object} Data response
     */
    const convertHTTPResponse = (response, type, resource, params) => {
        let {json} = response;
        if (json.code && json.message && json.data) json = json.data;
        switch (type) {
            case GET_LIST:
            case GET_MANY_REFERENCE:
                if (!json.hasOwnProperty("totalElements")) {
                    throw new Error(
                        "The numberOfElements property must be must be present in the Json response"
                    );
                }
                return {
                    data: json.content,
                    total: parseInt(json.totalElements, 10)
                };
            case CREATE:
                return {data: {...params.data, id: json.id}};
            default:
                return {data: json};
        }
    };

    /**
     * @param {string} type Request type, e.g GET_LIST
     * @param {string} resource Resource name, e.g. "posts"
     * @param {Object} payload Request parameters. Depends on the request type
     * @returns {Promise} the Promise for a data response
     */
    return (type, resource, params) => {
        // simple-rest doesn't handle filters on UPDATE route, so we fallback to calling UPDATE n times instead
        if (type === UPDATE_MANY) {
            return Promise.all(
                params.ids.map(id =>
                    httpClient(`${apiUrl}/${resource}/${id}`, {
                        method: "PUT",
                        body: stringify(params.data)
                    })
                )
            ).then(responses => ({
                data: responses.map(response => response.json)
            }));
        }
        // simple-rest doesn't handle filters on DELETE route, so we fallback to calling DELETE n times instead
        if (type === DELETE_MANY) {
            return Promise.all(
                params.ids.map(id =>
                    httpClient(`${apiUrl}/${resource}/${id}`, {
                        method: "DELETE"
                    })
                )
            ).then(responses => ({
                data: responses.map(response => response.json)
            }));
        }

        const {url, options} = convertDataRequestToHTTP(type, resource, params);
        return httpClient(url, options).then(response =>
            convertHTTPResponse(response, type, resource, params)
        );
    };
};