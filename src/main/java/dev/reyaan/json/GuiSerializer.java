package dev.reyaan.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.reyaan.widgets.SlotWidget;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;

public class GuiSerializer {
    public static Gson PRIMARY_GSON = new GsonBuilder()
            .setPrettyPrinting()
            .disableHtmlEscaping()
            .excludeFieldsWithoutExposeAnnotation()
            .serializeNulls()
            .serializeSpecialFloatingPointValues()
            .registerTypeAdapter(SlotWidget.class, new SlotWidgetAdapter())
            .create();

    public static Gson DATA_GSON = new GsonBuilder()
            .setPrettyPrinting()
            .disableHtmlEscaping()
            .excludeFieldsWithoutExposeAnnotation()
            .serializeNulls()
            .serializeSpecialFloatingPointValues()
            .registerTypeAdapter(NbtCompound.class, new NbtCompoundAdapter())
            .registerTypeAdapter(Item.class, new ItemAdapter())
            .create();
}
