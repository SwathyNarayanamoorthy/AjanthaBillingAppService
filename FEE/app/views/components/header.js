'use strict';

import React from 'react';
import PropTypes from 'prop-types';
const Header = (props) =>  {
    return(
      <header>
        <div className="app-header">
          <i className="logo1">     
           <img src="/img/e-billLogo.png" />
          </i>
          <div className="user-utilities">
            <span className="member-user-name">{props.loggedInUser.fullName}</span>
            <i className="fa fa-user" aria-hidden="true"></i>
            <ul className="user-utilities-list">
              <li>
                  <a href={`/dashboard`}>Dashboard</a>
                </li>
                <li className="dropdown-option">
                  <a href={`/users/${props.loggedInUser.userId}`}>Profile</a>
              </li>
              <li className="highlight">
                <a href={`/users/${props.loggedInUser.userId}/signout`}>Sign Out</a>
              </li>
            </ul>
          </div>
        </div>
        <div className="title-bar">
          <h1>{props.pageTitle}</h1>
        </div>
      </header>
    );
}

Header.displayName = 'Header';

Header.propTypes = {
  pageTitle: PropTypes.string,
  loggedInUser: PropTypes.object
}
Header.defaultProps = {
  pageTitle: "",
  loggedInUser: {
    username:'Swathy'
  }
};
export default Header;
