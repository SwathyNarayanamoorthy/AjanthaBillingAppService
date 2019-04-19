import { combineReducers } from 'redux'
import login from './login'
import errorMessages from './errorMessages';

export default combineReducers({
  userData:login,
  notificationMessages: errorMessages
})
