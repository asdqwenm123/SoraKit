package io.github.asdqwenm123;

import com.google.common.reflect.TypeToken;
import com.google.gson.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

public class SoraKit extends JavaPlugin {
    private static HashMap<UUID, HashMap<String, Kit>> kits = new HashMap<>();
    private static HashMap<Player, String> previousKit = new HashMap<>();
    private final File kitFile = new File(getDataFolder(), "kit.json");

    @Override
    public void onEnable() {
        getDataFolder().mkdirs();

        if (!kitFile.exists()) {
            try {
                kitFile.createNewFile();
                FileWriter fileWriter = new FileWriter(kitFile);
                fileWriter.write(new Gson().toJson(new JsonObject()));
                fileWriter.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        load();
        new KommandKit(this);

        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);

        getLogger().info("start");
    }

    @Override
    public void onDisable() {
        save();
        getLogger().info("stop");
    }

    private void save() {
        Gson gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(ItemStack.class, new ItemStackAdapter()).excludeFieldsWithoutExposeAnnotation().create();
        try {
            FileWriter fileWriter = new FileWriter(kitFile);
            String json = gson.toJson(kits);
            fileWriter.write(json);
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void load() {
        Gson gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(ItemStack.class, new ItemStackAdapter()).excludeFieldsWithoutExposeAnnotation().create();
        try {
            Type type = new TypeToken<HashMap<UUID, HashMap<String, Kit>>>(){}.getType();
            HashMap<UUID, HashMap<String, Kit>> tmp = gson.fromJson(new FileReader(kitFile), type);
            if (tmp != null) {
                for (UUID uuid : tmp.keySet()) {
                    for (Kit kit : tmp.get(uuid).values()) {
                        kit.setPlugin(this);
                    }
                }
            }
//            kits.putAll(gson.fromJson(new FileReader(kitFile), type));
            kits.putAll(tmp);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (JsonIOException ignored) {}
    }

    public static HashMap<UUID, HashMap<String, Kit>> getKits() {
        return kits;
    }

    public static HashMap<String, Kit> getKits(UUID uuid) {
        return kits.get(uuid);
    }

    public static Kit getKit(UUID uuid, String name) {
        return kits.get(uuid).get(name);
    }

    public static void addKit(UUID uuid, Kit kit) {
        getKits().get(uuid).put(kit.getName(), kit);
    }

    public static HashMap<Player, String> getPreviousKit() {
        return previousKit;
    }
}
