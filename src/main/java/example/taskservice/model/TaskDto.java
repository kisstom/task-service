package example.taskservice.model;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.Date;

@Value
@Jacksonized
@Builder(toBuilder = true)
public class TaskDto {

    Long id;

    String name;

    String description;

    Date deadline;
}
