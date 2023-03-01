package ee.tallink.test.backoffice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateConferenceRoomDTO {

  @NotNull
  private Long id;
  @NotNull
  private String status;
  @NotNull
  private Long capacity;

}
