package com.sekai.potionmixer.recipes;

import com.sekai.potionmixer.util.MixingUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class CustomTippedArrowRecipe extends CustomRecipe {
    public CustomTippedArrowRecipe(ResourceLocation location) {
        super(location);
    }

    public boolean matches(CraftingContainer container, Level level) {
        if (container.getWidth() == 3 && container.getHeight() == 3) {
            for(int i = 0; i < container.getWidth(); ++i) {
                for(int j = 0; j < container.getHeight(); ++j) {
                    ItemStack itemstack = container.getItem(i + j * container.getWidth());
                    if (itemstack.isEmpty()) {
                        return false;
                    }

                    if (i == 1 && j == 1) {
                        if (!itemstack.is(Items.LINGERING_POTION)) {
                            return false;
                        }
                    } else if (!itemstack.is(Items.ARROW)) {
                        return false;
                    }
                }
            }

            return true;
        } else {
            return false;
        }
    }

    public ItemStack assemble(CraftingContainer p_44513_) {
        ItemStack itemstack = p_44513_.getItem(1 + p_44513_.getWidth());
        if (!itemstack.is(Items.LINGERING_POTION)) {
            return ItemStack.EMPTY;
        } else {
            ItemStack itemstack1 = new ItemStack(Items.TIPPED_ARROW, 8);
            PotionUtils.setPotion(itemstack1, PotionUtils.getPotion(itemstack));
            if(MixingUtils.isMixedPotion(itemstack)) {
                itemstack1.getOrCreateTag().putInt("CustomPotionColor", PotionUtils.getColor(PotionUtils.getCustomEffects(itemstack)));
            }
            PotionUtils.setCustomEffects(itemstack1, PotionUtils.getCustomEffects(itemstack));
            MixingUtils.correctPotionDurations(itemstack1, 1/2D);
            return itemstack1;
        }
    }

    public boolean canCraftInDimensions(int p_44505_, int p_44506_) {
        return p_44505_ >= 2 && p_44506_ >= 2;
    }

    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializer.TIPPED_ARROW;
    }
}