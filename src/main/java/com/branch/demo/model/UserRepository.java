package com.branch.demo.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRepository {
    private String name;
    private String url;
}
