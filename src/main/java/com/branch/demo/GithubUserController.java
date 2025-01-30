package com.branch.demo;

import com.branch.demo.model.Repository;
import com.branch.demo.model.UserResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GithubUserController {

    @GetMapping("/users/{username}")
    public UserResponse getUserInformation(
            @PathVariable final String username
    ) {

        final var repository = new Repository();
        repository.setName("foo");
        repository.setUrl("https://github.com/foo");

        final var response = new UserResponse();
        response.setUserName(username);
        response.setRepositoryList(List.of(repository));
        return response;

    }

}
