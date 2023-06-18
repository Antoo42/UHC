package fr.anto42.emma.utils.versionsUtils;

import fr.anto42.emma.UHC;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public abstract class VersionUtils{

    private static VersionUtils versionUtils = null;

    public static VersionUtils getVersionUtils(){
        if (versionUtils == null){
            int version = UHC.getInstance().getVersion();
            if (version < 12){
                versionUtils = new VersionUtils_1_8();
            }
        }
        return versionUtils;
    }

    public abstract void setEntityAI(LivingEntity entity, boolean b);
    public abstract void removeRecipe(ItemStack item, Recipe recipe);
    public abstract ShapedRecipe createShapedRecipe(ItemStack craft, String craftKey);
    public abstract void setChestName(Chest chest, String name);
    public abstract void setEye(Block block, boolean eye);
    public abstract void setEndPortalFrameOrientation(Block block, BlockFace blockFace);
}