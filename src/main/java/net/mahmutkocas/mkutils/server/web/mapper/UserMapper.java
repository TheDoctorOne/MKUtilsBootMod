package net.mahmutkocas.mkutils.server.web.mapper;

import net.mahmutkocas.mkutils.server.web.dao.UserDAO;
import net.mahmutkocas.mkutils.common.dto.UserDTO;
import net.mahmutkocas.mkutils.common.dto.UserLoginDTO;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Locale;

public class UserMapper {
    public static UserDAO toDAO(UserDTO userDTO) {
        return UserDAO.builder()
                .username(userDTO.getUsername().toLowerCase(Locale.ENGLISH))
                .password(DigestUtils.sha256Hex(userDTO.getPassword()))
                .mail(userDTO.getMail())
                .build();
    }

    public static UserDAO toDAO(UserLoginDTO userDTO) {
        return UserDAO.builder()
                .username(userDTO.getUsername().toLowerCase(Locale.ENGLISH))
                .password(DigestUtils.sha256Hex(userDTO.getPassword()))
                .build();
    }
}
