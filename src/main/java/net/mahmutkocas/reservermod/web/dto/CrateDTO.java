package net.mahmutkocas.reservermod.web.dto;

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
    private List<CrateContentDTO> contents;
}