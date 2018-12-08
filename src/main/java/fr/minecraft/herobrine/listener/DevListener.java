package fr.minecraft.herobrine.listener;

import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.entities.SelfUser;
import net.dv8tion.jda.core.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DevListener extends ListenerAdapter {

    @Override
    public void onPrivateMessageReceived(PrivateMessageReceivedEvent event) {
        @NotNull PrivateChannel privateChannel = event.getChannel();
        @NotNull SelfUser me = event.getJDA().getSelfUser();
        String message = event.getMessage().getContentRaw();

        if (message.equalsIgnoreCase("listroles")) {
            @NotNull List<Guild> mutualGuilds = event.getAuthor().getMutualGuilds();

            if (mutualGuilds.isEmpty()) {
                privateChannel.sendMessage("Nous n'avons aucune guilde en commun. :'(").queue();
                return;
            }

            for (Guild guild : mutualGuilds) {
                Member meAsMember = guild.getMember(me);

                if (!meAsMember.hasPermission(Permission.MANAGE_ROLES)) {
                    privateChannel.sendMessage(String.format("Je n'aie pas assez de permissions sur la guilde %s pour faire ceci !", guild.getName())).queue();
                } else {
                    StringBuilder response = new StringBuilder();
                    response.append("Guilde ");
                    response.append(guild.getName());
                    response.append(" :");

                    guild.getRoles().forEach(r -> {
                        response.append("\n  ");
                        response.append(r.getName());
                        response.append(" - ");
                        response.append(r.getIdLong());
                    });

                    privateChannel.sendMessage(response.toString()).queue();
                }

            }
        }
    }
}
