package example.taskservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.springframework.lang.Nullable;

import java.util.Date;

@Value
@Jacksonized
@Builder(toBuilder = true)
public class TaskDto {

    Long id;

    String name;

    @Nullable
    String description;

    Date deadline;
}
