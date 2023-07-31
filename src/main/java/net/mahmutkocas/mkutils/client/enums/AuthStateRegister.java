package net.mahmutkocas.mkutils.client.enums;

import java.awt.*;

public enum AuthStateRegister {
    NONE("",Color.BLACK),
    FAIL("Başarısız, kullanıcı adı kullanılıyor olabilir.", Color.RED),
    SERVER_ERROR("Sunucuya Bağlanılamıyor", Color.RED),
    SUCCESS("Başarılı! Giriş yapınız.", Color.green);
    private final String info;
    private final Color color;

    AuthStateRegister(String info, Color color) {
        this.info = info;
        this.color = color;
    }
    public String getInfo() {
        return info;
    }

    public int getColorInt() {
        return color.getRGB();
    }
}
