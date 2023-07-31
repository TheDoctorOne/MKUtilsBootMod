package net.mahmutkocas.mkutils.common.dto;

import lombok.*;
import org.apache.commons.codec.digest.DigestUtils;

@Builder
@Setter
@Getter
@NoArgsConstructor
public class UserDTO {
    private String username;
    private String password;
    private String discord;

    public UserDTO(String username, String password, String discord) {
        this.username = username;
        this.password = DigestUtils.sha256Hex(password);
        this.discord = discord;
    }
}
