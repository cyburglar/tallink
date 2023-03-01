package ee.tallink.test.backoffice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

  private Long id;
  @NotNull
  Conference conference;
  @NotNull
  private String firstname;
  @NotNull
  private String lastname;

}
