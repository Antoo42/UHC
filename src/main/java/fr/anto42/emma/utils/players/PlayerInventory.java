package fr.anto42.emma.utils.players;

import fr.anto42.emma.utils.materials.ItemCreator;
import fr.blendman974.kinventory.inventories.KInventory;
import fr.blendman974.kinventory.inventories.KItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerInventory {
    private KInventory kInventory;

    public PlayerInventory(Player player){
        this.kInventory = new KInventory(45, "Inventaire de " + player.getName());
        ItemStack[] armorContents = player.getInventory().getArmorContents();
        KItem b = new KItem(new ItemCreator(Material.BARRIER).name("").get());
        KItem helmet = new KItem(new ItemCreator(Material.CHAINMAIL_HELMET).name("").get());
        KItem chestplate = new KItem(new ItemCreator(Material.CHAINMAIL_CHESTPLATE).name("").get());
        KItem leg = new KItem(new ItemCreator(Material.CHAINMAIL_LEGGINGS).name("").get());
        KItem boot = new KItem(new ItemCreator(Material.CHAINMAIL_BOOTS).name("").get());
        if(armorContents[0].getType() == Material.AIR){
            this.kInventory.setElement(0, boot);
        }else{
            this.kInventory.setElement(0,new KItem(armorContents[0]));
        }

        if(armorContents[1].getType() == Material.AIR){
            this.kInventory.setElement(1, leg);
        }else{
            this.kInventory.setElement(1,new KItem(armorContents[1]));
        }

        if(armorContents[2].getType() == Material.AIR){
            this.kInventory.setElement(2, chestplate);
        }else{
            this.kInventory.setElement(2,new KItem(armorContents[2]));
        }

        if(armorContents[3].getType() == Material.AIR){
            this.kInventory.setElement(3, helmet);
        }else{
            this.kInventory.setElement(3,new KItem(armorContents[3]));
        }

        for(int i= 4; i < 9; i++){
            this.kInventory.setElement(i,b);
        }

        ItemStack[] mainContents = player.getInventory().getContents();
        for (int i = 0; i < mainContents.length; i++) {
            if(mainContents[i] != null) {
                KItem kItem = new KItem(mainContents[i]);
                this.kInventory.setElement(i + 9, kItem);
            }
        }
    }

    public KInventory getkInventory() {
        return kInventory;
    }
}
