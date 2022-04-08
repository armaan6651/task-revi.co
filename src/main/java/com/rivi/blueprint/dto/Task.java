package com.rivi.blueprint.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Builder
@Data
public class Task {
    private Long id;

    @NotBlank
    private String title;

    private String description;

    @Valid
    private User user;
}
