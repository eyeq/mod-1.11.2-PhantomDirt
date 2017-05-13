package eyeq.phantomdirt;

import eyeq.phantomdirt.block.BlockPhantomDirt;
import eyeq.phantomdirt.block.BlockPhantomGlass;
import eyeq.util.client.model.UModelCreator;
import eyeq.util.client.model.UModelLoader;
import eyeq.util.client.renderer.ResourceLocationFactory;
import eyeq.util.client.renderer.block.statemap.StateMapper;
import eyeq.util.client.resource.ULanguageCreator;
import eyeq.util.client.resource.lang.LanguageResourceManager;
import eyeq.util.oredict.CategoryTypes;
import eyeq.util.oredict.UOreDictionary;
import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.init.Blocks;
import net.minecraft.item.*;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.io.File;
import java.util.ArrayList;

import static eyeq.phantomdirt.PhantomDirt.MOD_ID;

@Mod(modid = MOD_ID, version = "1.0", dependencies = "after:eyeq_util")
@Mod.EventBusSubscriber
public class PhantomDirt {
    public static final String MOD_ID = "eyeq_phantomdirt";

    @Mod.Instance(MOD_ID)
    public static PhantomDirt instance;

    private static final ResourceLocationFactory resource = new ResourceLocationFactory(MOD_ID);

    public static Block phantomDirt;
    public static Block phantomGlass;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        addRecipes();
        if(event.getSide().isServer()) {
            return;
        }
        renderBlockModels();
        renderItemModels();
        createFiles();
    }

    @SubscribeEvent
    protected static void registerBlocks(RegistryEvent.Register<Block> event) {
        phantomDirt = new BlockPhantomDirt().setUnlocalizedName("phantomDirt");
        phantomGlass = new BlockPhantomGlass().setUnlocalizedName("phantomGlass");

        GameRegistry.register(phantomDirt, resource.createResourceLocation("phantom_dirt"));
        GameRegistry.register(phantomGlass, resource.createResourceLocation("phantom_glass"));
    }

    @SubscribeEvent
    protected static void registerItems(RegistryEvent.Register<Item> event) {
        GameRegistry.register(new ItemBlock(phantomDirt), phantomDirt.getRegistryName());
        GameRegistry.register(new ItemColored(phantomGlass, true), phantomGlass.getRegistryName());
    }

    public static void addRecipes() {
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(phantomDirt, 4), "XYX", "YZY", "XYX",
                'X', UOreDictionary.OREDICT_DIRT, 'Y', Blocks.SOUL_SAND, 'Z', CategoryTypes.PREFIX_DYE.getDictionaryName("blue")));
        for(EnumDyeColor color : EnumDyeColor.values()) {
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(phantomGlass, 1, color.getMetadata()),
                    new ItemStack(phantomGlass, 1, OreDictionary.WILDCARD_VALUE), CategoryTypes.PREFIX_DYE.getDictionaryName(color.getUnlocalizedName())));
        }

        GameRegistry.addSmelting(phantomDirt, new ItemStack(phantomGlass, 1, EnumDyeColor.BLUE.getMetadata()), 0.35F);
    }

    @SideOnly(Side.CLIENT)
    public static void renderBlockModels() {
        ModelLoader.setCustomStateMapper(phantomGlass, new StateMapper(resource, null, BlockColored.COLOR, "phantom_glass_", new ArrayList<>()));
    }

	@SideOnly(Side.CLIENT)
    public static void renderItemModels() {
        UModelLoader.setCustomModelResourceLocation(phantomDirt);
        Item itemGlass = Item.getItemFromBlock(phantomGlass);
        for(EnumDyeColor color : EnumDyeColor.values()) {
            String post = BlockColored.COLOR.getName(color);
            ModelLoader.setCustomModelResourceLocation(itemGlass, color.getMetadata(), resource.createModelResourceLocation("phantom_glass_" + post));
        }
    }
	
    public static void createFiles() {
    	File project = new File("../1.11.2-PhantomDirt");
    	
        LanguageResourceManager language = new LanguageResourceManager();

        language.register(LanguageResourceManager.EN_US, phantomDirt, "Phantom Dirt");
        language.register(LanguageResourceManager.JA_JP, phantomDirt, "お化け土");
        language.register(LanguageResourceManager.EN_US, phantomGlass, "Phantom Glass");
        language.register(LanguageResourceManager.JA_JP, phantomGlass, "お化けガラス");

        ULanguageCreator.createLanguage(project, MOD_ID, language);

        UModelCreator.createBlockstateJson(project, phantomDirt);
        for(EnumDyeColor color : EnumDyeColor.values()) {
            String post = BlockColored.COLOR.getName(color);
            UModelCreator.createBlockstateJson(project, resource.createResourceLocation("phantom_glass_" + post));
        }
    }
}
