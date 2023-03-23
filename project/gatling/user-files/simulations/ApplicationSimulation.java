package application.gatling

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import java.util.Objects;

import io.gatling.http.protocol.ProxyBuilder;
import io.gatling.javaapi.core.Body.WithBytes;
import io.gatling.javaapi.core.CoreDsl;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Session;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpDsl;
import io.gatling.javaapi.http.HttpProtocolBuilder;
import io.gatling.javaapi.http.Proxy;

public class ApplicationSimulation extends Simulation {
	
	private String filename = System.getProperty("filename", "data.json");
	private final WithBytes RAW_FILE_BODY = RawFileBody(filename);
	private String url = System.getProperty("url");
	private int nbUsers = Integer.parseInt(System.getProperty("nbUsers"));
	private int duration = Integer.parseInt(System.getProperty("duration"));
	
	HttpProtocolBuilder httpProtocol = http // 4
			.contentTypeHeader("application/json")
			;

	ScenarioBuilder scn = scenario("Application Burst")
			.during(duration).on(
				exec(
					http("GET HOTELS") // 8
					.post(url+"/example/v1/hotels")
					.check(status().is(200), status().saveAs("STATUS"))
				)
				.exec(
					(Session session) -> {
						if (((Integer)session.get("STATUS")).intValue() == 500) {
							System.out.println("Status = " + (Integer)session.get("STATUS"));
						}
						return session;
					}
				)
			);

	Boolean end = Boolean.FALSE;
	
	{
		Objects.requireNonNull(url, "-Durl=http[s]://<host:port> is mandatory");
		Objects.requireNonNull(nbUsers, "-DnbUsers=<int> is mandatory");
		
		setUp( // 11
			scn.injectOpen(
				rampUsers(nbUsers).during(60)
			)
						
		).protocols(httpProtocol); 
	}

}
