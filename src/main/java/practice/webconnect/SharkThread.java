package practice.webconnect;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class SharkThread extends Thread {
	private static final Logger log = LoggerFactory.getLogger(SharkThread.class);
	@Autowired
	private ConnectionService webService;
	
	private String requestString;
	private int sleepTime = 1000;
	public int getSleepTime() {
		return sleepTime;
	}

	public void setSleepTime(int sleepTime) {
		this.sleepTime = sleepTime;
	}

	private final int timeLimit = 6 * 60 * 1000;	// 6 minutes
	
	private String reverse(String s) {
		return new StringBuilder(s).reverse().toString();
	}
	
	public void run() {
		if(requestString == null || requestString.isEmpty())
			log.error("No requestString!");
		if(webService == null)
			log.error("Wiring failed");
		
		long startTime = System.currentTimeMillis();
		
		do {
			try {
				String s = webService.sendSynchronousRequest(requestString);
				if(!s.equals(reverse(requestString))) {
					log.error("Sent " + requestString + " got " + s);
				} else {
					log.debug(requestString + " OK");
				}
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				log.error(e.getMessage());
			} catch (IOException e) {
				log.error(e.getMessage());
			}
		} while (System.currentTimeMillis() < startTime + timeLimit);
		
	}

	public ConnectionService getWebService() {
		return webService;
	}

	public void setWebService(ConnectionService webService) {
		this.webService = webService;
	}

	public String getRequestString() {
		return requestString;
	}

	public void setRequestString(String requestString) {
		this.requestString = requestString;
	}
}
