package com.sekai.potionmixer.menu;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

import javax.annotation.Nullable;

public class MixingStandMenu extends AbstractContainerMenu {
    protected MixingStandMenu(@Nullable MenuType<?> p_38851_, int p_38852_) {
        super(p_38851_, p_38852_);
    }

    public static MixingStandMenu createContainerClientSide(int windowID, Inventory inv, FriendlyByteBuf data) {
        //  don't need extraData for this example; if you want you can use it to provide extra information from the server, that you can use
        //  when creating the client container
        //  eg String detailedDescription = extraData.readString(128);
        ///FurnaceZoneContents inputZoneContents = FurnaceZoneContents.createForClientSideContainer(INPUT_SLOTS_COUNT);
        ///FurnaceZoneContents outputZoneContents = FurnaceZoneContents.createForClientSideContainer(OUTPUT_SLOTS_COUNT);
        ///FurnaceZoneContents fuelZoneContents = FurnaceZoneContents.createForClientSideContainer(FUEL_SLOTS_COUNT);
        ///FurnaceStateData furnaceStateData = new FurnaceStateData();

        // on the client side there is no parent TileEntity to communicate with, so we:
        // 1) use dummy inventories and furnace state data (tracked ints)
        // 2) use "do nothing" lambda functions for canPlayerAccessInventory and markDirty
        return new MixingStandMenu(windowID, playerInventory,
                inputZoneContents, outputZoneContents, fuelZoneContents, furnaceStateData);
    }

    @Override
    public boolean stillValid(Player p_38874_) {
        return false;
    }
}
