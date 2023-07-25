package net.mahmutkocas.mkutils.common.dto;

import lombok.*;
import org.apache.commons.codec.digest.DigestUtils;

@Builder
@Setter
@Getter
@NoArgsConstructor
public class UserLoginDTO {
    private String username;
    private String password;

    public UserLoginDTO(String username, String password) {
        this.username = username;
        this.password = DigestUtils.sha256Hex(password);
    }
}
