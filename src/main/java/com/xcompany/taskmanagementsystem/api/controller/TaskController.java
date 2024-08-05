package com.xcompany.taskmanagementsystem.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.xcompany.taskmanagementsystem.api.dto.TaskDto;
import com.xcompany.taskmanagementsystem.api.model.Task;
import com.xcompany.taskmanagementsystem.api.model.TaskStatus;
import com.xcompany.taskmanagementsystem.api.service.TaskService;

import java.util.List;

@RestController
@Tag(name = "task management methods")
@RequiredArgsConstructor
@RequestMapping("/api/tasks")
@Slf4j
public class TaskController {
    private final TaskService taskService;

    @GetMapping
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/{taskId}")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(
            summary = "Получение задачи по ее id",
            description = "Обращение к БД и поиск задачи по id"
    )
    public ResponseEntity<Task> getTaskById(@PathVariable Long taskId) {
        Task task = taskService.getTaskById(taskId);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @PostMapping
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Long> createTask(@RequestBody TaskDto taskDto) {
        Long taskId = taskService.createTask(taskDto);
        return new ResponseEntity<>(taskId, HttpStatus.CREATED);
    }

    @PutMapping("/{taskId}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Task> updateTask(@PathVariable Long taskId,
                                           @RequestBody @Valid TaskDto updatedTaskDto) {
        Task updatedTask = taskService.updateTask(taskId, updatedTaskDto);
        log.info("Amendment task: {}" , taskId);
        return new ResponseEntity<>(updatedTask, HttpStatus.OK);
    }

    @PostMapping("/{taskId}/comments")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Task> addComment(@PathVariable Long taskId, @RequestBody String commentText) {
        Task task = taskService.addComment(taskId, commentText);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @PutMapping("/{taskId}/status")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Task> updateTaskStatus(@PathVariable Long taskId,
                                                 @RequestBody @Valid TaskStatus newStatus) {
        Task updatedTask = taskService.updateTaskStatus(taskId, newStatus);
        return new ResponseEntity<>(updatedTask, HttpStatus.OK);
    }

    @PostMapping("/{taskId}/executor/{executorId}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Task> assignTaskExecutor(@PathVariable Long taskId, @PathVariable Long executorId) {
        Task task = taskService.assignTaskExecutor(taskId, executorId);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @DeleteMapping("/{taskId}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
