package dennys.iBluPrint.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import scala.Console;

import java.io.File;

import org.apache.commons.io.FilenameUtils;
import org.lwjgl.opengl.GL11;

public class SchematicSlot extends GuiSlot{
	private Load load;
	public static int selectedIndex;
	private double lastClicked;
	
	public SchematicSlot(Load load) {
		super(Minecraft.getMinecraft(), load.width, load.height, 16, load.height - 40, 24);
		this.load = load;
		lastClicked = 0;
		selectedIndex = -1;
	}
	
	@Override
	protected int getSize() {
		return this.load.schematicFiles.length;
	}
	
	@Override
	protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY) {
		boolean ignoreClick = Minecraft.getSystemTime() - this.lastClicked < 500;
		this.lastClicked = Minecraft.getSystemTime();
		
		if (ignoreClick == true) {
			return;
		}
		this.selectedIndex = slotIndex;
//		SchematicEntry schematic = this.load.schematicFiles.get(slotIndex);
//		if (schematic.isDirectory()) {
//			this.load.changeDirectory(schematic.getName());
//			this.selectedIndex = -1;
//		}
//		else {
//			this.selectedIndex = slotIndex;
//		}
	}
	
	@Override
	protected boolean isSelected(int index) {
		return index == this.selectedIndex;
	}
	
	@Override
	protected void drawBackground() {
		
	}
	

//	protected void drawContainerBackGround(Tessellator tessellator) {
//		
//	}
	
    @Override
    protected void drawSlot(int index, int x, int y, int par4, int mouseX, int mouseY, float partialTicks) {
        if (index < 0 || index >= this.load.schematicFiles.length) {
            return;
        }

        File schematic = this.load.schematicFiles[index];
        String schematicName = schematic.getName();
        int i = schematicName.lastIndexOf(".");
        schematicName = schematicName.substring(0, i);

//        if (schematic.isDirectory()) {
//            schematicName += "/";
//        } 
//        else {
//            schematicName = FilenameUtils.getBaseName(schematicName);
//        }

        //this.minecraft.renderEngine, schematic.getItemStack(), x, y
        Minecraft.getMinecraft().renderEngine.bindTexture(Gui.STAT_ICONS);

        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder buffer = tessellator.getBuffer();
        final double uScale = 1.0 / 128.0;
        final double vScale = 1.0 / 128.0;

        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        buffer.pos(x+1, y+1, 0).tex(0, 0).endVertex();
        buffer.pos(x+1, y + 1 + 18, 0).tex(0, vScale * 18).endVertex();
        buffer.pos(x + 1 + 18, y + 1 + 18, 0).tex(uScale * 18, vScale * 18).endVertex();
        buffer.pos(x + 1 + 18, y+1, 0).tex(uScale * 18, 0).endVertex();
        tessellator.draw();

        //if (schematic.getItemStack() != null && schematic.getItemStack().getItem() != null) {
            GlStateManager.enableRescaleNormal();
            RenderHelper.enableGUIStandardItemLighting();
            //Minecraft.getMinecraft().getRenderItem().renderItemIntoGUI(schematic.getItemStack(), x + 2, y + 2);
            RenderHelper.disableStandardItemLighting();
            GlStateManager.disableRescaleNormal();
        //}

        this.load.drawString(Minecraft.getMinecraft().fontRenderer, schematicName, x + 24, y + 6, 0x00FFFFFF);
    }
}
