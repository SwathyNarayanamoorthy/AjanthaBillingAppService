import React from 'react'
import { Provider } from 'react-redux'
import store from './store'
import ReactDOM from 'react-dom'
import { Route, Router } from 'react-router-dom'
import Dashboard from './views/dashboard'
import Login from './views/login'
import { hashHistory  } from 'react-router';
import { createBrowserHistory } from 'history';

const history = createBrowserHistory()
const routing = (
  <Router history={history}>
    <div>
      <Route exact path="/" component={Login} />
      <Route path="/login" component={Login} />
      <Route path="/users/*/" component={Dashboard} />
    </div>
  </Router>
)
ReactDOM.render(<Provider store={store}>
       {routing}
     </Provider>, document.getElementById('root'))
