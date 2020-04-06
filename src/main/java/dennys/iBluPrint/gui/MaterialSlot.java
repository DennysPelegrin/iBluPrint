package dennys.iBluPrint.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class MaterialSlot extends GuiSlot{
	private Materials materials;
	public int index;
	
	public MaterialSlot(Materials parent) {
		super(Minecraft.getMinecraft(), parent.width, parent.height, 16, parent.height - 34, 24);
		this.materials = parent;
		index = -1;
	}

    @Override
    protected int getSize() {
        return this.materials.map.length;
    }

    @Override
    protected void elementClicked(final int index, final boolean par2, final int par3, final int par4) {
        this.index = index;
    }

    @Override
    protected boolean isSelected(final int index) {
        return index == this.index;
    }

    @Override
    protected void drawBackground() {
    }

    @Override
    protected void drawContainerBackground(final Tessellator tessellator) {
    }

    @Override
    protected int getScrollBarX() {
        return this.width / 2 + getListWidth() / 2 + 2;
    }
    
    @Override
    protected void drawSlot(final int index, final int x, final int y, final int par4, final int mouseX, final int mouseY, final float partialTicks) {
        final int[] blockAndAmount = this.materials.map[index];
        final Block block = Block.getBlockById(blockAndAmount[0]);
        final ItemStack itemStack = new ItemStack(block, blockAndAmount[1]);
        final String itemName = itemStack.getDisplayName();
        final String amount = blockAndAmount[1] + "";

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

        if (itemStack != null && itemStack.getItem() != null) {
            GlStateManager.enableRescaleNormal();
            RenderHelper.enableGUIStandardItemLighting();
            Minecraft.getMinecraft().getRenderItem().renderItemIntoGUI(itemStack, x + 2, y + 2);
            RenderHelper.disableStandardItemLighting();
            GlStateManager.disableRescaleNormal();
        }

        this.materials.drawString(Minecraft.getMinecraft().fontRenderer, itemName, x + 24, y + 6, 0xFFFFFF);
        this.materials.drawString(Minecraft.getMinecraft().fontRenderer, amount, x + 215 - Minecraft.getMinecraft().fontRenderer.getStringWidth(amount), y + 1, 0xFFFFFF);

        if (mouseX > x && mouseY > y && mouseX <= x + 18 && mouseY <= y + 18) {
            this.materials.renderToolTip(itemStack, mouseX, mouseY);
            GlStateManager.disableLighting();
        }
    }
}
