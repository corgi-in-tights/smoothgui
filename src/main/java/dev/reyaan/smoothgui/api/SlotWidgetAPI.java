package dev.reyaan.smoothgui.api;

import dev.reyaan.smoothgui.widgets.BaseSlotWidget;
import dev.reyaan.smoothgui.widgets.IteratingSlotWidget;
import dev.reyaan.smoothgui.widgets.PageNavigationWidget;
import dev.reyaan.smoothgui.widgets.SlotWidget;

import java.util.HashMap;
import java.util.Map;

public class SlotWidgetAPI {
    public static final String typeElementName = "type";
    private static boolean registeredDefaults = false;
    public static final Map<String, Class<? extends BaseSlotWidget>> widgetTypes = new HashMap<>();

    public static void registerDefaults() {
        if (!registeredDefaults) {
            registerType("default", SlotWidget.class);
            registerType("iterating", IteratingSlotWidget.class);
            registerType("page_navigation", PageNavigationWidget.class);
            registeredDefaults = true;
        }
    }

    public static void registerType(String id, Class<? extends BaseSlotWidget> widgetClass) {
        widgetTypes.put(id, widgetClass);
    }


    public static Class<? extends BaseSlotWidget> getTypeById(String id) {
        return widgetTypes.get(id);
    }

    public static String getIdByType(Class<? extends BaseSlotWidget> type) {
        for (var entry : widgetTypes.entrySet()) {
            if (entry.getValue() == type) {
                return entry.getKey();
            }
        }
        return null;
    }
}
