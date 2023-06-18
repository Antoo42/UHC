package fr.anto42.emma.game.impl;

import org.bukkit.inventory.ItemStack;

public class UHCConfig {
    private int startBorderSize = 800;
    private final StuffConfig stuffConfig = new StuffConfig();

    public StuffConfig getStuffConfig() {
        return stuffConfig;
    }

    public int getStartBorderSize() {
        return startBorderSize;
    }

    private float blockPerS = 1;

    public float getBlockPerS() {
        return blockPerS;
    }

    public void setBlockPerS(float blockPerS) {
        this.blockPerS = blockPerS;
    }

    private int finalBorderSize = 300;

    public int getFinalBorderSize() {
        return finalBorderSize;
    }

    public void setFinalBorderSize(int finalBorderSize) {
        this.finalBorderSize = finalBorderSize;
    }

    public void setStartBorderSize(int startBorderSize) {
        this.startBorderSize = startBorderSize;
    }

    private boolean nether = false;

    public boolean isNether() {
        return nether;
    }

    public void setNether(boolean nether) {
        this.nether = nether;
    }
    private boolean end = false;

    public boolean isEnd() {
        return end;
    }

    public void setEnd(boolean end) {
        this.end = end;
    }


    private int pvp = 20;

    public int getPvp() {
        return pvp;
    }

    public void setPvp(int pvp) {
        this.pvp = pvp;
    }

    private String allowSpec = "everyone";

    public String getAllowSpec() {
        return allowSpec;
    }

    public void setAllowSpec(String allowSpec) {
        this.allowSpec = allowSpec;
    }

    private int slots = 40;

    public int getSlots() {
        return slots;
    }

    public void setSlots(int slots) {
        this.slots = slots;
    }

    private int timerBorder = 60;

    public int getTimerBorder() {
        return timerBorder;
    }

    public void setTimerBorder(int timerBorder) {
        this.timerBorder = timerBorder;
    }

    private boolean gappleOnKill = true;

    public boolean isGappleOnKill() {
        return gappleOnKill;
    }

    public void setGappleOnKill(boolean gappleOnKill) {
        this.gappleOnKill = gappleOnKill;
    }

    private int afkTime = 10;

    public int getAfkTime() {
        return afkTime;
    }

    public void setAfkTime(int afkTime) {
        this.afkTime = afkTime;
    }

    private int godStart = 30;

    public int getGodStart() {
        return godStart;
    }

    public void setGodStart(int godStart) {
        this.godStart = godStart;
    }

    private ItemStack[] armorInv = new ItemStack[0];

    public ItemStack[] getArmorInv() {
        return armorInv;
    }

    public void setArmorInv(ItemStack[] armorInv) {
        this.armorInv = armorInv;
    }

    private ItemStack[] startInv = new ItemStack[0];

    public ItemStack[] getStartInv() {
        return startInv;
    }
    
    public void setStartInv(ItemStack[] startInv) {
        this.startInv = startInv;
    }

    private int xpBoost = 1;

    public int getXpBoost() {
        return xpBoost;
    }

    public void setXpBoost(int xpBoost) {
        this.xpBoost = xpBoost;
    }

    public boolean isGroupSystem() {
        return groupSystem;
    }

    public void setGroupSystem(boolean groupSystem) {
        this.groupSystem = groupSystem;
    }

    private boolean groupSystem = false;

    private int diamonLimit = 17;

    public int getDiamonLimit() {
        return diamonLimit;
    }

    public void setDiamonLimit(int diamonLimit) {
        this.diamonLimit = diamonLimit;
    }

    private boolean bowLife = false;

    public boolean isBowLife() {
        return bowLife;
    }

    public void setBowLife(boolean bowLife) {
        this.bowLife = bowLife;
    }

    private boolean lifeTab = false;

    public boolean isLifeTab() {
        return lifeTab;
    }

    public void setLifeTab(boolean lifeTab) {
        this.lifeTab = lifeTab;
    }


    private boolean milkBukket = true;

    public boolean isMilkBukket() {
        return milkBukket;
    }

    public void setMilkBukket(boolean milkBukket) {
        this.milkBukket = milkBukket;
    }

    private boolean HideScenarios = false;

    public boolean isHideScenarios() {
        return HideScenarios;
    }

    public void setHideScenarios(boolean hideScenarios) {
        HideScenarios = hideScenarios;
    }
}