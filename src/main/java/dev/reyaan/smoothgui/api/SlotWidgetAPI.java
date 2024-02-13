package dev.reyaan.smoothgui.api;

import dev.reyaan.smoothgui.json.SlotWidgetAdapter;
import dev.reyaan.smoothgui.widgets.IteratingSlotWidget;
import dev.reyaan.smoothgui.widgets.SlotWidget;

import java.util.HashMap;
import java.util.Map;

public class SlotWidgetAPI {
    public static final String typeElementName = "type";
    private static boolean registeredDefaults = false;
    public static final Map<String, Class<? extends SlotWidget>> widgetTypes = new HashMap<>();

    public static void registerDefaults() {
        if (!registeredDefaults) {
            registerType("default", SlotWidget.class);
            registerType("iterating", IteratingSlotWidget.class);
            registeredDefaults = true;
        }
    }

    public static void registerType(String id, Class<? extends SlotWidget> widgetClass) {
        widgetTypes.put(id, widgetClass);
    }


    public static Class<? extends SlotWidget> getTypeById(String id) {
        return widgetTypes.get(id);
    }

    public static String getIdByType(Class<? extends SlotWidget> type) {
        for (var entry : widgetTypes.entrySet()) {
            if (entry.getValue() == type) {
                return entry.getKey();
            }
        }
        return null;
    }
}
