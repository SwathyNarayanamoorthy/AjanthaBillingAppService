import * as actionEvents from '../actions/events';

const login = (state = [], action) => {
  let newState = Object.assign({}, state);
  switch (action.type) {
    case actionEvents.USER_AUTHENTICATED_SUCCESSFULY:
      newState = action.payload;
  }
  return newState;
}

export default login
