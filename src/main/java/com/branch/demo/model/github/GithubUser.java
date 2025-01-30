package com.branch.demo.model.github;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class GithubUser {
    private String login;
    private String name;
    @JsonProperty("avatar_url")
    private String avatar;
    private String location;
    private String email;
    private String url;
    @JsonProperty("created_at")
    private String createdAt;
}
