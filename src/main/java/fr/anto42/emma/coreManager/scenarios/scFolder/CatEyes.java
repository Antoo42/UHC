package fr.anto42.emma.coreManager.scenarios.scFolder;

import fr.anto42.emma.UHC;
import fr.anto42.emma.coreManager.scenarios.ScenarioManager;
import fr.anto42.emma.coreManager.scenarios.ScenarioType;
import fr.anto42.emma.coreManager.scenarios.UHCScenario;
import fr.anto42.emma.utils.skulls.SkullList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class CatEyes extends UHCScenario {
    public CatEyes(ScenarioManager scenarioManager, int page) {
        super("CatEyes", SkullList.EYE.getItemStack(), scenarioManager, page);
        super.setDesc("§8┃ §fAh... On y voit quand même bien mieux !");
        setScenarioType(ScenarioType.PVE);
    }


    @Override
    public void onEnable() {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            onlinePlayer.addPotionEffect(PotionEffectType.NIGHT_VISION.createEffect(Integer.MAX_VALUE, 1), false);
        }
        Bukkit.getScheduler().runTaskTimer(UHC.getInstance(), () -> {
            if (!isActivated()){
                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    onlinePlayer.removePotionEffect(PotionEffectType.NIGHT_VISION);
                }
            }else{
                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    onlinePlayer.addPotionEffect(PotionEffectType.NIGHT_VISION.createEffect(Integer.MAX_VALUE, 1), false);
                }
            }
        }, 0L, 33L);
    }
}
