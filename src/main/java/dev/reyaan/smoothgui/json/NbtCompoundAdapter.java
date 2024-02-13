package dev.reyaan.smoothgui.json;

import com.google.gson.*;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.StringNbtReader;
import net.minecraft.nbt.visitor.StringNbtWriter;

import java.lang.reflect.Type;

public class NbtCompoundAdapter implements JsonSerializer<NbtCompound>, JsonDeserializer<NbtCompound> {
    @Override
    public JsonElement serialize(NbtCompound nbtCompound, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(new StringNbtWriter().apply(nbtCompound));
    }

    @Override
    public NbtCompound deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        try {
            return new StringNbtReader(
                    new StringReader(json.getAsString())
            ).parseCompound();
        } catch (CommandSyntaxException e) {
            throw new RuntimeException(e);
        }
    }


}
