package net.mahmutkocas.mkutils.common.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CrateResultDTO {
    private CrateContentDTO result;
    private Long number;
}
