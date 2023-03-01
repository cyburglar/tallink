package ee.tallink.test.backoffice.util;

import ee.tallink.test.backoffice.dto.ConferenceDTO;
import ee.tallink.test.backoffice.dto.ConferenceRoomDTO;
import ee.tallink.test.backoffice.model.Conference;
import ee.tallink.test.backoffice.model.ConferenceRoom;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DTOUtilsTest {

  @Test
  void testConferenceRoomToDTO() {
    ConferenceRoom src = new ConferenceRoom(
        1L, "Title", 100, "Tallinn", "OPEN"
    );

    ConferenceRoomDTO dto = DTOUtils.toDTO(src);

    assertAll(
        () -> assertEquals(src.getStatus(), dto.getStatus()),
        () -> assertEquals(src.getCapacity(), dto.getCapacity()),
        () -> assertEquals(src.getTitle(), dto.getTitle()),
        () -> assertEquals(src.getId(), dto.getId()),
        () -> assertEquals(src.getLocation(), dto.getLocation())
    );

  }

  @Test
  void testConferenceRoomFromDTO() {
    ConferenceRoomDTO src = new ConferenceRoomDTO(
        1L, "Title", 100, "Tallinn", "DUMMY"
    );

    ConferenceRoom dto = DTOUtils.fromDTO(src);

    assertAll(
        () -> assertEquals(src.getStatus(), dto.getStatus()),
        () -> assertEquals(src.getCapacity(), dto.getCapacity()),
        () -> assertEquals(src.getTitle(), dto.getTitle()),
        () -> assertEquals(src.getId(), dto.getId()),
        () -> assertEquals(src.getLocation(), dto.getLocation())
    );

  }

  @Test
  void testConferenceToDTO() {
    LocalDateTime now = LocalDateTime.now();
    Conference src = new Conference(
        1L, "Title", now, now, 10L,  "Conference 1", 10
    );

    ConferenceDTO dto = DTOUtils.toDTO(src);

    assertAll( "testConferenceFromDTO",
        () -> assertNotNull(dto.getRoomId()),
        () -> assertEquals(src.getRoomId(), dto.getRoomId()),
        () -> assertEquals(dto.getEventStart(), src.getEventStart()),
        () -> assertEquals(dto.getEventEnd(), src.getEventEnd())
    );

  }

  @Test
  void testConferenceFromDTO() {
    ConferenceDTO src = new ConferenceDTO(
        1L, "Title", 100,
        LocalDateTime.now(), LocalDateTime.now(), 10L,  "Conference 1"
    );

    Conference dto = DTOUtils.fromDTO(src);

    assertAll( "testConferenceFromDTO",
        () -> assertNotNull(dto.getRoomId()),
        () -> assertEquals(dto.getRoomId(), src.getRoomId()),
        () -> assertEquals(dto.getEventStart(), src.getEventStart()),
        () -> assertEquals(dto.getEventEnd(), src.getEventEnd())
    );
  }
}