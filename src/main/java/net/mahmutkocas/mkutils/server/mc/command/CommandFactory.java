package net.mahmutkocas.mkutils.server.mc.command;

import lombok.extern.log4j.Log4j2;
import net.mahmutkocas.mkutils.server.ServerGlobals;
import net.mahmutkocas.mkutils.server.web.dao.UserCrateDAO;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import scala.Int;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Log4j2
public class CommandFactory {

    private final static List<Command> COMMANDS = new ArrayList<>();
    private static boolean isInit = false;

    private CommandFactory() {
        initCommands();
    }

    public static List<Command> getCommands() {
        if(!isInit) {
            new CommandFactory();
        }
        return Collections.unmodifiableList(COMMANDS);
    }

    private void initCommands() {
        invSeeCommand();
        giveCrate();
        delCrate();
        isInit = true;
    }

    private void delCrate() {
        int minLen = 3;
        Command delCrate = Command.builder()
                .help("Oyuncunun kasasini sil. 'playerName' kısmını @a girilerek serverda online olan herkesten sil!")
                .commands(new String[]{"remove", "playerName", "crateName"})
                .minArgLen(minLen)
                .onCommand((server, sender, args) -> {
                    if(args.length < minLen - 1) {
                        return;
                    }
                    sender.sendMessage(delPlayerCrate(args[0], args[1]));
                })
                .build();
        COMMANDS.add(delCrate);
    }

    private void giveCrate() {
        int minLen = 3;
        Command giveCrate = Command.builder()
                .help("Oyunucuya kasa ver. 'playerName' kısmını @a girilerek serverda online olan herkese kasa ver!")
                .commands(new String[]{"give", "playerName", "crateName"})
                .minArgLen(minLen)
                .onCommand((server, sender, args) -> {
                    if (args.length < minLen - 1) {
                        return;
                    }
                    sender.sendMessage(givePlayerCrate(args[0], args[1]));
                })
                .build();

        COMMANDS.add(giveCrate);
    }

    private void invSeeCommand() {
        int minLen = 3;
        Command invSee = Command.builder()
                .help("Oyuncularin kasalarini gör. Eger 'showAll' eklenirse, kullanicinin eski kasalari da gosterilir.")
                .commands(new String[]{"see", "playerName", "page", "showAll"})
                .minArgLen(minLen)
                .onCommand((server, sender, args) -> {
                    if(args.length < minLen-1) {
                        return;
                    }
                    boolean showAll = args.length>minLen-1;
                    try {
                        int page = Integer.parseInt(args[1]);
                        sender.sendMessage(getPlayerCrates(args[0], page, showAll));
                    } catch (NumberFormatException ex) {
                        sender.sendMessage(new TextComponentString("Sayfa sayisini kontrol ediniz."));
                    }
                })
                .build();
        COMMANDS.add(invSee);
    }

    private ITextComponent givePlayerCrate(String playerName, String crateName) {
        ITextComponent res;
        try {
            ServerGlobals.WEBSERVICE.giveUserCrate(playerName, crateName);
            res = new TextComponentString(playerName + " isimli oyuncuya " + crateName + " kasasi verildi.");
            res.getStyle().setColor(TextFormatting.GREEN);
        } catch (IllegalArgumentException ex) {
            log.error("Player crate giving failed!", ex);
            res = new TextComponentString(ex.getMessage());
            res.getStyle().setColor(TextFormatting.RED);
        } catch (Exception ex) {
            log.error("Player crate giving failed! Unknown", ex);
            res = new TextComponentString(playerName + " Oyunucuya kasa verilirken hata olustu!");
            res.getStyle().setColor(TextFormatting.RED);
        }
        return res;
    }

    private ITextComponent delPlayerCrate(String playerName, String crateName) {
        ITextComponent res;
        try {
            ServerGlobals.WEBSERVICE.deleteUserCrate(playerName, crateName);
            res = new TextComponentString(playerName + " isimli oyuncudan " + crateName + " kasasi silindi.");
            res.getStyle().setColor(TextFormatting.GREEN);
        } catch (IllegalArgumentException ex) {
            log.error("Player crate deletion failed!", ex);
            res = new TextComponentString(ex.getMessage());
            res.getStyle().setColor(TextFormatting.RED);
        } catch (Exception ex) {
            log.error("Player crate deletion failed! Unknown", ex);
            res = new TextComponentString(playerName + " oyunucudan kasa silinirken hata olustu!");
            res.getStyle().setColor(TextFormatting.RED);
        }
        return res;
    }

    // Page start from 1
    private ITextComponent getPlayerCrates(String player, int page, boolean all) {
        List<UserCrateDAO> userCrates = ServerGlobals.WEBSERVICE.getUserCrates(player, !all);
        ITextComponent crates = new TextComponentString("========= " + player + " Kasalari =========\n");
        ITextComponent paging = new TextComponentString("========= Sayfa " + page + " =========\n");
        crates.getStyle().setBold(true);
        crates.getStyle().setColor(TextFormatting.AQUA);
        paging.getStyle().setColor(TextFormatting.AQUA);
        crates.getSiblings().add(paging);

        int realPage = page -1;
        for(int i=realPage*10; i<realPage*10+10; i++) {
            if(i>userCrates.size()) {
                break;
            }

            UserCrateDAO userCrate = userCrates.get(i);
            String msg = (i+1) + ". " + userCrate.getCrateDAO().getName();
            String claimDate = userCrate.getClaimDate() != null ? String.format(String.valueOf(DateTimeFormatter.BASIC_ISO_DATE)) : "";
            if(all) {
                msg += (userCrate.isClaimed()
                        ? (" Kullanildi " + claimDate)
                        : " Kullanilmadi"
                );
            }
            ITextComponent crate = new TextComponentString(msg + "\n");
            crate.getStyle().setColor(TextFormatting.WHITE);
            crates.getSiblings().add(crate);
        }

        return crates;
    }
}
