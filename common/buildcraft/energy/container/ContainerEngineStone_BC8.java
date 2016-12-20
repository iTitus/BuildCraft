package buildcraft.energy.container;

import net.minecraft.entity.player.EntityPlayer;

import buildcraft.energy.tile.TileEngineStone_BC8;
import buildcraft.lib.gui.ContainerBCTile;
import buildcraft.lib.gui.slot.SlotBase;

public class ContainerEngineStone_BC8 extends ContainerBCTile<TileEngineStone_BC8> {
    private static final int PLAYER_INV_START = 84;

    public ContainerEngineStone_BC8(EntityPlayer player, TileEngineStone_BC8 engine) {
        super(player, engine);

        addFullPlayerInventory(PLAYER_INV_START);
        addSlotToContainer(new SlotBase(engine.invFuel, 0, 80, 41));
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return tile.canInteractWith(player);
    }
}
