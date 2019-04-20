import * as actionEvents from '../actions/events';

const errorMessages = (state = [], action) => {
  let newState = Object.assign({}, state);
  switch (action.type) {
    case actionEvents.FAILED_USER_AUTHENTICATION:
      newState = {
        actionType: action.type,
        type: 'error',
        errorCode: action.payload.response.status
      };
  }
  return newState;
}

export default errorMessages
