package com.branch.demo.controller;

import com.branch.demo.model.UserResponse;
import com.branch.demo.service.GithubApi;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class GithubUserController {

    private final GithubApi githubApi;

    @GetMapping("/users/{username}")
    public Mono<UserResponse> getUserInformation(
            @PathVariable final String username
    ) {
        return githubApi.getUserResponse(username);
    }

}
