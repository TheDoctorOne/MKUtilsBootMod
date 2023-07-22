package net.mahmutkocas.reservermod.web.dto;

import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDTO {
    private String username;
    private String password;
}
