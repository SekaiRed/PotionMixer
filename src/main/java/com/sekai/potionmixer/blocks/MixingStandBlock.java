package com.sekai.potionmixer.blocks;

import com.sekai.potionmixer.tileentities.MixingStandBlockEntity;
import com.sekai.potionmixer.util.RegistryHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.Random;

public class MixingStandBlock extends BaseEntityBlock {
    public static final BooleanProperty[] HAS_BOTTLE = new BooleanProperty[]{BlockStateProperties.HAS_BOTTLE_0, BlockStateProperties.HAS_BOTTLE_1, BlockStateProperties.HAS_BOTTLE_2};
    protected static final VoxelShape SHAPE = Shapes.or(Block.box(1.0D, 0.0D, 1.0D, 15.0D, 2.0D, 15.0D), Block.box(7.0D, 0.0D, 7.0D, 9.0D, 14.0D, 9.0D));

    public MixingStandBlock() {
        super(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(0.5F).lightLevel((blockState) -> 1).noOcclusion());
        this.registerDefaultState(this.stateDefinition.any().setValue(HAS_BOTTLE[0], Boolean.valueOf(true)).setValue(HAS_BOTTLE[1], Boolean.valueOf(false)).setValue(HAS_BOTTLE[2], Boolean.valueOf(false)));
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_152694_, BlockState p_152695_, BlockEntityType<T> p_152696_) {
        return p_152694_.isClientSide ? null : createTickerHelper(p_152696_, RegistryHandler.MIXING_STAND_ENTITY.get(), MixingStandBlockEntity::serverTick);
    }

    public RenderShape getRenderShape(BlockState p_50950_) {
        return RenderShape.MODEL;
    }

    public VoxelShape getShape(BlockState p_50952_, BlockGetter p_50953_, BlockPos p_50954_, CollisionContext p_50955_) {
        return SHAPE;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
        return new MixingStandBlockEntity(p_153215_, p_153216_);
    }

    public void animateTick(BlockState p_50943_, Level p_50944_, BlockPos p_50945_, Random p_50946_) {
        double d0 = (double)p_50945_.getX() + 0.4D + (double)p_50946_.nextFloat() * 0.2D;
        double d1 = (double)p_50945_.getY() + 0.3D + (double)p_50946_.nextFloat() * 0.6D;
        double d2 = (double)p_50945_.getZ() + 0.4D + (double)p_50946_.nextFloat() * 0.2D;
        if (p_50946_.nextInt(5) == 0)
            p_50944_.addParticle(ParticleTypes.END_ROD, d0, d1, d2, p_50946_.nextGaussian() * 0.005D, p_50946_.nextGaussian() * 0.005D, p_50946_.nextGaussian() * 0.005D);
    }

    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult hitResult) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            BlockEntity blockentity = level.getBlockEntity(blockPos);
            if (blockentity instanceof MixingStandBlockEntity) {
                player.openMenu((MenuProvider) blockentity);
            }

            return InteractionResult.CONSUME;
        }
    }

    /*public void setPlacedBy(Level p_50913_, BlockPos p_50914_, BlockState p_50915_, LivingEntity p_50916_, ItemStack p_50917_) {
        if (p_50917_.hasCustomHoverName()) {
            BlockEntity blockentity = p_50913_.getBlockEntity(p_50914_);
            if (blockentity instanceof BrewingStandBlockEntity) {
                ((BrewingStandBlockEntity)blockentity).setCustomName(p_50917_.getHoverName());
            }
        }

    }

    public void onRemove(BlockState p_50937_, Level p_50938_, BlockPos p_50939_, BlockState p_50940_, boolean p_50941_) {
        if (!p_50937_.is(p_50940_.getBlock())) {
            BlockEntity blockentity = p_50938_.getBlockEntity(p_50939_);
            if (blockentity instanceof BrewingStandBlockEntity) {
                Containers.dropContents(p_50938_, p_50939_, (BrewingStandBlockEntity)blockentity);
            }

            super.onRemove(p_50937_, p_50938_, p_50939_, p_50940_, p_50941_);
        }
    }

    public boolean hasAnalogOutputSignal(BlockState p_50919_) {
        return true;
    }

    public int getAnalogOutputSignal(BlockState p_50926_, Level p_50927_, BlockPos p_50928_) {
        return AbstractContainerMenu.getRedstoneSignalFromBlockEntity(p_50927_.getBlockEntity(p_50928_));
    }*/

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_50948_) {
        p_50948_.add(HAS_BOTTLE[0], HAS_BOTTLE[1], HAS_BOTTLE[2]);
    }

    public boolean isPathfindable(BlockState p_50921_, BlockGetter p_50922_, BlockPos p_50923_, PathComputationType p_50924_) {
        return false;
    }
}
