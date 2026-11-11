package com.nonidbridge.mixin;

import com.nonidbridge.IdentityBridge;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Estos metodos de NoN llaman a entity.getType() directamente.
 * Se redirige a: tipo del mob si esta morfeado, o el tipo normal.
 * (El target usa nombres intermediary porque apunta a bytecode de otro mod.)
 */
@Pseudo
@Mixin(targets = "com.nonid.internal.animation.data.AnimationDefinitions", remap = false)
public abstract class AnimationDefinitionsMixin {
    @Redirect(
        method = {
            "hasEnabledGenericAnimationForEntity",
            "hasEnabledGenericMobPairAnimationForEntity",
            "areEnabledGenericMobPairActorsCompatible",
            "fallbackActorKey"
        },
        at = @At(value = "INVOKE",
                 target = "Lnet/minecraft/class_1297;method_5864()Lnet/minecraft/class_1299;",
                 remap = false),
        remap = false, require = 0)
    private static EntityType<?> nonidbridge$getType(Entity entity) {
        EntityType<?> morph = IdentityBridge.morphType(entity);
        return morph != null ? morph : entity.getType();
    }
}
