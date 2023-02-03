package example.taskservice.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import example.taskservice.model.TaskDto;
import example.taskservice.repository.TaskRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static example.taskservice.util.Util.simpleDateFormat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BasicIT {

    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskRepository taskRepository;

    @BeforeAll
    static void setupClass() {
        om.setDateFormat(simpleDateFormat);
    }

    @BeforeEach
    public void setup() {
        taskRepository.deleteAll();
    }

    @Test
    void test() throws Exception {
        // --------------------------- POST -------------------------------------

        TaskDto taskDto = TaskDto.builder()
                .name("name")
                .deadline(simpleDateFormat.parse("2019-03-12"))
                .description("desc")
                .build();

        MvcResult mvcResult = mockMvc.perform(post("/api/v1/task")
                .with(httpBasic("userMike", "123"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(taskDto))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
        TaskDto actualDto = om.readValue(mvcResult.getResponse().getContentAsString(), TaskDto.class);

        assertTrue(taskRepository.findById(actualDto.getId()).isPresent());
        assertTrue(new ReflectionEquals(taskDto, "id").matches(actualDto));

        // -------------------------- GET ------------------------------

        TaskDto expectedDto = actualDto;

        mvcResult = mockMvc.perform(get("/api/v1/task/" + actualDto.getId())
                .with(httpBasic("userMike", "123"))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        actualDto = om.readValue(mvcResult.getResponse().getContentAsString(), TaskDto.class);
        assertEquals(expectedDto, actualDto);

        // ---------------------------- findAll ----------------------------

        TaskDto taskDto2 = TaskDto.builder()
                .name("name2")
                .deadline(simpleDateFormat.parse("2020-03-12"))
                .description("desc2")
                .build();

        mockMvc.perform(post("/api/v1/task")
                .with(httpBasic("userMike", "123"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(taskDto2))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        mvcResult = mockMvc.perform(get("/api/v1/task/all")
                .with(httpBasic("userMike", "123"))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        List<Long> expectedIds = new ArrayList<>();
        expectedIds.add(1L);
        expectedIds.add(2L);

        assertEquals(expectedIds, om.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<Long>>(){}));

        // -------------------------------- DELETE ------------------------------

        mockMvc.perform(delete("/api/v1/task/1")
                .with(httpBasic("userMike", "123"))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        mockMvc.perform(get("/api/v1/task/1")
                .with(httpBasic("userMike", "123"))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

    }

}
