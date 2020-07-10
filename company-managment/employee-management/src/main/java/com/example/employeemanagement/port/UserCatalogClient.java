package com.example.employeemanagement.port;

import com.example.employeemanagement.domain.model.User;
import com.example.employeemanagement.domain.model.UserId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;

@Service
public class UserCatalogClient {
    private final RestTemplate restTemplate;
    private final String serverUrl;

    public UserCatalogClient(@Value("${app.usermanagement.url}") String serverUrl, @Value("${app.usermanagement.connect-timeout-ms}") int connectionTimeout, @Value("${app.usermanagement.read-timeout-ms}") int readTimeout) {
        this.restTemplate = new RestTemplate();
        this.serverUrl = serverUrl;

        var requestFactory = new SimpleClientHttpRequestFactory();
        // Never ever do a remote call without a finite timeout!
        requestFactory.setConnectTimeout(connectionTimeout);
        requestFactory.setReadTimeout(readTimeout);
        restTemplate.setRequestFactory(requestFactory);
    }

    private UriComponentsBuilder uri() {
        return UriComponentsBuilder.fromUriString(serverUrl);
    }



    public User findById(UserId id) {
        try {
            return restTemplate.exchange(uri().path("/api/users/"+id.getId()).build().toUri(), HttpMethod.GET, null,
                    new ParameterizedTypeReference<User>() {
                    }).getBody();
        } catch (Exception ex) {

            return null;
        }
    }
}
