package dennys.iBluPrint.proxy;

import dennys.iBluPrint.util.handlers.InputHandler;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class CommonProxy {
	
	public void preInit(FMLPreInitializationEvent event) {
		ClientRegistry.registerKeyBinding(InputHandler.areaSelection);
	}
	
	public void init(FMLInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(InputHandler.INSTANCE);
	}
	
	public void registerItemRenderer(Item item, int meta, String id) {
		
	}
}
