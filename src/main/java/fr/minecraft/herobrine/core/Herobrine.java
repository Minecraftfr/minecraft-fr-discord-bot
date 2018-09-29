package fr.minecraft.herobrine.core;

import com.eclipsesource.json.JsonObject;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.util.Scanner;

public class Herobrine {

    private static final Logger LOGGER = LoggerFactory.getLogger(Herobrine.class);
    private final JsonObject conf;
    private final JDA jda;
    private volatile boolean running = true;

    public Herobrine() throws LoginException, IOException {
        this.conf = Configuration.load();

        this.jda = new JDABuilder(AccountType.BOT).setToken(
                this.conf.getString("token", null)
        ).build();
    }

    public void loop() {
        try (Scanner scanner = new Scanner(System.in)) {
            while (running) {
                if (scanner.nextLine().equalsIgnoreCase("stop"))
                    stop();
            }
        }
    }

    private void stop() {
        this.running = false;

        this.jda.shutdown();
    }

}
