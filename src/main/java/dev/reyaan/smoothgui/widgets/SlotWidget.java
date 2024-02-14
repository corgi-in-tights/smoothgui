package dev.reyaan.smoothgui.widgets;

import com.google.gson.annotations.Expose;
import dev.reyaan.smoothgui.SimpleWidgetGui;
import dev.reyaan.smoothgui.SlotWidgetIcon;
import eu.pb4.sgui.api.ClickType;
import eu.pb4.sgui.api.elements.GuiElementBuilder;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.List;

public class SlotWidget extends BaseSlotWidget {
    @Expose
    public int defaultState = 0;
    @Expose
    protected List<SlotWidgetIcon> states;
    @Expose
    public String id;
    @Expose
    public int index;

    protected ServerPlayerEntity player;
    protected SimpleWidgetGui gui;
    protected int state = -1;

    public SlotWidget(String id, int index, List<SlotWidgetIcon> states, ServerPlayerEntity player, SimpleWidgetGui gui) {
        super(id, index, player, gui);
        this.states = states;
    }

    @Override
    public void construct() {
        GuiElementBuilder builder = createBuilder();
        builder.setCallback(this::callback);
        gui.setSlot(index, builder);
    }

    public void callback(ClickType type) {
    }

    protected GuiElementBuilder createBuilder() {
        if (state == -1) state = defaultState;
        SlotWidgetIcon icon = this.states.get(state);
        return icon.createBuilder(player);
    }

    public int getCurrentState() {
        if (state == -1) state = defaultState;
        return this.state;
    }
}
