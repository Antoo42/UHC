package fr.anto42.emma.coreManager.scenarios;

import fr.anto42.emma.UHC;
import fr.anto42.emma.game.UHCGame;
import fr.blendman974.kinventory.inventories.KInventory;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public abstract class UHCScenario implements Listener {
    private final String name;
    private String desc;
    private ScenarioType scenarioType;
    private final ItemStack itemStack;
    private boolean configurable = false;
    private KInventory kInventory;
    private final UHCGame uhcGame = UHC.getInstance().getUhcGame();
    private final ScenarioManager scenarioManager;

    private final int page;

    public UHCScenario(String name, ItemStack itemStack, ScenarioManager scenarioManager, int page) {
        setScenarioType(ScenarioType.OTHER);
        this.name = name;
        this.scenarioManager = scenarioManager;
        this.page = page;
        this.desc = "§cAucune information.";
        this.itemStack = itemStack;
    }

    public ScenarioManager getScenarioManager(){
        return scenarioManager;
    }

    public String getName() {
        return name;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public boolean isActivated(){
        if (scenarioManager.getActivatedScenarios().contains(this))
            return true;
        else
            return false;
    }

    public boolean isConfigurable() {
        return configurable;
    }

    public void setConfigurable(boolean configurable) {
        this.configurable = configurable;
    }

    public KInventory getkInventory() {
        return kInventory;
    }

    public void setkInventory(KInventory kInventory) {
        this.kInventory = kInventory;
    }

    public UHCGame getUhcGame() {
        return uhcGame;
    }

    public void onEnable(){
    }

    public void onDisable(){}

    public int getPage() {
        return page;
    }

    public ScenarioType getScenarioType() {
        return scenarioType;
    }

    public void setScenarioType(ScenarioType scenarioType) {
        this.scenarioType = scenarioType;
    }


    public void onStart(){}
}
