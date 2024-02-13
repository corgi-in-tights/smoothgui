package dev.reyaan.smoothgui.json;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import dev.reyaan.smoothgui.api.SlotWidgetAPI;
import dev.reyaan.smoothgui.widgets.SlotWidget;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.TreeMap;

public class SlotWidgetAdapter implements JsonSerializer<SlotWidget>, JsonDeserializer<SlotWidget> {
    @Override
    public SlotWidget deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject widgetObject = json.getAsJsonObject();
        JsonElement widgetTypeElement = widgetObject.get(SlotWidgetAPI.typeElementName);

        Class<? extends SlotWidget> widgetType = SlotWidgetAPI.getTypeById(widgetTypeElement.getAsString());

        return GuiSerializer.DATA_GSON.fromJson(widgetObject, widgetType);
    }

    @Override
    public JsonElement serialize(SlotWidget slotWidget, Type type, JsonSerializationContext jsonSerializationContext) {
        String id = SlotWidgetAPI.getIdByType(slotWidget.getClass());

        JsonObject json = GuiSerializer.DATA_GSON.toJsonTree(slotWidget).getAsJsonObject();
        json.addProperty(SlotWidgetAPI.typeElementName, id);
        return json;
    }
}