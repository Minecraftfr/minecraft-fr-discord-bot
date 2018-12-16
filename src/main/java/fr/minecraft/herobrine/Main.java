package fr.minecraft.herobrine;

import fr.minecraft.herobrine.core.Herobrine;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class Main {

    public static void main(String[] args) {
        try {
            Path configFilePath = Paths.get(args.length >= 1 ? args[0] : "config.json");

            new Herobrine(configFilePath).loop();
        } catch (LoginException | InvalidPathException | IOException exc) {
            exc.printStackTrace();
        }
    }

}
