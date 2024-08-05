package com.xcompany.taskmanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.xcompany.taskmanagementsystem.api.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>   {
}
