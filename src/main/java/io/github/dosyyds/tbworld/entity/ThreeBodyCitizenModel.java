package io.github.dosyyds.tbworld.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class ThreeBodyCitizenModel<T extends Entity> extends EntityModel<T> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath("tbworld", "three_body_citizen"), "main");

    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart leftLeg;
    private final ModelPart rightLeg;
    private final ModelPart leftFoot;
    private final ModelPart rightFoot;

    public ThreeBodyCitizenModel(ModelPart root) {
        // 获取各个独立部件（名称与 createBodyLayer 中一致）
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.leftLeg = root.getChild("left_leg");
        this.rightLeg = root.getChild("right_leg");
        this.leftFoot = root.getChild("left_foot");
        this.rightFoot = root.getChild("right_foot");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        // ========== 头部 ==========
        // 原始：texOffs(0,17), addBox(-3, -24, -5, 4,4,4) 相对于 bb_main 偏移 (0,24,0)
        // 全局最小角 = (-3, 0, -5)，尺寸 4x4x4
        // 中心点 = (-3+2, 0+2, -5+2) = (-1, 2, -3)
        root.addOrReplaceChild("head",
                CubeListBuilder.create()
                        .texOffs(0, 17)
                        .addBox(-2.0F, -2.0F, -2.0F, 4, 4, 4), // 中心对称
                PartPose.offset(-1.0F, 2.0F, -3.0F) // 中心位置
        );

        // ========== 身体 ==========
        // 原始：texOffs(0,3), addBox(-5, -21, -4, 8,9,5) 全局最小角 = (-5, 3, -4)
        // 中心点 = (-5+4, 3+4.5, -4+2.5) = (-1, 7.5, -1.5)
        root.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(0, 3)
                        .addBox(-4.0F, -4.5F, -2.5F, 8, 9, 5),
                PartPose.offset(-1.0F, 7.5F, -1.5F));

        // ========== 左腿 ==========
        // 原始：texOffs(4,25), addBox(-4, -12, -2, 1,11,1) 全局最小角 = (-4, 12, -2)
        // 为了旋转方便，轴心点放在腿部顶端（Y=12），而不是中心
        // 我们将其原点设在 (x,z) 中心，Y 在顶端，这样腿部摆动以大腿根部为轴
        // 尺寸 1x11x1，中心在 (-4+0.5, 12+5.5, -2+0.5) = (-3.5, 17.5, -1.5)，但为了顶端旋转，我们设原点在
        // (-3.5, 12, -1.5) 并让 addBox 从 0 向下延伸
        // 更简单：原点设在最小角，然后 addBox 从 0,0,0 开始，这样旋转轴在角落，但用户未要求腿的旋转，主要解决头部问题，所以腿部可以保持简单。
        // 为了与头部一致，我们将所有部件原点设为中心（除脚外），这样容易统一。
        // 但腿部旋转轴通常在顶端，但我们现在先解决头部，其他可后调。
        // 为保留原始数据，我们采用最小角+从0开始，或者中心+对称。这里为了代码统一，我全部用中心。
        // 左腿中心 = (-4+0.5, 12+5.5, -2+0.5) = (-3.5, 17.5, -1.5)
        root.addOrReplaceChild("left_leg",
                CubeListBuilder.create()
                        .texOffs(4, 25)
                        .addBox(-0.5F, -5.5F, -0.5F, 1, 11, 1),
                PartPose.offset(-3.5F, 17.5F, -1.5F));

        // ========== 右腿 ==========
        // 原始：texOffs(0,25), addBox(1, -12, -2, 1,11,1) 全局最小角 = (1, 12, -2)
        // 中心 = (1.5, 17.5, -1.5)
        root.addOrReplaceChild("right_leg",
                CubeListBuilder.create()
                        .texOffs(0, 25)
                        .addBox(-0.5F, -5.5F, -0.5F, 1, 11, 1),
                PartPose.offset(1.5F, 17.5F, -1.5F));

        // ========== 左脚 ==========
        // 原始：texOffs(16,17), addBox(-5, -1, -4, 3,1,4) 全局最小角 = (-5, 23, -4)
        // 中心 = (-3.5, 23.5, -2)
        root.addOrReplaceChild("left_foot",
                CubeListBuilder.create()
                        .texOffs(16, 17)
                        .addBox(-1.5F, -0.5F, -2.0F, 3, 1, 4),
                PartPose.offset(-3.5F, 23.5F, -2.0F));

        // ========== 右脚 ==========
        // 原始：texOffs(16,22), addBox(0, -1, -4, 3,1,4) 全局最小角 = (0, 23, -4)
        // 中心 = (1.5, 23.5, -2)
        root.addOrReplaceChild("right_foot",
                CubeListBuilder.create()
                        .texOffs(16, 22)
                        .addBox(-1.5F, -0.5F, -2.0F, 3, 1, 4),
                PartPose.offset(1.5F, 23.5F, -2.0F));

        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
            float headPitch) {
        float speed = 1.0f;
        float amount = limbSwingAmount * 0.8f;
        this.leftLeg.xRot = (float) Math.sin(limbSwing * speed) * amount;
        this.rightLeg.xRot = (float) Math.sin(limbSwing * speed + Math.PI) * amount;
        // 脚可以跟随腿旋转，也可以独立，这里让脚跟随腿（同方向旋转）
        this.leftFoot.xRot = this.leftLeg.xRot;
        this.rightFoot.xRot = this.rightLeg.xRot;
        // 头部可以看向玩家
        this.head.xRot = headPitch * (float) (Math.PI / 180F);
        this.head.yRot = netHeadYaw * (float) (Math.PI / 180F);
    }

    @Override
    public void renderToBuffer(PoseStack arg0, VertexConsumer arg1, int arg2, int arg3, int arg4) {
        this.head.render(arg0, arg1, arg2, arg3, arg4);
        this.body.render(arg0, arg1, arg2, arg3, arg4);
        this.leftFoot.render(arg0, arg1, arg2, arg3, arg4);
        this.rightFoot.render(arg0, arg1, arg2, arg3, arg4);
        this.leftLeg.render(arg0, arg1, arg2, arg3, arg4);
        this.rightLeg.render(arg0, arg1, arg2, arg3, arg4);
    }
}