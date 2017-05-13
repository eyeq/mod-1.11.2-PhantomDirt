package eyeq.phantomdirt.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class BlockPhantomDirt extends Block {
    public BlockPhantomDirt() {
        super(Material.GROUND);
        this.setHardness(1.5F);
        this.setSoundType(SoundType.GROUND);
        this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World world, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entity, boolean p_185477_7_) {
        if(entity instanceof IMob) {
            return;
        }
        super.addCollisionBoxToList(state, world, pos, entityBox, collidingBoxes, entity, p_185477_7_);
    }
}
