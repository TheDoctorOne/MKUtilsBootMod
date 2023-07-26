package net.mahmutkocas.mkutils.common.dto;

import lombok.*;

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
}
