import React from 'react'
import { Provider } from 'react-redux'
import store from './store'
import ReactDOM from 'react-dom'
import { Route, BrowserRouter as Router } from 'react-router-dom'
import Dashboard from './views/dashboard'
import Login from './views/login'
const routing = (
  <Router>
    <div>
      <Route path="/" component={Login} />
      <Route path="/login" component={Login} />
      <Route path="/users/123/" component={Dashboard} />
    </div>
  </Router>
)
ReactDOM.render(<Provider store={store}>
       {routing}
     </Provider>, document.getElementById('root'))
