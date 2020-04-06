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
		int width, height, length;
		width = 5;
		height = 5;
		length = 5;
		List<BlockPos> positions = new ArrayList<BlockPos>();
		for(int y = 0; y <= height; y++) {
			for(int z = 0; z <= length; z++) {
				for(int x = 0; x <= width; x++) {
					BlockPos position = new BlockPos((pos.getX() + x), (pos.getY()+y), (pos.getZ()+z));
					positions.add(position);
				}
			}
		}
		
		//Iterable<BlockPos> positions = BlockPos.getAllInBox(pos, pos.add(width,length,height));
		File blueprint = new File("iBluPrint/");
		String filename = "example";
		
		if(!blueprint.exists()) {
			blueprint.mkdirs();
		}
		
		try {
			FileWriter w = new FileWriter("iBluPrint/" + filename + ".txt");
			w.write(Integer.toString(width));
			w.write('\n');
			w.write(Integer.toString(length));
			w.write('\n');
			w.write(Integer.toString(height));
			w.write('\n');
			
			for(BlockPos currBlockPos : positions) {
				int blockID = worldIn.getBlockState(currBlockPos).getBlock().getIdFromBlock(worldIn.getBlockState(currBlockPos).getBlock());
				w.write(Integer.toString(blockID));
				w.write('\n');
			}
			
			w.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return true;
	}
}
