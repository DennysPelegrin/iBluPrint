package dennys.iBluPrint.events;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;

import dennys.iBluPrint.blocks.GetterBlock;
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

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void highlightGhostBlock(RenderWorldLastEvent event) {
		//if we need to render ghost blocks
		if(GetterBlock.isActivated()) {
			EntityPlayer player = Minecraft.getMinecraft().player;
			World world = player.world;
			
			double d0 = player.lastTickPosX + (player.posX - player.lastTickPosX) * event.getPartialTicks();
			double d1 = player.lastTickPosY + (player.posY - player.lastTickPosY) * event.getPartialTicks();
			double d2 = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * event.getPartialTicks();
			
			Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
			BlockRendererDispatcher renderer = Minecraft.getMinecraft().getBlockRendererDispatcher();
			
			GlStateManager.pushMatrix();
			GlStateManager.enableBlend();
			
			
			ArrayList<BlockPos> positions = GetterBlock.getBlockPos();
			ArrayList<IBlockState> states = GetterBlock.getBlockStates();
			
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
