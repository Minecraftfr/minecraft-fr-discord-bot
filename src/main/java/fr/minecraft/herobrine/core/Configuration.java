package fr.minecraft.herobrine.core;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

class Configuration {

    static JsonObject load(Path configFile) throws IOException {
        if (Files.notExists(configFile)) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                    Configuration.class.getResourceAsStream("/config.json")
            ))) {
                Files.write(configFile, reader.lines().collect(Collectors.toList()));
            }
        }

        try (BufferedReader reader = Files.newBufferedReader(configFile, StandardCharsets.UTF_8)) {
            return Json.parse(reader).asObject();
        }
    }

}
