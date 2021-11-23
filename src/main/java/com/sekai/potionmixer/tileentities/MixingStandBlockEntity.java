package com.sekai.potionmixer.tileentities;

import com.sekai.potionmixer.menu.MixingStandMenu;
import com.sekai.potionmixer.util.RegistryHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.BrewingStandMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BrewingStandBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class MixingStandBlockEntity extends BaseContainerBlockEntity {
    private static final int INGREDIENT_SLOT = 3;
    private static final int FUEL_SLOT = 4;
    private static final int[] SLOTS_FOR_UP = new int[]{3};
    private static final int[] SLOTS_FOR_DOWN = new int[]{0, 1, 2, 3};
    private static final int[] SLOTS_FOR_SIDES = new int[]{0, 1, 2, 4};
    public static final int FUEL_USES = 20;
    public static final int DATA_BREW_TIME = 0;
    public static final int DATA_FUEL_USES = 1;
    public static final int NUM_DATA_VALUES = 2;
    private NonNullList<ItemStack> items = NonNullList.withSize(5, ItemStack.EMPTY);
    int brewTime;
    private boolean[] lastPotionCount;
    private Item ingredient;
    int fuel;
    protected final ContainerData dataAccess = new ContainerData() {
        public int get(int p_59038_) {
            switch(p_59038_) {
                case 0:
                    return MixingStandBlockEntity.this.brewTime;
                case 1:
                    return MixingStandBlockEntity.this.fuel;
                default:
                    return 0;
            }
        }

        public void set(int p_59040_, int p_59041_) {
            switch(p_59040_) {
                case 0:
                    MixingStandBlockEntity.this.brewTime = p_59041_;
                    break;
                case 1:
                    MixingStandBlockEntity.this.fuel = p_59041_;
            }

        }

        public int getCount() {
            return 2;
        }
    };

    public MixingStandBlockEntity(BlockEntityType<?> p_155228_, BlockPos p_155229_, BlockState p_155230_) {
        super(p_155228_, p_155229_, p_155230_);
    }

    public void load(CompoundTag p_155297_) {
        super.load(p_155297_);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(p_155297_, this.items);
        this.brewTime = p_155297_.getShort("BrewTime");
        this.fuel = p_155297_.getByte("Fuel");
    }

    public CompoundTag save(CompoundTag p_59012_) {
        super.save(p_59012_);
        p_59012_.putShort("BrewTime", (short)this.brewTime);
        ContainerHelper.saveAllItems(p_59012_, this.items);
        p_59012_.putByte("Fuel", (byte)this.fuel);
        return p_59012_;
    }

    @Override
    protected Component getDefaultName() {
        return new TranslatableComponent("container.mixing");
    }

    @Override
    protected AbstractContainerMenu createMenu(int p_58990_, Inventory p_58991_) {
        return new MixingStandMenu(p_58990_, p_58991_, this, this.dataAccess);
    }

    public MixingStandBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(RegistryHandler.MIXING_STAND_ENTITY.get(), blockPos, blockState);
    }

    @Override
    public int getContainerSize() {
        return this.items.size();
    }

    @Override
    public boolean isEmpty() {
        for(ItemStack itemstack : this.items) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public ItemStack getItem(int index) {
        return index >= 0 && index < this.items.size() ? this.items.get(index) : ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeItem(int p_18942_, int p_18943_) {
        return ContainerHelper.removeItem(this.items, p_18942_, p_18943_);
    }

    @Override
    public ItemStack removeItemNoUpdate(int p_18951_) {
        return ContainerHelper.takeItem(this.items, p_18951_);
    }

    @Override
    public void setItem(int p_18944_, ItemStack p_18945_) {
        if (p_18944_ >= 0 && p_18944_ < this.items.size()) {
            this.items.set(p_18944_, p_18945_);
        }
    }

    @Override
    public boolean stillValid(Player p_18946_) {
        if (this.level.getBlockEntity(this.worldPosition) != this) {
            return false;
        } else {
            return !(p_18946_.distanceToSqr((double)this.worldPosition.getX() + 0.5D, (double)this.worldPosition.getY() + 0.5D, (double)this.worldPosition.getZ() + 0.5D) > 64.0D);
        }
    }

    @Override
    public void clearContent() {
        this.items.clear();
    }
}
