package noc.http;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Servlet Filter implementation class HelpFilter
 */
public class HelpFilter implements Filter {
	private static final Log log = LogFactory.getLog(HelpFilter.class);

    /**
     * Default constructor. 
     */
    public HelpFilter() {
        // TODO Auto-generated constructor stub
    }

    int count = 0;
	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		count++;
		
		HttpServletRequest req = (HttpServletRequest) request;
		log.debug("== START  "+count+" - " + System.currentTimeMillis() + " : " + req.getRequestURI() + " < " + req.getClass().getName());
		
		// pass the request along the filter chain
		chain.doFilter(request, response);
		log.debug("== FINISH "+count+" - " + System.currentTimeMillis() + " : " + req.getRequestURI());
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
