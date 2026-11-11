package com.nonidbridge;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NonIdentityBridge implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("nonidentitybridge");

    @Override
    public void onInitialize() {
        LOGGER.info("NoN Identity Bridge cargado: los jugadores/IAs morfeados con Identity2 se tratan como su mob en Needs of Nature.");
    }
}
