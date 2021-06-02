package net.noknt.livestreamcamera.listener;

import net.noknt.livestreamcamera.LivestreamCamera;
import net.noknt.livestreamcamera.Zoom;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class OnMove implements Listener {
    Zoom zoom = new Zoom();
    Integer delayCounter = 0;

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if(LivestreamCamera.getInstance().getCamPlayers().containsKey(e.getPlayer().getUniqueId())) {
            delayCounter++;
            if(delayCounter > 1) {
                Player player = e.getPlayer();

                if (e.getFrom().getY() < e.getTo().getY()) {
                    zoom.zoomIn(player);
                } else if (e.getFrom().getY() > e.getTo().getY()) {
                    zoom.zoomOut(player);
                }
                delayCounter = 0;
            }
            Location location = new Location(e.getFrom().getWorld(),e.getTo().getX(),e.getFrom().getY(),e.getTo().getZ());
            e.setTo(location);
        }
    }
}
