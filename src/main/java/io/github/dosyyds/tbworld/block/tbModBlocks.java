package io.github.dosyyds.tbworld.block;

import java.util.function.Supplier;

import io.github.dosyyds.tbworld.tbworld;
import io.github.dosyyds.tbworld.item.tbModItems;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class tbModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(tbworld.MODID); // 替换为你的模组ID
    
    // 1. 注册 BlockItem 的方法
    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        tbModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    // 2. 注册方块并自动注册其 BlockItem 的方法
    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> blockSupplier) {
        DeferredBlock<T> block = BLOCKS.register(name, blockSupplier);
        registerBlockItem(name, block);
        return block;
    }

    public static final DeferredBlock<Block> TBWGROUND_BLOCK = registerBlock(
            "tbwground", // 方块的注册名，将作为 ID 的一部分
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(3.0F, 3.0F) // 设置硬度和爆炸抗性
                    .sound(SoundType.WART_BLOCK) // 设置音效
                    .mapColor(MapColor.COLOR_CYAN) // 设置地图颜色
            ));
            
    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}