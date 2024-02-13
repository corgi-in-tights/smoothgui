package dev.reyaan.smoothgui;

import dev.reyaan.smoothgui.api.SlotWidgetAPI;
import dev.reyaan.smoothgui.example.ExampleGui;
import dev.reyaan.smoothgui.example.ExampleSearchWidget;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SmoothGui implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("smoothgui");

	@Override
	public void onInitialize() {
		LOGGER.info("Hello Fabric world!");
		SlotWidgetAPI.registerDefaults();
		SlotWidgetAPI.registerType("example:search", ExampleSearchWidget.class);
	}
}