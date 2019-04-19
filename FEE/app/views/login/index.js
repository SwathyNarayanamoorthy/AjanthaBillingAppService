'use strict';
import React from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import Button from '@material-ui/core/Button';
import * as actions from '../../actions';
import TextField from '@material-ui/core/TextField';

import FormControl from '@material-ui/core/FormControl';
import InputLabel from '@material-ui/core/InputLabel';
import Input from '@material-ui/core/Input';
import InputAdornment from '@material-ui/core/InputAdornment';
import IconButton from '@material-ui/core/IconButton';
import Visibility from '@material-ui/icons/Visibility';
import VisibilityOff from '@material-ui/icons/VisibilityOff';

class Login extends React.Component {
/* istanbul ignore next */
  constructor (props) {
    super(props);
    this.state = {
      showPassword: false,
      password: "",
      username: ""
    }
    this.login = () => this._login();
    this.usernameHandler = (event) => this._usernameHandler(event);
    this.handleChangePassword = (event) => this._handleChangePassword(event);
    this.handleClickShowPassword = () => this._handleClickShowPassword();
  }
  
  _usernameHandler (event) {
    this.setState({
      username: event.target.value
    });
  }
  
  _login () {
    let username=this.state.username,
        password=this.state.password;
    if (username !== "" && password != "" ) {
      this.props.dispatch(actions.authenticateUser(username, password));
    }
  }
  
  _handleChangePassword (event) {
    this.setState({ password: event.target.value });
  };

  _handleClickShowPassword () {
    this.setState(state => ({ showPassword: !state.showPassword }));
  }
  render () {
    return (
      <div>
         <TextField
          required
          id="standard-required"
          label="Username"
          margin="normal"
          value={this.state.username}
          onChange={this.usernameHandler}
        />
        <FormControl>
          <InputLabel htmlFor="adornment-password">Password</InputLabel>
          <Input
            id="adornment-password"
            type={this.state.showPassword ? 'text' : 'password'}
            value={this.state.password}
            onChange={this.handleChangePassword}
            endAdornment={
              <InputAdornment position="end">
                <IconButton
                  aria-label="Toggle password visibility"
                  onClick={this.handleClickShowPassword}>
                  {this.state.showPassword ? <Visibility /> : <VisibilityOff />}
                </IconButton>
              </InputAdornment>
            }
          />
        </FormControl>
        <Button value="Login" variant="contained" color="primary" onClick = {this.login}>
        Login
        </Button>
      </div>
    )
  }
}


Login.displayName = 'Login';


Login.propTypes = {
  dispatch: PropTypes.func
}
function select (state) {
  return {
    userData: state.userData,
    notificationMessages: state.notificationMessages
  };
}

export default connect(select)(Login);
