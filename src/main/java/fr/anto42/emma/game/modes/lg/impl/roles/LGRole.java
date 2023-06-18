package fr.anto42.emma.game.modes.lg.impl.roles;

import fr.anto42.emma.coreManager.Module;
import fr.anto42.emma.coreManager.players.roles.Camp;
import fr.anto42.emma.coreManager.players.roles.Role;

public abstract class LGRole extends Role {
    public LGRole(String name, Camp camp, Module gamemode) {
        super(name, camp, gamemode);
    }
}
