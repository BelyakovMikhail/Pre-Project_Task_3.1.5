package org.rest.Communication;

import org.rest.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class Communication {

    private final RestTemplate restTemplate;
    static final String URL = "http://91.241.64.178:7081/api/users";
    private HttpHeaders headers = new HttpHeaders();
    private String cookie;

    @Autowired
    public Communication(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    public List<User> getAllUsers() {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(URL, String.class);
        List<String> cookies = responseEntity.getHeaders().get("Set-Cookie");
        headers.set("Cookie",cookies.stream().collect(Collectors.joining(";")));
        cookie = responseEntity.getHeaders().getFirst("Set-Cookie");
        // отправляем request и получаем его в ResponseEntity
        ResponseEntity<List<User>> response = restTemplate.exchange(URL, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<User>>() {});
        return response.getBody();
    }
    public void saveUser(User user) {
        HttpEntity<User> entity = new HttpEntity<>(user, getHeaders());
        ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.POST, entity,String.class);
        System.out.print(response.getBody());

    }

    public void updateUser(User user) {
        HttpEntity<User> entity = new HttpEntity<>(user, getHeaders());
        ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.PUT, entity, String.class);
        System.out.print(response.getBody());
    }

    public void deleteUser(Long id) {
        HttpEntity<Long> entity = new HttpEntity<>(id, getHeaders());
        ResponseEntity<String> response = restTemplate.exchange(URL + "/" + id, HttpMethod.DELETE, entity, String.class);
        System.out.print(response.getBody());
    }

    public HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", cookie);
        return headers;
    }
}
