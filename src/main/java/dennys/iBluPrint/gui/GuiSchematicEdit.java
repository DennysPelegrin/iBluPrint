package dennys.iBluPrint.gui;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dennys.iBluPrint.events.ClientEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.client.config.GuiUnicodeGlyphButton;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiSchematicEdit extends GuiScreen{
	private final GuiScreen lastScreen;
	private static final Logger LOGGER = LogManager.getLogger("it");
	
	public GuiSchematicEdit(final GuiScreen guiScreen) {
		this.lastScreen = guiScreen;
	}
	
	@Override
	public void initGui() {
		this.buttonList.clear();
		int id = 20;
		
		if (this.mc.world != null) {
			this.buttonList.add(new GuiNumberField(this.fontRenderer, id++, this.width/2 - 241, this.height/2 - 30, 100, 20));
			id++;
			this.buttonList.add(new GuiNumberField(this.fontRenderer, id++, this.width/2 - 241, this.height/2 - 5, 100, 20));
			id++;
			this.buttonList.add(new GuiNumberField(this.fontRenderer, id++, this.width/2 - 241, this.height/2 + 20, 100, 20));
			id++;
			this.buttonList.add(new GuiButton(id++, this.width - 90, this.height - 200, 80, 20, I18n.format("Unload")));
			this.buttonList.add(new GuiButton(id++, this.width - 90, this.height - 170, 80, 20, I18n.format("Move Here")));
			//this.buttonList.add(new GuiUnicodeGlyphButton(id++, this.width - 90, this.height - 140, 80, 20, " " + I18n.format("Rotate"), "\u21bb", 2.0f));
			this.buttonList.add(new GuiButton(id++, 10, this.height - 70, 80, 20, I18n.format("Materials")));
			
		}
	}
	
	@Override
	public void updateScreen() {
		for (GuiButton button : this.buttonList) {
			if (button instanceof GuiNumberField) {
				((GuiNumberField) button).updateCursorCounter();
			}
		}
		super.updateScreen();
	}
	
	@Override
	public void mouseClicked(int x, int y, int mouseButton) throws IOException{
		for (GuiButton button : this.buttonList) {
			if (button instanceof GuiNumberField) {
				((GuiNumberField) button).mouseClicked(x,  y,  mouseButton);
			}
		}
		super.mouseClicked(x,  y,  mouseButton);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks){
		drawCenteredString(this.fontRenderer, I18n.format("Move Schematic"), this.width/2 - 200, this.height/2 - 45, 0xFFFFFF);
        drawString(this.fontRenderer, I18n.format("X: "), this.width/2 - 256, this.height/2 - 24, 0xFFFFFF);
        drawString(this.fontRenderer, I18n.format("Y: "), this.width/2 - 256, this.height/2 + 1, 0xFFFFFF);
        drawString(this.fontRenderer, I18n.format("Z: "), this.width/2 - 256, this.height/2 + 26, 0xFFFFFF);

        super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	public void actionPerformed(GuiButton guiButton) {
		if (guiButton.enabled) {
			if (guiButton.id == 26) {
				ClientEvents.isActivated = false;
				ClientEvents.positions.clear();
				ClientEvents.states.clear();
			}
			if (guiButton.id == 27) {
				ClientEvents.loadBlueprint(Load.fileName, false, 0, 0, 0);
			}
			if (guiButton.id == 28) {
				
			}
			if (guiButton.id == 29) {
				Minecraft.getMinecraft().displayGuiScreen(new Materials(this));
			}
		}
	}
}