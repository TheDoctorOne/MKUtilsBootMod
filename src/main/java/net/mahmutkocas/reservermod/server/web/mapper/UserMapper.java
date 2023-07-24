package net.mahmutkocas.reservermod.server.web.mapper;

import net.mahmutkocas.reservermod.server.web.dao.UserDAO;
import net.mahmutkocas.reservermod.common.dto.UserDTO;
import net.mahmutkocas.reservermod.common.dto.UserLoginDTO;
import org.apache.commons.codec.digest.DigestUtils;

public class UserMapper {
    public static UserDAO toDAO(UserDTO userDTO) {
        return UserDAO.builder()
                .username(userDTO.getUsername())
                .password(DigestUtils.sha256Hex(userDTO.getPassword()))
                .mail(userDTO.getMail())
                .build();
    }


    public static UserDAO toDAO(UserLoginDTO userDTO) {
        return UserDAO.builder()
                .username(userDTO.getUsername())
                .password(DigestUtils.sha256Hex(userDTO.getPassword()))
                .build();
    }
}
