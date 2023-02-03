package example.taskservice.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import example.taskservice.model.Task;
import example.taskservice.model.TaskDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class MapStructMapperTest {

    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private MapStructMapper mapStructMapper;

    @Test
    void testCustomerDtoToCustomer() throws Exception {
        TaskDto taskDto = TaskDto.builder()
                .id(1L)
                .name("name")
                .deadline(simpleDateFormat.parse("2019-03-12"))
                .description("desc")
                .build();

        Task task = mapStructMapper.taskDtoToTask(taskDto);
        Task expected = Task.builder()
                .id(1L)
                .name("name")
                .description("desc")
                .deadline(simpleDateFormat.parse("2019-03-12"))
                .build();

        assertEquals(expected, task);
    }
}
