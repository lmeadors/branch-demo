package com.branch.demo.controller;

import com.branch.demo.model.UserResponse;
import com.branch.demo.service.GithubApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
public class GithubUserController {

    private final GithubApi githubApi;

    @GetMapping("/users/{username}")
    public Mono<UserResponse> getUserInformation(
            @PathVariable final String username
    ) {
        log.debug("Getting user information for username '{}' from github API.", username);
        final var response = githubApi.getUserResponse(username);
        log.debug("Got user information for username {} from github API.", username);
        return response;
    }

}
