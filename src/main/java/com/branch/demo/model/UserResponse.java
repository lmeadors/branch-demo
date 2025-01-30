package com.branch.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class UserResponse {

    @JsonProperty("user_name")
    private String userName;
    @JsonProperty("display_name")
    private String displayName;
    private String avatar;
    @JsonProperty("geo_location")
    private String geoLocation;
    private String email;
    private String url;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("repos")
    private List<UserRepository> userRepositoryList = List.of();

}
