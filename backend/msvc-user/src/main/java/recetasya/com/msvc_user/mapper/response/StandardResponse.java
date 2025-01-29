package recetasya.com.msvc_user.mapper.response;

import lombok.*;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;

@Data @NoArgsConstructor @AllArgsConstructor
public class StandardResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime timestamp;
    private long id;
    private int status;
    private String message;

    public StandardResponse(int status, String message, long id) {
        this.id = id;
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.message = message;
    }
}
