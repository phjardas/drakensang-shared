package de.jardas.drakensang.shared.db.inventory;

import de.jardas.drakensang.shared.model.inventory.Recipe;


public class RecipeDao extends InventoryItemDao<Recipe> {
    public RecipeDao() {
        super(Recipe.class, "Recipe");
    }
}
