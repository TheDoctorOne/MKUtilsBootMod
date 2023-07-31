package net.mahmutkocas.mkutils.server.web.dto.file;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.awt.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CrateContentFileDTO {
    private String name;
    private String imageUrl;
    private Integer chance;
    private String command;
    @Builder.Default
    private String colorHex = "#" + String.format("%06X", 0xFFFFFF & Color.WHITE.getRGB());

    @JsonIgnore
    public Integer getColor() {
        return Color.decode(colorHex).getRGB();
    }
}
