package com.sekai.potionmixer.menu;

import com.sekai.potionmixer.util.RegistryHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;

public class MixingStandMenu extends AbstractContainerMenu {
    private final Container mixingStand;
    private final ContainerData mixingStandData;
    private final Slot ingredientSlot;

    public MixingStandMenu(int index, Inventory inv, FriendlyByteBuf friendlyByteBuf) {
        this(index, inv, new SimpleContainer(5), new SimpleContainerData(2));
    }

    public MixingStandMenu(int index, Inventory p_39094_, Container p_39095_, ContainerData p_39096_) {
        super(RegistryHandler.MIXING_STAND_MENU.get(), index);
        checkContainerSize(p_39095_, 5);
        checkContainerDataCount(p_39096_, 2);
        this.mixingStand = p_39095_;
        this.mixingStandData = p_39096_;
        this.addSlot(new MixingStandMenu.PotionSlot(p_39095_, 0, 56, 24));
        this.addSlot(new MixingStandMenu.PotionSlot(p_39095_, 1, 79, 17));
        this.addSlot(new MixingStandMenu.PotionSlot(p_39095_, 2, 102, 24));
        this.addSlot(new OutputSlot(p_39095_, 3, 79, 58)); //result
        this.ingredientSlot = this.addSlot(new IngredientSlot(p_39095_, 4, 19, 40));
        this.addDataSlots(p_39096_);

        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(p_39094_, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for(int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(p_39094_, k, 8 + k * 18, 142));
        }

    }

    @Override
    public boolean stillValid(Player player) {
        return this.mixingStand.stillValid(player);
    }

    public int getBrewingTicks() {
        return this.mixingStandData.get(0);
    }

    //todo uh phantom membrane?
    static class IngredientSlot extends Slot {
        public IngredientSlot(Container p_39105_, int p_39106_, int p_39107_, int p_39108_) {
            super(p_39105_, p_39106_, p_39107_, p_39108_);
        }

        public boolean mayPlace(ItemStack itemStack) {
            return mayPlaceItem(itemStack);
        }

        public static boolean mayPlaceItem(ItemStack itemStack) {
            return itemStack.is(Items.REDSTONE) || itemStack.is(Items.GLOWSTONE_DUST);
        }

        public int getMaxStackSize() {
            return 64;
        }
    }

    static class OutputSlot extends Slot {
        public OutputSlot(Container p_39115_, int p_39116_, int p_39117_, int p_39118_) {
            super(p_39115_, p_39116_, p_39117_, p_39118_);
        }

        public boolean mayPlace(ItemStack itemStack) {
            return mayPlaceItem(itemStack);
        }

        public static boolean mayPlaceItem(ItemStack itemStack) {
            return itemStack.is(Items.GLASS_BOTTLE) || PotionUtils.getPotion(itemStack).equals(Potions.WATER);
        }

        public int getMaxStackSize() {
            return 1;
        }
    }

    static class PotionSlot extends Slot {
        public PotionSlot(Container p_39123_, int p_39124_, int p_39125_, int p_39126_) {
            super(p_39123_, p_39124_, p_39125_, p_39126_);
        }

        public boolean mayPlace(ItemStack itemStack) {
            return itemStack.is(Items.POTION);
        }

        public int getMaxStackSize() {
            return 1;
        }
    }
}
