package fr.minecraft.herobrine.core;

import com.eclipsesource.json.JsonObject;
import fr.minecraft.herobrine.listener.DevListener;
import fr.minecraft.herobrine.listener.MessageReceived;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.util.Scanner;

public class Herobrine {

    private static final Logger LOGGER = LoggerFactory.getLogger(Herobrine.class);

    public final @NotNull JsonObject conf;
    private final @NotNull JDA jda;
    private final @NotNull Profile profile;
    private volatile boolean running = true;

    public Herobrine() throws LoginException, IOException {
        this.conf = Configuration.load();

        // Load Profile
        this.profile = Profile.fromString(conf.getString("profile", ""));
        LOGGER.info(String.format("Using '%s' profile", this.profile.toString()));

        // Prepare the JDA Object building
        JDABuilder jdaBuilder = new JDABuilder(AccountType.BOT)
                .setToken(this.conf.getString("token", null))
                .addEventListener(new MessageReceived(this));

        // Register DevListener only for dev period
        if (profile.equals(Profile.DEV)) {
            jdaBuilder.addEventListener(new DevListener());
        }

        // Finally build JDA
        this.jda = jdaBuilder.build();

        // Set presence
        this.jda.getPresence().setPresence(OnlineStatus.ONLINE, Game.playing(this.conf.getString("presence", "")));
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
