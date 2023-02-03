package example.taskservice.repository;

import example.taskservice.model.Task;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class TaskRepositoryTest {

    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private TaskRepository taskRepository;

    @Test
    void test() throws ParseException {
        Task task1 = Task.builder()
                .description("desc")
                .name("name")
                .deadline(simpleDateFormat.parse("2019-03-12"))
                .build();

        Task task2 = Task.builder()
                .description("desc2")
                .name("name2")
                .deadline(simpleDateFormat.parse("2019-03-12"))
                .build();

        Task task3 = Task.builder()
                .description("desc3")
                .name("name3")
                .deadline(simpleDateFormat.parse("2019-03-12"))
                .build();


        taskRepository.save(task1);
        taskRepository.save(task2);
        taskRepository.save(task3);

        List<Long> allTaskId = taskRepository.getAllTaskId();
        assertEquals(Arrays.asList(1L, 2L, 3L), allTaskId);
    }
}