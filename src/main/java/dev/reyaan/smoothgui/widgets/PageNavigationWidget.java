package dev.reyaan.smoothgui.widgets;

import dev.reyaan.smoothgui.SimpleWidgetGui;
import dev.reyaan.smoothgui.SlotWidgetIcon;
import eu.pb4.sgui.api.ClickType;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.List;

public class PageNavigationWidget extends SlotWidget {
    public int step;

    public PageNavigationWidget(String id, int index, List<SlotWidgetIcon> states, ServerPlayerEntity player, SimpleWidgetGui gui, int step) {
        super(id, index, states, player, gui);
        this.step = step;
    }

    public void callback(ClickType type) {
        gui.stepPage(step);
        gui.markDirty();
    }
}
