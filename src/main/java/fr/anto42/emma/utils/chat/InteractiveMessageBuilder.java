package fr.anto42.emma.utils.chat;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class InteractiveMessageBuilder {
    private TextComponent textComponent;

    public InteractiveMessageBuilder(String text) {
        this.textComponent = new TextComponent(text);
    }

    public InteractiveMessageBuilder setHoverMessage(String... msg) {
        StringBuilder sb = new StringBuilder();
        int i = 1;
        for (String s : msg) {
            sb.append(s + ((i < msg.length) ? "" : ""));
            i++;
        }
        this.textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (new ComponentBuilder(sb.toString())).create()));
        return this;
    }

    public InteractiveMessageBuilder setClickAction(ClickEvent.Action action, String value) {
        this.textComponent.setClickEvent(new ClickEvent(action, value));
        return this;
    }

    public TextComponent build() {
        return this.textComponent;
    }
}
