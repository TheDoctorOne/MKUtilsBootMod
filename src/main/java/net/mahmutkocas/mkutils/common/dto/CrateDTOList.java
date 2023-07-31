package net.mahmutkocas.mkutils.common.dto;


import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CrateDTOList {
    @Builder.Default
    private List<CrateDTO> crateDTOs = new ArrayList<>();
}
