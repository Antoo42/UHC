package fr.anto42.emma.coreManager.scoreboard;

import fr.anto42.emma.UHC;
import fr.anto42.emma.game.GameState;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/*
 * This file is part of SamaGamesAPI.
 *
 * SamaGamesAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SamaGamesAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SamaGamesAPI.  If not, see <http://www.gnu.org/licenses/>.
 */
public class ScoreboardManager {
    private final Map<UUID, PersonalScoreboard> scoreboards;
    private final ScheduledFuture glowingTask;
    private final ScheduledFuture reloadingTask;
    private int ipCharIndex;
    private int cooldown;
    

    public ScoreboardManager() {
        scoreboards = new HashMap<>();
        ipCharIndex = 0;
        cooldown = 0;

        glowingTask = UHC.getInstance().getScheduledExecutorService().scheduleAtFixedRate(() ->
        {
            String ip = colorIpAt();
            for (PersonalScoreboard scoreboard : scoreboards.values())
                UHC.getInstance().getExecutorMonoThread().execute(() -> scoreboard.setLines(ip));
        }, 80, 80, TimeUnit.MILLISECONDS);

        reloadingTask = UHC.getInstance().getScheduledExecutorService().scheduleAtFixedRate(() ->
        {
            for (PersonalScoreboard scoreboard : scoreboards.values())
                UHC.getInstance().getExecutorMonoThread().execute(scoreboard::reloadData);
        }, 1, 1, TimeUnit.SECONDS);
    }

    public void onDisable() {
        scoreboards.values().forEach(PersonalScoreboard::onLogout);
    }

    public void onLogin(Player player) {
        if (scoreboards.containsKey(player.getUniqueId())) {
            return;
        }
        scoreboards.put(player.getUniqueId(), new PersonalScoreboard(player));
    }

    public void onLogout(Player player) {
        if (scoreboards.containsKey(player.getUniqueId())) {
            scoreboards.get(player.getUniqueId()).onLogout();
            scoreboards.remove(player.getUniqueId());
        }
    }

    public void update(Player player) {
        if (scoreboards.containsKey(player.getUniqueId())) {
            scoreboards.get(player.getUniqueId()).reloadData();
        }
    }


    private String colorIpAt() {
        String ip = "mc.peijinuhc.eu";

        if (this.cooldown > 0) {
            this.cooldown--;
            return ChatColor.GOLD + ip;
        }

        StringBuilder formattedIp = new StringBuilder();

        if (this.ipCharIndex > 0) {
            formattedIp.append(ip, 0, this.ipCharIndex - 1);
            formattedIp.append(ChatColor.YELLOW).append(ip.charAt(this.ipCharIndex - 1));
        }
        else
            formattedIp.append(ip, 0, this.ipCharIndex);


        formattedIp.append(ChatColor.GOLD).append(ip.charAt(this.ipCharIndex));

        if (this.ipCharIndex + 1 < ip.length()) {
            formattedIp.append(ChatColor.GOLD).append(ip.charAt(this.ipCharIndex + 1));

            if (this.ipCharIndex + 2 < ip.length())
                formattedIp.append(ChatColor.GOLD).append(ip.substring(this.ipCharIndex + 2));

            this.ipCharIndex++;
        } else {
            this.ipCharIndex = 0;
            this.cooldown = 20;
        }

        return ChatColor.GOLD + formattedIp.toString();
    }

    public Map<UUID, PersonalScoreboard> getScoreboards() {
        return scoreboards;
    }
}