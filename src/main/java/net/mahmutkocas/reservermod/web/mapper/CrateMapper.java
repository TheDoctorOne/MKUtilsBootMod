package net.mahmutkocas.reservermod.web.mapper;

import net.mahmutkocas.reservermod.web.dao.CrateContentDAO;
import net.mahmutkocas.reservermod.web.dao.CrateDAO;
import net.mahmutkocas.reservermod.web.dto.CrateContentDTO;
import net.mahmutkocas.reservermod.web.dto.CrateDTO;

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
