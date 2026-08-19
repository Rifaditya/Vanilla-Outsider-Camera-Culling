// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.vanillaoutsider.culling.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.CeilingHangingSignBlock;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.WallHangingSignBlock;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.entity.SignText;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.vanillaoutsider.culling.config.CameraCullingConfig;

public final class SignTextCullingHelper {

    private SignTextCullingHelper() {}

    /**
     * Determines whether the given text side (front or back) of a sign should be culled.
     *
     * @param sign The sign block entity
     * @param cameraPos The current camera position
     * @param isFront True if evaluating front text, false if evaluating back text
     * @return True if the text side is occluded/empty and should NOT be rendered
     */
    public static boolean shouldCullSignTextSide(SignBlockEntity sign, Vec3 cameraPos, boolean isFront) {
        if (!CameraCullingConfig.isEnabled() || !CameraCullingConfig.isCullSignText()) {
            return false;
        }
        if (sign == null || cameraPos == null) {
            return false;
        }

        SignText text = sign.getText(isFront ? net.minecraft.world.level.block.entity.SignTextSlot.FRONT : net.minecraft.world.level.block.entity.SignTextSlot.BACK);
        if (isTextEmpty(text)) {
            return true; // Fast-pass: skip rendering blank sign faces
        }

        BlockState state = sign.getBlockState();
        BlockPos pos = sign.getBlockPos();
        return shouldCullFace(state, pos, cameraPos, isFront);
    }

    /**
     * Calculates face normal dot product to determine if the face is pointing away from camera.
     */
    public static boolean shouldCullFace(BlockState state, BlockPos pos, Vec3 cameraPos, boolean isFront) {
        if (state == null || pos == null || cameraPos == null) {
            return false;
        }

        double signCenterX = pos.getX() + 0.5;
        double signCenterZ = pos.getZ() + 0.5;

        // Vector from sign center to camera
        double vx = cameraPos.x - signCenterX;
        double vz = cameraPos.z - signCenterZ;

        // Normal vector (Nx, Nz) of the FRONT face
        double nx;
        double nz;

        if (state.hasProperty(WallSignBlock.FACING)) {
            Direction facing = state.getValue(WallSignBlock.FACING);
            nx = facing.getStepX();
            nz = facing.getStepZ();
        } else if (state.hasProperty(StandingSignBlock.ROTATION)) {
            int rotation = state.getValue(StandingSignBlock.ROTATION);
            float angleDeg = (rotation * 360.0f) / 16.0f;
            float angleRad = angleDeg * Mth.DEG_TO_RAD;
            nx = -Mth.sin(angleRad);
            nz = Mth.cos(angleRad);
        } else if (state.hasProperty(WallHangingSignBlock.FACING)) {
            Direction facing = state.getValue(WallHangingSignBlock.FACING);
            nx = facing.getStepX();
            nz = facing.getStepZ();
        } else if (state.hasProperty(CeilingHangingSignBlock.ROTATION)) {
            int rotation = state.getValue(CeilingHangingSignBlock.ROTATION);
            float angleDeg = (rotation * 360.0f) / 16.0f;
            float angleRad = angleDeg * Mth.DEG_TO_RAD;
            nx = -Mth.sin(angleRad);
            nz = Mth.cos(angleRad);
        } else {
            return false; // Unknown sign state, render safely
        }

        // Dot product between Front Face Normal and Vector to Camera
        double dot = nx * vx + nz * vz;

        if (isFront) {
            // Front text is visible when camera is on front side (dot > -0.05 tolerance)
            return dot < -0.05;
        } else {
            // Back text is visible when camera is on back side (dot < 0.05 tolerance)
            return dot > 0.05;
        }
    }

    /**
     * Checks if all 4 lines of a sign text are empty.
     */
    public static boolean isTextEmpty(SignText text) {
        if (text == null) {
            return true;
        }
        for (Component msg : text.getMessages(false)) {
            if (msg != null && !msg.getString().trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }
}
