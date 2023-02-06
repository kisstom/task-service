package example.taskservice.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import example.taskservice.config.BasicConfiguration;
import example.taskservice.config.SecurityConfig;
import example.taskservice.model.TaskDto;
import example.taskservice.service.TaskService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static example.taskservice.util.Util.simpleDateFormat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {TaskController.class})
@Import({SecurityConfig.class, BasicConfiguration.class})
class TaskControllerTest {

    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @BeforeAll
    static void setup() {
        om.setDateFormat(simpleDateFormat);
    }

    @Test
    void testGetAll() throws Exception {
        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        ids.add(3L);
        ids.add(2L);

        when(taskService.geAllTaskIds()).thenReturn(ids);

        MvcResult mvcResult = mockMvc.perform(get("/api/v1/task/all")
                .with(httpBasic("userMike", "123"))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(ids, om.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<Long>>(){}));
    }

    @Test
    void testGet() throws Exception {
        TaskDto taskDto = TaskDto.builder()
                .id(1L)
                .name("name")
                .description("desc")
                .deadline(simpleDateFormat.parse("2019-03-12"))
                .build();

        when(taskService.getTask(1L)).thenReturn(Optional.of(taskDto));

        MvcResult mvcResult = mockMvc.perform(get("/api/v1/task/1")
                .with(httpBasic("userMike", "123"))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(taskDto, om.readValue(mvcResult.getResponse().getContentAsString(), TaskDto.class));
    }

    @Test
    void testCreate() throws Exception {
        TaskDto taskDto = TaskDto.builder()
                .name("name")
                .deadline(simpleDateFormat.parse("2019-03-12"))
                .description("desc")
                .build();

        TaskDto expectedTaskDto = taskDto.toBuilder().id(1L).build();
        when(taskService.createTask(eq(taskDto))).thenReturn(expectedTaskDto);

        MvcResult mvcResult = mockMvc.perform(post("/api/v1/task")
                .with(httpBasic("userMike", "123"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(taskDto))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn();

        assertEquals(expectedTaskDto, om.readValue(mvcResult.getResponse().getContentAsString(), TaskDto.class));
    }
}
