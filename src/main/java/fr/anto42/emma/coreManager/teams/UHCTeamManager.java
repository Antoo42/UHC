package fr.anto42.emma.coreManager.teams;

import org.apache.commons.lang.RandomStringUtils;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UHCTeamManager {


    private final static UHCTeamManager uhcTeamManager = new UHCTeamManager();
    public static UHCTeamManager getInstance() {
        return uhcTeamManager;
    }

    private final List<UHCTeam> initialTeams = new ArrayList<>();

    private boolean activated = false;
    private int slots = 2;

    private List<UHCTeam> uhcTeams = new ArrayList<>();
    public List<UHCTeam> getUhcTeams() {
        return uhcTeams;
    }

    public void setUhcTeams(List<UHCTeam> uhcTeams) {
        this.uhcTeams = uhcTeams;
    }

    public int getSlots() {
        return slots;
    }

    public void setSlots(int slots) {
        this.slots = slots;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public UHCTeam createNewTeam(String name, String prefix, DyeColor dyeColor, int colorNumber, String color){
        Scoreboard score = Bukkit.getScoreboardManager().getMainScoreboard();
        String uuid = RandomStringUtils.random(4, true, false);
        uuid = uuid + "-";
        uuid = uuid + RandomStringUtils.random(4, false, true);

        Team team = score.registerNewTeam(uuid.toString());
        score.getTeam(uuid.toString()).setPrefix(prefix);

        UHCTeam uhcTeam = new UHCTeam(name, color, prefix, dyeColor, colorNumber, uuid, team);
        this.uhcTeams.add(uhcTeam);
        this.initialTeams.add(uhcTeam);
        System.out.println("Succes in the creation of the team: " + prefix + " (" + name + ")");
        return uhcTeam;
    }

    public void createTeams(){
        getUhcTeams().clear();

        //Hearts
        createNewTeam("Rouge ♥","§c♥ ", DyeColor.RED, 14, "§c");
        createNewTeam("Orange ♥","§6♥ ", DyeColor.ORANGE, 1,"§6");
        createNewTeam("Jaune ♥","§e♥ ", DyeColor.YELLOW, 4,"§e");
        createNewTeam("Vert ♥","§a♥ ", DyeColor.LIME, 5,"§a");
        createNewTeam("Cyan ♥","§b♥ ", DyeColor.LIGHT_BLUE, 3,"§b");
        createNewTeam("Bleue ♥","§9♥ ", DyeColor.BLUE, 11,"§9");
        createNewTeam("Rose ♥","§d♥ ", DyeColor.PINK, 6,"§d");

        //LOSANGE
        createNewTeam("Rouge ♦","§c♦ ", DyeColor.RED, 14, "§c");
        createNewTeam("Orange ♦","§6♦ ", DyeColor.ORANGE, 1,"§6");
        createNewTeam("Jaune ♦","§e♦ ", DyeColor.YELLOW, 4,"§e");
        createNewTeam("Vert ♦","§a♦ ", DyeColor.LIME, 5,"§a");
        createNewTeam("Cyan ♦","§b♦ ", DyeColor.LIGHT_BLUE, 3,"§b");
        createNewTeam("Bleue ♦","§9♦ ", DyeColor.BLUE, 11,"§9");
        createNewTeam("Rose ♦","§d♦ ", DyeColor.PINK, 6,"§d");

        //PIQUE
        createNewTeam("Rouge ♠","§c♠ ", DyeColor.RED, 14, "§c");
        createNewTeam("Orange ♠","§6♠ ", DyeColor.ORANGE, 1,"§6");
        createNewTeam("Jaune ♠","§e♠ ", DyeColor.YELLOW, 4,"§e");
        createNewTeam("Vert ♠","§a♠ ", DyeColor.LIME, 5,"§a");
        createNewTeam("Cyan ♠","§b♠ ", DyeColor.LIGHT_BLUE, 3,"§b");
        createNewTeam("Bleue ♠","§9♠ ", DyeColor.BLUE, 11,"§9");
        createNewTeam("Rose ♠","§d♠ ", DyeColor.PINK, 6,"§d");

        //TREFLE
        createNewTeam("Rouge ♣","§c♣ ", DyeColor.RED, 14,"§c");
        createNewTeam("Orange ♣","§6♣ ", DyeColor.ORANGE, 1,"§6");
        createNewTeam("Jaune ♣","§e♣ ", DyeColor.YELLOW, 4,"§e");
        createNewTeam("Vert ♣","§a♣ ", DyeColor.LIME, 5,"§a");
        createNewTeam("Cyan ♣","§b♣ ", DyeColor.LIGHT_BLUE, 3,"§b");
        createNewTeam("Bleue ♣","§9♣ ", DyeColor.BLUE, 11,"§9");
        createNewTeam("Rose ♣","§d♣ ", DyeColor.PINK, 6,"§d");
    }


    public List<UHCTeam> getFreeTeams(){
        List<UHCTeam> uhcTeams = new ArrayList<>();

        for(UHCTeam uhcTim : getInitialTeams()){
            if (uhcTim.getPlayersAmount() > 0 && uhcTim.getPlayersAmount() + 1 < getSlots()){
                uhcTeams.add(uhcTim);
            }
        }

        if (uhcTeams.isEmpty()){
            for(UHCTeam uhcTim : getUhcTeams()){
                if (uhcTim.getPlayersAmount() == 0){
                    uhcTeams.add(uhcTim);
                }
            }
        }

        if (uhcTeams.isEmpty())
            createNewTeam("Jaune ✓","§e§l✓ ", DyeColor.YELLOW, 4,"§e");


        return uhcTeams;
    }

    public UHCTeam getRandomFreeTeam(){
        List<UHCTeam> uhcTeams = new ArrayList<>();

        for(UHCTeam uhcTim : getUhcTeams()){
            if (uhcTim.getPlayersAmount() > 0 && uhcTim.getPlayersAmount() + 1 < getSlots()){
                uhcTeams.add(uhcTim);
            }
        }

        if (uhcTeams.isEmpty()){
            for(UHCTeam uhcTim : getUhcTeams()){
                if (uhcTim.getPlayersAmount() == 0){
                    uhcTeams.add(uhcTim);
                }
            }
        }
        return initialTeams.get(new Random().nextInt(initialTeams.size()));
    }

    public String getDisplayFormat(){
        if (!isActivated())
            return "FFA";
        else
            return "To" + slots;
    }

    private boolean friendlyFire = false;

    public boolean isFriendlyFire() {
        return friendlyFire;
    }

    public void setFriendlyFire(boolean friendlyFire) {
        this.friendlyFire = friendlyFire;
    }

    private boolean directionalArrow = false;

    public boolean isDirectionalArrow() {
        return directionalArrow;
    }

    public void setDirectionalArrow(boolean directionalArrow) {
        this.directionalArrow = directionalArrow;
    }

    public int getNumberOfAliveTeams(){
        return getUhcTeams().size();
    }

    public String translateTeamAlivesNumber(){
        if (!isActivated())
            return "";
        else
            return String.valueOf(getNumberOfAliveTeams());
    }

    public List<UHCTeam> getInitialTeams() {
        return initialTeams;
    }
}
