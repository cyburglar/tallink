package ee.tallink.test.backoffice.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResponseDTO {
   private String result;
   private LocalDateTime datetime;
}
