'use strict';


import * as actionEvents from './events';
import * as dataRequests from './dataRequests';

export function setUsersData (data) {
  return {
    type: actionEvents.USER_AUTHENTICATED,
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
        dispatch(setUsersData(response.data));
      })
      .catch(function (error) {
        dispatch(dispatchErrorHandler(actionEvents.FAILED_USER_AUTHENTICATION, error));
      });
  };
}