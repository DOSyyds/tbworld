package io.github.dosyyds.tbworld;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import io.github.dosyyds.tbworld.entity.ThreeBodyCitizen;
import io.github.dosyyds.tbworld.entity.tbModEntities;
import io.github.dosyyds.tbworld.item.*;
import io.github.dosyyds.tbworld.block.*;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(tbworld.MODID)
public class tbworld {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "tbworld";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public tbworld(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        tbModItems.register(modEventBus);
        tbModBlocks.register(modEventBus);
        createModeTags.register(modEventBus);

        tbModEntities.ENTITIES.register(modEventBus);
        modEventBus.addListener(this::registerMobAttr);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (tbworld) to
        // respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in
        // this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        // Register our mod's ModConfigSpec so that FML can create and load the config
        // file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

        LOGGER.info("{}{}", Config.MAGIC_NUMBER_INTRODUCTION.get(), Config.MAGIC_NUMBER.getAsInt());
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    private void registerMobAttr(EntityAttributeCreationEvent event) {
        event.put(tbModEntities.TBCITIZEN.get(), ThreeBodyCitizen.createAttributes().build());
    }
}
