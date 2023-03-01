package ee.tallink.test.backoffice.util;

import ee.tallink.test.backoffice.dto.ConferenceDTO;
import ee.tallink.test.backoffice.dto.ConferenceRoomDTO;
import ee.tallink.test.backoffice.dto.FeedbackDTO;
import ee.tallink.test.backoffice.dto.UpdateConferenceDTO;
import ee.tallink.test.backoffice.dto.UpdateConferenceRoomDTO;
import ee.tallink.test.backoffice.model.Conference;
import ee.tallink.test.backoffice.model.ConferenceRoom;
import ee.tallink.test.backoffice.model.Feedback;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DTOUtils {

  private static ModelMapper mapper = new ModelMapper();

  @Autowired
  public DTOUtils(final ModelMapper mapper) {
    DTOUtils.mapper = mapper;
  }

  public static ConferenceRoomDTO toDTO(final ConferenceRoom room) {
    return mapper.map(room, ConferenceRoomDTO.class);
  }

  public static ConferenceRoom fromDTO(final ConferenceRoomDTO room) {
    return mapper.map(room, ConferenceRoom.class);
  }

  public static Conference fromDTO(final ConferenceDTO conference) {
    return mapper.map(conference, Conference.class);
  }

  public static ConferenceDTO toDTO(final Conference conference) {
    return mapper.map(conference, ConferenceDTO.class);
  }

  public static Conference fromConferenceUpdateDTO(final UpdateConferenceDTO dto) {
    return mapper.map(dto, Conference.class);
  }

  public static ConferenceRoom fromConferenceRoomUpdateDTO(final UpdateConferenceRoomDTO dto) {
    return mapper.map(dto, ConferenceRoom.class);
  }

  public static FeedbackDTO toDTO(final Feedback feedback) {
    return mapper.map(feedback, FeedbackDTO.class);
  }

  public static Feedback fromDTO(final FeedbackDTO feedback) {
    return mapper.map(feedback, Feedback.class);
  }

}
