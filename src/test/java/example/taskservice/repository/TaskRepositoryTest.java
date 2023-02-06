package example.taskservice.repository;

import example.taskservice.model.Task;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import static example.taskservice.util.Util.simpleDateFormat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Test
    public void testSave() throws ParseException {
        Task task = Task.builder()
                .description("desc")
                .name("name")
                .deadline(simpleDateFormat.parse("2019-03-12"))
                .build();

        Task save = taskRepository.save(task);
        Task retrieved = taskRepository.findById(save.getId()).get();
        assertTrue(new ReflectionEquals(task, "id").matches(retrieved));
    }

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
        assertEquals(Arrays.asList(task1.getId(), task2.getId(), task3.getId()), allTaskId);
    }
}
