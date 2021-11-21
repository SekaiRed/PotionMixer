package com.sekai.potionmixer.util;

import com.sekai.potionmixer.Main;
import com.sekai.potionmixer.blocks.MixingStandBlock;
import com.sekai.potionmixer.items.BlockItemBase;
import com.sekai.potionmixer.menu.MixingStandMenu;
import com.sekai.potionmixer.tileentity.MixingStandBlockEntity;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class RegistryHandler {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Main.MODID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Main.MODID);
    public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITY_TYPE = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, Main.MODID);
    public static final DeferredRegister<MenuType<?>> MENU_TYPE = DeferredRegister.create(ForgeRegistries.CONTAINERS, Main.MODID);

    public static void init()
    {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        TILE_ENTITY_TYPE.register(FMLJavaModLoadingContext.get().getModEventBus());
        MENU_TYPE.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    //Blocks
    public static final RegistryObject<Block> MIXING_STAND_BLOCK = BLOCKS.register("mixing_stand", MixingStandBlock::new);

    //Block Items
    public static final RegistryObject<Item> MIXING_STAND_ITEM = ITEMS.register("mixing_stand", () -> new BlockItemBase(MIXING_STAND_BLOCK.get()));

    //Tile Entities
    public static final RegistryObject<BlockEntityType<MixingStandBlockEntity>> MIXING_STAND_ENTITY =
            TILE_ENTITY_TYPE.register("mixing_stand", () -> BlockEntityType.Builder.of(MixingStandBlockEntity::new, MIXING_STAND_BLOCK.get()).build(null));

    //Menu Type
    public static final RegistryObject<MenuType<MixingStandMenu>> MIXING_STAND_MENU =
            MENU_TYPE.register("mixing_stand", IForgeContainerType.create(MixingStandMenu::createContainerClientSide));
}
