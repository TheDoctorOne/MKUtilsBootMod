package net.mahmutkocas.mkutils.server.mc.command.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import net.mahmutkocas.mkutils.server.ServerGlobals;
import net.mahmutkocas.mkutils.server.mc.command.Command;
import net.mahmutkocas.mkutils.server.web.dao.UserDAO;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Log4j2
public class UserCommandFactory extends BaseCommandFactory {

    private final static List<Command> COMMANDS = new ArrayList<>();
    private static boolean isInit = false;

    public static List<Command> getCommands() {
        if(!isInit) {
            new UserCommandFactory();
        }
        return Collections.unmodifiableList(COMMANDS);
    }

    @Override
    protected void initCommands() {
        COMMANDS.clear();
        info();
        clearPass();
        isInit = true;
    }

    private void info() {
        int minLen = 2;
        Command info = Command.builder()
                .desc("Oyuncunun bilgilerini getirir")
                .commands(new String[]{"info", "playerName"})
                .minArgLen(minLen)
                .onCommand((server, sender, args) -> {
                    if(args.length < minLen - 1) {
                        return;
                    }
                    String name = args[0];
                    UserDAO user = ServerGlobals.WEBSERVICE.getUserByName(name);
                    if(user == null) {
                        sender.sendMessage(errorMsg("Kullanici bulunamadi!"));
                        return;
                    }
                    String msg = "ID:" + user.getId() + "\n" +
                            "Kullanici Adi: " + user.getUsername() + "\n" +
                            "Discord: " + user.getDiscord() + "\n" +
                            "Token Gecerlilik Tarihi: " + DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(user.getTokenExpDate());
                    sender.sendMessage(infoMsg(msg));
                })
                .build();
        COMMANDS.add(info);
    }

    private void clearPass() {
        int minLen = 2;
        Command clearPass = Command.builder()
                .desc("Oyuncunun sifresini siler. Oyuncunun tekrar kayıt olmasi gerekir.")
                .commands(new String[]{"clearpass", "playerName"})
                .minArgLen(minLen)
                .onCommand((server, sender, args) -> {
                    if(args.length < minLen - 1) {
                        return;
                    }
                    String name = args[0];
                    try {
                        ServerGlobals.WEBSERVICE.clearUserPassword(sender.getName(), name);
                        sender.sendMessage(infoMsg(name + " isimli kullanicin sifresi silinmistir!"));
                    } catch (IllegalArgumentException ex) {
                        log.error("Error on pass clear requester: {}, target: {}, exc: {}"
                                , sender.getName(), name, ex);
                        sender.sendMessage(errorMsg("Kullanici bulunamadi!"));
                    }

                })
                .build();
        COMMANDS.add(clearPass);
    }




    private static ITextComponent errorMsg(String err) {
        ITextComponent textComponent = new TextComponentString(err);
        textComponent.getStyle().setBold(true).setColor(TextFormatting.RED);
        return textComponent;
    }

    private static ITextComponent infoMsg(String info) {
        ITextComponent textComponent = new TextComponentString(info);
        textComponent.getStyle().setBold(true).setColor(TextFormatting.GREEN);
        return textComponent;
    }
}
