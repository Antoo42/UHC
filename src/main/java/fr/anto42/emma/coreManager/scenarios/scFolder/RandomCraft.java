package fr.anto42.emma.coreManager.scenarios.scFolder;

import fr.anto42.emma.coreManager.listeners.customListeners.StartEvent;
import fr.anto42.emma.coreManager.scenarios.ScenarioManager;
import fr.anto42.emma.coreManager.scenarios.ScenarioType;
import fr.anto42.emma.coreManager.scenarios.UHCScenario;
import fr.anto42.emma.utils.materials.ItemCreator;
import fr.anto42.emma.utils.versionsUtils.VersionUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

import java.util.*;

public class RandomCraft extends UHCScenario {
    public RandomCraft(ScenarioManager scenarioManager, int page) {
        super("RandomCraft", new ItemCreator(Material.WORKBENCH).get(), scenarioManager, page);
        super.setDesc("§8┃ §fTout les crafts sont aléatoires");
        setScenarioType(ScenarioType.WORLD);
    }

    @EventHandler
    public void onStart(StartEvent event){
        Iterator<Recipe> iterator = Bukkit.recipeIterator();
        List<ItemStack> results = new ArrayList<>();
        Set<ShapedRecipe> removeRecipes = new HashSet<>();

        Recipe recipe;
        while (iterator.hasNext()){
            recipe = iterator.next();
            if (!(recipe instanceof ShapedRecipe)){
                continue;
            }

            results.add(recipe.getResult());
            removeRecipes.add((ShapedRecipe) recipe);
        }

        Collections.shuffle(results);
        Iterator<ItemStack> resultIterator = results.iterator();
        Set<ShapedRecipe> randomizedRecipes = new HashSet<>();

        for (ShapedRecipe oldRecipe : removeRecipes){
            ShapedRecipe newRecipe = cloneRecipeWithResult(oldRecipe, resultIterator.next());
            randomizedRecipes.add(newRecipe);

            VersionUtils.getVersionUtils().removeRecipe(newRecipe.getResult(), oldRecipe);
        }

        randomizedRecipes.forEach(r -> Bukkit.getServer().addRecipe(r));
    }

    @Override
    public void onDisable(){
        Bukkit.resetRecipes();
    }

    private ShapedRecipe cloneRecipeWithResult(ShapedRecipe recipe, ItemStack result){
        ShapedRecipe clone = VersionUtils.getVersionUtils().createShapedRecipe(result, UUID.randomUUID().toString());
        clone.shape(recipe.getShape());

        Map<Character, ItemStack> recipeChoiceMap = recipe.getIngredientMap();
        for (char c : recipeChoiceMap.keySet()){
            clone.setIngredient(c, recipeChoiceMap.get(c).getType());
        }

        return clone;
    }

}
