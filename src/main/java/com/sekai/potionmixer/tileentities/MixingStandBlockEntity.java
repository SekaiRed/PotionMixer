package com.sekai.potionmixer.tileentities;

import com.sekai.potionmixer.blocks.MixingStandBlock;
import com.sekai.potionmixer.menu.MixingStandMenu;
import com.sekai.potionmixer.util.RegistryHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Containers;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public static void serverTick(Level level, BlockPos blockPos, BlockState blockState, MixingStandBlockEntity blockEntity) {
        /*ItemStack itemstack = blockEntity.items.get(4);
        if (blockEntity.fuel <= 0 && itemstack.is(Items.BLAZE_POWDER)) {
            blockEntity.fuel = 20;
            itemstack.shrink(1);
            setChanged(level, blockPos, blockState);
        }*/

        //blockEntity.brewTime--;
        if(isMixable(blockEntity.items)) {
            blockEntity.brewTime -= 1;
            if(blockEntity.brewTime == 0) {
                blockEntity.brewTime = 400;
                doMix(level, blockPos, blockEntity.items);
                setChanged(level, blockPos, blockState);
            }
        } else {
            blockEntity.brewTime = 400;
        }

        //blockEntity.brewTime = blockEntity.brewTime < 0 ? 400 : blockEntity.brewTime - 1;

        /*boolean flag = isBrewable(blockEntity.items);
        boolean flag1 = blockEntity.brewTime > 0;
        ItemStack itemstack1 = blockEntity.items.get(3);
        if (flag1) {
            --blockEntity.brewTime;
            boolean flag2 = blockEntity.brewTime == 0;
            if (flag2 && flag) {
                doMix(level, blockPos, blockEntity.items);
                setChanged(level, blockPos, blockState);
            } else if (!flag || !itemstack1.is(blockEntity.ingredient)) {
                blockEntity.brewTime = 0;
                setChanged(level, blockPos, blockState);
            }
        } else if (flag && blockEntity.fuel > 0) {
            --blockEntity.fuel;
            blockEntity.brewTime = 400;
            blockEntity.ingredient = itemstack1.getItem();
            setChanged(level, blockPos, blockState);
        }*/

        boolean[] aboolean = blockEntity.getPotionBits();
        if (!Arrays.equals(aboolean, blockEntity.lastPotionCount)) {
            blockEntity.lastPotionCount = aboolean;
            BlockState blockstate = blockState;
            if (!(blockState.getBlock() instanceof MixingStandBlock)) {
                return;
            }

            for(int i = 0; i < MixingStandBlock.HAS_BOTTLE.length; ++i) {
                blockstate = blockstate.setValue(MixingStandBlock.HAS_BOTTLE[i], Boolean.valueOf(aboolean[i]));
            }

            level.setBlock(blockPos, blockstate, 2);
        }

    }

    private boolean[] getPotionBits() {
        boolean[] aboolean = new boolean[3];

        for(int i = 0; i < 3; ++i) {
            if (!this.items.get(i).isEmpty()) {
                aboolean[i] = true;
            }
        }

        return aboolean;
    }

    private static boolean isMixable(NonNullList<ItemStack> items) {
        int potionCount = 0;
        for(int i = 0; i<3; i++) {
            if(items.get(i).getItem().equals(Items.POTION))
                potionCount++;
        }
        return potionCount>1;
    }

    private static void doMix(Level level, BlockPos pos, NonNullList<ItemStack> items) {
        List<MobEffectInstance> finalEffects = new ArrayList<>();
        List<KnownMobEffect> effects = new ArrayList<>();
        int duplicate = 0;
        for(int i = 0; i < 3; i++) {
            for(MobEffectInstance effectInstance : PotionUtils.getMobEffects(items.get(i))) {
                boolean replaced = false;
                for(KnownMobEffect effect : effects) {
                    if(effectInstance.getEffect().equals(effect.effect)) {
                        effect.count++;
                        effect.duration += effectInstance.getDuration();
                        effect.lowest_level = Math.min(effectInstance.getAmplifier(), effect.lowest_level);
                        replaced = true;
                    }
                }
                if(!replaced) {
                    effects.add(new KnownMobEffect(effectInstance.getEffect(), effectInstance.getDuration(), 1, effectInstance.getAmplifier()));
                }
            }
            //effects.addAll(PotionUtils.getMobEffects(items.get(i)));
        }

        for(KnownMobEffect effect : effects) {
            if(effect.count == 2)
                finalEffects.add(new MobEffectInstance(effect.effect, effect.duration/4, effect.lowest_level+1));
            else if(effect.count == 3)
                finalEffects.add(new MobEffectInstance(effect.effect, effect.duration/3, effect.lowest_level+1));
            else
                finalEffects.add(new MobEffectInstance(effect.effect, effect.duration, effect.lowest_level));
        }

        ItemStack result = new ItemStack(Items.POTION);
        PotionUtils.setCustomEffects(result, finalEffects);
        CompoundTag nameNBT = new CompoundTag();
        //TODO Change to a translation thingy
        nameNBT.putString("Name", "{\"text\":\"Mixed Potion\",\"italic\":\"false\"}");
        result.addTagElement("display", nameNBT);
        //result.tag();
        items.set(3, result);

        /*//if (net.minecraftforge.event.ForgeEventFactory.onPotionAttemptBrew(items)) return;
        ItemStack itemstack = items.get(3);

        //net.minecraftforge.common.brewing.BrewingRecipeRegistry.brewPotions(items, itemstack, SLOTS_FOR_SIDES);
        //net.minecraftforge.event.ForgeEventFactory.onPotionBrewed(items);
        if (itemstack.hasContainerItem()) {
            ItemStack itemstack1 = itemstack.getContainerItem();
            itemstack.shrink(1);
            if (itemstack.isEmpty()) {
                itemstack = itemstack1;
            } else {
                Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), itemstack1);
            }
        }
        else itemstack.shrink(1);

        items.set(3, itemstack);
        //level.levelEvent(1035, pos, 0);*/
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

    private static class KnownMobEffect {
        public MobEffect effect;
        public int duration;
        public int count;
        public int lowest_level;

        public KnownMobEffect(MobEffect effect, int duration, int count, int lowest_level) {
            this.effect = effect;
            this.duration = duration;
            this.count = count;
            this.lowest_level = lowest_level;
        }
    }
}
