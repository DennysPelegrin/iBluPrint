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

import com.jcraft.jorbis.Block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
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
			int width = Integer.parseInt(currBlock);
			
			currBlock = bufferedReader.readLine();
			int length = Integer.parseInt(currBlock);
			
			currBlock = bufferedReader.readLine();
			int height = Integer.parseInt(currBlock);
			
			currBlock = bufferedReader.readLine();
			while(currBlock != null) {
				//Console.println(currBlock);
				blocksFromFile.add(currBlock);
				currBlock = bufferedReader.readLine();
			}
			
			reader.close();
			
			double orig_x = playerIn.posX + 1;
			double orig_y = playerIn.posY + 5;
			double orig_z = playerIn.posZ + 1;
			
			for(int y = 0; y <= height; y++) {
				for(int z = 0; z <= length; z++) {
					for(int x = 0; x <= width; x++) {
						String currBlockID = blocksFromFile.get(x + y + z);
						
						BlockPos newBlockPos = new BlockPos(orig_x + x, orig_y + y, orig_z + z);
						
						net.minecraft.block.Block newBlock = worldIn.getBlockState(newBlockPos).getBlock().getBlockById(Integer.parseInt(currBlockID));
						IBlockState newBlockState = newBlock.getDefaultState();
						
						worldIn.setBlockState(newBlockPos, newBlockState, 0x12);
					}
				}
			}
			
			
			
			//final IBlockState barrier = Blocks.BARRIER.getDefaultState();
			
			//IBlockState newBlockState = worldIn.getBlockState(pos.down());
			
			//BlockPos newBlockPos = new BlockPos(playerIn.posX+1, playerIn.posY+1, playerIn.posZ+1);
			
			//worldIn.setBlock(playerIn.posX+1, playerIn.posY+1, playerIn.posZ+1, dennys.iBluPrint.init.ModBlocks.GHOST_BLOCK, worldIn.getBlockState(pos).getBlock().getMetaFromState(state), 3);
			
			
			//worldIn.setBlockState(newBlockPos, barrier, 0x14);
			//worldIn.setBlockState(newBlockPos, newBlockState, 0x12);
			
			
			/*
			BlockRendererDispatcher renderer = Minecraft.getMinecraft().getBlockRendererDispatcher();

			GlStateManager.pushMatrix();
			GlStateManager.translate(newBlockPos.getX(), newBlockPos.getY(), newBlockPos.getZ());
			GlStateManager.rotate(-90, 0, 1, 0);
			renderer.renderBlockBrightness(newBlockState, 0);
			
			GlStateManager.popMatrix();
			
			*/
			
			/*
			RenderWorldLastEvent event = null;
			
			double d0 = playerIn.lastTickPosX + (playerIn.posX - playerIn.lastTickPosX) * event.getPartialTicks();
			double d1 = playerIn.lastTickPosY + (playerIn.posY - playerIn.lastTickPosY) * event.getPartialTicks();
			double d2 = playerIn.lastTickPosZ + (playerIn.posZ - playerIn.lastTickPosZ) * event.getPartialTicks();

			IBlockState stateToRender = newBlockState;

			Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
			BlockRendererDispatcher renderer = Minecraft.getMinecraft().getBlockRendererDispatcher();

			GlStateManager.pushMatrix();
			GlStateManager.enableBlend();
			GlStateManager.blendFunc(GL11.GL_CONSTANT_ALPHA, GL11.GL_ONE_MINUS_CONSTANT_ALPHA);

			GlStateManager.pushMatrix();
			GlStateManager.translate(-d0, -d1, -d2);
			GlStateManager.translate(newBlockPos.getX(), newBlockPos.getY(), newBlockPos.getZ());
			GlStateManager.rotate(-90, 0f, 1f, 0f);
			GL14.glBlendColor(1f, 1f, 1f, 0.8f);
			renderer.renderBlockBrightness(stateToRender, 1f);
			GlStateManager.popMatrix();

			GlStateManager.disableBlend();
			GlStateManager.popMatrix();
			 */
			/*
			GlStateManager.pushMatrix();
            GlStateManager.disableLighting();
            GlStateManager.enableAlpha();
            //GlStateManager.alphaFunc(0, 0);
            
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder vertexbuffer = tessellator.getBuffer();
            newBlockPos.add(0.5, 0, 0.5);
            vertexbuffer.begin(7, DefaultVertexFormats.BLOCK);
            BlockPos blockpos = new BlockPos(newBlockPos.getX(), newBlockPos.getY(), newBlockPos.getZ());
            GlStateManager.translate(blockpos.getX(), blockpos.getY(), blockpos.getZ());
            
            BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
            blockrendererdispatcher.getBlockModelRenderer().renderModel(worldIn, blockrendererdispatcher.getModelForState(newBlockState), newBlockState, blockpos, vertexbuffer, false, MathHelper.getPositionRandom(blockpos));
            tessellator.draw();

            GlStateManager.enableLighting();
            GlStateManager.popMatrix();
            */
			
			/*
			TileEntity te = worldIn.getTileEntity(pos.down());
			NBTTagCompound teNBT = te.getTileData();
			
			teNBT = teNBT.copy();
            teNBT.setInteger("x", newBlockPos.getX());
            teNBT.setInteger("y", newBlockPos.getY());
            teNBT.setInteger("z", newBlockPos.getZ());
            
            te.readFromNBT(teNBT);
            */
			
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
