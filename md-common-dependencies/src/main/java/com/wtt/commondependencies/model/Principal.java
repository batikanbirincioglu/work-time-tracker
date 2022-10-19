package com.wtt.commondependencies.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Principal {
    private Long id;
    private String name;
    private String email;
    private boolean manager;
    private String username;
    private String encodedPassword;
}
