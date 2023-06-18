package fr.anto42.emma.coreManager.enchants.config;

import fr.anto42.emma.UHC;
import fr.anto42.emma.coreManager.players.UHCPlayer;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

public class EnchantConfiguration extends Command {


    public EnchantConfiguration() {
        super("enchant");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        Player player = ((Player) commandSender);
        UHCPlayer uhcPlayer = UHC.getUHCPlayer(player);
        if (!uhcPlayer.isEditing()){
            uhcPlayer.sendMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §cVous ne pouvez pas faire ça !");
            return false;
        }
        if (player.getItemInHand().getType() == Material.AIR){
            uhcPlayer.sendMessage(UHC.getInstance().getConfig().getString("generalPrefix").replace("&", "§") + " §cVeuillez prendre un item en main !");
            return false;
        }
        new EnchantGUI(player.getItemInHand()).getkInventory().open(player);
        return false;
    }

    public enum EnchantementEnum {
        PROTECTION_ENVIRONMENTAL(Enchantment.PROTECTION_ENVIRONMENTAL, "§8┃ §cProtection", 0, 50, 4),
        FIRE_PROTECTION(Enchantment.PROTECTION_FIRE, "§8┃ §cFire Protection", 0, 50, 4),
        FEATHER_FALLING(Enchantment.PROTECTION_FALL, "§8┃ §cFeather Falling", 0, 50, 4),
        BLAST_PROTECTION(Enchantment.PROTECTION_EXPLOSIONS, "§8┃ §cBlast Protection", 0, 50, 4),
        PROJECTILE_PROTECTION(Enchantment.PROTECTION_PROJECTILE, "§8┃ §cProjectile Protection", 0, 50, 4),
        RESPIRATION(Enchantment.OXYGEN, "§8┃ §cRespiration", 0, 50, 3),
        AQUA_AFFINITY(Enchantment.WATER_WORKER, "§8┃ §cAqua Affinity", 0, 50, 1),
        THORNS(Enchantment.THORNS, "§8┃ §cThorns", 0, 50, 3),
        DEPTH_STRIDERS(Enchantment.DEPTH_STRIDER, "§8┃ §cDepth Strider", 0, 50, 3),
        SHARPNESS(Enchantment.DAMAGE_ALL, "§8┃ §cSharpness", 0, 50, 5),
        SMITE(Enchantment.DAMAGE_UNDEAD, "§8┃ §cSmite", 0, 50, 5),
        BANE_OF_ARTHROPODS(Enchantment.DAMAGE_ARTHROPODS, "§8┃ §cBane of Arthropods", 0, 50, 5),
        KNOCKBACK(Enchantment.KNOCKBACK, "§8┃ §cKnockback", 0, 50, 2),
        FIRE_ASPECT(Enchantment.FIRE_ASPECT, "§8┃ §cFire Aspect", 0, 50, 2),
        LOOTING(Enchantment.LOOT_BONUS_MOBS, "§8┃ §cLooting", 0, 50, 3),
        POWER(Enchantment.ARROW_DAMAGE, "§8┃ §cPower", 0, 50, 5),
        PUNCH(Enchantment.ARROW_KNOCKBACK, "§8┃ §cPunch", 0, 50, 2),
        FLAME(Enchantment.ARROW_FIRE, "§8┃ §cFlame", 0, 1, 1),
        INFINITY(Enchantment.ARROW_INFINITE, "§8┃ §cInfinity", 0, 1, 1),
        EFFICIENCY(Enchantment.DIG_SPEED, "§8┃ §cEfficiency", 0, 50, 5),
        SILK_TOUCH(Enchantment.SILK_TOUCH, "§8┃ §cSilk Touch", 0, 1, 1),
        UNBREAKING(Enchantment.DURABILITY, "§8┃ §cUnbreaking", 0, 50, 3),
        FORTUNE(Enchantment.LOOT_BONUS_BLOCKS, "§8┃ §cFortune", 0, 50, 3),
        LUCK_OF_THE_SEA(Enchantment.LUCK, "§8┃ §cLuck of the Sea", 0, 100, 3),
        LURE(Enchantment.LURE, "§8┃ §cLure", 0, 100, 3);


        private final Enchantment enchantment;
        private final String name;
        private final int min;
        private final int max;
        private int configValue;

        EnchantementEnum(Enchantment enchantment, String name, int min, int max, int configValue) {
            this.enchantment = enchantment;
            this.name = name;
            this.min = min;
            this.max = max;
            this.configValue = configValue;
        }

        public Enchantment getEnchantment() {
            return enchantment;
        }

        public String getName() {
            return name;
        }

        public int getMin() {
            return min;
        }

        public int getMax() {
            return max;
        }

        public int getConfigValue() {
            return configValue;
        }

        public void setConfigValue(int configValue) {
            this.configValue = configValue;
        }

        public void addConfigValue(){
            if (this.configValue < enchantment.getMaxLevel())
                configValue++;
        }

        public void removeConfigValue(){
            if (this.configValue > 0)
                configValue--;
        }
    }

}
