package fr.anto42.emma.utils.versionsUtils;


import fr.anto42.emma.utils.NMSUtils;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

import java.lang.reflect.Method;
import java.util.Iterator;

@SuppressWarnings("deprecation")
public class VersionUtils_1_8 extends VersionUtils{
    @Override
    public void setEntityAI(LivingEntity entity, boolean b){
        try{
            Object mcEntity = NMSUtils.getHandle(entity);
            Method getNBTTag = NMSUtils.getMethod(mcEntity.getClass(), "getNBTTag");
            Class NBTTagCompound = NMSUtils.getNMSClass("NBTTagCompound");
            Object tag = getNBTTag.invoke(mcEntity);

            if (tag == null){
                tag = NBTTagCompound.newInstance();
            }

            Method c = NMSUtils.getMethod(mcEntity.getClass(), "c", NBTTagCompound);
            Method f = NMSUtils.getMethod(mcEntity.getClass(), "f", NBTTagCompound);

            Method setInt = NMSUtils.getMethod(NBTTagCompound, "setInt", String.class, int.class);

            c.invoke(mcEntity, tag);
            setInt.invoke(tag, "NoAI", b?0:1);
            f.invoke(mcEntity, tag);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void removeRecipe(ItemStack item, Recipe recipe){
        Iterator<Recipe> iterator = Bukkit.recipeIterator();

        try {
            while (iterator.hasNext()){
                if (iterator.next().getResult().isSimilar(item)){
                    iterator.remove();
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public ShapedRecipe createShapedRecipe(ItemStack craft, String craftKey) {
        return new ShapedRecipe(craft);
    }
    @Override
    public void setChestName(Chest chest, String name){
        try {
            Class craftChest = NMSUtils.getNMSClass("block.CraftChest");
            Method getTileEntity = NMSUtils.getMethod(craftChest, "getTileEntity");
            Object tileChest = getTileEntity.invoke(chest);
            Method a = NMSUtils.getMethod(tileChest.getClass(), "a", String.class);
            a.invoke(tileChest, name);
        }catch (Exception ex){
            Bukkit.getLogger().severe("[UHC] Failed to rename chest ! A you sure you are in 1.8.x ? For support contact Anto42_#0001 !");
            ex.printStackTrace();
        }
    }
    @Override
    public void setEye(Block block, boolean eye){
        byte data = block.getData();
        if (eye && data < 4){
            data += 4;
        }else if (!eye && data > 3){
            data -= 4;
        }

        setBlockData(block, data);
    }

    @Override
    public void setEndPortalFrameOrientation(Block block, BlockFace blockFace){
        byte data = -1;
        switch (blockFace){
            case NORTH:
                data = 2;
                break;
            case EAST:
                data = 3;
                break;
            case SOUTH:
                data = 0;
                break;
            case WEST:
                data = 1;
                break;
        }

        setBlockData(block, data);
    }
    private void setBlockData(Block block, byte data){
        try {
            Method setData = NMSUtils.getMethod(Block.class, "setData",1);
            setData.invoke(block, data);
        }catch (ReflectiveOperationException ex){
            ex.printStackTrace();
        }
    }
}