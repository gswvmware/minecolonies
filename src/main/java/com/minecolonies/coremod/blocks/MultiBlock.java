package com.minecolonies.coremod.blocks;

import com.minecolonies.api.util.constant.Constants;
import com.minecolonies.blockout.BlockOut;
import com.minecolonies.blockout.binding.dependency.DependencyObjectHelper;
import com.minecolonies.blockout.binding.property.PropertyCreationHelper;
import com.minecolonies.blockout.connector.core.inventory.builder.IItemHandlerManagerBuilder;
import com.minecolonies.blockout.element.root.RootGuiElement;
import com.minecolonies.blockout.element.simple.TextField;
import com.minecolonies.coremod.MineColonies;
import com.minecolonies.coremod.creativetab.ModCreativeTabs;
import com.minecolonies.coremod.tileentities.TileEntityMultiBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Locale;

import static com.minecolonies.api.util.constant.Suppression.DEPRECATION;

/**
 * This Class is about the MultiBlock which takes care of pushing others around (In a non mean way).
 */
public class MultiBlock extends AbstractBlockMinecolonies<MultiBlock>
{

    /**
     * The hardness this block has.
     */
    private static final float BLOCK_HARDNESS = 0.0F;

    /**
     * This blocks name.
     */
    private static final String BLOCK_NAME = "multiBlock";

    /**
     * The resistance this block has.
     */
    private static final float RESISTANCE = 1F;

    /**
     * Constructor for the Substitution block.
     * sets the creative tab, as well as the resistance and the hardness.
     */
    public MultiBlock()
    {
        super(Material.WOOD);
        initBlock();
    }

    /**
     * initialize the block
     * sets the creative tab, as well as the resistance and the hardness.
     */
    private void initBlock()
    {
        setRegistryName(BLOCK_NAME);
        setTranslationKey(String.format("%s.%s", Constants.MOD_ID.toLowerCase(Locale.ENGLISH), BLOCK_NAME));
        setCreativeTab(ModCreativeTabs.MINECOLONIES);
        setHardness(BLOCK_HARDNESS);
        setResistance(RESISTANCE);
    }

    @Override
    public boolean onBlockActivated(
            final World worldIn,
            final BlockPos pos,
            final IBlockState state,
            final EntityPlayer playerIn,
            final EnumHand hand,
            final EnumFacing facing,
            final float hitX,
            final float hitY,
            final float hitZ)
    {
        final TileEntity tileEntity = worldIn.getTileEntity(pos);
        if (!worldIn.isRemote && tileEntity instanceof TileEntityMultiBlock)
        {
            final String[] speedCache = {String.valueOf(((TileEntityMultiBlock) tileEntity).getSpeed())};
            final String[] rangeCache = {String.valueOf(((TileEntityMultiBlock) tileEntity).getRange())};

            BlockOut.getBlockOut().getProxy().getGuiController().openUI(
              playerIn,
              iGuiKeyBuilder -> iGuiKeyBuilder
                                  .forPosition(worldIn, pos)
                                  .usingDefaultData()
                                  .withDefaultItemHandlerManager()
                                  .ofFile(new ResourceLocation("minecolonies:gui/blockout_new/multiblock.json"))
                                  .usingData(iBlockOutGuiConstructionDataBuilder ->
                                               iBlockOutGuiConstructionDataBuilder
                                                 .withControl("root", RootGuiElement.RootGuiConstructionDataBuilder.class, rootGuiConstructionDataBuilder -> rootGuiConstructionDataBuilder.withDependentDataContext(DependencyObjectHelper.createFromValue(tileEntity)))
                                  .withControl("speed_input", TextField.TextFieldConstructionDataBuilder.class, textFieldConstructionDataBuilder -> textFieldConstructionDataBuilder
                                                 .withDependentContents(DependencyObjectHelper.createFromProperty(
                                                   PropertyCreationHelper.createFromNonOptional(
                                                     (Object context) -> speedCache[0],
                                                     (Object context, String input) -> {
                                                         speedCache[0] = input;
                                                         try {
                                                             ((TileEntityMultiBlock) context).setSpeed(Integer.parseInt(input));
                                                         }
                                                         catch (final Exception ignored)
                                                         {
                                                             //Thrown when something other then a number is inserted. disregard.
                                                         }
                                                     }
                                                   ), "1")))
                                  .withControl("range_input", TextField.TextFieldConstructionDataBuilder.class , textFieldConstructionDataBuilder -> textFieldConstructionDataBuilder
                                                 .withDependentContents(DependencyObjectHelper.createFromProperty(
                                                   PropertyCreationHelper.createFromNonOptional(
                                                     (Object context) -> rangeCache[0],
                                                     (Object context, String input) -> {
                                                         rangeCache[0] = input;
                                                         try {
                                                             ((TileEntityMultiBlock) context).setRange(Integer.parseInt(input));
                                                         }
                                                         catch (final Exception ignored)
                                                         {
                                                             //Thrown when something other then a number is inserted. disregard.
                                                         }
                                                     }
                                                   ), "3")))
                                  )

            );
        }
        return true;
    }

    @Override
    public void neighborChanged(final IBlockState state, final World worldIn, final BlockPos pos, final Block blockIn, final BlockPos fromPos)
    {
        if(worldIn.isRemote)
        {
            return;
        }
        final TileEntity te = worldIn.getTileEntity(pos);
        if(te instanceof TileEntityMultiBlock)
        {
            ((TileEntityMultiBlock) te).handleRedstone(worldIn.isBlockPowered(pos));
        }
    }

    @Override
    public boolean hasTileEntity(final IBlockState state)
    {
        return true;
    }

    @Override
    public TileEntity createTileEntity(final World world, final IBlockState state)
    {
        return new TileEntityMultiBlock();
    }

    /**
     * @deprecated (Remove this as soon as minecraft offers anything better).
     */
    @SuppressWarnings(DEPRECATION)
    @Override
    @Deprecated
    public boolean isFullBlock(final IBlockState state)
    {
        return false;
    }

    /**
     * Used to determine ambient occlusion and culling when rebuilding chunks
     * for render.
     *
     * @return true
     *
     * @deprecated
     */
    //todo: remove once we no longer need to support this
    @SuppressWarnings(DEPRECATION)
    @Override
    @Deprecated
    public boolean isOpaqueCube(final IBlockState state)
    {
        return false;
    }

    @Override
    public boolean doesSideBlockRendering(final IBlockState state, final IBlockAccess world, final BlockPos pos, final EnumFacing face)
    {
        return false;
    }
}
