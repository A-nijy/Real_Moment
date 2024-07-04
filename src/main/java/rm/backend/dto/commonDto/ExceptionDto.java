package rm.backend.dto.commonDto;

import lombok.Getter;
import lombok.Setter;
import okhttp3.internal.http2.ErrorCode;

import java.time.LocalDateTime;

@Getter
@Setter
public class ExceptionDto {

    private LocalDateTime timestamp;
    private ErrorCode errorCode;
    private String message;

    public ExceptionDto (ErrorCode errorCode, String message){
        this.timestamp = LocalDateTime.now();
        this.errorCode = errorCode;
        this.message = message;
    }
}
