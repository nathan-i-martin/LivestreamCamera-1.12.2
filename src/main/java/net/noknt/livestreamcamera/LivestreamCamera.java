package net.noknt.livestreamcamera;

import com.google.common.collect.Maps;
import net.noknt.livestreamcamera.command.CommandCam;
import net.noknt.livestreamcamera.listener.Hotbar;
import net.noknt.livestreamcamera.listener.OnMove;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

public final class LivestreamCamera extends JavaPlugin implements Listener {
    private Map<UUID,Integer> camPlayers = Maps.newHashMap();
    private static LivestreamCamera instance;

    private YamlConfiguration genericYML; // Used for overwriting
    private YamlConfiguration mainConfigYML;
    public Map<String, Object> mainConfig;

    // Getters/Setters
    public static LivestreamCamera getInstance() {
        return instance;
    }

    /* YAML FILES */

    public void setYML(YamlConfiguration controlPanelYML, String type) {
        switch (type) {
            case "generic":
                this.genericYML = controlPanelYML;
                break;
            case "mainConfig":
                this.mainConfigYML = controlPanelYML;
                break;
        }
    }
    public YamlConfiguration getYML(String type) {
        switch (type) {
            case "generic":
                return this.genericYML;
            case "mainConfig":
                return this.mainConfigYML;
        }
        return null;
    }

    /* CONFIG MAPS */

    public void setMap(Map<String, Object> map, String type) {
        switch (type) {
            case "mainConfig":
                this.mainConfig = map;
                break;
        }
    }
    public Map<String, Object> getMap(String type) {
        switch (type) {
            case "mainConfig":
                return this.mainConfig;
        }
        return Maps.newHashMap();
    }

    public Map<UUID,Integer> getCamPlayers() {
        return this.camPlayers;
    }

    @Override
    public void onEnable() {
        instance = this;

        registerConfig();
        registerCommands();
        registerEvents();
        log("Starting Effects EffectRender Engine...");

        log("Started Successfully!");
    }

    @Override
    public void onDisable() {
/*
        http http = new http();
        JsonObject json = new JsonObject();
        json.add("registration", new JsonPrimitive(mainConfig.get("registration-key").toString()));
        String response = http.post("4aabb6cf391a89e94417f97a1b3b6593",json);*/

        log("Shutting down ShowControl...");
    }

    /**
     * Register all of the plugin's commands
     */
    public void registerCommands() {
        this.getCommand("cam").setExecutor(new CommandCam());
    }

    /**
     * Register all of the plugin's Event Listeners
     */
    public void registerEvents() {
        PluginManager eventManager = getServer().getPluginManager();

        eventManager.registerEvents(new Hotbar(), this);
    }

    /**
     * Register the plugin's config files
     */
    public void registerConfig() {
        createCustomConfig("config.yml");
        setYML(getYML("generic"),"mainConfig");
        setMap(getYML("generic").getValues(true),"mainConfig");
    }

    /**
     * Creates a custom config file in the plugin's data file using a template from resources/config
     * @param configName The name of the config file in resources/config
     * @throws StackTraceElement Thrown if file cannot be loaded after creation
     */
    private void createCustomConfig(String configName) {
        File customConfigFile;
        customConfigFile = new File(getDataFolder(), configName); // Load the custom config from the plugins data file
        log("Searching for "+configName);
        if (!customConfigFile.exists()) { // Check if the custom config actually exists
            log(configName + " could not be found. Making it now!");
            this.saveResource(configName, false); // If it doesn't, create it
            log(configName + " was successfully generated!");
        } else {
            log(configName + " was found!");
        }

        if (customConfigFile.exists()) { // Re-check if the custom config exists
            try {
                setYML(YamlConfiguration.loadConfiguration(customConfigFile),"generic"); // Load the custom config into a variable
            } catch (IllegalArgumentException e) {
                log(configName + " could not be loaded!");
            }
        } else {
            log(configName + " still doesn't exist!");
        }
    }

    public void reloadConfig(String configName) {
        setYML(YamlConfiguration.loadConfiguration(new File(getDataFolder(), configName)),"generic");
    }

    public void saveConfig(String configName, Map<String, Object> configMap) {
        FileConfiguration customFileConfiguration;
        File customConfigFile = new File(getDataFolder(), configName);
        customFileConfiguration = YamlConfiguration.loadConfiguration(customConfigFile);
        for(Map.Entry<String, Object> entry : configMap.entrySet()) {
            customFileConfiguration.set(entry.getKey(),entry.getValue());
        }
        try {
            customFileConfiguration.save(customConfigFile);
        } catch (IOException e) {
            log("Failed to save "+configName); // shouldn't really happen, but save throws the exception
        }
    }

    /**
     * Sends a String to the log under preffix [ShowControl]
     * @param log The text to be logged
     */
    public void log(String log) {
        System.out.println("[ShowControl] " + log);
    }
    public void debugLog(String log) {
        if(mainConfig.get("debug-log")=="TRUE") {
            System.out.println("[LivestreamCamera] " + log);
        }
    }
}
