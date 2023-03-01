package ee.tallink.test.backoffice.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoomStatus {
  private Integer biggest;
  private Integer totalEvents;
  private String status;
}
