package util;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.revature.models.Principal;

public class RequestViews {
	
private static Logger log = Logger.getLogger(RequestViews.class);
	
	private RequestViews() {
		super();
	}
	
	public static String process(HttpServletRequest request) {
		log.info("in RequestViews.process()");
		
		switch(request.getRequestURI()) {
		
		case "/Zachary-Jaitee-Project1/login.view":
			log.info("Fetching login.html");
			return "partials/login.html";
		
		case "/Zachary-Jaitee-Project1/register.view":
			log.info("Fetching register.html");
			return "partials/register.html";
		
		case "/Zachary-Jaitee-Project1/dashboard.view":
			
			Principal principal = (Principal) request.getAttribute("principal");
			
			if(principal == null) {
				log.warn("No principal attribute found on request object");
				return null;
			}
			
			log.info("Fetching dashboard.html");
			return "partials/dashboard.html";
		
		default: 
			log.info("Invalid view requested");
			return null;
		
		}
	}

}
