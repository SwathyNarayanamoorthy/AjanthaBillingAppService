import React from 'react';
import PropTypes from 'prop-types';
import AddAlert from '@material-ui/icons/AddAlert';

const Notification = (props) =>  {
    let symbol,
        showMessage = (props.message != "") ? "show" :"hide" ;
    if(props.type == "error" && props.message != ""){
        symbol =(<i className="fa fa-exclamation-circle" aria-hidden="true"></i>);
    };
    return(
        <div className={showMessage}>
            <div align="center" id="msg" className={"notification-message " + props.type}>
            {symbol}
            <span>{props.message}</span>
            </div>
        </div>
    );
}

Notification.displayName = 'Notification';

Notification.propTypes = {
    type: PropTypes.string,
    message: PropTypes.string
}
Notification.defaultProps = {
    type: "error",
    message: ""
};


export default Notification;
