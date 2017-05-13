package eyeq.phantomdirt.block;

import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.IEntityOwnable;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class BlockPhantomGlass extends BlockStainedGlass {
    public BlockPhantomGlass() {
        super(Material.GLASS);
        this.setHardness(1.0F);
        this.setSoundType(SoundType.GLASS);
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World world, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entity, boolean p_185477_7_) {
        switch(state.getValue(COLOR)) {
        case WHITE:
            if(entity instanceof EntityPlayer) {
                return;
            }
            break;
        case BLACK:
            if(entity instanceof EntityItem) {
                return;
            }
            break;
        case BROWN:
            if(entity instanceof IProjectile) {
                return;
            }
            break;
        case GREEN:
            if(entity instanceof EntityAgeable) {
                return;
            }
            break;
        default:
            if(entity instanceof EntityPlayer || entity instanceof IEntityOwnable) {
                return;
            }
            break;
        }
        super.addCollisionBoxToList(state, world, pos, entityBox, collidingBoxes, entity, p_185477_7_);
    }
}
