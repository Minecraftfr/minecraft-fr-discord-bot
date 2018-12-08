package fr.minecraft.herobrine.listener;

import fr.minecraft.herobrine.core.Herobrine;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.stream.Collectors;

public class MessageReceived extends ListenerAdapter {

    private final Herobrine herobrine;

    public MessageReceived(Herobrine herobrine) {
        this.herobrine = herobrine;
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        @NotNull MessageChannel channel = event.getMessage().getChannel();
        @NotNull Guild guild = event.getGuild();

        if (channel instanceof TextChannel) {
            TextChannel textChannel = ((TextChannel) channel);
            long welcomeChannel = herobrine.conf.getLong("welcomeChannel", 0L);

            if (textChannel.getIdLong() == welcomeChannel) {
                String message = event.getMessage().getContentRaw();
                long nouveauRole = herobrine.conf.getLong("nouveauRole", 0L);

                if (message.toLowerCase().startsWith("approuv")) {
                    Member member = event.getMember();
                    Set<Long> memberRoles = member.getRoles()
                            .stream()
                            .map(Role::getIdLong)
                            .collect(Collectors.toSet());

                    if (memberRoles.contains(nouveauRole)) {
                        event.getGuild().getController().removeRolesFromMember(member, guild.getRoleById(nouveauRole)).queue();
                    }
                }
            }
        }
    }
}