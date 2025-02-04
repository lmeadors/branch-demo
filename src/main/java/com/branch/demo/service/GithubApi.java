package com.branch.demo.service;

import com.branch.demo.model.UserRepository;
import com.branch.demo.model.UserResponse;
import com.branch.demo.model.github.GithubRepository;
import com.branch.demo.model.github.GithubUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.List;
import java.util.function.Function;

/**
 * Simple wrapper for the GitHub api. Docs for it are available
 * <a href="https://docs.github.com/en/rest/using-the-rest-api">here</a>.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class GithubApi {

    // these are here to make testing simpler
    public static final String USER_URI = "/{username}";
    public static final String REPO_LIST_URI = "/{username}/repos";

    private final WebClient webClient;

    @Cacheable("GithubApi")
    public Mono<UserResponse> getUserResponse(
            final String username
    ) {

        log.debug("getUserResponse username: {}", username);
        final var githubUserMono = webClient.get()
                .uri(USER_URI, username)
                .retrieve()
                .bodyToMono(GithubUser.class);

        final var githubRepositoryMono = webClient.get()
                .uri(REPO_LIST_URI, username)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<GithubRepository>>() {
                    // java generics can be silly sometimes
                });

        return Mono.zip(githubUserMono, githubRepositoryMono)
                .map(objects -> {

                    final var user = objects.getT1();

                    final var repositoryList = objects.getT2().stream()
                            .map(githubRepository -> UserRepository.builder()
                                    .name(githubRepository.getName())
                                    .url(githubRepository.getUrl())
                                    .build())
                            .toList();

                    return UserResponse.builder()
                            .url(user.getUrl())
                            .avatar(user.getAvatar())
                            .email(user.getEmail())
                            .createdAt(user.getCreatedAt())
                            .displayName(user.getName())
                            .userName(user.getLogin())
                            .geoLocation(user.getLocation())
                            .userRepositoryList(repositoryList)
                            .build();
                })
                .onErrorMap(e -> {
                    log.error(e.toString(), e);
                    return new UserNotFoundException(username);
                });

    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static class UserNotFoundException extends RuntimeException {
        public UserNotFoundException(String message) {
            super(message);
        }
    }

}
