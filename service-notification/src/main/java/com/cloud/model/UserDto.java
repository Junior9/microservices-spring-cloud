package com.cloud.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {

    private Long id;
    private String name;
    private String email;
    private String paassword;
    
}
