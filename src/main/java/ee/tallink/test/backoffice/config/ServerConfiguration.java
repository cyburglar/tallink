package ee.tallink.test.backoffice.config;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.modelmapper.ModelMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.format.DateTimeFormatter;

@Configuration
@MapperScan("ee.tallink.test.backoffice")
public class ServerConfiguration {

  @Bean
  public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {

    return builder -> {

      DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
      DateTimeFormatter dateTimeFormatter =  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

      builder.deserializers(new LocalDateDeserializer(dateFormatter));
      builder.deserializers(new LocalDateTimeDeserializer(dateTimeFormatter));

      builder.serializers(new LocalDateSerializer(dateFormatter));
      builder.serializers(new LocalDateTimeSerializer(dateTimeFormatter));
    };
  }

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper() {{
//      addConverter((Converter<Conference, ConferenceDTO>) context -> {
//            if (isNotEmpty(context.getSource().getRoomId())) {
//              context.getDestination().setRoomId(
//                  context.getSource().getRoomId()
//              );
//            }
//            return context.getDestination();
//          }
//      );
//      addConverter((Converter<ConferenceDTO, Conference>) context -> {
//            if (isNotEmpty(context.getSource().getRoomId())) {
//              context.getDestination().setConferenceRoom(new ConferenceRoom() {{
//                setId(context.getSource().getRoomId());
//              }});
//            }
//            return context.getDestination();
//          }
//      );
    }};
  }

}
