package dev.reyaan.smoothgui;

import com.google.gson.reflect.TypeToken;
import dev.reyaan.smoothgui.json.GuiSerializer;
import dev.reyaan.smoothgui.utils.WidgetDataUtils;
import dev.reyaan.smoothgui.widgets.SlotWidget;
import eu.pb4.sgui.api.gui.SimpleGui;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.server.network.ServerPlayerEntity;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.util.*;

public class SimpleWidgetGui extends SimpleGui {
    protected int page = 0;
    int maxPages;

    public boolean enableSerialization;
    /**
     * Constructs a new simple container gui for the supplied player.
     *
     * @param type                  the screen handler that the client should display
     * @param player                the player to server this gui to
     * @param manipulatePlayerSlots if <code>true</code> the players inventory
     *                              will be treated as slots of this gui
     */
    public SimpleWidgetGui(ScreenHandlerType<?> type, ServerPlayerEntity player, boolean manipulatePlayerSlots, boolean enableSerialization) {
        super(type, player, manipulatePlayerSlots);
        this.enableSerialization = enableSerialization;
    }

    /**
     * Fetch and construct all the annotated widgets
     */
    public void initialize() {
        this.maxPages = findInitialMaxPages();

        // setup
        final File file = getDataFilePath().toFile();
        final List<SlotWidget> serializedObjects = new ArrayList<>();
        final ArrayList<SlotWidget> toDeserialize = new ArrayList<>();

        if (enableSerialization) {
            // @TODO cache objects?? if its static it cant be passed...

            // if process has already been cached
            if (this.getCachedWidgets() != null) {
                serializedObjects.addAll(this.getCachedWidgets());
            } else {
                TypeToken<List<SlotWidget>> type = new TypeToken<>() {};

                // deserialize default file (or leave serializedObjects empty)
                if (file.exists()) {
                    try {
                        List<SlotWidget> z = GuiSerializer.PRIMARY_GSON.fromJson(new FileReader(file), type);
                        serializedObjects.addAll(z);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }

        Field[] fields = FieldUtils.getFieldsWithAnnotation(this.getClass(), GuiWidget.class);
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object object = field.get(this);
                if (object instanceof SlotWidget widget) {

                    // the actual serialization core
                    if (enableSerialization && field.getAnnotation(GuiWidget.class).serialize()) {
                        // there is a valid default file
                        if (!serializedObjects.isEmpty()) {
                            // find the first item with a matching id (if exists)
                            serializedObjects.stream()
                                    .filter(sw -> Objects.equals(sw.id, widget.id))
                                    .findFirst()
                                    .ifPresent(match -> {
                                        serializedObjects.remove(match);
                                        // copy all exposed fields over to default widget
                                        WidgetDataUtils.copyExposedFields(match, widget);
                                    });
                        }
                        // OR append to toDeserialize array
                        else {
                            toDeserialize.add(widget);
                        }
                    }

                    // update and construct user-added widgets (ids do not match)
                    serializedObjects.forEach(sw -> {
                        sw.updateData(player, this);
                        sw.construct();
                    });

                    // construct modified or default widget
                    widget.construct();
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        if (!toDeserialize.isEmpty()) {
            try (FileWriter writer = new FileWriter(file)) {
                Type type = new TypeToken<ArrayList<SlotWidget>>() {}.getType();
                String s = GuiSerializer.PRIMARY_GSON.toJson(toDeserialize, type);
                writer.write(s);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    /**
     * @return Path to the JSON file that stores the de/serialized widgets, if any
     */
    public Path getDataFilePath() {
        return Path.of("default.json");
    }

    /**
     * @return A list of cached widgets, use SimpleWidgetGui#setCachedWidgets to find out
     */
    public List<SlotWidget> getCachedWidgets() {
        return null;
    }

    /**
     * @param cachedWidgets A list of cached/possibly serialized widgets
     */
    public void setCachedWidgets(List<SlotWidget> cachedWidgets) {
    }

    /**
     * Called whenever there is an unspecified update in the GUI
     */
    public void markDirty() {
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void stepPage(int step) {
        this.setPage(this.getPage() + step);
    }

    public int findInitialMaxPages() {
        return 1;
    }
}
