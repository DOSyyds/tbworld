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
        // 原：texOffs(0,17).addBox(-2, -24, -2, 4, 4, 4) 全局位置 = (-2, 0, -2)
        root.addOrReplaceChild("head",
                CubeListBuilder.create()
                        .texOffs(0, 17)
                        .addBox(-2.0F, -2.0F, -2.0F, 4, 4, 4),
                PartPose.offsetAndRotation(0.0F, 20.0F, 0.0F, 0.0F, (float) Math.toRadians(-90), 0.0F));

        // ========== 身体 ==========
        // 原：texOffs(0,0).addBox(-4, -21, -4, 5, 9, 8) 全局最小角 = (-4, 3, -4)
        root.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(0, 0, 0, 5, 9, 8),
                PartPose.offset(-4.0F, 3.0F, -4.0F));

        // ========== 左腿 ==========
        // 原：texOffs(0,25).addBox(-2, -12, 2, 1, 11, 1) 全局最小角 = (-2, 12, 2)
        root.addOrReplaceChild("left_leg",
                CubeListBuilder.create()
                        .texOffs(0, 25)
                        .addBox(0, 0, 0, 1, 11, 1),
                PartPose.offset(-2.0F, 12.0F, 2.0F));

        // ========== 右腿 ==========
        // 原：texOffs(4,25).addBox(-2, -12, -3, 1, 11, 1) 全局最小角 = (-2, 12, -3)
        root.addOrReplaceChild("right_leg",
                CubeListBuilder.create()
                        .texOffs(4, 25)
                        .addBox(0, 0, 0, 1, 11, 1),
                PartPose.offset(-2.0F, 12.0F, -3.0F));

        // ========== 左脚 ==========
        // 原：texOffs(16,17).addBox(-2, -1, -4, 4, 1, 3) 全局最小角 = (-2, 23, -4)
        root.addOrReplaceChild("left_foot",
                CubeListBuilder.create()
                        .texOffs(16, 17)
                        .addBox(0, 0, 0, 4, 1, 3),
                PartPose.offset(-2.0F, 23.0F, -4.0F));

        // ========== 右脚 ==========
        // 原：texOffs(16,21).addBox(-2, -1, 1, 4, 1, 3) 全局最小角 = (-2, 23, 1)
        root.addOrReplaceChild("right_foot",
                CubeListBuilder.create()
                        .texOffs(16, 21)
                        .addBox(0, 0, 0, 4, 1, 3),
                PartPose.offset(-2.0F, 23.0F, 1.0F));

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