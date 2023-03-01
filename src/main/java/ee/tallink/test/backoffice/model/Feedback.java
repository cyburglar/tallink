package ee.tallink.test.backoffice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Feedback {

  private Long id;
  @NotNull
  private Long conferenceId;
  @NotNull
  private Long customerId;
  private String customerName;
  @NotNull
  private String comment;
  @NotNull
  private LocalDateTime created;

}
