package net.noknt.livestreamcamera.command;

import net.noknt.livestreamcamera.LivestreamCamera;
import net.noknt.livestreamcamera.Zoom;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import sun.jvm.hotspot.ui.action.ShowAction;


public class CommandCam implements CommandExecutor {
    Zoom zoom = new Zoom();

    private final String chatPrefix = ChatColor.DARK_GRAY+"["+ChatColor.AQUA+"LivestreamCamera"+ChatColor.DARK_GRAY+"]: "+ChatColor.GRAY;
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if(args.length == 0) {
                sender.sendMessage(chatPrefix + ChatColor.RED + "Proper usage: /cam <on/off/chat/zoom>");
                return true;
            }
            switch(args[0]) {
                case "on":
                    LivestreamCamera.getInstance().getCamPlayers().put(((Player) sender).getUniqueId(),0);
                    break;
                case "off":
                    LivestreamCamera.getInstance().getCamPlayers().remove(((Player) sender).getUniqueId());
                    break;
                case "zoom":
                    if(args.length == 1) {
                        sender.sendMessage(chatPrefix + ChatColor.RED + "Proper usage: /cam zoom <zoom level>");
                        return true;
                    }
                    if(LivestreamCamera.getInstance().getCamPlayers().containsKey(((Player) sender).getUniqueId())) {
                        zoom.setZoom(player,Integer.valueOf(args[1]));
                    } else {
                        sender.sendMessage(chatPrefix + ChatColor.RED + "To use this command, you must be in cam mode! Use: /cam on");
                    }
                    break;
                case "reload":
                    sender.sendMessage(chatPrefix + "Disabling all cams...");
                    LivestreamCamera.getInstance().getCamPlayers().clear();
                    sender.sendMessage(chatPrefix + "Reloading configs...");
                    LivestreamCamera.getInstance().reloadConfig("config.yml");
                    LivestreamCamera.getInstance().setYML(LivestreamCamera.getInstance().getYML("generic"),"mainConfig");
                    LivestreamCamera.getInstance().setMap(LivestreamCamera.getInstance().getYML("mainConfig").getValues(true),"mainConfig");

                    sender.sendMessage(chatPrefix + ChatColor.GREEN + "Finished!");
                    break;
                case "chat":

                    break;
                default:
                    sender.sendMessage(chatPrefix + ChatColor.RED + "Proper usage: /cam <on/off/chat>");
                    break;
            }
        }
        return true;
    }

}
