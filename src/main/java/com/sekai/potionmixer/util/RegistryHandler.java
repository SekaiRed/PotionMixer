package com.sekai.potionmixer.util;

import com.sekai.potionmixer.Main;
import com.sekai.potionmixer.blocks.MixingStandBlock;
import com.sekai.potionmixer.items.BlockItemBase;
import com.sekai.potionmixer.menu.MixingStandMenu;
import com.sekai.potionmixer.recipes.CustomTippedArrowRecipe;
import com.sekai.potionmixer.tileentities.MixingStandBlockEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShieldDecorationRecipe;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RegistryHandler {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Main.MODID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Main.MODID);
    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTIONS, Main.MODID);
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Main.MODID);
    public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITY_TYPE = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, Main.MODID);
    public static final DeferredRegister<MenuType<?>> MENU_TYPE = DeferredRegister.create(ForgeRegistries.CONTAINERS, Main.MODID);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPES = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Main.MODID);

    public static void init()
    {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        POTIONS.register(FMLJavaModLoadingContext.get().getModEventBus());
        SOUNDS.register(FMLJavaModLoadingContext.get().getModEventBus());
        TILE_ENTITY_TYPE.register(FMLJavaModLoadingContext.get().getModEventBus());
        MENU_TYPE.register(FMLJavaModLoadingContext.get().getModEventBus());
        RECIPES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    //Blocks
    public static final RegistryObject<Block> MIXING_STAND_BLOCK = BLOCKS.register("mixing_stand", MixingStandBlock::new);

    //Block Items
    public static final RegistryObject<Item> MIXING_STAND_ITEM = ITEMS.register("mixing_stand", () -> new BlockItemBase(MIXING_STAND_BLOCK.get()));

    //Potions
    public static final RegistryObject<Potion> MIXED_POTION = POTIONS.register("mixed", Potion::new);

    //Sounds
    public static final RegistryObject<SoundEvent> MIXING_SOUND = SOUNDS.register("mix",
            () -> new SoundEvent(new ResourceLocation(Main.MODID,"mix")));

    //Tile Entities
    public static final RegistryObject<BlockEntityType<MixingStandBlockEntity>> MIXING_STAND_ENTITY =
            TILE_ENTITY_TYPE.register("mixing_stand", () -> BlockEntityType.Builder.of(MixingStandBlockEntity::new, MIXING_STAND_BLOCK.get()).build(null));

    //Menu Type
    public static final RegistryObject<MenuType<MixingStandMenu>> MIXING_STAND_MENU =
            MENU_TYPE.register("mixing_stand", () -> IForgeContainerType.create(MixingStandMenu::new));

    //Recipe Serializer
    public static final RegistryObject<SimpleRecipeSerializer<CustomTippedArrowRecipe>> TIPPED_ARROW_RECIPE =
            RECIPES.register("crafting_special_tippedarrowcustom", () -> new SimpleRecipeSerializer<>(CustomTippedArrowRecipe::new));
}
