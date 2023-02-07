package example.taskservice.service;

import example.taskservice.mapper.MapStructMapper;
import example.taskservice.model.CreateTaskRequest;
import example.taskservice.model.Task;
import example.taskservice.model.TaskDto;
import example.taskservice.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private MapStructMapper mapper;

    public Optional<TaskDto> getTask(Long id) {
        Optional<Task> task = taskRepository.findById(id);
        if (task.isPresent()) {
            return Optional.of(mapper.taskToTaskDto(task.get()));
        }

        return Optional.empty();
    }

    public TaskDto createTask(CreateTaskRequest createTaskRequest) {
        Task task = Task.builder()
                .name(createTaskRequest.getName())
                .description(createTaskRequest.getDescription())
                .deadline(createTaskRequest.getDeadline())
                .build();

        return mapper.taskToTaskDto(taskRepository.save(task));
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public List<Long> geAllTaskIds() {
        return taskRepository.getAllTaskIds();
    }
}
