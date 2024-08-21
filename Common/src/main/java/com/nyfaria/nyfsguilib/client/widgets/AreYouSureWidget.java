package com.nyfaria.nyfsguilib.client.widgets;

import com.nyfaria.nyfsguilib.client.widgets.api.ParentWidget;
import com.nyfaria.nyfsguilib.Constants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

public class AreYouSureWidget<T extends Screen> extends ParentWidget<T> {
    private Button.OnPress yes;
    private Button.OnPress no;
    private Component yesOption;
    private Component noOption;


    public AreYouSureWidget(@Nullable T parent, Component pMessage, int pX, int pY, int pWidth, int pHeight, Component yesOption, Component noOption, Button.OnPress yes, Button.OnPress no) {
        super(parent, pX, pY, pWidth, pHeight, pMessage);
        this.yes = yes;
        this.no = no;
        this.yesOption = yesOption;
        this.noOption = noOption;
    }


    public AreYouSureWidget(@Nullable T parent, Component pMessage, int pX, int pY, int pWidth, int pHeight) {
        this(parent, pMessage, pX, pY, pWidth, pHeight, Component.translatable(Constants.MODID + ".button.yes"), Component.translatable(Constants.MODID + ".button.no"), (button) -> {
        }, (button) -> {
        });
    }

    @Override
    public void init(boolean rebuild) {
        Button yesButton = Button.builder(yesOption, this::yes).pos(getX() + width / 2 - 32, getY() + height / 2).size(30, 20).build();
        Button noButton = Button.builder(noOption, this::no).pos(getX() + width / 2 + 2, getY() + height / 2).size(30, 20).build();
        this.addRenderableWidget(yesButton);
        this.addRenderableWidget(noButton);
    }

    public void yes(Button button) {
        this.yes.onPress(button);
    }

    public void no(Button button) {
        this.no.onPress(button);
    }

    public void setYes(Button.OnPress pYes) {
        this.yes = pYes;
        init(true);
    }

    public void setNo(Button.OnPress pNo) {
        this.no = pNo;
        init(true);
    }

    @Override
    protected void renderBackground(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        pGuiGraphics.fill(this.getX(), this.getY(), this.getX() + this.width, this.getY() + this.height, 0xFFFFFFFF);
        pGuiGraphics.fill(this.getX() + 1, this.getY() + 1, this.getX() + this.width - 1, this.getY() + this.height - 1, 0xFF000000);
        pGuiGraphics.drawCenteredString(Minecraft.getInstance().font, this.getMessage(), this.getX() + this.width / 2, this.getY() + 10, 0xFFFFFFFF);
    }

    @Override
    protected void renderForeground(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {

    }
}
