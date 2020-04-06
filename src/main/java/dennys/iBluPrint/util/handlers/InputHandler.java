package dennys.iBluPrint.util.handlers;

import org.lwjgl.input.Keyboard;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dennys.iBluPrint.gui.AreaSelect;
import dennys.iBluPrint.gui.GuiSchematicEdit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class InputHandler {
	
	public static InputHandler INSTANCE = new InputHandler();
	public static KeyBinding areaSelection = new KeyBinding("AreaSelection", Keyboard.KEY_O, "Category");
	public static KeyBinding editSchematic = new KeyBinding("EditSchematic", Keyboard.KEY_U, "Category");
	private static final Logger LOGGER = LogManager.getLogger("it");
	
	@SubscribeEvent
	public void onKeyInput(InputEvent event) {
		if (Minecraft.getMinecraft().currentScreen == null) {
			if (areaSelection.isPressed()) {
				Minecraft.getMinecraft().displayGuiScreen(new AreaSelect(Minecraft.getMinecraft().currentScreen));
			}
			if (editSchematic.isPressed()) {
				Minecraft.getMinecraft().displayGuiScreen(new GuiSchematicEdit(Minecraft.getMinecraft().currentScreen));
			}
		}
	}
}
