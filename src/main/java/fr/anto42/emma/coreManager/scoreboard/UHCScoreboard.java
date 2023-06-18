package fr.anto42.emma.coreManager.scoreboard;

import fr.anto42.emma.coreManager.scoreboard.tools.ObjectiveSign;

import java.util.UUID;

public interface UHCScoreboard {
    void updateScoreboard(ObjectiveSign objectiveSign, UUID uuid, String ip);
}
