package fr.anto42.emma.game.modes.deathNoteV4.impl;


import fr.anto42.emma.game.modes.deathNoteV4.roles.Kira;
import fr.anto42.emma.game.modes.deathNoteV4.roles.Mello;
import fr.anto42.emma.game.modes.deathNoteV4.roles.Near;
import fr.anto42.emma.game.modes.deathNoteV4.roles.Shinigami;
import fr.anto42.emma.game.modes.deathNoteV4.utils.GameUtils;

import java.util.ArrayList;
import java.util.List;

public class DNConfig {

    public List<DNRole> roleList = new ArrayList<DNRole>() {{
        add(new Kira(GameUtils.getModule(), GameUtils.getModule()));
        add(new Shinigami(GameUtils.getModule(), GameUtils.getModule()));
        add(new Mello(GameUtils.getModule(), GameUtils.getModule()));
        add(new Near(GameUtils.getModule(), GameUtils.getModule()));
    }};
    public List<DNRole> getRoleList() {
        return roleList;
    }
}
