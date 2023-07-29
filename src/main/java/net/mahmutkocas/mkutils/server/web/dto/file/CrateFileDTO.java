package net.mahmutkocas.mkutils.server.web.dto.file;

import lombok.*;

import java.awt.*;
import java.util.List;


@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CrateFileDTO {
    private String name;
    private String imageUrl;
    private List<CrateContentFileDTO> contents;
    @Builder.Default
    private String colorHex = String.format("%06X", 0xFFFFFF & Color.WHITE.getRGB());

    public Integer getColor() {
        return Color.decode(colorHex).getRGB();
    }
}
