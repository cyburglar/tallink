package ee.tallink.test.backoffice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConferenceDTO {

  private Long id;
  @NotNull
  private String title;
  @NotNull
  private Integer capacity;
  @NotNull
  private LocalDateTime eventStart;
  @NotNull
  private LocalDateTime eventEnd;
  @NotNull
  private Long roomId;
  private String description;

}
