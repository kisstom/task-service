package example.taskservice.model;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Value
@Jacksonized
@Builder(toBuilder = true)
public class CreateTaskRequest {
    @NotNull
    String name;

    String description;

    @NotNull
    Date deadline;
}
