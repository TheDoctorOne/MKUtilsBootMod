package net.mahmutkocas.mkutils.common.dto;

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
