package com.ajantha.billing.parent.billingWeb;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ajantha.billing.parent.billingManager.HibernateManager;
import com.ajantha.billing.user.info.to.UserInfoTO;
import com.ajantha.billing.user.manager.UserInfoManager;



/**
 * 
 * @author sivabharani
 *
 */
public class BillingStartupServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	private Log log = LogFactory.getLog(BillingStartupServlet.class);
	
	/**
	 * Constructor of the object.
	 */
	public BillingStartupServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy();
	}

	/**
	 * The doGet method of the servlet. <br>
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * The doPost method of the servlet. <br>
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurred
	 */
	@SuppressWarnings("deprecation")
	public void init() throws ServletException {
		log.info("Started Initialising Tenant Portal");
		Properties properties = null;
		InputStream in = null;
		try {
			ServletConfig servletConfig = getServletConfig();
			String parentPath = System.getProperty("catalina.home").concat(File.separator).concat("conf").concat(File.separator);
			String startupFile = parentPath.concat(servletConfig.getInitParameter("startup-properties"));
			if (new File(startupFile).exists()) {
				in = new FileInputStream(startupFile);
			}
			if (in == null) {
				new Exception("Property " + startupFile + " file not found");
			}
			properties = new Properties();
			properties.load(in);
			HibernateManager hibernateManager = new HibernateManager();
			hibernateManager.init(properties);
			UserInfoManager userInfoManager = new UserInfoManager();
			UserInfoTO userInfoTO =null;
			/*UserInfoTO persistUserInfoTO = new UserInfoTO();
			persistUserInfoTO.setPhoneNumber("999");
			persistUserInfoTO.setUserName("Pugazh");
			persistUserInfoTO.setPassword("pugazh-123");*/
			userInfoTO = userInfoManager.getUserById(1L);
			log.info("UserName - "+userInfoTO.getUserName());
		} catch (Exception e) {
			log.error("Error in startup", e);
			throw new ServletException("Error occured in TenantPortalStartup Servlet: ", e);
		} finally {
			if (in != null) {
				IOUtils.closeQuietly(in);
			}
		}
	}
}
