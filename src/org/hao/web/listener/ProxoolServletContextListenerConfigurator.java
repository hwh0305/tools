/**
 * 
 */
package org.hao.web.listener;

import java.io.File;
import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.logicalcobwebs.proxool.ProxoolException;
import org.logicalcobwebs.proxool.ProxoolFacade;
import org.logicalcobwebs.proxool.configuration.JAXPConfigurator;
import org.logicalcobwebs.proxool.configuration.PropertyConfigurator;

/**
 * Proxool 的连接池启动器，用于在ServletContextListener中启动
 * 
 * @author 胡文昊
 * 
 * <br />
 * 创建时间：2008-6-20 下午03:12:12
 */
public class ProxoolServletContextListenerConfigurator implements
		ServletContextListener {

	private static final String PREFIX = "jdbc";

	private static final Log LOG = LogFactory
			.getLog(ProxoolServletContextListenerConfigurator.class);

	private static final String XML_FILE_PROPERTY = "xmlFile";

	private static final String PROPERTY_FILE_PROPERTY = "propertyFile";

	private static final String AUTO_SHUTDOWN_PROPERTY = "autoShutdown";

	private boolean autoShutdown = true;

	/*
	 * （非 Javadoc）
	 * 
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent event) {
		if (autoShutdown) {
			ProxoolFacade.shutdown(0);
		}
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent event) {
		ServletContext servletContext = event.getServletContext();
		String appDir = servletContext.getRealPath("/");

		Properties properties = new Properties();

		Enumeration names = servletContext.getInitParameterNames();
		while (names.hasMoreElements()) {
			String name = (String) names.nextElement();
			String value = servletContext.getInitParameter(name);

			if (name.equals(XML_FILE_PROPERTY)) {
				try {
					File file = new File(value);
					if (file.isAbsolute()) {
						JAXPConfigurator.configure(value, false);
					} else {
						JAXPConfigurator.configure(appDir + File.separator
								+ value, false);
					}
				} catch (ProxoolException e) {
					LOG.error("Problem configuring " + value, e);
				}
			} else if (name.equals(PROPERTY_FILE_PROPERTY)) {
				try {
					File file = new File(value);
					if (file.isAbsolute()) {
						PropertyConfigurator.configure(value);
					} else {
						PropertyConfigurator.configure(appDir + File.separator
								+ value);
					}
				} catch (ProxoolException e) {
					LOG.error("Problem configuring " + value, e);
				}
			} else if (name.equals(AUTO_SHUTDOWN_PROPERTY)) {
				autoShutdown = Boolean.valueOf(value).booleanValue();
			} else if (name.startsWith(PREFIX)) {
				properties.setProperty(name, value);
			}
		}

		if (properties.size() > 0) {
			try {
				PropertyConfigurator.configure(properties);
			} catch (ProxoolException e) {
				LOG.error("Problem configuring using init properties", e);
			}
		}
	}

}
