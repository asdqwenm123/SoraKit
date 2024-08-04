package io.github.asdqwenm123;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class PlayerJoinListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (!SoraKit.getKits().containsKey(player.getUniqueId())) {
            SoraKit.getKits().put(player.getUniqueId(), new HashMap<>());
        }
    }
}
