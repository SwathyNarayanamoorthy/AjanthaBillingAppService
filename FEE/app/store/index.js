import { createStore, applyMiddleware, combineReducers, compose } from 'redux';
import thunk from 'redux-thunk';
import { routerMiddleware } from 'react-router-redux';
import localreducers from '../reducers';
import { browserHistory } from 'react-router';

const reducer = combineReducers({
  billing: localreducers
});

const routingMiddleware = routerMiddleware(browserHistory);

const store = createStore(reducer, {}, compose(
  applyMiddleware(thunk, routingMiddleware),
  window.devToolsExtension ? window.devToolsExtension() : f => f
));

export default store;
