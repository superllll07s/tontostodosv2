package com.nonidbridge.mixin;

import com.nonidbridge.IdentityBridge;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.lang.reflect.Method;

/**
 * NoN guarda el id de tipo en el render state (afw$setEntityTypeId) usando getType().
 * Lo sobrescribimos despues (prioridad alta = se ejecuta despues) con el id del mob.
 * EXPERIMENTAL: si el nombre del metodo no coincide en tus mappings, simplemente no se aplica.
 */
@Mixin(value = EntityRenderDispatcher.class, priority = 2000)
public abstract class RenderDispatcherMixin {
    private static Method nonidbridge$setter;
    private static boolean nonidbridge$searched;

    @Inject(method = "extractRenderState", at = @At("RETURN"), require = 0)
    private void nonidbridge$overrideTypeId(Entity entity, float partialTick, CallbackInfoReturnable<EntityRenderState> cir) {
        EntityType<?> morph = IdentityBridge.morphType(entity);
        EntityRenderState state = cir.getReturnValue();
        if (morph == null || state == null) return;
        try {
            if (!nonidbridge$searched) {
                nonidbridge$searched = true;
                for (Method m : state.getClass().getMethods()) {
                    if (m.getName().equals("afw$setEntityTypeId") && m.getParameterCount() == 1) {
                        nonidbridge$setter = m;
                    }
                }
            }
            if (nonidbridge$setter != null) {
                nonidbridge$setter.invoke(state, EntityType.getKey(morph));
            }
        } catch (Throwable ignored) {
        }
    }
}
