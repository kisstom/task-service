package example.taskservice.mapper;

import example.taskservice.model.Task;
import example.taskservice.model.TaskDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;

import static example.taskservice.util.Util.simpleDateFormat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class MapStructMapperTest {

    @Autowired
    private MapStructMapper mapStructMapper;

    @Test
    void testCustomerDtoToCustomer() throws Exception {
        Task task = Task.builder()
                .id(1L)
                .name("name")
                .deadline(simpleDateFormat.parse("2019-03-12"))
                .description("desc")
                .build();

        TaskDto expectedTaskDto = TaskDto.builder()
                .id(1L)
                .name("name")
                .description("desc")
                .deadline(simpleDateFormat.parse("2019-03-12"))
                .build();

        TaskDto taskDto = mapStructMapper.taskToTaskDto(task);
        assertEquals(expectedTaskDto, taskDto);
    }
}
