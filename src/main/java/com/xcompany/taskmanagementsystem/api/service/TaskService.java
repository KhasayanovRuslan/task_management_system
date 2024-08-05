package com.xcompany.taskmanagementsystem.api.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import com.xcompany.taskmanagementsystem.api.dto.TaskDto;
import com.xcompany.taskmanagementsystem.api.model.Priority;
import com.xcompany.taskmanagementsystem.api.model.Task;
import com.xcompany.taskmanagementsystem.api.model.TaskStatus;
import com.xcompany.taskmanagementsystem.api.model.User;
import com.xcompany.taskmanagementsystem.repository.TaskRepository;
import com.xcompany.taskmanagementsystem.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task getTaskById(Long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + taskId));
    }

    public Long createTask(TaskDto taskDto) {
        Task task = modelMapper.map(taskDto, Task.class);
        Task savedTask = taskRepository.save(task);
        return savedTask.getId();
    }

    @Transactional
    public Task updateTask(Long taskId, TaskDto updatedTaskDto) {
        Task existingTask = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + taskId));

        if (existingTask != null) {
            String updatedTitle = updatedTaskDto.getTitle();
            if (updatedTitle != null && !updatedTitle.isBlank()) {
                existingTask.setTitle(updatedTitle);
            }

            String updatedDescription = updatedTaskDto.getDescription();
            if (updatedDescription != null) {
                existingTask.setDescription(updatedDescription);
            }

            TaskStatus updatedStatus = updatedTaskDto.getStatus();
            if (updatedStatus != null) {
                existingTask.setStatus(updatedStatus);
            }

            Priority updatedPriority = updatedTaskDto.getPriority();
            if (updatedPriority != null) {
                existingTask.setPriority(updatedPriority);
            }

            return taskRepository.save(existingTask);
        }
        return null;
    }

    public Task addComment(Long taskId, String commentText) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + taskId));

        task.getComments().add(commentText);
        return taskRepository.save(task);
    }

    public Task updateTaskStatus(Long taskId, TaskStatus newStatus) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + taskId));

        task.setStatus(newStatus);
        return taskRepository.save(task);
    }

    public Task assignTaskExecutor(Long taskId, Long executorId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + taskId));

        User executor = userRepository.findById(executorId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + executorId));

        if (task.getExecutor() != null) {
            throw new IllegalArgumentException("Task already has an executor.");
        }

        task.setExecutor(executor);
        return taskRepository.save(task);
    }

    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }

}
