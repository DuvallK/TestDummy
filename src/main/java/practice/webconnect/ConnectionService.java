package practice.webconnect;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("connectionService")
public class ConnectionService {
	private static final Logger log = LoggerFactory.getLogger(ConnectionService.class);
	
	private String urlString = "http://localhost:9080/HelloWebApp/reverse.html";
	private static Set<URLConnection> connSet = new HashSet<URLConnection>();
	
	public String sendSynchronousRequest(String requestString) throws IOException {
		StringBuilder response = new StringBuilder();
		log.debug("Request: " + requestString);
		
		String ohubRequestParam = URLEncoder.encode(requestString, "UTF-8");
		String query = String.format("value=%s", ohubRequestParam);
		
	    // Send data
	    URL url = new URL(urlString);
	    URLConnection conn = url.openConnection();
	    if(!connSet.add(conn))
	    	log.error("Reused connection");
	    conn.setUseCaches(false);
	    conn.setDoOutput(true); // Triggers POST.
	    conn.setRequestProperty("accept-charset", "UTF-8");
	    conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
	    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
	    wr.write(query);
	    wr.flush();

	    // Get the response
	    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	    String line;
	    while ((line = rd.readLine()) != null) {
	        response.append(line);
	    }
	    wr.close();
	    rd.close();
	
		return response.toString();
	}
}
