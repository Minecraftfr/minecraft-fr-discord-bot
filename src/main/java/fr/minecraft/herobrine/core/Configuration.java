package fr.minecraft.herobrine.core;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class Configuration {

    private static final Path PATH = Paths.get("config.json");

    public static JsonObject load() throws IOException {
        if (Files.notExists(PATH)) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                    Configuration.class.getResourceAsStream("/config.json")
            ))) {
                Files.write(PATH, reader.lines().collect(Collectors.toList()));
            }
        }

        try (BufferedReader reader = Files.newBufferedReader(PATH, StandardCharsets.UTF_8)) {
            return Json.parse(reader).asObject();
        }
    }


}
