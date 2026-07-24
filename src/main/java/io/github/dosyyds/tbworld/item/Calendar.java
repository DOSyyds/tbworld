package io.github.dosyyds.tbworld.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

// 1. 继承 Item 类
public class Calendar extends Item {
    public static final ResourceKey<Level> tbworlddk = ResourceKey.create(Registries.DIMENSION,
            ResourceLocation.tryParse("tbworld:tbworld"));

    // 2. 构造函数，接收 Properties 参数并传给父类
    public Calendar(Properties properties) {
        super(properties);
    }

    // 3. 重写 use 方法，实现右键功能
    @Override
    public InteractionResultHolder use(Level level, Player player, InteractionHand usedHand) {

        if (!level.isClientSide) {
            ResourceKey<Level> currentDimension = level.dimension();
            // 与你的自定义维度 Key 进行比较
            if (currentDimension != tbworlddk) {
                player.sendSystemMessage(Component.translatable("cs.tbworld.alwaysgood"));
            } else {
                player.sendSystemMessage(Component.translatable("cs.tbworld.wtfisthis"));
            }
        }
        // 设置物品使用冷却（40 tick = 2秒），防止刷屏
        player.getCooldowns().addCooldown(this, 40);

        return InteractionResultHolder.success(player.getItemInHand(usedHand));

    }
}