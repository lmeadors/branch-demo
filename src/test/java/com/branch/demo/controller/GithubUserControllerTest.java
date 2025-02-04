package com.branch.demo.controller;

import com.branch.demo.model.UserResponse;
import com.branch.demo.service.GithubApi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static reactor.core.publisher.Mono.when;

@ExtendWith(MockitoExtension.class)
class GithubUserControllerTest {

    private GithubUserController githubUserController;

    @Mock
    private GithubApi githubApi;

    @BeforeEach
    public void beforeEachGithubUserControllerTest() {
        githubUserController = new GithubUserController(githubApi);
    }

    @Test
    void should_get_user_by_id_happy_path() {

        // setup
        final var testUserName = "test_user";
        UserResponse userResponse = UserResponse.builder()
                .userName(testUserName)
                .build();
        Mono<UserResponse> userMono = Mono.just(userResponse);
        assertNotNull(githubApi);

        Mockito.when(githubApi.getUserResponse(testUserName)).thenReturn(userMono);

        // run test
        final var testUser = githubUserController.getUserInformation(testUserName);

        // assert outcomes
        StepVerifier.create(testUser)
                .expectNext(userResponse)
                .verifyComplete();

    }

}
