package io.github.dosyyds.tbworld.item;

import io.github.dosyyds.tbworld.tbworld;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class tbModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(tbworld.MODID);

    public static final DeferredItem<Item> CALENDAR = ITEMS.registerItem("calendar",
            // 这里使用 Lambda 表达式调用我们自定义类的构造方法
            (properties) -> new Calendar(properties),
            // 在这里配置物品的基础属性，例如最大堆叠数
            new Item.Properties().stacksTo(1));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}
