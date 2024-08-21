package com.nyfaria.nyfsguilib.client.widgets;

import com.nyfaria.nyfsguilib.client.widgets.api.TooltipRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.function.Consumer;

public class DisplaySlotWidget extends AbstractWidget implements TooltipRenderer {
    private ItemStack stack;
    private Consumer<DisplaySlotWidget> clickConsumer = null;
    private Consumer<DisplaySlotWidget> stackCallBack = null;
    private ResourceLocation slotIcon;
    private boolean renderEmpty = false;
    private static final ResourceLocation EMPTY_SLOT = new ResourceLocation("textures/item/barrier.png");

    public DisplaySlotWidget(ItemStack stack, int $$0, int $$1, int $$2, int $$3) {
        this(stack, $$0, $$1, $$2, $$3, null);
    }

    public DisplaySlotWidget(ItemStack stack, int x, int y, int width, int height, ResourceLocation slotIcon) {
        this(stack, x, y, width, height, slotIcon, null, null);
    }

    public DisplaySlotWidget(ItemStack stack, int x, int y, int width, int height, ResourceLocation slotIcon, Consumer<DisplaySlotWidget> clickConsumer, Consumer<DisplaySlotWidget> stackCallBack) {
        this(stack, x, y, width, height, slotIcon, clickConsumer, stackCallBack, false);
    }
    public DisplaySlotWidget(ItemStack stack, int x, int y, int width, int height, ResourceLocation slotIcon, Consumer<DisplaySlotWidget> clickConsumer, Consumer<DisplaySlotWidget> stackCallBack, boolean renderEmpty) {
        super(x, y, width, height, Component.empty());
        this.stack = stack;
        this.clickConsumer = clickConsumer;
        this.stackCallBack = stackCallBack;
        if (slotIcon == null || slotIcon.getPath().contains(".png")) {
            this.slotIcon = slotIcon;
        } else {
            this.slotIcon = slotIcon.withSuffix(".png").withPrefix("textures/");
        }
        this.renderEmpty = renderEmpty;
    }

    @Override
    public boolean mouseScrolled(double $$0, double $$1, double $$2) {
        return super.mouseScrolled($$0, $$1, $$2);
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        guiGraphics.fill(getX(), getY(), getX() + getWidth(), getY() + getHeight(), 0xFFFFFFFF);
        guiGraphics.fill(getX() + 1, getY() + 1, getX() + getWidth() - 1, getY() + getHeight() - 1, 0xFF000000);
        if (slotIcon != null && stack.isEmpty()) {
            guiGraphics.blit(slotIcon, getX() + 2, getY() + 2, getWidth() - 4, getHeight() - 4, 0, (isHovered ? 0 : getHeight() - 4), getWidth() - 4, getHeight() - 4, getWidth() - 4, getHeight() -4);
        }
        if(stack.isEmpty() && renderEmpty){
            guiGraphics.blit(EMPTY_SLOT, getX() + 2, getY() + 2, getWidth() - 4, getHeight() - 4, 0, (isHovered ? 0 : getHeight() - 4), getWidth() - 4, getHeight() - 4, getWidth() - 4, getHeight() -4);
        } else {
            guiGraphics.renderItem(stack, getX() + 2, getY() + 2);
            guiGraphics.renderItemDecorations(Minecraft.getInstance().font, stack, getX() + 2, getY() + 2);
        }
    }

    @Override
    public void onClick(double $$0, double $$1) {
        super.onClick($$0, $$1);
        if (clickConsumer != null) {
            clickConsumer.accept(this);
        }
    }

    public ItemStack getStack() {
        return stack;
    }

    public void setStack(ItemStack stack) {
        setStack(stack, true);
    }

    public void setStack(ItemStack stack, boolean callback) {
        this.stack = stack;
        if (stackCallBack != null && callback) {
            stackCallBack.accept(this);
        }
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }

    @Override
    public void renderTooltip(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        if (isHovered() && !stack.isEmpty()) {
            pGuiGraphics.renderTooltip(Minecraft.getInstance().font, AbstractContainerScreen.getTooltipFromItem(Minecraft.getInstance(), stack), stack.getTooltipImage(), getX(), getY() + 30);
        }
    }
}
