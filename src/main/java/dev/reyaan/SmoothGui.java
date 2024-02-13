package dev.reyaan;

import dev.reyaan.api.SlotWidgetAPI;
import dev.reyaan.example.ExampleSearchWidget;
import net.fabricmc.api.ModInitializer;

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