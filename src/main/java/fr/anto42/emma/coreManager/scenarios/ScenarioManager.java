package fr.anto42.emma.coreManager.scenarios;

import fr.anto42.emma.UHC;
import fr.anto42.emma.coreManager.scenarios.scFolder.*;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class ScenarioManager {


    private final List<UHCScenario> initialScenarioList = new ArrayList<>();
    private final List<UHCScenario> activatedScenarios = new ArrayList<>();


    public ScenarioManager() {
        getInitialScenarioList().clear();
        getActivatedScenarios().clear();
        getInitialScenarioList().add(new BetaZombies(this,1));
        //getInitialScenarioList().add(new BigCrack(this,1));
        getInitialScenarioList().add(new BowSwap(this,1));
        getInitialScenarioList().add(new CatEyes(this,1));
        getInitialScenarioList().add(new CutClean(this,1));
        getInitialScenarioList().add(new DiamondBlood(this,1));
        getInitialScenarioList().add(new DoubleHealth(this,1));
        getInitialScenarioList().add(new DragonRush(this, 1));
        getInitialScenarioList().add(new FastSmelting(this,1));
        getInitialScenarioList().add(new FinalHeal(this,1));
        getInitialScenarioList().add(new FireLess(this,1));
        getInitialScenarioList().add(new FlowerPower(this,1));
        getInitialScenarioList().add(new GoneFishing(this,1));
        getInitialScenarioList().add(new HasteyBoys(this,1));
        getInitialScenarioList().add(new HorseLess(this,1));
        getInitialScenarioList().add(new InfiniteEnchant(this,1));
        getInitialScenarioList().add(new LuckyLeaves(this,1));
        getInitialScenarioList().add(new MasterLevel(this,1));
        getInitialScenarioList().add(new MeleeFun(this,1));
        getInitialScenarioList().add(new NineSlots(this,1));
        getInitialScenarioList().add(new NoBow(this,1));
        //getInitialScenarioList().add(new NoBreak(this));
        getInitialScenarioList().add(new NoCleanUP(this,1));
        getInitialScenarioList().add(new NoFall(this,1));
        getInitialScenarioList().add(new NoFood(this,1));
        //getInitialScenarioList().add(new NoNameTag(this,1));
        getInitialScenarioList().add(new OnlyStones(this,1));
        getInitialScenarioList().add(new ProgressiveSpeed(this, 1));
        getInitialScenarioList().add(new RandomCraft(this,1));
        getInitialScenarioList().add(new RandomLoots(this,1));
        getInitialScenarioList().add(new RodLess(this,2));
        getInitialScenarioList().add(new SafeMiners(this,2));
        getInitialScenarioList().add(new SkyHigh(this,2));
        getInitialScenarioList().add(new Symetrie(this,2));
        getInitialScenarioList().add(new SuperHeroes(this,2));
        getInitialScenarioList().add(new Timber(this,2));
        getInitialScenarioList().add(new TimberPvP(this,2));
        getInitialScenarioList().add(new TimeBomb(this,2));
        getInitialScenarioList().add(new VanillaPlus(this,2));
        getInitialScenarioList().add(new VeinMiner(this,2));
        getInitialScenarioList().add(new WeakestLink(this,2));
        getInitialScenarioList().add(new WebCage(this,2));
        getInitialScenarioList().add(new XHealths(this,2));
        UHC.getInstance().getUhcGame().getUhcConfig().getScenarios().forEach(s -> {
            for (UHCScenario uhcScenario : getInitialScenarioList()) {
                if (uhcScenario.getName().equalsIgnoreCase(s))
                    getActivatedScenarios().add(uhcScenario);
            }
        });
    }

    public List<UHCScenario> getInitialScenarioList() {
        return initialScenarioList;
    }

    public boolean isEnabled(String name){
        AtomicBoolean a = new AtomicBoolean(false);
        getActivatedScenarios().forEach(scenario -> {
            if (scenario.getName().equalsIgnoreCase(name))
                a.set(true);
        });
        return a.get();
    }


    public List<UHCScenario> getActivatedScenarios() {
        return activatedScenarios;
    }

    public void activateScenerio(UHCScenario uhcScenario){
        Bukkit.getServer().getPluginManager().registerEvents(uhcScenario, UHC.getInstance());
        uhcScenario.onEnable();
        getActivatedScenarios().add(uhcScenario);
    }

    public void disableScenario(UHCScenario uhcScenario){
        getActivatedScenarios().remove(uhcScenario);
        uhcScenario.onDisable();
    }

    int l;
    int max;
    public String getFormattedScenarios(){
        if (activatedScenarios.size() == 0)
            return "Aucun scénario";
        l = 1;
        max = getActivatedScenarios().size();
        StringBuilder sb = new StringBuilder();
        if (UHC.getInstance().getUhcGame().getUhcConfig().isHideScenarios())
            return "§cScénarios cachés";
        getActivatedScenarios().forEach(uhcScenario -> {
            l++;
            if (l == max)
                sb.append(uhcScenario.getName()).append("et ");
            else
                sb.append(uhcScenario.getName()).append(", ");
        });
        return sb.toString();
    }

    public List<String> getMotdScenarios() {
        List<String> motd = new ArrayList<>();
        if (activatedScenarios.size() == 0) {
            motd.add("§cAucun scénario");
            return motd;
        }
        final int[] i = {0};
        if (UHC.getInstance().getUhcGame().getUhcConfig().isHideScenarios()) {
            motd.add("§cScénarios cachés");
            return motd;
        }
        getActivatedScenarios().forEach(uhcScenario -> {
            if (i[0] == 5) {
                motd.add(" §8┃ §eet §c" + (activatedScenarios.size() - i[0]) + " autres...");
                i[0]++;
                return;
            }
            if (i[0] > 5)
                return;
            motd.add(" §8┃ §e" + uhcScenario.getName());
            i[0]++;
        });
        return motd;
    }
}
