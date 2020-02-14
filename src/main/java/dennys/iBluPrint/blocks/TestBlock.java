package dennys.iBluPrint.blocks;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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
		Iterable<BlockPos> positions = BlockPos.getAllInBox(pos, pos.add(3,3,3));
		List<String> blockNames = new ArrayList<String>();
		for(BlockPos currBlockPos : positions) {
			String blockName = worldIn.getBlockState(currBlockPos).getBlock().getLocalizedName();
			blockNames.add(worldIn.getBlockState(currBlockPos).getBlock().getLocalizedName());
			Console.println(blockName + ": " + currBlockPos);
		}
		
		File blueprint = new File("iBluPrint/");
		String filename = "example";
		
		if(!blueprint.exists()) {
			blueprint.mkdirs();
		}
		
		try {
			FileWriter w = new FileWriter("iBluPrint/" + filename + ".txt");
			for(int i = 0; i < blockNames.size(); i++) {
				String currBlock = blockNames.get(i);
				w.write(currBlock + "\n");
			}
			w.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return true;
	}

}
