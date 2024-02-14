package dev.reyaan.smoothgui.json;

import com.google.gson.*;
import dev.reyaan.smoothgui.api.SlotWidgetAPI;
import dev.reyaan.smoothgui.widgets.BaseSlotWidget;
import dev.reyaan.smoothgui.widgets.SlotWidget;

import java.lang.reflect.Type;

public class BaseSlotWidgetAdapter implements JsonSerializer<BaseSlotWidget>, JsonDeserializer<BaseSlotWidget> {
    @Override
    public BaseSlotWidget deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject widgetObject = json.getAsJsonObject();
        JsonElement widgetTypeElement = widgetObject.get(SlotWidgetAPI.typeElementName);

        Class<? extends BaseSlotWidget> widgetType = SlotWidgetAPI.getTypeById(widgetTypeElement.getAsString());

        return GuiSerializer.DATA_GSON.fromJson(widgetObject, widgetType);
    }

    @Override
    public JsonElement serialize(BaseSlotWidget slotWidget, Type type, JsonSerializationContext jsonSerializationContext) {
        String id = SlotWidgetAPI.getIdByType(slotWidget.getClass());

        JsonObject json = GuiSerializer.DATA_GSON.toJsonTree(slotWidget).getAsJsonObject();
        json.addProperty(SlotWidgetAPI.typeElementName, id);
        return json;
    }
}
