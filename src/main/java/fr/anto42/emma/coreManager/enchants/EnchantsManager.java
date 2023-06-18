package fr.anto42.emma.coreManager.enchants;

public class EnchantsManager {


    private int diamondSharpness, diamondArmor;
    private int ironSharpness, ironArmor;
    private int infinity, power, punch, kb, fireAspect, flame, thorns;


    public EnchantsManager() {

        this.diamondSharpness = 3;
        this.diamondArmor = 2;

        this.ironSharpness = 4;
        this.ironArmor = 3;

        this.infinity = 0;
        this.power = 3;
        this.punch = 0;
        this.kb = 1;
        this.fireAspect = 0;
        this.flame = 0;
        this.thorns = 0;
    }


    public int getDiamondSharpness() {
        return diamondSharpness;
    }
    public void setDiamondSharpness(int diamondSharpness) {
        this.diamondSharpness = diamondSharpness;
    }
    public void addDiamondSharpness(int add) {
        setDiamondSharpness(Math.min(getDiamondSharpness() + add, 5));
    }
    public void removeDiamondSharpness(int remove) {
        setDiamondSharpness(Math.max(getDiamondSharpness() - remove, 0));
    }

    public int getDiamondArmor() {
        return diamondArmor;
    }
    public void setDiamondArmor(int diamondArmor) {
        this.diamondArmor = diamondArmor;
    }
    public void addDiamondArmor(int add) {
        setDiamondArmor(Math.min(getDiamondArmor() + add, 4));
    }
    public void removeDiamondArmor(int remove) {
        setDiamondArmor(Math.max(getDiamondArmor() - remove, 0));
    }

    public int getIronSharpness() {
        return ironSharpness;
    }
    public void setIronSharpness(int ironSharpness) {
        this.ironSharpness = ironSharpness;
    }
    public void addIronSharpness(int add) {
        setIronSharpness(Math.min(getIronSharpness() + add, 5));
    }
    public void removeIronSharpness(int remove) {
        setIronSharpness(Math.max(getIronSharpness() - remove, 0));
    }

    public int getIronArmor() {
        return ironArmor;
    }
    public void setIronArmor(int ironArmor) {
        this.ironArmor = ironArmor;
    }
    public void addIronArmor(int add) {
        setIronArmor(Math.min(getIronArmor() + add, 4));
    }
    public void removeIronArmor(int remove) {
        setIronArmor(Math.max(getIronArmor() - remove, 0));
    }

    public int getInfinity() {
        return infinity;
    }
    public void setInfinity(int infinity) {
        this.infinity = infinity;
    }
    public void addInfinity(int add) {
        setInfinity(Math.min(getInfinity() + add, 1));
    }
    public void removeInfinity(int remove) {
        setInfinity(Math.max(getInfinity() - remove, 0));
    }

    public int getPower() {
        return power;
    }
    public void setPower(int power) {
        this.power = power;
    }
    public void addPower(int add) {
        setPower(Math.min(getPower() + add, 5));
    }
    public void removePower(int remove) {
        setPower(Math.max(getPower() - remove, 0));
    }

    public int getPunch() {
        return punch;
    }
    public void setPunch(int punch) {
        this.punch = punch;
    }
    public void addPunch(int add) {
        setPunch(Math.min(getPunch() + add, 2));
    }
    public void removePunch(int remove) {
        setPunch(Math.max(getPunch() - remove, 0));
    }

    public int getKb() {
        return kb;
    }
    public void setKb(int kb) {
        this.kb = kb;
    }
    public void addKb(int add) {
        setKb(Math.min(getKb() + add, 2));
    }
    public void removeKb(int remove) {
        setKb(Math.max(getKb() - remove, 0));
    }

    public int getFireAspect() {
        return fireAspect;
    }
    public void setFireAspect(int fireAspect) {
        this.fireAspect = fireAspect;
    }
    public void addFireAspect(int add) {
        setFireAspect(Math.min(getFireAspect() + add, 2));
    }
    public void removeFireAspect(int remove) {
        setFireAspect(Math.max(getFireAspect() - remove, 0));
    }

    public int getFlame() {
        return flame;
    }
    public void setFlame(int flame) {
        this.flame = flame;
    }
    public void addFlame(int add) {
        setFlame(Math.min(getFlame() + add, 1));
    }
    public void removeFlame(int remove) {
        setFlame(Math.max(getFlame() - remove, 0));
    }

    public int getThorns() {
        return thorns;
    }
    public void setThorns(int thorns) {
        this.thorns = thorns;
    }
    public void addThorns(int add) {
        setThorns(Math.min(getThorns() + add, 3));
    }
    public void removeThorns(int remove) {
        setThorns(Math.max(getThorns() - remove, 0));
    }

}
