package com.sekai.potionmixer.tileentity;

import com.sekai.potionmixer.blocks.MixingStandBlock;
import com.sekai.potionmixer.util.RegistryHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class MixingStandBlockEntity extends BlockEntity {
    public MixingStandBlockEntity(BlockEntityType<?> p_155228_, BlockPos p_155229_, BlockState p_155230_) {
        super(p_155228_, p_155229_, p_155230_);
    }

    public MixingStandBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(RegistryHandler.MIXING_STAND_ENTITY.get(), blockPos, blockState);
    }
}
