package net.mahmutkocas.mkutils.server.web.mapper.file;

import net.mahmutkocas.mkutils.server.web.dao.CrateDAO;
import net.mahmutkocas.mkutils.server.web.dto.file.CrateFileDTO;

public class CrateFileMapper {
    public static CrateDAO toDAO(CrateFileDTO dto) {
        return CrateDAO.builder()
                .name(dto.getName())
                .imageUrl(dto.getImageUrl())
                .color(dto.getColor())
                .build();
    }
}
