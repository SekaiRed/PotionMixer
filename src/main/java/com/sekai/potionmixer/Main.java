package com.sekai.potionmixer;

import com.sekai.potionmixer.screens.MixingStandScreen;
import com.sekai.potionmixer.util.RegistryHandler;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Main.MODID)
public class Main
{
    //TODO Add support for splash potions and tipped arrows, it might be tough but it's worth it
    private static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "potionmixer";

    public Main() {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);

        RegistryHandler.init();

        // Register ourselves for server and other game events we are interested in
        //MinecraftForge.EVENT_BUS.register(this);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(RegistryHandler.MIXING_STAND_BLOCK.get(), RenderType.cutout());

        //Need to register those here
        event.enqueueWork(() -> {
            MenuScreens.register(RegistryHandler.MIXING_STAND_MENU.get(), MixingStandScreen::new);
        });
    }
}
