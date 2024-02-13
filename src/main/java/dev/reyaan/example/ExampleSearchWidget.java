package dev.reyaan.example;

import com.google.gson.annotations.Expose;
import dev.reyaan.SimpleWidgetGui;
import dev.reyaan.SlotWidgetIcon;
import dev.reyaan.widgets.SlotWidget;
import eu.pb4.placeholders.api.PlaceholderContext;
import eu.pb4.sgui.api.ClickType;
import eu.pb4.sgui.api.elements.GuiElementBuilder;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.List;
import java.util.Map;

public class ExampleSearchWidget extends SlotWidget {
    @Expose
    public String defaultSearchValue = "";
    @Expose
    public SlotWidgetIcon searchGuiBlankIcon = SlotWidgetIcon.of(Items.GRAY_STAINED_GLASS_PANE, "");
    @Expose
    public SlotWidgetIcon searchGuiConfirmIcon = SlotWidgetIcon.of(Items.BAMBOO, "Confirm?");
    private String searchValue = null;

    public static final String searchValuePlaceholder = "searchValue";

    public ExampleSearchWidget(String id, int index, List<SlotWidgetIcon> states, ServerPlayerEntity player, SimpleWidgetGui gui) {
        super(id, index, states, player, gui);
    }

    @Override
    protected GuiElementBuilder createBuilder() {
        if (searchValue == null) searchValue = defaultSearchValue;
        if (state == -1) state = defaultState;

        SlotWidgetIcon icon = this.states.get(state);
        return icon.createBuilder(PlaceholderContext.of(player), Map.of(searchValuePlaceholder, Text.of(this.getSearchValue())));
    }

    public void callback(ClickType type) {
        if (!this.updateState()) {
            new ExampleSearchGui(searchGuiConfirmIcon, searchGuiBlankIcon,this, gui, player).open();
        } else {
            this.clearSearchValue();
        }
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String v) {
        this.searchValue = v;
        updateState();
        construct();
    }

    public void clearSearchValue() {
        this.setSearchValue("");
    }

    boolean updateState() {
        if (this.searchValue == null || this.searchValue.isEmpty()) {
            this.state = 0;
            return false;
        }

        this.state = 1;
        return true;
    }
}
