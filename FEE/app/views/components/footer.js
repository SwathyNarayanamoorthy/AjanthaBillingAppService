'use strict';

import React from 'react';
import PropTypes from 'prop-types';
const Footer = (props) =>  {
    let enterpriseSiteLink = {
        url: 'https://e-billing/',
        title: 'E-Billing'
      };
    return(
        <footer>
        <span className="copyright">
          Copyright © {(new Date()).getFullYear()} <a href={enterpriseSiteLink.url} target="_blank">{enterpriseSiteLink.title}</a>
        </span>
        </footer>
    );
}

Footer.displayName = 'Footer';

Footer.propTypes = {
  pageTitle: PropTypes.string,
  loggedInUser: PropTypes.object
}
Footer.defaultProps = {
  pageTitle: "",
  loggedInUser: {
    username:'Swathy'
  }
};

export default Footer;
