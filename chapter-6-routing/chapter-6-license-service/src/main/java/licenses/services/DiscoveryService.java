package licenses.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

public class DiscoveryService {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    public List getEurkeaServices() {
        return discoveryClient.getServices()
                              .stream()
                              .map(serviceName -> discoveryClient.getInstances(serviceName))
                              .flatMap(i -> i.stream())
                              .map(i -> format("%s:%s", i.getServiceId(), i.getUri()))
                              .collect(toList());
    }
}
