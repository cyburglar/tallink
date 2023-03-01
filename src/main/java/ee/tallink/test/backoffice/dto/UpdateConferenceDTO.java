package ee.tallink.test.backoffice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateConferenceDTO {

  @NotNull
  private Long id;
  @NotNull
  private LocalDateTime eventStart;
  @NotNull
  private LocalDateTime eventEnd;
  @NotNull
  private Long roomId;

}
