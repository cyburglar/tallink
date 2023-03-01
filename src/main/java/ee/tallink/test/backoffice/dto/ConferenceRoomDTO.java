package ee.tallink.test.backoffice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConferenceRoomDTO {

  private Long id;
  @NotNull
  private String title;
  @NotNull
  private Integer capacity;
  @NotNull
  private String location;
  @NotNull
  private String status;

}
