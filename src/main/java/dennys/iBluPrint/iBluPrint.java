package dennys.iBluPrint;

import dennys.iBluPrint.events.ClientEvents;
import dennys.iBluPrint.proxy.CommonProxy;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class iBluPrint {
	
	@Instance
	public static iBluPrint instance;
	
	@SidedProxy(clientSide = Reference.CLIENTPROXY, serverSide = Reference.COMMONPROXY)
	public static CommonProxy proxy;
	
	//means that stuff below will run things
	//runs before initialization. Usually used for blocks, items, etc
	@EventHandler
	public static void preInit(FMLPreInitializationEvent event) {
		
	}
	
	//runs after initialization. Usually used for renders
	@EventHandler
	public static void init(FMLInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(new ClientEvents());
	}
	
	//runs after initialization. ?
	@EventHandler
	public static void postInit(FMLPostInitializationEvent event) {
		
	}
}
