package com.xcompany.taskmanagementsystem.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private long id;
    private String username;
    private String password;
    private List<TaskDto> authoredTasks;
    private List<TaskDto> assignedTasks;

}
