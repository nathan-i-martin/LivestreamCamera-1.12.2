package net.noknt.livestreamcamera.listener;

import net.noknt.livestreamcamera.LivestreamCamera;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;

public class Hotbar implements Listener {

    @EventHandler
    public void hotbar(PlayerItemHeldEvent e) {
        if(LivestreamCamera.getInstance().getCamPlayers().containsKey(e.getPlayer().getUniqueId())) {
            Player player = e.getPlayer();
            int newSlot = e.getNewSlot();
            if (LivestreamCamera.getInstance().getMap("mainConfig").containsKey("camera." + newSlot)) {
                LivestreamCamera.getInstance().log((e.getNewSlot())+"");
                if (LivestreamCamera.getInstance().getMap("mainConfig").get("camera." + newSlot).equals("null")) {
                } else {
                    Location location;
                    if(LivestreamCamera.getInstance().getMap("mainConfig").containsKey("camera." + newSlot + ".pitch") && LivestreamCamera.getInstance().getMap("mainConfig").containsKey("camera." + newSlot + ".yaw")) {
                        location = new Location(player.getWorld(),
                                (double) LivestreamCamera.getInstance().getMap("mainConfig").get("camera." + newSlot + ".x"),
                                (double) LivestreamCamera.getInstance().getMap("mainConfig").get("camera." + newSlot + ".y"),
                                (double) LivestreamCamera.getInstance().getMap("mainConfig").get("camera." + newSlot + ".z"),
                                Float.parseFloat(String.valueOf(LivestreamCamera.getInstance().getMap("mainConfig").get("camera." + newSlot + ".pitch"))),
                                Float.parseFloat(String.valueOf(LivestreamCamera.getInstance().getMap("mainConfig").get("camera." + newSlot + ".yaw"))));
                    } else {
                        location = new Location(player.getWorld(),
                                (double) LivestreamCamera.getInstance().getMap("mainConfig").get("camera." + newSlot + ".x"),
                                (double) LivestreamCamera.getInstance().getMap("mainConfig").get("camera." + newSlot + ".y"),
                                (double) LivestreamCamera.getInstance().getMap("mainConfig").get("camera." + newSlot + ".z"));
                    }
                    player.teleport(location);
                }
            }
        }
    }
}
