package ee.tallink.test.backoffice.repository;

import ee.tallink.test.backoffice.model.Conference;
import ee.tallink.test.backoffice.model.ConferenceRoom;
import ee.tallink.test.backoffice.model.Customer;
import ee.tallink.test.backoffice.model.Feedback;
import ee.tallink.test.backoffice.model.RoomStatus;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ConferenceRepository {

  @Insert("INSERT INTO CONFERENCE_ROOM(title, capacity, location, status) " +
      " VALUES (#{title}, #{capacity}, #{location}, #{status})")
  void addRoom(ConferenceRoom room);

  @Select("SELECT * FROM CONFERENCE_ROOM")
  List<ConferenceRoom> findAllConferenceRooms();

  @Select("SELECT * FROM CONFERENCE_ROOM WHERE title = #{title}")
  ConferenceRoom findConferenceRoomByTitle(final String title);

  @Select("SELECT * FROM CONFERENCE_ROOM WHERE id = #{id}")
  ConferenceRoom findConferenceRoomById(final Long id);

  @Update("UPDATE CONFERENCE_ROOM SET status=#{status}, " +
      " capacity=#{capacity} " +
      " where id=#{id}")
  int updateConferenceRoom(ConferenceRoom conferenceRoom);

  @Insert("INSERT INTO CONFERENCE(title, event_start, event_end, room_id, description) " +
      " VALUES (#{title}, #{eventStart}, #{eventEnd}, #{roomId}, #{description})")
  void addConference(Conference conference);

  @Select("SELECT c.id, c.event_start as eventStart, c.event_end as eventEnd," +
      " cor.capacity - COUNT(cu) AS capacity " +
      " FROM CONFERENCE c " +
      " LEFT JOIN CUSTOMER cu ON c.id = cu.conference_id " +
      " JOIN CONFERENCE_ROOM cor ON c.room_id = cor.id " +
      " WHERE c.room_id = #{roomId}" +
      " GROUP BY c.id, c.title, c.event_start, c.event_end, capacity ")
  List<Conference> findConferencesByRoomId(final Long roomId);

  @Select("SELECT * FROM CONFERENCE WHERE title = #{title} AND room_id = #{roomId}")
  Conference findConferenceByTitleAndRoomId(final String title, final Long roomId);

  @Select("SELECT * FROM CONFERENCE WHERE id = #{id}")
  Conference findConferenceById(final Long id);

  @Select("SELECT  " +
      " cor.capacity - COUNT(cu) AS capacity " +
      " FROM CONFERENCE c " +
      " LEFT JOIN CUSTOMER cu ON c.id = cu.conference_id " +
      " JOIN CONFERENCE_ROOM cor ON c.room_id = cor.id " +
      " WHERE c.id = #{conferenceId} AND cor.status != 'OPEN' AND c.event_start >= now() " +
      " GROUP BY capacity ")
  Integer findConferenceCapacity(final Long conferenceId);

  @Update("UPDATE CONFERENCE SET " +
      " room_id=#{roomId}, event_start=#{eventStart}, event_end=#{eventEnd} " +
      " where id=#{id}")
  int updateConference(Conference conference);

  @Delete("DELETE FROM CONFERENCE WHERE id = #{id}")
  boolean cancelConference(long id);

  @Insert("INSERT INTO FEEDBACK(conference_id, customer_id, comment, created) " +
      " VALUES (#{conferenceId}, #{customerId}, #{comment}, #{created})")
  void addFeedback(Feedback feedback);

  @Select("SELECT conference_id, (LEFT(c.firstname,1) || '. ' || c.lastname) as customerName," +
      " c.comment, c.created FROM FEEDBACK f JOIN CUSTOMER c ON f.customer_id = c.id " +
      " WHERE conference_id = #{conferenceId}")
  List<Feedback> findConferenceFeedbacks(final Long conferenceId);

  @Select("SELECT * FROM CUSTOMER WHERE id = #{id}")
  Customer findCustomerById(final Long id);

  @Select("WITH events AS (" +
      "SELECT COUNT(cu) AS customers" +
      "       FROM CONFERENCE c " +
      "       LEFT JOIN CUSTOMER cu ON c.id = cu.conference_id " +
      "       JOIN CONFERENCE_ROOM cor ON c.room_id = cor.id " +
      "       WHERE cor.id = #{id} AND c.event_start >= now()" +
      "       GROUP BY c.id" +
      "), core_stat AS (" +
      "SELECT DISTINCT COUNT(c.id) as total_events, cor.status as status" +
      "       FROM CONFERENCE c " +
      "       LEFT JOIN CUSTOMER cu ON c.id = cu.conference_id " +
      "       JOIN CONFERENCE_ROOM cor ON c.room_id = cor.id " +
      "       WHERE cor.id = #{id} AND c.event_start >= now()" +
      "       GROUP BY c.id, capacity, cor.status" +
      ")" +
      "SELECT MAX(events.customers) as biggest, core_stat.total_events as totalEvents, core_stat.status" +
      "FROM events, core_stat " +
      "GROUP BY core_stat.total_events, core_stat.status")
  RoomStatus getConferenceRoomStatistics(final Long id);

}
