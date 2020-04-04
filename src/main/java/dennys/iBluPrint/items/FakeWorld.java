package dennys.iBluPrint.items;

import java.util.Set;

import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FakeWorld implements IBlockAccess {
    private BlockPos position;
    private IBlockState state;
    private World realWorld;
    private final IBlockState AIR = Blocks.AIR.getDefaultState();


    public void setWorldAndState(World rWorld, IBlockState setBlock, BlockPos coordinate) {
        this.state = setBlock;
        this.realWorld = rWorld;
        position = coordinate;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getCombinedLight(BlockPos pos, int lightValue) {
        return realWorld.getCombinedLight(pos, lightValue);
    }

    @Nullable
    @Override
    public TileEntity getTileEntity(BlockPos pos) {
        return null;
    }


    @Override
    public IBlockState getBlockState(BlockPos pos) {
        return (pos == position) ? state : AIR;
    }

    @Override
    public boolean isAirBlock(BlockPos pos) {
        return !(pos == position);
    }

    @Override
    public Biome getBiome(BlockPos pos) {
        return realWorld.getBiome(pos);
    }

    @Override
    public int getStrongPower(BlockPos pos, EnumFacing direction) {
        return 0;
    }

    @Override
    public WorldType getWorldType() {
        return realWorld.getWorldType();
    }

    @Override
    public boolean isSideSolid(BlockPos pos, EnumFacing side, boolean _default) {
        return getBlockState(pos).isSideSolid(this, pos, side);
    }
}