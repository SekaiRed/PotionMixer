package com.sekai.potionmixer.util;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;

import java.util.List;

public class MixingUtils {
    public static final String MIXED_POTION = "{\"translate\":\"item.mixed_potion\",\"italic\":\"false\"}";
    public static final String SPLASH_MIXED_POTION = "{\"translate\":\"item.splash_mixed_potion\",\"italic\":\"false\"}";
    public static final String LINGERING_MIXED_POTION = "{\"translate\":\"item.lingering_mixed_potion\",\"italic\":\"false\"}";
    public static final String MIXED_TIPPED_ARROW = "{\"translate\":\"item.mixed_tipped_arrow\",\"italic\":\"false\"}";

    public static boolean isMixedPotion(ItemStack itemStack) {
        return PotionUtils.getPotion(itemStack).equals(RegistryHandler.MIXED_POTION.get());
    }

    public static void correctPotionDurations(ItemStack itemStack, double factor) {
        List<MobEffectInstance> customEffects = PotionUtils.getCustomEffects(itemStack);
        MobEffectInstance index;
        for(int i = 0; i < customEffects.size(); i++) {
            index = customEffects.get(i);
            customEffects.set(i, new MobEffectInstance(index.getEffect(), (int) (index.getDuration() * factor), index.getAmplifier()));
        }
        PotionUtils.setCustomEffects(itemStack, customEffects);
    }
}
