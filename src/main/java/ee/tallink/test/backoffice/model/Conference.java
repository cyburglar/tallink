package ee.tallink.test.backoffice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Conference {

  private Long id;
  private String title;
  private LocalDateTime eventStart;
  private LocalDateTime eventEnd;
  private Long roomId;
  private String description;
  @JsonIgnore
  private Integer capacity;

}
