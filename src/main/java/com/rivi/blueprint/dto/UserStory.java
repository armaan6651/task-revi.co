package com.rivi.blueprint.dto;

import com.rivi.blueprint.utils.Constant;
import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Builder
@Data
public class UserStory {
    private Long id;

    @NotBlank
    private String title;

    private String description;

    private Date dueDate;

    @Valid
    private User user;

    private List<Task> taskList;

    @NotNull
    private Constant.StoryState status;

    public Boolean isAllFieldsNull(UserStory userStory) {
        return Arrays.stream(UserStory.class.getDeclaredFields()).allMatch(f -> {
            try {
                return f.get(userStory) == null;
            } catch (IllegalAccessException e) {
                return false;
            }
        });
    }
}
