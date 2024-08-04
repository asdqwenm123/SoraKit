package io.github.asdqwenm123;

import com.google.gson.annotations.Expose;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

public class Kit {
    private SoraKit plugin;
    @Expose private UUID uuid;
    @Expose private String name;
    @Expose private ItemStack[] inventory;

    public Kit(SoraKit plugin, UUID uuid, String name) {
        this.plugin = plugin;
        this.uuid = uuid;
        this.name = name;
        this.inventory = plugin.getServer().getPlayer(uuid).getInventory().getContents();
    }

    public String getName() {
        return name;
    }

    public ItemStack[] getInventory() {
        return inventory;
    }

    public void setPlugin(SoraKit plugin) {
        this.plugin = plugin;
    }

    @Override
    public String toString() {
        return "Kit{" +
                "plugin=" + plugin +
                ", uuid=" + uuid +
                ", name='" + name + '\'' +
                ", inventory=" + Arrays.toString(inventory) +
                '}';
    }
}
