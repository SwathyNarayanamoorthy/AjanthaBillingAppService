'use strict';

import React from 'react';
import { connect } from 'react-redux';
import {MuiThemeProvider} from '@material-ui/core/styles';
import PropTypes from 'prop-types';
import theme from '../../themes/theme';
import { Link as RouterLink } from 'react-router-dom'
import Link from '@material-ui/core/Link';
class Dashboard extends React.Component {
  /* istanbul ignore next */
  constructor (props) {
    super(props);
  }

  render () {
    return (
      <div>
        Welcome Swathy
       <ul className="navlinks-section">
            <li>
              <Link component={RouterLink} to="/billing/">
                Billing
              </Link>
            </li>
            <li>
              <Link component={RouterLink} to={`/manage/bills/`}>
                <i className="luna-icon-pos luna-icon-32 icon-user-gear"></i>
                View Bill histrory
              </Link>
              </li>
          </ul>
      </div>
    );
  }
}

Dashboard.displayName = 'Dashboard';

Dashboard.defaultProps = {
  notificationResponse: {},
  loggedInUser: {
    username:'Swathy'
  }
};

Dashboard.propTypes = {
  dispatch: PropTypes.func,
  loggedInUser: PropTypes.object,
  notificationResponse: PropTypes.object
};

function select (state) {
  return {
    loggedInUser: state.billing.userData,
    notificationResponse: state.billing.notificationMessages
  };
}

export default connect(select)(Dashboard);
