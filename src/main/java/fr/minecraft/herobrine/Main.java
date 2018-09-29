package fr.minecraft.herobrine;

import fr.minecraft.herobrine.core.Herobrine;

import javax.security.auth.login.LoginException;
import java.io.IOException;

public final class Main {

    public static void main(String[] args) {
        try {
            new Herobrine().loop();
        } catch (LoginException | IOException exc) {
            exc.printStackTrace();
        }
    }

}
