package ee.tallink.test.backoffice.controller;

import ee.tallink.test.backoffice.dto.ConferenceDTO;
import ee.tallink.test.backoffice.dto.ConferenceRoomDTO;
import ee.tallink.test.backoffice.dto.FeedbackDTO;
import ee.tallink.test.backoffice.dto.ResponseDTO;
import ee.tallink.test.backoffice.dto.UpdateConferenceDTO;
import ee.tallink.test.backoffice.dto.UpdateConferenceRoomDTO;
import ee.tallink.test.backoffice.model.Conference;
import ee.tallink.test.backoffice.model.ConferenceRoom;
import ee.tallink.test.backoffice.service.ConferenceService;
import ee.tallink.test.backoffice.util.DTOUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/conferences")
@Validated
public class ConferenceController {

  private final ConferenceService service;

  public ConferenceController(ConferenceService service) {
    this.service = service;
  }

  @GetMapping(path = "/rooms")
  public List<ConferenceRoomDTO> getConferenceRooms() {
    return service.findConferenceRooms().stream().map(
        room -> DTOUtils.toDTO(room)
    ).collect(Collectors.toList());
  }

  @PostMapping(path = "/add_room")
  public ConferenceRoomDTO registerConferenceRooms(@RequestBody ConferenceRoomDTO room) {
     return DTOUtils.toDTO(service.registerRoom(DTOUtils.fromDTO(room)));
  }

  @PostMapping(path = "/register_conference")
  public ConferenceDTO registerConference(@RequestBody ConferenceDTO conference) {
    return DTOUtils.toDTO(service.registerConference(DTOUtils.fromDTO(conference)));
  }

  @DeleteMapping(path = "/cancel_conference/{id}")
  @ResponseBody
  public ResponseDTO cancelConference(@PathVariable Long id) {
    Boolean res = service.cancelConference(id);
    return new ResponseDTO() {{
       setResult(
           res != null && res.booleanValue() ?
               MessageFormat.format("Conference {0} canceled" ,id ):
               MessageFormat.format("Can't cancel conference {0}", id)
           );
       setDatetime(LocalDateTime.now());
    }};
  }


  @GetMapping(path = "/check_conference/{id}")
  @ResponseBody
  public ResponseDTO getCheckConference(@PathVariable Long id) {
    Integer capacity = service.findConferenceCapacity(id);
    return response(
          capacity != null && capacity.intValue() > 0 ?
              MessageFormat.format("Available {0} places" , capacity ): "Sorry, no places anymore"
      );
  }

  @PutMapping(path = "/update_conference/{id}")
  @ResponseBody
  public ConferenceDTO updateConference(
      @PathVariable Long id, @RequestBody UpdateConferenceDTO updateConference
  ) {
    if (ObjectUtils.isEmpty(id) || !id.equals(updateConference.getId())) {
      throw new IllegalArgumentException("Wrong data");
    }

    Conference conference = service.updateConference(DTOUtils.fromConferenceUpdateDTO(updateConference));
    return DTOUtils.toDTO(conference);
  }


  @PutMapping(path = "/update_room/{id}")
  @ResponseBody
  public ConferenceRoomDTO updateConferenceRoom(
      @PathVariable Long id, @RequestBody UpdateConferenceRoomDTO dto
  ) {
    if (ObjectUtils.isEmpty(id) || !id.equals(dto.getId())) {
      throw new IllegalArgumentException("Wrong data");
    }
    ConferenceRoom conferenceRoom = service.updateConferenceRoom(DTOUtils.fromConferenceRoomUpdateDTO(dto));
    return DTOUtils.toDTO(conferenceRoom);
  }

  @GetMapping(path = "/feedbacks/{id}")
  public List<FeedbackDTO> getConferenceFeedbacks(final Long id) {
    return service.findConferenceFeedbacks(id).stream().map(
        feedback -> DTOUtils.toDTO(feedback)
    ).collect(Collectors.toList());
  }

  @PostMapping(path = "/add_feedback/{id}")
  public ResponseDTO addFeedback(@PathVariable Long id, @RequestBody FeedbackDTO feedback) {
    if (ObjectUtils.isEmpty(id) || !id.equals(feedback.getConferenceId())) {
      throw new IllegalArgumentException("Wrong data");
    }
    service.addFeedback(DTOUtils.fromDTO(feedback));
    return response("Feedback successfully added ");
  }

  private static ResponseDTO response(final String result) {
    return new ResponseDTO() {{
        setResult(result);
        setDatetime(LocalDateTime.now());
    }};
  }


}