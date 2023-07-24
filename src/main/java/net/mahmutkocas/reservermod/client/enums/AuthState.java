package net.mahmutkocas.reservermod.client.enums;


import java.awt.*;

public enum AuthState {
    NONE("", Color.BLACK),
    FAIL("Başarısız", Color.RED),
    FAIL_REGISTER("Başarısız, kullanıcı adı kullanılıyor olabilir.", Color.RED),
    SERVER_ERROR("Sunucuya Bağlanılamıyor", Color.RED),
    SUCCESS("Başarılı!", Color.green);
    private final String info;
    private final Color color;
    AuthState(String info, Color color) {
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
