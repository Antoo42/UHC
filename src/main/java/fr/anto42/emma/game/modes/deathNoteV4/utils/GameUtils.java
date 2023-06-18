package fr.anto42.emma.game.modes.deathNoteV4.utils;

import fr.anto42.emma.game.modes.deathNoteV4.DNModule;

public class GameUtils {

    private static DNModule module;
    public static void init(DNModule dnModule){
        module = dnModule;
    }

    public static DNModule getModule(){
        return module;
    }
}
