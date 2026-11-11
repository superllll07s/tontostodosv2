package com.nonidbridge.mixin;

import com.nonidbridge.IdentityBridge;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/** Un jugador morfeado ya no usa el rig humano "player" de NoN. */
@Pseudo
@Mixin(targets = "com.nonid.integration.NonMcaCompat", remap = false)
public abstract class McaCompatPlayerRigMixin {
    @Inject(method = "supportsPlayerRig", at = @At("HEAD"), cancellable = true, remap = false, require = 1)
    private static void nonidbridge$supportsPlayerRig(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if (IdentityBridge.morphType(entity) != null) {
            cir.setReturnValue(false);
        }
    }
}
