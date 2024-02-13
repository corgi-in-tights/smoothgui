package dev.reyaan.smoothgui.example;

import dev.reyaan.smoothgui.SimpleWidgetGui;
import dev.reyaan.smoothgui.SlotWidgetIcon;
import eu.pb4.sgui.api.gui.AnvilInputGui;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class ExampleSearchGui extends AnvilInputGui {
    private final ExampleSearchWidget widget;
    private final SimpleWidgetGui gui;
    private final SlotWidgetIcon confirmIcon;
    private final SlotWidgetIcon blankIcon;


    public ExampleSearchGui(SlotWidgetIcon confirmIcon, SlotWidgetIcon blankIcon, ExampleSearchWidget widget, SimpleWidgetGui gui, ServerPlayerEntity player) {
        super(player, false);
        this.widget = widget;
        this.gui = gui;
        this.confirmIcon = confirmIcon;
        this.blankIcon = blankIcon;
        setInput();
    }

    @Override
    public void setDefaultInputValue(String input) {
    }

    public void setInput() {
        if (blankIcon != null) {
            var initial = blankIcon.createBuilder(player)
                    .setCallback((callback) -> {
                        this.close();
                        widget.clearSearchValue();
                        gui.open();
                    })
                    .setName(Text.of(""));
            setSlot(0, initial);
        }

        if (confirmIcon != null) {
            var confirm = confirmIcon.createBuilder(player)
                    .setCallback((callback) -> {
                        this.close();
                        widget.setSearchValue(this.getInput());
                        gui.open();
                    });

            setSlot(1, confirm);
        }
    }

}
