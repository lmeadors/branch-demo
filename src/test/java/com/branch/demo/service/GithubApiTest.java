package com.branch.demo.service;

import com.branch.demo.model.UserRepository;
import com.branch.demo.model.UserResponse;
import com.branch.demo.model.github.GithubRepository;
import com.branch.demo.model.github.GithubUser;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
class GithubApiTest {

    private GithubApi githubApi;

    @Mock
    private WebClient webClient;
    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;
    @Mock
    private WebClient.RequestHeadersSpec userRequestHeadersSpec;
    @Mock
    private WebClient.RequestHeadersSpec repoRequestHeadersSpec;
    @Mock
    private WebClient.ResponseSpec userResponseSpec;
    @Mock
    private WebClient.ResponseSpec repoResponseSpec;

    @BeforeEach
    public void beforeEachGithubApiTest() {
        githubApi = new GithubApi(webClient);
    }

    @Test
    void getUser() {

        final var testUser = "test_user";

        final var githubUser = GithubUser.builder()
                .login(testUser)
                .build();
        final var repositoryList = List.of(GithubRepository.builder().build());

        final var githubUserMono = Mono.just(githubUser);
        final var listMono = Mono.just(repositoryList);

        // todo: this setup could be cleaner; it's noisy now...
        when(webClient.get())
                .thenReturn(requestHeadersUriSpec);

        // this will mock the user uri call
        when(requestHeadersUriSpec.uri(GithubApi.USER_URI, testUser))
                .thenReturn(userRequestHeadersSpec);
        when(userRequestHeadersSpec.retrieve())
                .thenReturn(userResponseSpec);
        lenient().doReturn(githubUserMono)
                .when(userResponseSpec).bodyToMono(GithubUser.class);

        // this will mock the repo list uri call
        when(requestHeadersUriSpec.uri(GithubApi.REPO_LIST_URI, testUser))
                .thenReturn(repoRequestHeadersSpec);
        when(repoRequestHeadersSpec.retrieve()).thenReturn(repoResponseSpec);
        lenient().doReturn(listMono)
                .when(repoResponseSpec).bodyToMono(new ParameterizedTypeReference<List<GithubRepository>>() {
                    // java generics can really be silly sometimes
                });

        final var response = githubApi.getUserResponse(testUser).block();
        assertNotNull(response);
        assertEquals(
                UserResponse.builder().userName(testUser).userRepositoryList(List.of(UserRepository.builder().build())).build(),
                response
        );

    }


}