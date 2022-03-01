package ratatoskr.Utilities;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class Recipes {
	Plugin plugin;
	
	public Recipes(Plugin plugin) {
		this.plugin = plugin;
	}
	
	public void godAppleRestored() {
		ItemStack item = new ItemStack(Material.ENCHANTED_GOLDEN_APPLE);
		NamespacedKey key = new NamespacedKey(this.plugin,"enchanted_golden_apple");
		ShapedRecipe recipe = new ShapedRecipe(key,item);
		recipe.shape("BBB","BAB","BBB");
		recipe.setIngredient('A', Material.APPLE);
		recipe.setIngredient('B', Material.GOLD_BLOCK);
		Bukkit.addRecipe(recipe);
	}
}
