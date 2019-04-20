'use strict';
import React from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import * as actions from '../../actions';
import theme from '../../themes/theme';
import { Paper,Typography, Checkbox,FormControlLabel,Input, InputLabel, FormControl, Button } from '@material-ui/core';
import {MuiThemeProvider} from '@material-ui/core/styles';
import {errorMessage} from '../../constants';
import Notification from '../components/notification';
class Login extends React.Component {
/* istanbul ignore next */
  constructor (props) {
    super(props);
    this.state = {
      showPassword: false,
      password: "",
      username: "",
      errorMessage: ""
    }
    this.login = () => this._login();
    this.usernameHandler = (event) => this._usernameHandler(event);
    this.handleChangePassword = (event) => this._handleChangePassword(event);
    this.handleClickShowPassword = () => this._handleClickShowPassword();
  }
  componentWillReceiveProps(nextProps) {
    if (nextProps.notificationMessages.errorCode == "404") {
      this.setState({
        errorMessage: errorMessage.InvalidAuthenticationMessage
      });
    }
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
    } else {
      this.setState({
        errorMessage: errorMessage.UsernamePasswordRequired
      });
    }
  }
  
  _handleChangePassword (event) {
    this.setState({ password: event.target.value });
  };

  render () {
    return (
      <MuiThemeProvider theme={theme}>
      <div className='logo'>
      <img src="/img/e-billLogo.png" />
    </div>
    <Paper className='content-container'>
        <Typography component="h1" variant="h5">
          Sign in
        </Typography>
        <Notification message = {this.state.errorMessage} type="error" />
          <FormControl margin="normal" required fullWidth>
            <InputLabel htmlFor="username">Username</InputLabel>
            <Input id="username" name="username" autoComplete="username"
              value={this.state.username}
              onChange={this.usernameHandler}
              autoFocus />
          </FormControl>
          <FormControl margin="normal" required fullWidth>
            <InputLabel htmlFor="password">Password</InputLabel>
            <Input name="password" type="password" id="password" autoComplete="current-password"
              value={this.state.password}
              onChange={this.handleChangePassword} />
          </FormControl>
          <FormControlLabel
            control={<Checkbox value="remember" color="primary" />}
            label="Remember me"/>
          <Button
            type="submit"
            fullWidth
            variant="contained"
            color="primary"
            className="submit"
            onClick = {this.login}>
            Sign in
          </Button>
        </Paper>
        </MuiThemeProvider>
        )
  }
}


Login.displayName = 'Login';

Login.defaultProps = {
  userData: {},
  notificationMessages: {}
}
Login.propTypes = {
  dispatch: PropTypes.func,
  notificationMessages: PropTypes.object,
  userData: PropTypes.object
}
function select (state) {
  return {
    userData: state.billing.userData,
    notificationMessages: state.billing.notificationMessages
  };
}

export default connect(select)(Login);
