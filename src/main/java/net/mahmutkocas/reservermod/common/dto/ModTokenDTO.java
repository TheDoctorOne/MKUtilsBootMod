package net.mahmutkocas.reservermod.common.dto;

import lombok.*;

import java.util.List;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ModTokenDTO {
    private String token;
    private List<String> modList;
}
