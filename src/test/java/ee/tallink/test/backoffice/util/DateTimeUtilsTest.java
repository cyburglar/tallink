package ee.tallink.test.backoffice.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.MatcherAssert.assertThat;

class DateTimeUtilsTest {

  @Test
  void testIsOverlapsConferenceTimes() {
     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    LocalDateTime s1 = LocalDateTime.parse("2023-02-27 12:30", formatter);
    LocalDateTime e1 = LocalDateTime.parse("2023-02-27 17:30", formatter);
    LocalDateTime s2 = LocalDateTime.parse("2023-02-28 12:30", formatter);
    LocalDateTime e2 = LocalDateTime.parse("2023-02-29 12:30", formatter);

    Assertions.assertEquals(true, DateTimeUtils.isOverlapsConferenceTimes(s1,s2,e1,e2));
    Assertions.assertEquals(false, DateTimeUtils.isOverlapsConferenceTimes(e2,s2,s2,e1));
    Assertions.assertEquals(false, DateTimeUtils.isOverlapsConferenceTimes(s1,e1,s2,e2));
    Assertions.assertEquals(false, DateTimeUtils.isOverlapsConferenceTimes(e1,s2,s1,e2));

  }

//  @Test
//  public void testJsonFormatAnnotationToFormatDate()
//      throws JsonProcessingException {
//
//    DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
//    String toParse = "01-03-2023 02:30";
//    LocalDateTime date = LocalDateTime.parse(toParse, df);
//    Conference event = new Conference() {{
//      setTitle("Conference 22");
//      setEventStart(date);
//    }};
//
//    ObjectMapper mapper = new ObjectMapper();
//    mapper.registerModule(new JavaTimeModule());
//    String result = mapper.writeValueAsString(event);
//    assertThat(result, containsString(toParse));
//  }

}