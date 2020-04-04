package dennys.iBluPrint.util;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.RegistryNamespaced;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class TilePlaceholder extends TilePointer {
	IBlockState replacedState;
	TileEntity replacedTile;
	
	public IBlockState getReplacedState() {
		if(replacedState == null)
			return Blocks.AIR.getDefaultState();
		return replacedState;
	}
	
	public void setReplacedBlockState(IBlockState state) {
		this.replacedState = state;
	}
	
	public TileEntity getReplacedTileEntity() {
		return replacedTile;
	}
	
	public int getReplacedMeta() {
		if(replacedState == null || replacedState.getBlock() == null)
			return 0;
		return replacedState.getBlock().getMetaFromState(replacedState);
	}
	
	public void setReplacedTileEntity(TileEntity tile) {
		replacedTile = tile;
	}
	
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound nbt = new NBTTagCompound();

		writeToNBT(nbt);
		return new SPacketUpdateTileEntity(pos, 0, nbt);
	}
	
	@Override
	public NBTTagCompound getUpdateTag() {
		NBTTagCompound nbt = new NBTTagCompound();

		return writeToNBT(nbt);
	}
	
	@Override
	public void handleUpdateTag(NBTTagCompound tag) {
		super.handleUpdateTag(tag);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		NBTTagCompound nbt = pkt.getNbtCompound();
		readFromNBT(nbt);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setInteger("ID", Block.getIdFromBlock(getReplacedState().getBlock()));
		nbt.setInteger("damage", getReplacedState().getBlock().getMetaFromState(getReplacedState()));
		NBTTagCompound tag = new NBTTagCompound();
		
		if(replacedTile != null) {
			replacedTile.writeToNBT(tag);
			nbt.setTag("tile", tag);
		}
		return nbt;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		//TODO: perform sanity check
		replacedState = Block.getBlockById(nbt.getInteger("ID")).getDefaultState();
		replacedState = replacedState.getBlock().getStateFromMeta(nbt.getInteger("damage"));
		
		if(nbt.hasKey("tile")) {
			NBTTagCompound tile = nbt.getCompoundTag("tile");
			
			replacedTile = createTile(tile);
		}
	}
	
	private TileEntity createTile(NBTTagCompound nbt)
	{
		TileEntity tileentity = null;
		ResourceLocation s = new ResourceLocation(nbt.getString("id"));
		Class <? extends TileEntity > oclass = null;


		try
		{
			oclass = ((RegistryNamespaced < ResourceLocation, Class <? extends TileEntity >>)ObfuscationReflectionHelper.getPrivateValue(TileEntity.class, null, "field_190562_f", "REGISTRY")).getObject(s);

			if (oclass != null)
			{
				tileentity = (TileEntity)oclass.newInstance();
			}
		}
		catch (Throwable throwable1)
		{
			net.minecraftforge.fml.common.FMLLog.log(org.apache.logging.log4j.Level.ERROR, throwable1,
					"A TileEntity %s(%s) has thrown an exception during loading, its state cannot be restored. Report this to the mod author",
					s, oclass.getName());
		}

		if (tileentity != null)
		{
			try
			{
				tileentity.readFromNBT(nbt);
			}
			catch (Throwable throwable)
			{
				net.minecraftforge.fml.common.FMLLog.log(org.apache.logging.log4j.Level.ERROR, throwable,
						"A TileEntity %s(%s) has thrown an exception during loading, its state cannot be restored. Report this to the mod author",
						s, oclass.getName());
				tileentity = null;
			}
		}
		else
		{
			net.minecraftforge.fml.common.FMLLog.warning("Skipping BlockEntity with id {}", new Object[] {s});
		}

		return tileentity;
	}
}