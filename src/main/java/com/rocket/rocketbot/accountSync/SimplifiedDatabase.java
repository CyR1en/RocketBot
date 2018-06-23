package com.rocket.rocketbot.accountSync;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;

public class SimplifiedDatabase extends Database {

    private static final Path filePath = Paths.get("plugins/MineCordBot/Users/Simplified-Accounts.json");
    protected static JSONObject config;

    public static void set(String key, Object val) {
        if (val == null) {
            config.remove(key);
        } else {
            config.put(key, val);
        }
        save();
    }


    @SuppressWarnings("unchecked")
    public static <T> T get(String key) {
        if (config.has(key)) {
            try {
                return (T) config.get(key);
            } catch (JSONException | ClassCastException ignored) {
            }
        }
        return null;
    }

    public static <T> T get(String key, T def) {
        T obj = get(key);
        if (obj == null) {
            obj = def;
            config.put(key, def);
            save();
        }
        return obj;
    }

    public static JSONObject getJSONObject(String key) {
        return config.getJSONObject(key);
    }

    public static void reload() {
        try {
            config = new JSONObject(new String(Files.readAllBytes(filePath), StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
        save();
    }

    public static void save() {
        try {
            Files.write(filePath, config.toString(4).getBytes(StandardCharsets.UTF_8));
        } catch (IOException ignored) {
        }
    }

    public static boolean load() {
        boolean exists = Files.exists(filePath);
        try {
            JSONObject def = getDefault();
            if (!exists) {
                Files.createDirectories(Paths.get("plugins/MineCordBot/Users"));
                new File(String.valueOf(filePath)).createNewFile();
                Files.write(filePath, def.toString(4).getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE);
            } else {
                config = new JSONObject(new String(Files.readAllBytes(filePath), StandardCharsets.UTF_8));
                for (String key : def.keySet()) {
                    if (!config.has(key)) {
                        config.put(key, def.get(key));
                        exists = false;
                    }
                }
                if (!exists) {
                    save();
                }
            }
        } catch (IOException ex) {
            System.err.println("Error reading /writing data file: ");
            ex.printStackTrace();
            return false;
        }
        return exists;
    }

    public static JSONObject getData() {
        return config;
    }

    public static Map<Object, String> getInvertedData() {
        Map<String, Object> data = getData().toMap();
        Map<Object, String> invertedData = new HashMap<>();
        for(Map.Entry<String, Object> entry: data.entrySet()){
            invertedData.put(entry.getValue(), entry.getKey());
        }
        return invertedData;
    }

    public static Path getConfigPath() {
        return filePath;
    }

    public static JSONObject getDefault() {
        JSONObject jsonObject = new JSONObject();
        return jsonObject;
    }
}

