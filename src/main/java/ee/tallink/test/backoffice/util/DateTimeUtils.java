package ee.tallink.test.backoffice.util;

import java.time.LocalDateTime;

public class DateTimeUtils {

  public final static boolean isOverlapsConferenceTimes(
      LocalDateTime event1_start, LocalDateTime event1_end,
      LocalDateTime event2_start, LocalDateTime event2_end
  ) {
    return event1_start.isBefore(event2_start) && event1_end.isAfter(event2_start) ||
        event1_start.isBefore(event2_end) && event1_end.isAfter(event2_end) ||
        event1_start.isBefore(event2_start) && event1_end.isAfter(event2_end) ||
        event1_start.isAfter(event2_start) && event1_end.isBefore(event2_end) ||
        event1_start.isEqual(event2_start) && event2_end.isEqual(event1_end);
  }

}
