package dev.reyaan.smoothgui.widgets;

import com.google.gson.annotations.Expose;
import dev.reyaan.smoothgui.SimpleWidgetGui;
import net.minecraft.server.network.ServerPlayerEntity;

public class BaseSlotWidget {
    @Expose
    public String id;
    @Expose
    public int index;

    protected ServerPlayerEntity player;
    protected SimpleWidgetGui gui;

    public BaseSlotWidget(String id, int index, ServerPlayerEntity player, SimpleWidgetGui gui) {
        this.id = id;
        this.index = index;
        this.player = player;
        this.gui = gui;
    }

    public void construct() {
    }

    public void updateData(ServerPlayerEntity player, SimpleWidgetGui gui) {
        this.player = player;
        this.gui = gui;
    }
}
