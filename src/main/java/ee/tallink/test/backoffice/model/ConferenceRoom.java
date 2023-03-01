package ee.tallink.test.backoffice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConferenceRoom {

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
