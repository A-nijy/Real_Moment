package rm.backend.smtp;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public class EmailDto {


    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class EmailRequest {
        private String email;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class EmailCodeRequest {
        private String email;
        private String code;
    }
}
