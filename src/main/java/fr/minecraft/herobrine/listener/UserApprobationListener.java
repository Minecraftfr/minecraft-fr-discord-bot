package fr.minecraft.herobrine.listener;

import fr.minecraft.herobrine.core.Herobrine;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Listener who manages new user approbation
 */
public class UserApprobationListener extends ListenerAdapter {

    private final Herobrine herobrine;
    private final Long nouveauRoleId;

    public UserApprobationListener(Herobrine herobrine) {
        this.herobrine = herobrine;
        this.nouveauRoleId = herobrine.conf.getLong("nouveauRole", 0);
    }

    // Called when a new user joins the guild to add to him the non-approved role
    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        @NotNull Guild guild = event.getGuild();
        @NotNull Member member = event.getMember();

        @Nullable Role targetedRole = guild.getRoleById(nouveauRoleId);

        if (targetedRole != null) {
            guild.getController().addRolesToMember(member, targetedRole).queue();
        }
    }

    // Remove the non-approved role from a user when he approved the rules
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        @NotNull MessageChannel channel = event.getMessage().getChannel();
        @NotNull Guild guild = event.getGuild();

        if (channel instanceof TextChannel) {
            TextChannel textChannel = ((TextChannel) channel);
            long welcomeChannel = herobrine.conf.getLong("welcomeChannel", 0L);

            if (textChannel.getIdLong() == welcomeChannel) {
                String message = event.getMessage().getContentRaw();

                if (message.toLowerCase().startsWith("approuv")) {
                    Member member = event.getMember();
                    Set<Long> memberRoles = member.getRoles()
                            .stream()
                            .map(Role::getIdLong)
                            .collect(Collectors.toSet());

                    if (memberRoles.contains(nouveauRoleId)) {
                        event.getGuild().getController().removeRolesFromMember(member, guild.getRoleById(nouveauRoleId)).queue();
                    }
                }
            }
        }
    }

}
