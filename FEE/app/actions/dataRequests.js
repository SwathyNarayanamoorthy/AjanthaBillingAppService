'use strict';

import axios from 'axios';

const defaultSettings = {
  method: 'get',
  baseUrl: 'http://localhost:5000',
  timeout: 20000,
  responseType: 'json'
};
let webServiceOptions = Object.assign({}, defaultSettings,defaultSettings.baseUrl),
  webService = axios.create(webServiceOptions),
  baseUrl = defaultSettings.baseUrl;

export function authenticateUser (username, password) {
  let details = {
    'username': username,
    'password': password
  };
  return webService.request({
    method: 'post',
    url: `${baseUrl}/api/user/auth`,
    data: JSON.stringify(details)
  });
}


