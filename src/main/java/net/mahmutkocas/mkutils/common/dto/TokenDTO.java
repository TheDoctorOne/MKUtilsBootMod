package net.mahmutkocas.mkutils.common.dto;

import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TokenDTO {
    @Builder.Default
    private String token = "";
}
