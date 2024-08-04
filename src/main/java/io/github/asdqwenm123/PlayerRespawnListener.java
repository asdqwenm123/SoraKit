package io.github.asdqwenm123;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerRespawnListener implements Listener {
    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        if (SoraKit.getPreviousKit().containsKey(player)) {
            player.getInventory().setContents(SoraKit.getKit(player.getUniqueId(), SoraKit.getPreviousKit().get(player)).getInventory());
        }
    }
}
