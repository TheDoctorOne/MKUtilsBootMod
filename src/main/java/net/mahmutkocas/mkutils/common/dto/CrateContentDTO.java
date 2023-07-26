package net.mahmutkocas.mkutils.common.dto;

import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CrateContentDTO {
    private String name;
    private String imageUrl;
    private int chance;
}
