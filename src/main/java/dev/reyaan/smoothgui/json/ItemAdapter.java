package dev.reyaan.smoothgui.json;

import com.google.gson.*;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.lang.reflect.Type;

public class ItemAdapter implements JsonSerializer<Item>, JsonDeserializer<Item> {
    @Override
    public JsonElement serialize(Item item, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(Registries.ITEM.getId(item).toString());
    }

    @Override
    public Item deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        return Registries.ITEM.get(new Identifier(json.getAsString()));
    }
}
