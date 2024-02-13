package dev.reyaan;

import com.google.gson.annotations.Expose;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import eu.pb4.placeholders.api.PlaceholderContext;
import eu.pb4.placeholders.api.Placeholders;
import eu.pb4.placeholders.api.TextParserUtils;
import eu.pb4.sgui.api.elements.GuiElementBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.StringNbtReader;
import net.minecraft.nbt.visitor.StringNbtWriter;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SlotWidgetIcon {
    @Expose
    public final Item item;
    @Expose
    public final String title;
    @Expose
    public final String placeholderNbt;
    @Expose
    public final List<String> placeholderLore;
    private NbtCompound nbt;


    public static SlotWidgetIcon of(Item item, String title) {
        return new SlotWidgetIcon(item, title, new NbtCompound(), new ArrayList<>());
    }

    public SlotWidgetIcon(Item item, String title, String placeholderNbt, List<String> lorePlaceholders) {
        this.item = item;
        this.title = title;
        this.placeholderNbt = placeholderNbt;
        this.placeholderLore = lorePlaceholders;
    }

    public SlotWidgetIcon(Item item, String title, NbtCompound nbt, List<String> lorePlaceholders) {
        this.item = item;
        this.title = title;
        this.placeholderNbt = new StringNbtWriter().apply(nbt);
        this.nbt = nbt;
        this.placeholderLore = lorePlaceholders;
    }

    public GuiElementBuilder createBuilder(ServerPlayerEntity player) {
        return createBuilder(PlaceholderContext.of(player), Map.of());
    }

    public GuiElementBuilder createBuilder(PlaceholderContext context, Map<String, Text> map) {
        // get stack
        ItemStack stack = item.getDefaultStack();

        // set or set and parse nbt
        if (nbt != null) {
            stack.setNbt(nbt);
        } else {
            String formattedNbt = parseTextUnformatted(placeholderNbt, context, map).asTruncatedString(25000);
            try {
                NbtCompound nbtCompound = new StringNbtReader(new StringReader(formattedNbt)).parseCompound();
                stack.setNbt(nbtCompound);
            } catch (CommandSyntaxException e) {
                throw new RuntimeException(e);
            }
        }

        // set and parse title
        stack.setCustomName(parseText(title, context, map));

        // create builder
        GuiElementBuilder builder = GuiElementBuilder.from(stack);

        // parse and append lore lines
        if (!placeholderLore.isEmpty()) {
            List<Text> parsedPlaceholders = new ArrayList<>();
            for (String s : placeholderLore) {
                parsedPlaceholders.add(parseText(s, context, map));
            }

            builder.setLore(parsedPlaceholders);
        }
        return builder;
    }

    public static Text parseText(String s, PlaceholderContext context, Map<String, Text> map) {
        Text f = TextParserUtils.formatText(s);
        Text defaultPlaceholderText = Placeholders.parseText(
                f, context, Placeholders.PREDEFINED_PLACEHOLDER_PATTERN
        );
        return Placeholders.parseText(defaultPlaceholderText, Placeholders.PREDEFINED_PLACEHOLDER_PATTERN, map);
    }

    public static Text parseTextUnformatted(String s, PlaceholderContext context, Map<String, Text> map) {
        Text defaultPlaceholderText = Placeholders.parseText(Text.of(s), context, Placeholders.PREDEFINED_PLACEHOLDER_PATTERN);
        return Placeholders.parseText(defaultPlaceholderText, Placeholders.PREDEFINED_PLACEHOLDER_PATTERN, map);
    }

}
