package com.rivi.blueprint.dto;

import com.rivi.blueprint.utils.Constant;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
@Data
public class User {
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String email;

    @NotNull
    private Constant.UserType type;
}
