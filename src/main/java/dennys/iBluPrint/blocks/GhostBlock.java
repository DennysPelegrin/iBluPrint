package dennys.iBluPrint.blocks;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class GhostBlock extends Block {

	public GhostBlock(Material mat) {
		super(mat);
	}

	@Override
	protected boolean canSilkHarvest() {
		return false;
	}
}