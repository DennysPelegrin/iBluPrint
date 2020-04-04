package dennys.iBluPrint.blocks;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;

import com.jcraft.jorbis.Block;

import dennys.iBluPrint.items.FakeWorld;
import dennys.iBluPrint.util.TilePlaceholder;
import dennys.iBluPrint.util.TileSchematic;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import scala.Console;

public class GetterBlock extends BlockBase {
	
	private static boolean isActivated = false;
	private static ArrayList<BlockPos> positions = new ArrayList<BlockPos>();
	private static ArrayList<IBlockState> states = new ArrayList<IBlockState>();

	public GetterBlock(String name, Material material) {
		super(name, material);
		
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		Console.println("Running");
		
		try {
			FileReader reader = new FileReader("iBluPrint/example.txt");
			List<String> blocksFromFile = new ArrayList<String>();
			List<Integer> metaFromFile = new ArrayList<Integer>();
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
				metaFromFile.add(Integer.parseInt(currBlock));
				currBlock = bufferedReader.readLine();
			}
			
			reader.close();
			
			double orig_x = playerIn.posX + 1;
			double orig_y = playerIn.posY + 2;
			double orig_z = playerIn.posZ + 1;
			
			//Rendering the ghost blocks
			/*
			FakeWorld fakeWorld = new FakeWorld();
			double playerX = playerIn.lastTickPosX + (playerIn.posX - playerIn.lastTickPosX);
			double playerY = playerIn.lastTickPosY + (playerIn.posY - playerIn.lastTickPosY);
			double playerZ = playerIn.lastTickPosZ + (playerIn.posZ - playerIn.lastTickPosZ);
			Vec3d playerPos = new Vec3d(playerX, playerY, playerZ);
			
			BlockRendererDispatcher dispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
			Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            
			GlStateManager.pushMatrix();
			GlStateManager.enableBlend();
            GlStateManager.blendFunc(GL11.GL_CONSTANT_ALPHA, GL11.GL_ONE_MINUS_CONSTANT_ALPHA);
			*/
			
			positions.clear();
			states.clear();
			
			int currSpot = 0;
			for(int y = 0; y <= height; y++) {
				for(int z = 0; z <= length; z++) {
					for(int x = 0; x <= width; x++) {
						
						String currBlockID = blocksFromFile.get(currSpot);
						
						BlockPos newBlockPos = new BlockPos(orig_x + x, orig_y + y, orig_z + z);
						
						net.minecraft.block.Block newBlock = worldIn.getBlockState(newBlockPos).getBlock().getBlockById(Integer.parseInt(currBlockID));
						IBlockState newBlockState = newBlock.getStateFromMeta(metaFromFile.get(currSpot));
						
						positions.add(newBlockPos);
						states.add(newBlockState);
						/*
						worldIn.setBlockState(newBlockPos, GhostBlock.getStateById(Integer.parseInt(currBlockID)));
						TileEntity newTile = worldIn.getTileEntity(pos);

						//TODO: compatibility fixes with the tile entity not reflecting current block
						if(newTile instanceof TilePlaceholder) {
							List<BlockMeta> block;
							block = new ArrayList<BlockMeta>();
							block.add(new BlockMeta(newBlock, EnumFacing.NORTH.getOpposite().ordinal()));
							((TileSchematic)newTile).setReplacedBlock(block);

							((TilePlaceholder)newTile).setReplacedTileEntity(block.get(0).getBlock().createTileEntity(worldIn, block.get(0).getBlock().getDefaultState()));
						}
						*/
						//worldIn.setBlockState(newBlockPos, newBlockState, 0x12);
						/*
						fakeWorld.setWorldAndState(worldIn, newBlockState, newBlockPos);
						GlStateManager.pushMatrix();
			            stateManagerPrepare(playerPos, newBlockPos, null);
			            GL14.glBlendColor(1F, 1F, 1F, 0.55f); //Set the alpha of the blocks we are rendering


			            Minecraft.getMinecraft().getBlockRendererDispatcher().renderBlockBrightness(newBlockState, 1f);//Render the defined block
			            GlStateManager.popMatrix();
						*/
						currSpot++;
					}
				}
			}
			isActivated = true;
			/*
			GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	        ForgeHooksClient.setRenderLayer(MinecraftForgeClient.getRenderLayer());
	        //Disable blend
	        GlStateManager.disableBlend();
	        //Pop from the original push in this method
	        GlStateManager.popMatrix();
			*/
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
	
	public static boolean isActivated() {
		return isActivated;
	}
	
	public static ArrayList<BlockPos> getBlockPos() {
		return positions;
	}
	
	public static ArrayList<IBlockState> getBlockStates() {
		return states;
	}
	
	public static void setActivated(boolean s) {
		isActivated = s;
	}
	
	public static void removeFromIndex(int index) {
		states.set(index, Blocks.AIR.getDefaultState());
		boolean allAir = true;
		for(int i = 0; i < states.size(); i++) {
			if(states.get(i) != Blocks.AIR.getDefaultState()) {
				allAir = false;
			}
		}
		
		if(allAir) {
			isActivated = false;
			positions.clear();
			states.clear();
		}
	}

}
