package net.mahmutkocas.mkutils.common.dto;

import lombok.*;

import java.awt.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CrateContentDTO {
    private String name;
    private String imageUrl;
    private Integer chance;
    @Builder.Default
    private Integer color = Color.WHITE.getRGB();
}
