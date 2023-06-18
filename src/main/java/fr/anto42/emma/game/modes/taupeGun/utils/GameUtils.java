package fr.anto42.emma.game.modes.taupeGun.utils;


import fr.anto42.emma.game.modes.taupeGun.TGModule;

public class GameUtils {
    private static TGModule werewolf;

    public static void setWerewolf(TGModule werewolfPlugin){
        werewolf = werewolfPlugin;
    }
    public static TGModule getModule() {
        return werewolf;
    }


}
