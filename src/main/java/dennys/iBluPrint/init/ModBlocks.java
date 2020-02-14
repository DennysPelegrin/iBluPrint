package dennys.iBluPrint.init;

import java.util.ArrayList;
import java.util.List;

import dennys.iBluPrint.blocks.BlockBase;
import dennys.iBluPrint.blocks.GetterBlock;
import dennys.iBluPrint.blocks.TestBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class ModBlocks {
	public static final List<Block> BLOCKS = new ArrayList<Block>();
	
	public static final Block TEST_BLOCK = new TestBlock("test_block", Material.IRON);
	
	public static final Block GETTER_BLOCK = new GetterBlock("getter_block", Material.IRON);
}
