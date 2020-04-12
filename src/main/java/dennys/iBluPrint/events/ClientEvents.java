package dennys.iBluPrint.events;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;

import dennys.iBluPrint.blocks.GetterBlock;
import dennys.iBluPrint.gui.AreaSelect;
import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import scala.Console;

public class ClientEvents {
	
	static boolean rememberA = false;
	static BlockPos positionToRememberA = null;
	static boolean rememberB = false;
	static BlockPos positionToRememberB = null;
	
	public static boolean isActivated = false;
	public static ArrayList<BlockPos> positions = new ArrayList<BlockPos>();
	public static ArrayList<IBlockState> states = new ArrayList<IBlockState>();
	
	static double orig_x;
	static double orig_y;
	static double orig_z;

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void highlightGhostBlock(RenderWorldLastEvent event) {
		//if we need to render ghost blocks
		if(isActivated) {
			EntityPlayer player = Minecraft.getMinecraft().player;
			World world = player.world;
			
			double d0 = player.lastTickPosX + (player.posX - player.lastTickPosX) * event.getPartialTicks();
			double d1 = player.lastTickPosY + (player.posY - player.lastTickPosY) * event.getPartialTicks();
			double d2 = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * event.getPartialTicks();
			
			Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
			BlockRendererDispatcher renderer = Minecraft.getMinecraft().getBlockRendererDispatcher();
			
			GlStateManager.pushMatrix();
			GlStateManager.enableBlend();
			
			int currState = 0;
			
			List<Block> badBlocks = new ArrayList<Block>();
			badBlocks.add(Blocks.LEAVES);
			badBlocks.add(Blocks.LEAVES2);
			badBlocks.add(Blocks.ACACIA_DOOR);
			badBlocks.add(Blocks.BIRCH_DOOR);
			badBlocks.add(Blocks.DARK_OAK_DOOR);
			badBlocks.add(Blocks.IRON_DOOR);
			badBlocks.add(Blocks.JUNGLE_DOOR);
			badBlocks.add(Blocks.OAK_DOOR);
			badBlocks.add(Blocks.SPRUCE_DOOR);
			badBlocks.add(Blocks.CHEST);
			badBlocks.add(Blocks.BED);
			badBlocks.add(Blocks.WATER);
			badBlocks.add(Blocks.ACACIA_FENCE);
			
			for(BlockPos pos : positions) {
				IBlockState stateToRender = states.get(currState);
				if(!badBlocks.contains(stateToRender.getBlock()) && stateToRender != Blocks.AIR.getDefaultState() && stateToRender.getBlock() == world.getBlockState(pos).getBlock() && stateToRender != world.getBlockState(pos)) {
					GlStateManager.pushMatrix();
					GlStateManager.translate(-d0, -d1, -d2);
					GlStateManager.translate(pos.getX(), pos.getY(), pos.getZ());
					GlStateManager.scale(1.001, 1.01, 1.01);
					GlStateManager.translate(-.0005, -.0005, -.0005);
					GL14.glBlendColor(1f, 1f, 1f, 0.5f);
					AxisAlignedBB aabb = stateToRender.getBoundingBox(world, pos);
					
					double w = aabb.maxX - aabb.minX;
					double h = aabb.maxY - aabb.minY;
					double d = aabb.maxZ - aabb.minZ;
					Vec3d center = aabb.getCenter();
					
					GlStateManager.translate(aabb.minX, aabb.minY, aabb.maxZ);
					GlStateManager.scale(w, h, d);
					renderer.renderBlockBrightness(Blocks.CONCRETE.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.ORANGE), 1f);
					GlStateManager.popMatrix();
					
					currState++;
					continue;
				}
				if(stateToRender == world.getBlockState(pos)) {
					currState++;
					continue;
				}
				if(stateToRender == Blocks.AIR.getDefaultState() || stateToRender == Blocks.CHEST.getDefaultState()) {
					currState++;
					continue;
				}
				
				if(world.getBlockState(pos) != Blocks.AIR.getDefaultState() && world.getBlockState(pos) != stateToRender) {
					GlStateManager.pushMatrix();
					GlStateManager.translate(-d0, -d1, -d2);
					GlStateManager.translate(pos.getX(), pos.getY(), pos.getZ());
					GlStateManager.scale(1.001, 1.01, 1.01);
					GlStateManager.translate(-.0005, -.0005, -.0005);
					GL14.glBlendColor(1f, 1f, 1f, 0.5f);
					AxisAlignedBB aabb = stateToRender.getBoundingBox(world, pos);
					
					double w = aabb.maxX - aabb.minX;
					double h = aabb.maxY - aabb.minY;
					double d = aabb.maxZ - aabb.minZ;
					Vec3d center = aabb.getCenter();
					
					GlStateManager.translate(aabb.minX, aabb.minY, aabb.maxZ);
					GlStateManager.scale(w, h, d);
					renderer.renderBlockBrightness(Blocks.CONCRETE.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.RED), 1f);
					GlStateManager.popMatrix();
					currState++;
					continue;
				}
				
				if(stateToRender.getBlock().getBlockLayer() != BlockRenderLayer.TRANSLUCENT) {
					GlStateManager.blendFunc(GL11.GL_CONSTANT_ALPHA, GL11.GL_ONE_MINUS_CONSTANT_ALPHA);
				}
				else {
					GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
				}
				
				GlStateManager.pushMatrix();
				GlStateManager.translate(-d0, -d1, -d2);
				GlStateManager.translate(pos.getX(), pos.getY(), pos.getZ());
				GlStateManager.rotate(-90, 0f, 1f, 0f);
				
				GL14.glBlendColor(1f, 1f, 1f, 0.65f);
				
				
				
				try {
					stateToRender = stateToRender.getActualState(world, pos);
				}
				catch(Exception e) {
					
				}
				
				try {
					renderer.renderBlockBrightness(stateToRender, 1f);
				}
				catch(Exception e) {
					GlStateManager.rotate(-90, 0f, 1f, 0f);
					renderer.renderBlockBrightness(Blocks.STONE.getDefaultState(), 1f);
				}
				
				GlStateManager.popMatrix();
				
				currState++;
			}
			
			GlStateManager.blendFunc(GL11.GL_CONSTANT_ALPHA, GL11.GL_ONE_MINUS_CONSTANT_ALPHA);
			
			GlStateManager.disableBlend();
			GlStateManager.popMatrix();
		}
	}
	
	public static void loadBlueprint(String fileName, boolean moving, int moveX, int moveY, int moveZ) {
		if(fileName == null || fileName == "") {
			return;
		}
		try {
			String file = "iBluPrint/" + fileName;
			FileReader reader = new FileReader(file);
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
				blocksFromFile.add(currBlock);
				currBlock = bufferedReader.readLine();
				metaFromFile.add(Integer.parseInt(currBlock));
				currBlock = bufferedReader.readLine();
			}
			
			reader.close();
			EntityPlayer player = Minecraft.getMinecraft().player;
			World worldIn = player.world;
			
			if(!moving) {
				orig_x = player.posX;
				orig_y = player.posY;
				orig_z = player.posZ;
			}
			else {
				orig_x += moveX;
				orig_y += moveY;
				orig_z += moveZ;
			}
			
			positions.clear();
			states.clear();
			
			int currSpot = 0;
			for(int y = 0; y < height; y++) {
				for(int z = 0; z < length; z++) {
					for(int x = 0; x < width; x++) {
						
						String currBlockID = blocksFromFile.get(currSpot);
						
						BlockPos newBlockPos = new BlockPos(orig_x + x, orig_y + y, orig_z + z);
						
						net.minecraft.block.Block newBlock = worldIn.getBlockState(newBlockPos).getBlock().getBlockById(Integer.parseInt(currBlockID));
						IBlockState newBlockState = newBlock.getStateFromMeta(metaFromFile.get(currSpot));
						
						positions.add(newBlockPos);
						states.add(newBlockState);
						
						currSpot++;
					}
				}
			}
			isActivated = true;
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void displayCornerA(RenderWorldLastEvent event) {
		if(AreaSelect.isACornerActive()) {

            EntityPlayer player = Minecraft.getMinecraft().player;

            World world = player.world;

           

            double d0 = player.lastTickPosX + (player.posX - player.lastTickPosX) * event.getPartialTicks();

            double d1 = player.lastTickPosY + (player.posY - player.lastTickPosY) * event.getPartialTicks();

            double d2 = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * event.getPartialTicks();

           

            Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

            BlockRendererDispatcher renderer = Minecraft.getMinecraft().getBlockRendererDispatcher();

           

            GlStateManager.pushMatrix();

            GlStateManager.enableBlend();

           

            if(!rememberA) {

            	positionToRememberA = player.getPosition();

            }



            IBlockState stateToRender = world.getBlockState(positionToRememberA);

           

            GlStateManager.pushMatrix();

            GlStateManager.translate(-d0, -d1, -d2);

            GlStateManager.translate(positionToRememberA.getX(), positionToRememberA.getY(), positionToRememberA.getZ());

            GlStateManager.scale(1.001, 1.01, 1.01);

            GlStateManager.translate(-.0005, -.0005, -.0005);

            GL14.glBlendColor(1f, 1f, 1f, 0.5f);

            AxisAlignedBB aabb = stateToRender.getBoundingBox(world, positionToRememberA);

           

            double w = aabb.maxX - aabb.minX;

            double h = aabb.maxY - aabb.minY;

            double d = aabb.maxZ - aabb.minZ;

            Vec3d center = aabb.getCenter();

           

            GlStateManager.translate(aabb.minX, aabb.minY, aabb.maxZ);

            GlStateManager.scale(w, h, d);

            renderer.renderBlockBrightness(Blocks.CONCRETE.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.RED), 1f);

            GlStateManager.popMatrix();

           

            GlStateManager.disableBlend();

            GlStateManager.popMatrix();

            rememberA = true;

		}
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void displayCornerB(RenderWorldLastEvent event) {
		if(AreaSelect.isBCornerActive()) {

            EntityPlayer player = Minecraft.getMinecraft().player;

            World world = player.world;

           

            double d0 = player.lastTickPosX + (player.posX - player.lastTickPosX) * event.getPartialTicks();

            double d1 = player.lastTickPosY + (player.posY - player.lastTickPosY) * event.getPartialTicks();

            double d2 = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * event.getPartialTicks();

           

            Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

            BlockRendererDispatcher renderer = Minecraft.getMinecraft().getBlockRendererDispatcher();

           

            GlStateManager.pushMatrix();

            GlStateManager.enableBlend();

           

            if(!rememberB) {

            	positionToRememberB = player.getPosition();

            }



            IBlockState stateToRender = world.getBlockState(positionToRememberB);

           

            GlStateManager.pushMatrix();

            GlStateManager.translate(-d0, -d1, -d2);

            GlStateManager.translate(positionToRememberB.getX(), positionToRememberB.getY(), positionToRememberB.getZ());

            GlStateManager.scale(1.001, 1.01, 1.01);

            GlStateManager.translate(-.0005, -.0005, -.0005);

            GL14.glBlendColor(1f, 1f, 1f, 0.5f);

            AxisAlignedBB aabb = stateToRender.getBoundingBox(world, positionToRememberB);

           

            double w = aabb.maxX - aabb.minX;

            double h = aabb.maxY - aabb.minY;

            double d = aabb.maxZ - aabb.minZ;

            Vec3d center = aabb.getCenter();

           

            GlStateManager.translate(aabb.minX, aabb.minY, aabb.maxZ);

            GlStateManager.scale(w, h, d);

            renderer.renderBlockBrightness(Blocks.CONCRETE.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.BLUE), 1f);

            GlStateManager.popMatrix();

           

            GlStateManager.disableBlend();

            GlStateManager.popMatrix();

            rememberB = true;

		}
	}
	
	public static void setRememberA(boolean b) {
		rememberA = b;
	}
	
	public static void setRememberB(boolean b) {
		rememberB = b;
	}
	
	public static void updatePosA(int x, int y, int z) {
		if(positionToRememberA == null) {
			return;
		}
		positionToRememberA = positionToRememberA.add(x, y, z);
	}
	
	public static void updatePosB(int x, int y, int z) {
		if(positionToRememberB == null) {
			return;
		}
		positionToRememberB = positionToRememberB.add(x, y, z);
	}

	public static void saveSchematic(String fileName) {
		if(positionToRememberA == null || positionToRememberB == null) {
			return;
		}
		
		int width, height, length;
		
		width = (int) Math.abs(positionToRememberA.getX() - positionToRememberB.getX()) + 1;
		height = (int) Math.abs(positionToRememberA.getY() - positionToRememberB.getY()) + 1;
		length = (int) Math.abs(positionToRememberA.getZ() - positionToRememberB.getZ()) + 1;
		
		List<BlockPos> positions = new ArrayList<BlockPos>();
		for(int y = 0; y < height; y++) {
			for(int z = 0; z < length; z++) {
				for(int x = 0; x < width; x++) {
					float posX = positionToRememberA.getX();
					float posY = positionToRememberA.getY();
					float posZ = positionToRememberA.getZ();
					
					if(posX > positionToRememberB.getX()) {
						posX -= x;
					}
					else {
						posX += x;
					}
					if(posY > positionToRememberB.getY()) {
						posY -= y;
					}
					else {
						posY += y;
					}
					if(posZ > positionToRememberB.getZ()) {
						posZ -= z;
					}
					else {
						posZ += z;
					}
					
					BlockPos position = new BlockPos(posX, posY, posZ);
					positions.add(position);
				}
			}
		}
		
		//Iterable<BlockPos> positions = BlockPos.getAllInBox(pos, pos.add(width,length,height));
		File blueprint = new File("iBluPrint/");
		EntityPlayer player = Minecraft.getMinecraft().player;
		World worldIn = player.world;
		
		if(!blueprint.exists()) {
			blueprint.mkdirs();
		}
		
		try {
			FileWriter w = new FileWriter("iBluPrint/" + fileName + ".txt");
			w.write(Integer.toString(width));
			w.write('\n');
			w.write(Integer.toString(length));
			w.write('\n');
			w.write(Integer.toString(height));
			w.write('\n');
			
			for(BlockPos currBlockPos : positions) {
				int blockID = worldIn.getBlockState(currBlockPos).getBlock().getIdFromBlock(worldIn.getBlockState(currBlockPos).getBlock());
				int meta = worldIn.getBlockState(currBlockPos).getBlock().getMetaFromState(worldIn.getBlockState(currBlockPos));
				Console.println(meta);
				w.write(Integer.toString(blockID));
				w.write('\n');
				w.write(Integer.toString(meta));
				w.write('\n');
			}
			
			w.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	@SubscribeEvent
	public void onPlayerInteract(BlockEvent.PlaceEvent e){
		Console.println(e.getPos());
		if(GetterBlock.getBlockPos().contains(e.getPos())) {
			int arrPos = GetterBlock.getBlockPos().indexOf(e.getPos());
			GetterBlock.removeFromIndex(arrPos);
		}
	}
	*/
}
