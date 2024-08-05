package com.xcompany.taskmanagementsystem.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.xcompany.taskmanagementsystem.api.model.Priority;
import com.xcompany.taskmanagementsystem.api.model.TaskStatus;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {

    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private Priority priority;
    private UserDto author;
    private UserDto executor;
    private List<String> comments;
    private Date createdDate;

}
