package net.noknt.livestreamcamera;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Zoom {
    public Zoom() {}
    public void setZoom(Player player) {
        Integer zoomLevel = LivestreamCamera.getInstance().getCamPlayers().get(player.getUniqueId());

        player.removePotionEffect(PotionEffectType.SLOW);
        player.removePotionEffect(PotionEffectType.SPEED);
        if(zoomLevel > 0) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,1000,zoomLevel));
        } else if(zoomLevel < 0) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,1000,zoomLevel));
        }
    }
    public void setZoom(Player player, Integer amount) {
        if(amount > 4) amount = 4; else
        if(amount < -6) amount = -6;

        player.removePotionEffect(PotionEffectType.SLOW);
        player.removePotionEffect(PotionEffectType.SPEED);
        if(amount > 0) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,1000,amount));
        } else if(amount < 0) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,1000,amount));
        }

        LivestreamCamera.getInstance().getCamPlayers().replace(player.getUniqueId(),amount);
    }
    public void zoomIn(Player player) {
        Integer zoomLevel = LivestreamCamera.getInstance().getCamPlayers().get(player.getUniqueId());

        zoomLevel++;

        if(zoomLevel > 4) zoomLevel = 4;

        LivestreamCamera.getInstance().getCamPlayers().replace(player.getUniqueId(),zoomLevel);
        setZoom(player);
    }
    public void zoomOut(Player player) {
        Integer zoomLevel = LivestreamCamera.getInstance().getCamPlayers().get(player.getUniqueId());

        zoomLevel--;

        if(zoomLevel < -6) zoomLevel = -6;

        LivestreamCamera.getInstance().getCamPlayers().replace(player.getUniqueId(),zoomLevel);
        setZoom(player);
    }

}
