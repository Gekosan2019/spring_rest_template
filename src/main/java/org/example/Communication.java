package org.example;

import org.example.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class Communication {

    private RestTemplate restTemplate;
    private final String URL = "http://94.198.50.185:7081/api/users";
    private List<String> cookie;

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<User> getAllUsers() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
        ResponseEntity<List<User>> responseEntity = restTemplate.exchange(URL, HttpMethod.GET, entity,
                new ParameterizedTypeReference<List<User>>() {
                });
        List<User> listUser = responseEntity.getBody();
        cookie = responseEntity.getHeaders().get("set-cookie");
        return listUser;
    }



    public void saveUser(User user) {
        HttpHeaders headers = getHttpHeaders();
        if (user.getId() == null) {
            user.setId(3L);
            HttpEntity<User> entity = new HttpEntity<>(user, headers);
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(URL, entity, String.class);
            System.out.print(responseEntity.getBody());
        } else {
            HttpEntity<User> entity = new HttpEntity<>(user, headers);
            ResponseEntity<String> responseEntity = restTemplate.exchange(URL, HttpMethod.PUT, entity, String.class);
            System.out.print(responseEntity.getBody());
        }
    }

    public void deleteUser(Long id) {
        HttpHeaders headers = getHttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL + "/" + id, HttpMethod.DELETE, entity, String.class);
        System.out.print(responseEntity.getBody());
    }

    public HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("cookie", cookie.stream().collect(Collectors.joining(";")));
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

}
