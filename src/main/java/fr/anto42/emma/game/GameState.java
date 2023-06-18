package fr.anto42.emma.game;

public enum GameState {
    WAITING("§6Partie en attente"), STARTING("§6Démarrage de la partie"), PLAYING("§aPartie en jeu"), FINISH("§cPartie terminée");

    private final String string;

    GameState(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }
}
