package fr.anto42.emma.coreManager.discord;

import fr.anto42.emma.UHC;
import fr.anto42.emma.coreManager.scenarios.ScenarioManager;
import fr.anto42.emma.coreManager.teams.UHCTeamManager;
import fr.anto42.emma.game.UHCGame;
import org.bukkit.Bukkit;
import org.bukkit.Color;

import java.io.IOException;
import java.util.Objects;

public class DiscordManager {
    private static final String announceURL = (String) UHC.getInstance().getConfig().get("announceWebhook");
    private static final String winURL = (String) UHC.getInstance().getConfig().get("winWebhook");
    private static final String avatarURL = (String) UHC.getInstance().getConfig().get("avatarURL");
    private static final String logURL = (String) UHC.getInstance().getConfig().get("logWebhook");

    public static String getAnnounceURL() {
        return announceURL;
    }

    public static String getWinURL() {
        return winURL;
    }


    private final UHCGame uhc = UHC.getInstance().getUhcGame();
    private final ScenarioManager scenarioManager = UHC.getInstance().getUhcManager().getScenarioManager();
    public void sendAnounce(){
        DiscordWebhook webhook = new DiscordWebhook(announceURL);
        webhook.setUsername("UHCHost - Annonce de partie");
        webhook.addEmbed(new DiscordWebhook.EmbedObject()
                .setTitle(uhc.getUhcConfig().getUHCName())
                .setColor(Color.ORANGE)
                .setDescription("🎮 Mode de jeu: **" + UHC.getInstance().getUhcManager().getGamemode().getDiscordName() + "**" +
                        "\n👨‍💻Host: **" + uhc.getUhcData().getHostName() + "**" +
                        "\n\n⚔PvP: **" + uhc.getUhcConfig().getPvp() +" minute(s)**" +
                        "\n🧨Bordure: **" + uhc.getUhcConfig().getTimerBorder() + " minute(s)**" +
                        "\n\nÉquipes: **" + UHCTeamManager.getInstance().getDisplayFormat() + "**" +
                        "\n📖Scénarios: " + scenarioManager.getFormattedScenarios() +
                        "\n\n» IP: " + UHC.getInstance().getConfig().get("ip"))
                .setFooter("Provient du serveur: " + Bukkit.getServer().getServerName(), null));
        try {
            webhook.execute();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
