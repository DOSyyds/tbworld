package io.github.dosyyds.tbworld.item;

import io.github.dosyyds.tbworld.tbworld;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class createModeTags {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister
            .create(Registries.CREATIVE_MODE_TAB, tbworld.MODID);

    public static final Supplier<CreativeModeTab> TB_TAB = CREATIVE_MODE_TABS.register("gui.tbworld.cttab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(tbModItems.CALENDAR.get()))
                    .title(Component.translatable("gui.tbworld.cttab"))
                    .displayItems((parameters, output) -> {
                        output.accept(tbModItems.CALENDAR);
                    }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}