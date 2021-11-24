package com.sekai.potionmixer.events;

import com.sekai.potionmixer.util.MixingUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.brewing.PotionBrewEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EventHandler {
    private static final CompoundTag[] resultOverride = {null, null, null};
    //TODO Reduce the potion times accordingly
    @SubscribeEvent
    public void potionBrewedEventPre(PotionBrewEvent.Pre e) {
        if(e.getItem(3).is(Items.GUNPOWDER)) {
            for (int i = 0; i < 3; i++) {
                ItemStack item = e.getItem(i);
                CompoundTag originalTag = item.getOrCreateTag();
                if (item.is(Items.POTION) && !item.getOrCreateTag().contains("Potion")) {
                    resultOverride[i] = originalTag.copy();
                    resultOverride[i].getCompound("display").putString("Name", MixingUtil.SPLASH_MIXED_POTION);
                }
            }
        }
        if(e.getItem(3).is(Items.DRAGON_BREATH)) {
            for (int i = 0; i < 3; i++) {
                ItemStack item = e.getItem(i);
                CompoundTag originalTag = item.getOrCreateTag();
                if (item.is(Items.SPLASH_POTION) && !item.getOrCreateTag().contains("Potion")) {
                    resultOverride[i] = originalTag.copy();
                    resultOverride[i].getCompound("display").putString("Name", MixingUtil.LINGERING_MIXED_POTION);
                }
            }
        }
    }
    @SubscribeEvent
    public void potionBrewedEventPost(PotionBrewEvent.Post e) {
        for (int i = 0; i < 3; i++) {
            if(resultOverride[i] != null) {
                e.getItem(i).setTag(resultOverride[i]);
                resultOverride[i] = null;
            }
        }
    }

    @SubscribeEvent
    public void itemCraftedEvent(PlayerEvent.ItemCraftedEvent e) {
        System.out.println(e.getCrafting());
    }
}
