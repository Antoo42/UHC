package fr.anto42.emma.utils.discord;

import fr.anto42.emma.UHC;
import fr.anto42.emma.coreManager.scenarios.ScenarioManager;
import fr.anto42.emma.coreManager.teams.UHCTeamManager;
import fr.anto42.emma.game.UHCGame;
import org.bukkit.Bukkit;
import org.bukkit.Color;

import java.io.IOException;

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



    private final ScenarioManager scenarioManager = UHC.getInstance().getUhcManager().getScenarioManager();

    public void sendAnounce(){
        DiscordWebhook webhook = new DiscordWebhook(announceURL);
        webhook.setUsername("PeijinUHC - Annonce de partie");
        webhook.addEmbed(new DiscordWebhook.EmbedObject()
                .setTitle(uhc.getUhcConfig().getUHCName())
                .setColor(Color.ORANGE)
                .addField("🎮 Mode de jeu:", UHC.getInstance().getUhcManager().getGamemode().getDiscordName(), false)
                .addField(" 👨‍💻Host:", uhc.getUhcData().getHostName(), false)
                .addField(" ⚔PvP:", uhc.getUhcConfig().getPvp() +" minute(s)", false)
                .addField(" 🧨Bordure:", uhc.getUhcConfig().getTimerBorder() + " minute(s)", false)
                .addField(" Équipes:", UHCTeamManager.getInstance().getDisplayFormat(), false)
                .addField(" 📖Scénarios: ", scenarioManager.getFormattedScenarios(), true)
                .addField(" » IP: ", "peijinuhc.eu", false)
                .setFooter("Provient du serveur: " + Bukkit.getServerName(), null));
        try {
            webhook.execute();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    private final UHCGame uhc = UHC.getInstance().getUhcGame();
    public void sendStarterAnounce(){
        DiscordWebhook webhook = new DiscordWebhook("https://discord.com/api/webhooks/1080615356684587038/F0RyITC4w-7-y8qgZoHDI7tKYsDA_yHOuKSkyHsTQcQeUH4ElKrYrCt8-wSSnC_8epXO");
        webhook.setUsername("Peijin - UHC");
        webhook.addEmbed(new DiscordWebhook.EmbedObject()
                .setTitle(uhc.getUhcConfig().getUHCName())
                .setColor(Color.BLUE)
                .setDescription("» Un serveur UHC vient de se démarrer")
                .setFooter("Provient du serveur: " + Bukkit.getServerName(), null));
        try {
            webhook.execute();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
