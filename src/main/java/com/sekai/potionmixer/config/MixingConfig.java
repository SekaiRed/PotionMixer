package com.sekai.potionmixer.config;

import com.sekai.potionmixer.Main;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import org.apache.commons.lang3.tuple.Pair;

@Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MixingConfig {
    public static final CommonConfig COMMON;
    public static final ForgeConfigSpec COMMON_SPEC;
    static {
        final Pair<CommonConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(CommonConfig::new);
        COMMON_SPEC = specPair.getRight();
        COMMON = specPair.getLeft();
    }

    //Common
    public static int maxAmplifier;

    public static void bakeConfig() {
        //Common
        maxAmplifier = COMMON.maxAmplifier.get();
    }

    public static class CommonConfig {
        public final ForgeConfigSpec.IntValue maxAmplifier;

        public CommonConfig(ForgeConfigSpec.Builder builder) {
            builder.comment("Warning : If you lower the maximum then some conditions/entries may get truncated out of the game, so take caution when changing those values.");
            builder.push("Max Amounts");
            maxAmplifier = builder
                    .comment("The maximal amplifier you can reach when mixing.")
                    .translation(Main.MODID + ".config." + "maxAmplifier")
                    .defineInRange("maxAmplifier", 10, 0, Integer.MAX_VALUE);
            builder.pop();

            builder.push("Data Sync");
            builder.pop();
        }
    }

    @SubscribeEvent
    public static void onModConfigEvent(final ModConfigEvent configEvent) {
        if (configEvent.getConfig().getSpec() == MixingConfig.COMMON_SPEC) {
            bakeConfig();
        }
    }
}
