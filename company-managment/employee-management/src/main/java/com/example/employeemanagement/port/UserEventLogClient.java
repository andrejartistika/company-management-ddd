package com.example.employeemanagement.port;


import com.example.employeemanagement.integration.RemoteEventLogImpl;
import com.example.sharedkernel.domain.base.RemoteEventLog;
import com.example.sharedkernel.infra.eventlog.RemoteEventLogService;
import com.example.sharedkernel.infra.eventlog.StoredDomainEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Service
public class UserEventLogClient implements RemoteEventLogService {
    private final String source;
    private final String serverUrl;
    private final RestTemplate restTemplate;

    public UserEventLogClient(@Value("${app.usermanagement.url}") String serverUrl, @Value("${app.usermanagement.connect-timeout-ms}") int connectionTimeout, @Value("${app.usermanagement.read-timeout-ms}") int readTimeout){
        this.source = serverUrl;
        this.serverUrl = serverUrl;
        restTemplate = new RestTemplate();
        var requestFactory = new SimpleClientHttpRequestFactory();

        requestFactory.setConnectTimeout(connectionTimeout);
        requestFactory.setReadTimeout(readTimeout);
        restTemplate.setRequestFactory(requestFactory);
    }
    private UriComponentsBuilder uri() {
        return UriComponentsBuilder.fromUriString(serverUrl);
    }

    @Override
    public String source() {
        return source;
    }


    @Override
    public RemoteEventLog currentLog(long lastProcessedId) {
        System.out.println("gets called with" + lastProcessedId);
        URI currentLogUri = UriComponentsBuilder.fromUriString(serverUrl).path(String.format("/api/event-log/%d", lastProcessedId)).build().toUri();
        return retrieveLog(currentLogUri);
    }

    private RemoteEventLog retrieveLog(@NonNull URI uri){
        ResponseEntity<List<StoredDomainEvent>> response = restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<StoredDomainEvent>>() {

        });
        if(response.getStatusCode() != HttpStatus.OK){
            throw new IllegalArgumentException("Couldn't retrieve log from URI "+ uri);
        }
        return new RemoteEventLogImpl(response);
    }
}
