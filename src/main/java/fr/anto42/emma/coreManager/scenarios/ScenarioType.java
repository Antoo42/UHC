package fr.anto42.emma.coreManager.scenarios;

public enum ScenarioType {
    STUFF("Equipements"),
    PVP("Combat"),
    PVE("PvE"),
    MINNING("Minage"),
    FUN("Extragavant"),
    WORLD("Monde"),
    OTHER("Autre"),
    ALL("Tous")

    ;


    private final String typeName;
    ScenarioType(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }
}
