import { createStore, applyMiddleware, combineReducers, compose } from 'redux';
import thunk from 'redux-thunk';
import { browserHistory } from 'react-router';
import { routerMiddleware } from 'react-router-redux';
import localreducers from '../reducers';

const reducer = combineReducers({
  user: localreducers
});

const routingMiddleware = routerMiddleware(browserHistory);

const store = createStore(reducer, {}, compose(
  applyMiddleware(thunk, routingMiddleware),
  window.devToolsExtension ? window.devToolsExtension() : f => f
));

export default store;
