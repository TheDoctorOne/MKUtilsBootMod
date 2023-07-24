package net.mahmutkocas.reservermod.web.dto;

import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CrateContentDTO {
    private String name;
    private int chance;
}
