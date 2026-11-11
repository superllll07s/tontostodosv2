package com.nonidbridge;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;

import java.lang.reflect.Method;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Lee la identidad actual de Identity2 por reflexion (sin dependencia en compilacion).
 * Devuelve el EntityType del mob si el jugador esta morfeado, o null si no.
 */
public final class IdentityBridge {
    private static final ConcurrentHashMap<String, Optional<EntityType<?>>> CACHE = new ConcurrentHashMap<>();
    private static volatile boolean initialized;
    private static volatile Method getCurrentMorphId;

    private IdentityBridge() {}

    private static void init() {
        if (initialized) return;
        synchronized (IdentityBridge.class) {
            if (initialized) return;
            try {
                Class<?> api = Class.forName("net.Gabou.identity2.api.IdentityApi");
                getCurrentMorphId = api.getMethod("getCurrentMorphId", Entity.class);
            } catch (Throwable t) {
                NonIdentityBridge.LOGGER.error("No se pudo enlazar IdentityApi.getCurrentMorphId; el puente queda inactivo", t);
            }
            initialized = true;
        }
    }

    public static EntityType<?> morphType(Entity entity) {
        if (!(entity instanceof Player)) return null;
        init();
        Method m = getCurrentMorphId;
        if (m == null) return null;
        try {
            Object id = m.invoke(null, entity);
            if (id == null) return null;
            EntityType<?> type = CACHE.computeIfAbsent(id.toString(), s -> EntityType.byString(s)).orElse(null);
            return (type == null || type == EntityType.PLAYER) ? null : type;
        } catch (Throwable t) {
            return null;
        }
    }
}
