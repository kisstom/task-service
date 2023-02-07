package example.taskservice.mapper;

import example.taskservice.model.Task;
import example.taskservice.model.TaskDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface MapStructMapper {

    TaskDto taskToTaskDto(Task task);

}
