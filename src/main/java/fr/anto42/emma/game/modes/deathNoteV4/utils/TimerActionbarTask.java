package fr.anto42.emma.game.modes.deathNoteV4.utils;

import fr.anto42.emma.UHC;
import fr.anto42.emma.utils.chat.Title;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TimerActionbarTask {

    private String mess;
    private int a = 0;
    private String endmess;

    public TimerActionbarTask(String message, int duration, String endmess){
        this.mess = "§8● " + message;
        this.a = duration;
        this.endmess = endmess;
    }


    public void sendToPlayer(Player player){
        new BukkitRunnable() {
            public void run() {
                a--;
                if(a <= 0){
                    Title.sendActionBar(player, endmess);
                    cancel();
                }
                else
                    Title.sendActionBar(player, mess + " §8(§f" + a + "§8)");
            }
        }.runTaskTimer(UHC.getInstance(), 0, 20);
    }

    /*public void sendToAll(){
        new BukkitRunnable() {
            public void run() {
                a--;
                if(a <= 0){
                    new ActionBar(endmess).sendToAll();
                    cancel();
                }

                else
                    new ActionBar(mess + " §8(§c" + a + "§8)").sendToAll();
            }
        }.runTaskTimer(UHC.getInstance(), 0, 20);
    }*/

}
