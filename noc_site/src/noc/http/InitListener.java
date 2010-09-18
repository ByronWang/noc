package noc.http;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import util.PrintObejct;

/**
 * Application Lifecycle Listener implementation class InitListener
 * 
 */
public class InitListener implements ServletContextListener {
	
	private static final Log log = LogFactory.getLog(InitListener.class);

	/**
	 * Default constructor.
	 */
	public InitListener() {}

	/**
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent arg0) {
		log.debug("IN__Listner__contextInitialized");
		
		final ServletContext context = arg0.getServletContext();

		context.setAttribute("fact", new Fact(context,true));

		PrintObejct.print(arg0);
	}

	/**
	 * @see ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent arg0) {
		
	}

}
