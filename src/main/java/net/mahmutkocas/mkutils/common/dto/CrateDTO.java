package net.mahmutkocas.mkutils.common.dto;

import lombok.*;

import java.awt.*;
import java.util.List;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CrateDTO {
    private Long id;
    private String name;
    private String imageUrl;
    private List<CrateContentDTO> contents;
    @Builder.Default
    private Integer color = Color.WHITE.getRGB();
}
