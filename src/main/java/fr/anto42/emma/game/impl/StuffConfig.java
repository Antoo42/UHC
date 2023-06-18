package fr.anto42.emma.game.impl;

public class StuffConfig {
    private boolean diamondHelmet = true;
    private boolean diamondChesp = true;
    private boolean diamondLeggins = true;
    private boolean diamondBoots = true;
    public boolean isDiamondHelmet() {
        return diamondHelmet;
    }

    public void setDiamondHelmet(boolean diamondHelmet) {
        this.diamondHelmet = diamondHelmet;
    }

    public boolean isDiamondChesp() {
        return diamondChesp;
    }

    public void setDiamondChesp(boolean diamondChesp) {
        this.diamondChesp = diamondChesp;
    }

    public boolean isDiamondLeggins() {
        return diamondLeggins;
    }

    public void setDiamondLeggins(boolean diamondLeggins) {
        this.diamondLeggins = diamondLeggins;
    }

    public boolean isDiamondBoots() {
        return diamondBoots;
    }

    public void setDiamondBoots(boolean diamondBoots) {
        this.diamondBoots = diamondBoots;
    }


    private int ironProtect = 3;
    private int diamondProtect = 2;
    private int ironSharpness = 3;
    private int diamonSharpness = 3;
    private int kb = 1;
    private int power = 3;
    private int punch = 1;

    public int getIronProtect() {
        return ironProtect;
    }

    public void setIronProtect(int ironProtect) {
        this.ironProtect = ironProtect;
    }

    public int getDiamondProtect() {
        return diamondProtect;
    }

    public void setDiamondProtect(int diamondProtect) {
        this.diamondProtect = diamondProtect;
    }

    public int getIronSharpness() {
        return ironSharpness;
    }

    public void setIronSharpness(int ironSharpness) {
        this.ironSharpness = ironSharpness;
    }

    public int getDiamonSharpness() {
        return diamonSharpness;
    }

    public void setDiamonSharpness(int diamonSharpness) {
        this.diamonSharpness = diamonSharpness;
    }

    public int getKb() {
        return kb;
    }

    public void setKb(int kb) {
        this.kb = kb;
    }


    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getPunch() {
        return punch;
    }

    public void setPunch(int punch) {
        this.punch = punch;
    }
}
