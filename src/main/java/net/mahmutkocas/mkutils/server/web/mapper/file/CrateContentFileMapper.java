package net.mahmutkocas.mkutils.server.web.mapper.file;

import net.mahmutkocas.mkutils.server.web.dao.CrateContentDAO;
import net.mahmutkocas.mkutils.server.web.dto.file.CrateContentFileDTO;

import java.util.List;
import java.util.stream.Collectors;

public class CrateContentFileMapper {
    public static CrateContentDAO toDAO(CrateContentFileDTO dto) {
        return CrateContentDAO.builder()
                .name(dto.getName())
                .command(dto.getCommand())
                .imageUrl(dto.getImageUrl())
                .color(dto.getColor())
                .chance(dto.getChance())
                .build();
    }

    public static List<CrateContentDAO> toDAO(List<CrateContentFileDTO> dtos) {
        return dtos.stream().map(CrateContentFileMapper::toDAO).collect(Collectors.toList());
    }
}
