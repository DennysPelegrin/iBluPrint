package dennys.iBluPrint.gui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import dennys.iBluPrint.events.ClientEvents;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import scala.Console;

public class Load extends GuiScreen{
	private SchematicSlot schematicSlot;
	private GuiScreen lastScreen;
	protected File currentDirectory = new File("iBluPrint/");
	protected File[] schematicFiles;
	public static String fileName;
	
	public Load(GuiScreen guiScreen) {
		this.lastScreen = guiScreen;
	}
	
	//this.buttonList.add(new GuiButton(++id, this.width / 2 - 50, this.height - 30, 100, 20, I18n.format("Done")));
    @Override
    public void initGui() {
        int id = 0;
        
        this.buttonList.add(new GuiButton(id++, this.width / 2 + 4, this.height - 36, 150, 20, I18n.format("Done")));

        this.schematicSlot = new SchematicSlot(this);
        Console.println("I am in the initGUI");
        
        schematicFiles = new File("iBluPrint/").listFiles();
        Console.println("I am after the line");
        
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        this.schematicSlot.handleMouseInput();
    }

    @Override
    protected void actionPerformed(final GuiButton guiButton) {
        if (guiButton.enabled) {
            if (guiButton.id == 0) {
                fileName = schematicFiles[SchematicSlot.selectedIndex].getName();
            	ClientEvents.loadBlueprint(fileName, false, 0, 0, 0);
                this.mc.displayGuiScreen(this.lastScreen);
            } 
            else {
                this.schematicSlot.actionPerformed(guiButton);
            }
        }
    }

    @Override
    public void drawScreen(final int x, final int y, final float partialTicks) {
        this.schematicSlot.drawScreen(x, y, partialTicks);

        drawCenteredString(this.fontRenderer, I18n.format("Title"), this.width / 2, 4, 0x00FFFFFF);
        drawCenteredString(this.fontRenderer, I18n.format("Folder Info"), this.width / 2 - 78, this.height - 12, 0x00808080);

        super.drawScreen(x, y, partialTicks);
    }

    @Override
    public void onGuiClosed() {
        // loadSchematic();
    }

    private void loadSchematic() {
//        final int selectedIndex = this.guiSchematicLoadSlot.selectedIndex;
//
//        try {
//            if (selectedIndex >= 0 && selectedIndex < this.schematicFiles.size()) {
//                final GuiSchematicEntry schematicEntry = this.schematicFiles.get(selectedIndex);
//                if (Schematica.proxy.loadSchematic(null, this.currentDirectory, schematicEntry.getName())) {
//                    final SchematicWorld schematic = ClientProxy.schematic;
//                    if (schematic != null) {
//                        ClientProxy.moveSchematicToPlayer(schematic);
//                    }
//                }
//            }
//        } catch (final Exception e) {
//            Reference.logger.error("Failed to load schematic!", e);
//        }
    }
}
