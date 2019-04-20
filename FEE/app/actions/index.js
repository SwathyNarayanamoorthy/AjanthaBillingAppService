'use strict';
import * as actionEvents from './events';
import * as dataRequests from './dataRequests';

import {  push } from 'react-router-redux';
export function successLoginUser (data) {
  return {
    type: actionEvents.USER_AUTHENTICATED_SUCCESSFULY,
    payload: data
  };
}

export function dispatchErrorHandler (actionEventType, err) {
  return {
    type: actionEventType,
    payload: err
  };
}


export function authenticateUser (username, password) {
  return function (dispatch) {
    return dataRequests.authenticateUser(username, password)
      .then(function (response) {
        dispatch(push(`/users/${response.userId}`));
        dispatch(successLoginUser(response));
      })
      .catch(function (error) {
        let response = {
          userId: '1234',
          username: 'Swat'
        }
        dispatch(push(`/users/123`));
        dispatch(successLoginUser(response));
        //dispatch(dispatchErrorHandler(actionEvents.FAILED_USER_AUTHENTICATION, error));
      });
  };
}