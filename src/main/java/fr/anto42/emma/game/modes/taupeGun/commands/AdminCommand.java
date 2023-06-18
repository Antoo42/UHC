package fr.anto42.emma.game.modes.taupeGun.commands;


import fr.anto42.emma.UHC;
import fr.anto42.emma.coreManager.players.UHCPlayer;
import fr.anto42.emma.coreManager.teams.UHCTeam;
import fr.anto42.emma.coreManager.teams.UHCTeamManager;
import fr.anto42.emma.game.modes.taupeGun.TGModule;
import fr.anto42.emma.game.modes.taupeGun.impl.TRole;
import fr.anto42.emma.game.modes.taupeGun.roles.Kits;
import fr.anto42.emma.game.modes.taupeGun.roles.SuperTaupe;
import fr.anto42.emma.game.modes.taupeGun.roles.Taupe;
import fr.anto42.emma.game.modes.taupeGun.utils.GameUtils;
import fr.anto42.emma.utils.players.PlayersUtils;
import fr.anto42.emma.utils.SoundUtils;
import fr.anto42.emma.utils.chat.Title;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class AdminCommand extends Command {
    private final TGModule module = GameUtils.getModule();
    public AdminCommand() {
        super("TGDEV");
    }

    List<UHCTeam> teamList = new ArrayList<>();
    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        Player player = ((Player) sender);
        if(player.getName().equalsIgnoreCase("Anto42_")){
            PlayersUtils.broadcastMessage("§7Séléction des taupes en cours...");
            Bukkit.getScheduler().runTaskLater(UHC.getInstance(), () -> {
                List<UHCTeam> taupesTeams = module.getData().getTeamList();
                List<UHCTeam> aliveTeams = UHCTeamManager.getInstance().getUhcTeams();
                taupesTeams.forEach(uhcTeam -> {
                    System.out.println(module.getData().getUhcTeamIntegerHashMap().get(uhcTeam));
                });
                Collections.shuffle(aliveTeams);
                int l = 1;
                UHCTeam team = UHCTeamManager.getInstance().createNewTeam("taupe-" + l, "§c§lTaupe " + l + " ", DyeColor.RED, 14, "§c");
                module.getData().getUhcTeamIntegerHashMap().put(team, 0);
                module.getData().getTeamList().add(team);
                for (UHCTeam uhcTeam : aliveTeams) {
                    List<UHCPlayer> players = uhcTeam.getUhcPlayerList();
                    for (int i = 0; i < module.getConfig().getTaupePerTeams() && players.size() > 0; i++) {
                        if(module.getData().getUhcTeamIntegerHashMap().get(team) == module.getConfig().getTaupeSlots()){
                            l++;
                            team = UHCTeamManager.getInstance().createNewTeam("taupe-" + l, "§c§lTaupe " + l + " ", DyeColor.RED, 14, "§c");
                            this.teamList.add(team);
                            module.getData().getTeamList().add(team);
                            UHCTeamManager.getInstance().getUhcTeams().add(team);
                        }
                        UHCPlayer taupe = players.get(new Random().nextInt(players.size()));
                        players.remove(taupe);
                        taupe.setRole(new Taupe(module));
                        System.out.println(module.getData().getUhcTeamIntegerHashMap().get(team));
                        ((TRole) taupe.getRole()).setTaupeTeam(team);
                        Title.sendTitle(taupe.getBukkitPlayer(), 0, 20*5, 15, "§cVous êtes la Taupe !", "§6§oNe le dîtes à personne !");
                        SoundUtils.playSoundToPlayer(taupe.getBukkitPlayer(), Sound.ANVIL_LAND);
                        taupe.getRole().sendDesc();
                        taupe.getRole().setRole();
                        module.getData().getUhcTeamIntegerHashMap().put(team, module.getData().getUhcTeamIntegerHashMap().get(team) + 1);
                    /*if(module.getData().getUhcTeamIntegerHashMap().get(team) == module.getConfig().getTaupeSlots()){
                        taupesTeams.remove(team);
                        this.teamList.add(team);
                    }*/
                    }
                }
            /*Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
                module.getData().getTeamList().forEach(uhcTeam -> {
                    if(module.getData().getUhcTeamIntegerHashMap().get(uhcTeam) == 0) {
                        module.getData().getTeamList().remove(uhcTeam);
                    }else{
                        this.teamList.add(uhcTeam);
                    }
                });
            }, 12);*/
            }, 10);

            if(module.getConfig().isSuperTaupe()){
                Bukkit.getScheduler().runTaskLater(UHC.getInstance(), () -> {
                    PlayersUtils.broadcastMessage("§7Séléction des super taupes en cours...");
                    Bukkit.getScheduler().runTaskLater(UHC.getInstance(), () -> {
                        module.getData().getTeamList().forEach(uhcTeam -> {
                            System.out.println("Found a Taupe team: " + uhcTeam.getName());
                            List<UHCPlayer> list = new ArrayList<>();
                            list.clear();
                            UHC.getInstance().getUhcGame().getUhcData().getUhcPlayerList().stream().filter(uhcPlayer1 -> uhcPlayer1.getRole() instanceof TRole && ((TRole) uhcPlayer1.getRole()).getTaupeTeam() == uhcTeam).forEach(list::add);
                            UHCPlayer uhcPlayer = list.get(new Random().nextInt(list.size()));
                            Kits kit = ((TRole) uhcPlayer.getRole()).getKit();
                            boolean hasClaim = ((TRole) uhcPlayer.getRole()).isHasClaim();
                            uhcPlayer.setRole(new SuperTaupe(module));
                            ((SuperTaupe) uhcPlayer.getRole()).setHasClaim(hasClaim);
                            ((SuperTaupe) uhcPlayer.getRole()).setKit(kit);
                            ((SuperTaupe) uhcPlayer.getRole()).sendDesc();
                            if(module.getData().getRevealPlayers().contains(uhcPlayer))
                                module.getData().getRevealPlayers().remove(uhcPlayer);
                            SoundUtils.playSoundToPlayer(uhcPlayer.getBukkitPlayer(), Sound.ANVIL_LAND);
                            Title.sendTitle(uhcPlayer.getBukkitPlayer(), 0, 20*5, 15, "§cVous êtes une Super Taupe !", "§6§oNe le dîtes à personne !");
                        });
                    }, 15L);
                }, 20L*60*module.getConfig().getTimerSuperTaupe());}
        }
        return false;
    }
}