package dev.reyaan.smoothgui.widgets;

import dev.reyaan.smoothgui.SimpleWidgetGui;
import dev.reyaan.smoothgui.SlotWidgetIcon;
import eu.pb4.sgui.api.ClickType;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.List;

public class IteratingSlotWidget extends SlotWidget {
    public IteratingSlotWidget(String id, int index, List<SlotWidgetIcon> states, ServerPlayerEntity player, SimpleWidgetGui gui) {
        super(id, index, states, player, gui);
    }

    public void callback(ClickType type) {
        this.state += 1;
        if (this.state >= this.states.size()) this.state = 0;
        this.construct();
    }
}
