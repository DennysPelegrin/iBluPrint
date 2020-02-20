package dennys.iBluPrint.blocks;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import scala.Console;

public class GetterBlock extends BlockBase {

	public GetterBlock(String name, Material material) {
		super(name, material);
		
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		//Loading from file
		try {
			FileReader reader = new FileReader("iBluPrint/example.txt");
			List<String> blocksFromFile = new ArrayList<String>();
			BufferedReader bufferedReader = new BufferedReader(reader);
			String currBlock = bufferedReader.readLine();
			while(currBlock != null) {
				Console.println(currBlock);
				blocksFromFile.add(currBlock);
				currBlock = bufferedReader.readLine();
			}
			
			reader.close();
			
			IBlockState newBlockState = worldIn.getBlockState(pos.down());
			
			BlockPos newBlockPos = new BlockPos(playerIn.posX+1, playerIn.posY+1, playerIn.posZ+1);
			
			//worldIn.setBlock(playerIn.posX+1, playerIn.posY+1, playerIn.posZ+1, dennys.iBluPrint.init.ModBlocks.GHOST_BLOCK, worldIn.getBlockState(pos).getBlock().getMetaFromState(state), 3);
			worldIn.setBlockState(newBlockPos, newBlockState, 3);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
	}

}
