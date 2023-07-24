package net.mahmutkocas.reservermod.server.web.mapper;

import net.mahmutkocas.reservermod.common.dto.CrateDTO;
import net.mahmutkocas.reservermod.server.web.dao.CrateDAO;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CrateMapper {

    public static CrateDTO toDTO(CrateDAO dao) {
        return CrateDTO.builder()
                .id(dao.getId())
                .name(dao.getName())
                .contents(CrateContentMapper.toDTO(dao.getCrateContents()))
                .build();
    }

    public static List<CrateDTO> toDTO(Set<CrateDAO> daoSet) {
        return daoSet.stream().map(CrateMapper::toDTO).collect(Collectors.toList());
    }

}
