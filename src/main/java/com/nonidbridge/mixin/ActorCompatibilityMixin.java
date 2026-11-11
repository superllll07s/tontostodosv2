package com.nonidbridge.mixin;

import com.nonidbridge.IdentityBridge;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Set;

/** NoN decide el/los "tipos" del actor aqui. Si esta morfeado -> solo el id del mob (sin rol "player"). */
@Pseudo
@Mixin(targets = "com.nonid.internal.animation.util.AnimationActorCompatibility", remap = false)
public abstract class ActorCompatibilityMixin {
    @Inject(method = "matchingTypes", at = @At("HEAD"), cancellable = true, remap = false, require = 1)
    private static void nonidbridge$matchingTypes(Entity entity, CallbackInfoReturnable<Set<Object>> cir) {
        EntityType<?> morph = IdentityBridge.morphType(entity);
        if (morph != null) {
            cir.setReturnValue(Set.of((Object) EntityType.getKey(morph)));
        }
    }
}
