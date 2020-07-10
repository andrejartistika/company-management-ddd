package com.example.employeemanagement.port;

import com.example.employeemanagement.domain.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ProjectCatalogClient {
    private final RestTemplate restTemplate;
    private final String serverUrl;

    public ProjectCatalogClient(@Value("${app.projectmanagement.url}") String serverUrl, @Value("${app.usermanagement.connect-timeout-ms}") int connectionTimeout, @Value("${app.usermanagement.read-timeout-ms}") int readTimeout) {
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

    public Project findById(ProjectId id) {
        try {
            return restTemplate.exchange(uri().path("/api/projects/"+id.getId()).build().toUri(), HttpMethod.GET, null,
                    new ParameterizedTypeReference<Project>() {
                    }).getBody();
        } catch (Exception ex) {

            return null;
        }
    }

    public List<Task> getAllActiveTasksFromProject(ProjectId id){
        try {
            return restTemplate.exchange(uri().path("/api/projects/"+id.getId()+"/tasks/get/active").build().toUri(), HttpMethod.GET, null,
                    new ParameterizedTypeReference<List<Task>>() {
                    }).getBody();
        } catch (Exception ex) {

            return null;
        }
    }

    public void changeTaskState(ProjectId id, TaskId taskId, TaskStatus nextStatus) {
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(serverUrl + "/api/projects/" + id.getId() + "/tasks/change_status")
                    .queryParam("taskId", taskId.getId())
                    .queryParam("nextTaskState", nextStatus.toString());

            restTemplate.exchange(builder.toUriString(), HttpMethod.POST, null, byte[].class);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());

        }
    }

    public void addTaskToProject(ProjectId id, String taskName, String about){
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(serverUrl + "/api/projects/" + id.getId() + "/tasks/create")
                    .queryParam("taskName", taskName)
                    .queryParam("about", about);

             restTemplate.exchange(builder.toUriString(), HttpMethod.POST, null, byte[].class);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());

        }

    }

}
