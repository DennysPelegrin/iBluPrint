package dennys.iBluPrint.gui;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dennys.iBluPrint.events.ClientEvents;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import scala.Console;

@SideOnly(Side.CLIENT)
public class Materials extends GuiScreen{
	private final GuiScreen lastScreen;
	private MaterialSlot materialSlot;
	//private ItemStackSortType sortType; might need a sorter
	protected int[][] map;
	
	public Materials(final GuiScreen guiScreen) {
		this.lastScreen = guiScreen;
		this.map = getBlockCount();
	}
	
	private int[][] getBlockCount(){
		HashMap<Integer, Integer> hashmap = new HashMap<Integer, Integer>();
		for (int i = 0; i < ClientEvents.states.size(); i++) {
			if (ClientEvents.states.get(i) == Blocks.AIR.getDefaultState()) {
				continue;
			}
			Block block = ClientEvents.states.get(i).getBlock();
			int blockID = block.getIdFromBlock(block);
			if (!hashmap.containsKey(blockID)) {
				hashmap.put(blockID, 1);
			}
			else {
				hashmap.put(blockID, hashmap.get(blockID) + 1);
			}
		}
		
		int[][] returnThis = new int[hashmap.size()][2];
		
		int i = 0;
		for (Integer blockID : hashmap.keySet()) {
			int blockCount = hashmap.get(blockID);
			returnThis[i] = new int [] {blockID, blockCount};
			i++;
		}
		
		return returnThis;
	}
	
	@Override
	public void initGui() {
		this.buttonList.clear();
		int id = 0;
		
		this.buttonList.add(new GuiButton(++id, this.width / 2 - 50, this.height - 30, 100, 20, I18n.format("Done")));
		this.materialSlot = new MaterialSlot(this);
	}
	
	@Override
	public void actionPerformed(final GuiButton guiButton) {
		if (guiButton.enabled) {
			if (guiButton.id == 1) {
				Minecraft.getMinecraft().displayGuiScreen(this.lastScreen);
			}
		}
	}
	
	@Override
	public void renderToolTip(ItemStack itemStack, int x, int y) {
		super.renderToolTip(itemStack, x, y);
	}
	
	@Override
	public void drawScreen(int x, int y, float partialTicks) {
		this.materialSlot.drawScreen(x, y, partialTicks);
		drawString(this.fontRenderer, I18n.format("Material"), this.width/2 - 108, 4, 0x00FFFFFF);
		drawString(this.fontRenderer, I18n.format("Amount"), this.width / 2 + 108 - this.fontRenderer.getStringWidth("Amount"), 4, 0x00FFFFFF);
		super.drawScreen(x,  y,  partialTicks);
	}
	
	@Override
	public void handleMouseInput() throws IOException {
		super.handleMouseInput();
		this.materialSlot.handleMouseInput();
	}
}