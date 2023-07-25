package net.mahmutkocas.mkutils.server.web.mapper;

import net.mahmutkocas.mkutils.common.dto.CrateContentDTO;
import net.mahmutkocas.mkutils.server.web.dao.CrateContentDAO;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CrateContentMapper {

    public static List<CrateContentDTO> toDTO(Set<CrateContentDAO> daoSet) {
        return daoSet.stream().map(CrateContentMapper::toDTO).collect(Collectors.toList());
    }

    public static CrateContentDTO toDTO(CrateContentDAO dao) {
        return CrateContentDTO.builder()
                .name(dao.getName())
                .chance(dao.getChance())
                .build();
    }
}
