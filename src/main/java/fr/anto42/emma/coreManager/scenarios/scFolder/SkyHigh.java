package fr.anto42.emma.coreManager.scenarios.scFolder;

import fr.anto42.emma.UHC;
import fr.anto42.emma.coreManager.listeners.customListeners.StartEvent;
import fr.anto42.emma.coreManager.scenarios.ScenarioManager;
import fr.anto42.emma.coreManager.scenarios.ScenarioType;
import fr.anto42.emma.coreManager.scenarios.UHCScenario;
import fr.anto42.emma.coreManager.scenarios.uis.SkyHighGUI;
import fr.anto42.emma.game.GameState;
import fr.anto42.emma.utils.materials.ItemCreator;
import fr.anto42.emma.utils.SoundUtils;
import fr.anto42.emma.utils.TimeUtils;
import fr.anto42.emma.utils.skulls.SkullList;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

public class SkyHigh extends UHCScenario {
    public SkyHigh(ScenarioManager scenarioManager, int page) {
        super("SkyHigh", new ItemCreator(SkullList.EARTH.getItemStack()).get(), scenarioManager, page);
        super.setDesc("§8┃ §fUn des pilliers de l'UHC mondial, ce scénario vous fera prendre de la hauteur sur la situation");
        taskId = -1;
        super.setConfigurable(true);
        super.setkInventory(new SkyHighGUI(this).getkInventory());
        setScenarioType(ScenarioType.PVP);
    }

    private int taskId;
    private long delay = 60;
    private long period = 30;
    private int yLayer = 120;


    @EventHandler
    public void onStart(StartEvent e){
        taskId = Bukkit.getScheduler().scheduleSyncDelayedTask(UHC.getInstance(), new SkyHighThread(this), TimeUtils.seconds((int) delay));
    }

    @Override
    public void onEnable() {
        if (getUhcGame().getGameState() == GameState.PLAYING){
            long timeUntilFirstRun = delay*20*60 - getUhcGame().getUhcData().getTimer();
            if (timeUntilFirstRun < 0){
                timeUntilFirstRun = 0;
            }
            taskId = Bukkit.getScheduler().scheduleSyncDelayedTask(UHC.getInstance(), new SkyHighThread(this), TimeUtils.seconds(((int) timeUntilFirstRun)));
        }
    }

    @Override
    public void onDisable() {
        if (taskId != -1) {
            Bukkit.getScheduler().cancelTask(taskId);
        }
    }

    public static class SkyHighThread implements Runnable{

        private final SkyHigh listener;

        public SkyHighThread(SkyHigh listener){
            this.listener = listener;
        }

        @Override
        public void run() {
            if (!listener.isActivated())
                return;

            UHC.getInstance().getUhcGame().getUhcData().getUhcPlayerList().stream().filter(uhcPlayer ->  uhcPlayer.getBukkitPlayer() != null).forEach(uhcPlayer -> {
                Player player = uhcPlayer.getBukkitPlayer();
                if (player.getLocation().getBlockY() < listener.yLayer) {
                    uhcPlayer.sendClassicMessage("§cVous êtes en dessous de la couche §e" + listener.yLayer + "§c ! Par conséquant, le scénario §bSkyHigh §cvous a infligé des dégâts !" );
                    player.setHealth(player.getHealth() - 1);
                    SoundUtils.playSoundToPlayer(player, Sound.IRONGOLEM_HIT);
                }
            });

            listener.taskId = Bukkit.getScheduler().scheduleSyncDelayedTask(UHC.getInstance(), this, TimeUtils.seconds(((int) listener.period)));
        }

    }

    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    public long getPeriod() {
        return period;
    }

    public void setPeriod(long period) {
        this.period = period;
    }

    public int getyLayer() {
        return yLayer;
    }

    public void setyLayer(int yLayer) {
        this.yLayer = yLayer;
    }
}
