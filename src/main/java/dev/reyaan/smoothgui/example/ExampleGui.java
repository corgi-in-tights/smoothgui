package dev.reyaan.smoothgui.example;

import dev.reyaan.smoothgui.GuiWidget;
import dev.reyaan.smoothgui.SimpleWidgetGui;
import dev.reyaan.smoothgui.widgets.SlotWidget;
import dev.reyaan.smoothgui.SlotWidgetIcon;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.server.network.ServerPlayerEntity;

import java.nio.file.Path;
import java.util.List;


public class ExampleGui extends SimpleWidgetGui {
    @GuiWidget(serialize = true)
    SlotWidget paperIcon;

    public ExampleGui(ServerPlayerEntity player) {
        super(ScreenHandlerType.GENERIC_9X1, player, false, true);

        paperIcon = new ExampleSearchWidget("lepaper",
                5,
                List.of(SlotWidgetIcon.of(Items.FEATHER, "no search"), SlotWidgetIcon.of(Items.FEATHER, "searching ${" + ExampleSearchWidget.searchValuePlaceholder + "}")),
                player,
                this);

        initialize();
    }

    @Override
    public Path getDataFilePath() {
        return FabricLoader.getInstance().getConfigDir().resolve(Path.of("example_gui.json"));
    }
}
