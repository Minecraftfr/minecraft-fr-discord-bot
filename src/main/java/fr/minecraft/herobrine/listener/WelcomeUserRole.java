package fr.minecraft.herobrine.listener;

import fr.minecraft.herobrine.core.Herobrine;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WelcomeUserRole extends ListenerAdapter {

    private final Herobrine herobrine;

    public WelcomeUserRole(Herobrine herobrine) {
        this.herobrine = herobrine;
    }

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        @NotNull Guild guild = event.getGuild();
        @NotNull Member member = event.getMember();

        long roleIdLong = herobrine.conf.getLong("nouveauRole", 0);
        @Nullable Role targetedRole = guild.getRoleById(roleIdLong);

        if (targetedRole != null) {
            guild.getController().addRolesToMember(member, targetedRole).queue();
        }
    }
}
