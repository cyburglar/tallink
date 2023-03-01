package ee.tallink.test.backoffice.service;

import ee.tallink.test.backoffice.exceptions.ChangeDataException;
import ee.tallink.test.backoffice.exceptions.DataNotFoundException;
import ee.tallink.test.backoffice.exceptions.DuplicateException;
import ee.tallink.test.backoffice.exceptions.TimeOverlapsException;
import ee.tallink.test.backoffice.model.Conference;
import ee.tallink.test.backoffice.model.ConferenceRoom;
import ee.tallink.test.backoffice.model.Customer;
import ee.tallink.test.backoffice.model.Feedback;
import ee.tallink.test.backoffice.model.RoomStatus;
import ee.tallink.test.backoffice.repository.ConferenceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

import static ee.tallink.test.backoffice.util.DateTimeUtils.isOverlapsConferenceTimes;
import static java.util.Arrays.asList;

@AllArgsConstructor
@Service
public class ConferenceService {

  private final static List<String> ROOM_CLOSED_STATUSES = asList("UNDER_CONSTRUCTION","CLOSED");
  private final ConferenceRepository repository;

  public ConferenceRoom registerRoom(ConferenceRoom conferenceRoom) {
    ConferenceRoom roomByTitle = getRoomByTitle(conferenceRoom.getTitle());
    if(!ObjectUtils.isEmpty(roomByTitle)){
      throw new DuplicateException(
          MessageFormat.format("Room {0} already exists in the system", conferenceRoom.getTitle())
      );
    }
    repository.addRoom(conferenceRoom);
    return getRoomByTitle(conferenceRoom.getTitle());
  }

  public ConferenceRoom updateConferenceRoom(ConferenceRoom conferenceRoom) {
    ConferenceRoom room = repository.findConferenceRoomById(conferenceRoom.getId());
    if(ObjectUtils.isEmpty(room)){
      throw new DataNotFoundException(
          MessageFormat.format("Conference room with id {0} not found", String.valueOf(conferenceRoom.getId()))
      );
    }
    RoomStatus statistics = repository.getConferenceRoomStatistics(room.getId());
    if(!ObjectUtils.isEmpty(statistics)){
      if(
          conferenceRoom.getCapacity().compareTo(room.getCapacity()) < 0
        && statistics.getBiggest().compareTo(conferenceRoom.getCapacity()) > 0
      ) {
         throw new ChangeDataException("Unable to reduce capacity as there are exists big event");
      }
      if(
          !statistics.getStatus().equals(conferenceRoom.getStatus())
          && ROOM_CLOSED_STATUSES.contains(conferenceRoom)
          && statistics.getTotalEvents().intValue() > 0
      ) {
        throw new ChangeDataException("Unable to close as there are exists events");
      }
    }

    room.setStatus(conferenceRoom.getStatus());
    room.setCapacity(conferenceRoom.getCapacity());
    repository.updateConferenceRoom(room);
    return room;
  }

  public List<ConferenceRoom> findConferenceRooms() {
    return repository.findAllConferenceRooms();
  }

  public Conference registerConference(final Conference conference) {
    ConferenceRoom room = findConferenceRoomById(conference.getRoomId());
    List<Conference> conferences = repository.findConferencesByRoomId(room.getId());
    checkOverlaps(conference, conferences);
    Conference conferenceByTitle = getConferenceByTitleAndRoomId(
        conference.getTitle(), conference.getRoomId()
    );
    if(!ObjectUtils.isEmpty(conferenceByTitle)){
      throw new DuplicateException(
          MessageFormat.format("Conference {0} already exists in the system", conference.getTitle())
      );
    }
    repository.addConference(conference);
    return getConferenceByTitleAndRoomId(
        conference.getTitle(), conference.getRoomId()
    );
  }

  public Integer findConferenceCapacity(final Long conferenceId) {
    return repository.findConferenceCapacity(conferenceId);
  }

  public Conference updateConference(Conference conference) {
    Conference dbConference = findConferenceById(conference.getId());
    List<Conference> conferences = repository.findConferencesByRoomId(dbConference.getRoomId());
    checkOverlaps(conference, conferences);

    dbConference.setTitle(conference.getTitle());
    dbConference.setEventStart(conference.getEventStart());
    dbConference.setEventEnd(conference.getEventEnd());
    dbConference.setRoomId(conference.getRoomId());
    repository.updateConference(dbConference);
    return repository.findConferenceById(dbConference.getId());
  }

  public boolean cancelConference(final Long conferenceId) {
    findConferenceById(conferenceId);
    return repository.cancelConference(conferenceId);
  }

  public void addFeedback(final Feedback feedback) {
    findConferenceById(feedback.getConferenceId());
    findCustomerById(feedback.getCustomerId());
    repository.addFeedback(feedback);
  }

  public List<Feedback> findConferenceFeedbacks(final Long conferenceId) {
    findConferenceById(conferenceId);
    return repository.findConferenceFeedbacks(conferenceId);
  }

  protected ConferenceRoom getRoomByTitle(final String title) {
    return repository.findConferenceRoomByTitle(title);
  }

  protected Conference getConferenceByTitleAndRoomId(final String title, final Long roomId) {
    return repository.findConferenceByTitleAndRoomId(title, roomId);
  }

  protected ConferenceRoom findConferenceRoomById(final Long id) {
    ConferenceRoom room = repository.findConferenceRoomById(id);
    if(ObjectUtils.isEmpty(room)){
      throw new DataNotFoundException(
          MessageFormat.format(
              "Conference room with id {0} not found", String.valueOf(id)
          )
      );
    }
    return room;
  }

  protected Conference findConferenceById(final Long id) {
    Conference dbConference = repository.findConferenceById(id);
    if(ObjectUtils.isEmpty(dbConference)){
      throw new DataNotFoundException(
          MessageFormat.format(
              "Conference with ID {0} not found", id
          )
      );
    }
    return dbConference;
  }

  protected Customer findCustomerById(final Long id) {
    Customer customer = repository.findCustomerById(id);
    if(ObjectUtils.isEmpty(customer)){
      throw new DataNotFoundException(
          MessageFormat.format(
              "Customer with ID {0} not found", id
          )
      );
    }
    return customer;
  }

  protected boolean checkOverlaps(Conference conference, List<Conference> conferences) {
    if(!ObjectUtils.isEmpty(conferences.toArray())){
      List<Conference> overlaps = conferences.stream().filter( conf -> isOverlapsConferenceTimes(
          conf.getEventStart(), conf.getEventEnd(),
          conference.getEventStart(), conference.getEventEnd()
      ) ).collect(Collectors.toList());
      if (overlaps.size() > 0) {
        throw new TimeOverlapsException(
            MessageFormat.format(
                "Conference times overlaps with at least with another one. {0}", overlaps.get(0).getTitle()
            )
        );
      }
    }
    return false;
  }

}
