package fr.anto42.emma.game.modes.deathNoteV4.roles.investigator;

public enum InvestPowers {
    INVESTIGATION("enquete","  §8• §fVous pouvez mener une enquêtes sur une personne de votre équipe avec l'aide de la commande /enquête <pseudo>, en échange de 3 coeurs permanents"),
    REAL_DIES("morts","  §8• §fVous pouvez voir les réelles morts."),
    WHEN_DN("dn","  §8• §fLors de l'utilisation du Death Note, vous serez avertis."),
    SEE_CHAT("chat","  §8• §fVous pouvez observer les messages dans le chat des Kira de manière anonyme.")
    ;

    private final String powerName;
    private final String desc;

    InvestPowers(String powerName, String desc){
        this.powerName = powerName;
        this.desc = desc;
    }

    public String getPowerName() {
        return powerName;
    }

    public String getDesc() {
        return desc;
    }
}
