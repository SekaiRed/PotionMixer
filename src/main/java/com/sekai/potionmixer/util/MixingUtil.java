package com.sekai.potionmixer.util;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;

public class MixingUtil {
    public static final String MIXED_POTION = "{\"translate\":\"item.mixed_potion\",\"italic\":\"false\"}";
    public static final String SPLASH_MIXED_POTION = "{\"translate\":\"item.splash_mixed_potion\",\"italic\":\"false\"}";
    public static final String LINGERING_MIXED_POTION = "{\"translate\":\"item.lingering_mixed_potion\",\"italic\":\"false\"}";
    public static final String MIXED_TIPPED_ARROW = "{\"translate\":\"item.mixed_tipped_arrow\",\"italic\":\"false\"}";

    public static boolean isMixedPotion(ItemStack itemStack) {
        return PotionUtils.getCustomEffects(itemStack).size() != 0;
    }
}
