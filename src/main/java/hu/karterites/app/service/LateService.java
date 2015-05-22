package hu.karterites.app.service;

import com.google.common.collect.Lists;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import hu.karterites.app.domain.Flight;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import java.util.List;
import java.util.TimeZone;

@Service
public class LateService {
    private static final Logger LOG = LoggerFactory.getLogger(LateService.class);
    private static final String LATE_URL = "http://mobile.api.fr24.com/common/v1/airport.json?limit=100&code=bud&plugin-setting%5Bschedule%5D%5Btimestamp%5D=";

    @PostConstruct
    public List<Flight> loadLateFlights() {
        ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);
        DateTime date = DateTime.now();
        DateTime oneHourBackStart = date.minusHours(1).hourOfDay().roundFloorCopy();
        String url = LATE_URL + oneHourBackStart.getMillis() / 1000;
        LOG.info("URL: {}", url);
        WebResource service = client.resource(UriBuilder.fromUri(url).build());
        String json = service.accept(MediaType.APPLICATION_JSON).get(String.class);
        LOG.info(json);

        return Lists.newArrayList();
    }
}
