package net.mahmutkocas.mkutils.server.web.dto.file;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    public Integer getColor() {
        return Color.decode(colorHex).getRGB();
    }
}
