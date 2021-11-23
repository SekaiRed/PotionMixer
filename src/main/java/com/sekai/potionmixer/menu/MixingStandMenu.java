package com.sekai.potionmixer.menu;

import com.sekai.potionmixer.util.RegistryHandler;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;

import javax.annotation.Nullable;

public class MixingStandMenu extends AbstractContainerMenu {
    private static final int BOTTLE_SLOT_START = 0;
    private static final int BOTTLE_SLOT_END = 2;
    private static final int INGREDIENT_SLOT = 3;
    private static final int FUEL_SLOT = 4;
    private static final int SLOT_COUNT = 5;
    private static final int DATA_COUNT = 2;
    private static final int INV_SLOT_START = 5;
    private static final int INV_SLOT_END = 32;
    private static final int USE_ROW_SLOT_START = 32;
    private static final int USE_ROW_SLOT_END = 41;
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
        this.ingredientSlot = this.addSlot(new MixingStandMenu.IngredientsSlot(p_39095_, 3, 19, 40));
        this.addSlot(new MixingStandMenu.FuelSlot(p_39095_, 4, 17, 17));
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

    //todo uh phantom membrane?
    static class FuelSlot extends Slot {
        public FuelSlot(Container p_39105_, int p_39106_, int p_39107_, int p_39108_) {
            super(p_39105_, p_39106_, p_39107_, p_39108_);
        }

        public boolean mayPlace(ItemStack p_39111_) {
            return mayPlaceItem(p_39111_);
        }

        public static boolean mayPlaceItem(ItemStack p_39113_) {
            return p_39113_.is(Items.BLAZE_POWDER);
        }

        public int getMaxStackSize() {
            return 64;
        }
    }

    static class IngredientsSlot extends Slot {
        public IngredientsSlot(Container p_39115_, int p_39116_, int p_39117_, int p_39118_) {
            super(p_39115_, p_39116_, p_39117_, p_39118_);
        }

        public boolean mayPlace(ItemStack p_39121_) {
            //todo replace with my own function to check if it's one of the ingredients
            return net.minecraftforge.common.brewing.BrewingRecipeRegistry.isValidIngredient(p_39121_);
        }

        public int getMaxStackSize() {
            return 64;
        }
    }

    static class PotionSlot extends Slot {
        public PotionSlot(Container p_39123_, int p_39124_, int p_39125_, int p_39126_) {
            super(p_39123_, p_39124_, p_39125_, p_39126_);
        }

        public boolean mayPlace(ItemStack p_39132_) {
            return mayPlaceItem(p_39132_);
        }

        public int getMaxStackSize() {
            return 1;
        }

        public void onTake(Player p_150499_, ItemStack p_150500_) {
            Potion potion = PotionUtils.getPotion(p_150500_);
            if (p_150499_ instanceof ServerPlayer) {
                net.minecraftforge.event.ForgeEventFactory.onPlayerBrewedPotion(p_150499_, p_150500_);
                CriteriaTriggers.BREWED_POTION.trigger((ServerPlayer)p_150499_, potion);
            }

            super.onTake(p_150499_, p_150500_);
        }

        public static boolean mayPlaceItem(ItemStack p_39134_) {
            return net.minecraftforge.common.brewing.BrewingRecipeRegistry.isValidInput(p_39134_);
        }
    }
}
