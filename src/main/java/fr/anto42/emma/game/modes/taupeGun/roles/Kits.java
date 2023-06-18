package fr.anto42.emma.game.modes.taupeGun.roles;

public enum Kits {
    MINEUR("mineur"), WIZARD("sorcier"), //MAGICIEN("magicien"),
    AIGLE("aigle"), ENCHANTEUR("enchanteur")
    ;

    private final String name;
    Kits(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
