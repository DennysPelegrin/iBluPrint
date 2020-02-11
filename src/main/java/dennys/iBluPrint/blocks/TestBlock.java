package dennys.iBluPrint.blocks;

import java.util.Iterator;
import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import scala.Console;

public class TestBlock extends BlockBase {

	public TestBlock(String name, Material material) {
		super(name, material);
		
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		Iterable<BlockPos> positions = BlockPos.getAllInBox(pos.add(-3,-3,-3), pos.add(3,3,3));
		for(BlockPos currBlockPos : positions) {
			String blockName = worldIn.getBlockState(currBlockPos).getBlock().getLocalizedName();
			Console.println(blockName + ": " + currBlockPos);
		}
		
		return true;
	}

}
