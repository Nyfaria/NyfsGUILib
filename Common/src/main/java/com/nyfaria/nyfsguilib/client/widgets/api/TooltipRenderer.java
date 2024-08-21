package com.nyfaria.nyfsguilib.client.widgets.api;

import net.minecraft.client.gui.GuiGraphics;

public interface TooltipRenderer {
    void renderTooltip(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick);
}
