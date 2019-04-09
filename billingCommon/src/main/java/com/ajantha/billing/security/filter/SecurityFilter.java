package com.ajantha.billing.security.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author sivabharani
 *
 */
public class SecurityFilter implements Filter
{
	private Log log = LogFactory.getLog(SecurityFilter.class);

	@Override
	public void destroy() {
		//Suppress
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletResponse res = (HttpServletResponse) response;
		try {
			HttpServletRequest req = (HttpServletRequest) request;
			String uri = req.getRequestURI();
			log.info("Current Url >> " + uri);
			if(uri != null) {
					chain.doFilter(request, response);
				}
		} catch (Exception e) {
			log.error("Error in SecurityFilter", e);
			if ("Session TimeOut".equalsIgnoreCase(e.getMessage())) {
				res.sendError(403, "Session TimeOut");
			} else if ("Unauthorised".equalsIgnoreCase(e.getMessage())) {
				res.sendError(401, "Unauthorised User");
			} else if ("User Not Found for given Verification Id".equalsIgnoreCase(e.getMessage())) {
				res.sendError(401, "Unauthorised User");
			} else if ("User Not Found".equalsIgnoreCase(e.getMessage())) {
				res.sendError(500, "User Not Found");
			} else if ("Verification Id not Found for the user".equalsIgnoreCase(e.getMessage()) || "Verfication link is expired".equalsIgnoreCase(e.getMessage())) {
				res.sendError(500, "Invalid Verification Link");
			} else if ("Verfication link is expired".equalsIgnoreCase(e.getMessage())) {
				res.sendError(500, "Verfication link is expired");
			} else if ("Company Not Found (Invalid Company URL".equalsIgnoreCase(e.getMessage())) {
				res.sendError(500, "Company Not Found (Invalid Company URL)");
			}
		} 
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		//Suppress
	}
}
