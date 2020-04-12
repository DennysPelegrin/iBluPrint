package dennys.iBluPrint.gui;

import java.io.IOException;

import org.lwjgl.input.Keyboard;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dennys.iBluPrint.Reference;
import dennys.iBluPrint.events.ClientEvents;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import scala.Console;

@SideOnly(Side.CLIENT)
public class AreaSelect extends GuiScreen{
	private final GuiScreen lastScreen;
	private GuiTextField schematicNameField;
	private String schematicName;
	private static final Logger LOGGER = LogManager.getLogger("it");
	private static boolean cornerAActivated = false;
	private static boolean cornerBActivated = false;
	
	public AreaSelect(final GuiScreen guiScreen) {
		LOGGER.info("AreaSelect constructor");
		this.lastScreen = guiScreen;
		schematicName = "";
		//TODO might neeed a second parameter here. Something to do with the settings. Schematica and Minecraft both have it. 
	}
	
	@Override
	public void initGui() {
		this.buttonList.clear(); //buttonList contains all the information that will be displayed. Make sure its clear so we have a blank canvas
		int id = 0; //the id that each button will have
		
		if (this.mc.world != null) {
			//add the Red Point button to the buttonList
			//parameters are button id, x and y of where you want the button to be, width and height of teh button, and the text in the button
			this.buttonList.add(new GuiButton(id++, this.width/2 - 256, this.height/2 - 55, 100, 20, I18n.format("Corner A"))); //0
			this.buttonList.add(new GuiNumberField(this.fontRenderer, id++, this.width/2 - 256, this.height/2 - 30, 100, 20)); //1 2 3
			id++;
			this.buttonList.add(new GuiNumberField(this.fontRenderer, id++, this.width/2 - 256, this.height/2 - 5, 100, 20)); //3 4 5
			id++;
			this.buttonList.add(new GuiNumberField(this.fontRenderer, id++, this.width/2 - 256, this.height/2 + 20, 100, 20)); //5 6 7
			id++;
			this.buttonList.add(new GuiButton(id++, this.width/2 + 156, this.height/2 - 55, 100, 20, I18n.format("Corner B"))); //7
			this.buttonList.add(new GuiNumberField(this.fontRenderer, id++, this.width/2 + 156, this.height/2 - 30, 100, 20)); //8 9 10
			id++;
			this.buttonList.add(new GuiNumberField(this.fontRenderer, id++, this.width/2 + 156, this.height/2 - 5, 100, 20)); //10 11 12
			id++;
			this.buttonList.add(new GuiNumberField(this.fontRenderer, id++, this.width/2 + 156, this.height/2 + 20, 100, 20)); //12 13 14
			id++;
			this.schematicNameField = new GuiTextField(id++, this.fontRenderer, this.width/2 - 76, this.height - 29, 153, 18);
			this.schematicNameField.setText(this.schematicName);
			this.schematicNameField.setFocused(true);
			this.schematicNameField.setMaxStringLength(1024);
			this.buttonList.add(new GuiButton(id++, this.width/2 + 80, this.height - 30, 40, 20, I18n.format("Save")));
		}
	}
	
	@Override
	public void updateScreen() {
		for (GuiButton button : this.buttonList) {
			if (button instanceof GuiNumberField) {
				((GuiNumberField) button).updateCursorCounter();
			}
		}
		
		this.schematicNameField.updateCursorCounter();
		
		super.updateScreen();
	}
	
	@Override
	public void keyTyped(char typedChar, int keyCode) throws IOException {
		if (keyCode == Keyboard.KEY_ESCAPE) {
			this.mc.displayGuiScreen(this.lastScreen);
			return;
		}
		
		for (GuiButton button : this.buttonList) {
			if (button instanceof GuiNumberField) {
				((GuiNumberField) button).keyTyped(typedChar, keyCode);
				
				if (((GuiNumberField) button).isFocused()) {
					actionPerformed((GuiNumberField) button);
				}
			}
		}
		
		this.schematicNameField.textboxKeyTyped(typedChar, keyCode);
		
		this.schematicName = this.schematicNameField.getText();
		
		super.keyTyped(typedChar, keyCode);
	}
	
	@Override
	public void mouseClicked(int x, int y, int mouseButton) throws IOException{
		this.schematicNameField.mouseClicked(x, y, mouseButton);
		
		for (GuiButton button : this.buttonList) {
			if (button instanceof GuiNumberField) {
				((GuiNumberField) button).mouseClicked(x,  y,  mouseButton);
			}
		}
		
		super.mouseClicked(x, y, mouseButton);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.schematicNameField.drawTextBox();
		
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	public void actionPerformed(GuiButton guiButton) {
		if (guiButton.enabled) {
			if (guiButton.id == 0) {
				ClientEvents.setRememberA(false);
				cornerAActivated = true;
			}
			if(guiButton.id == 7) {
				ClientEvents.setRememberB(false);
				cornerBActivated = true;
			}
			Console.println(guiButton.id);
			if (guiButton.id == 15) {
				Console.println(schematicName);
				ClientEvents.saveSchematic(schematicName);
			}
		}
	}
	
	public static boolean isACornerActive() {
		return cornerAActivated;
	}
	
	public static boolean isBCornerActive() {
		return cornerBActivated;
	}
}
