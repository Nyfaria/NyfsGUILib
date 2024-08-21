package com.nyfaria.nyfsguilib.client.widgets;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

public class TabsWidget extends AbstractWidget {
    private OnClickTab onClickTab;
    private int tabSize;
    private int tabCount;
    private String[] tabNames;
    private int selectedTab = 0;
    List<Tab> tabs = new ArrayList<>();

    public TabsWidget(int pX, int pY, int pHeight, int pWidth, Component pMessage, OnClickTab onClickTab, String... tabNames) {
        super(pX, pY, pWidth, pHeight, pMessage);
        this.onClickTab = onClickTab;
        this.tabSize = -1;
        this.tabCount = tabNames.length;
        this.tabNames = tabNames;
        int fillStart = 0;
        int fullTextWidth = Minecraft.getInstance().font.width(String.join("", tabNames));
        for(String tabName : tabNames) {
            int nameWidth = Minecraft.getInstance().font.width(tabName);
            int tabWidth = (int)(this.width * ((double)nameWidth / (double)fullTextWidth));
            tabs.add(new Tab(tabs.size(), tabName, pX+fillStart, pY, tabWidth, this.height));
            fillStart += tabWidth;
        }
    }
    public TabsWidget(int pX, int pY, int pHeight, Component pMessage, OnClickTab onClickTab, int tabWidth, String... tabNames) {
        super(pX, pY, tabWidth * tabNames.length, pHeight, pMessage);
        this.onClickTab = onClickTab;
        this.tabSize = tabWidth;
        this.tabCount = tabNames.length;
        this.tabNames = tabNames;
        for(int i = 0; i < tabNames.length; i++) {
            tabs.add(new Tab(i, tabNames[i], pX + i * tabWidth, pY, tabWidth, this.height));
        }
    }


    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int i, int i1, float v) {
        tabs.forEach(
                tab -> {
                    int color = tab.index == selectedTab ? -1 : -6250336;
                    guiGraphics.fill(tab.x, tab.y, tab.x + tab.width, tab.y + tab.height, color);
                    guiGraphics.fill(tab.x + 1, tab.y + 1, tab.x + tab.width - 1, tab.y + tab.height + (selectedTab == tab.index ? 3 : 0), 0xFF000000);
                    guiGraphics.drawCenteredString(Minecraft.getInstance().font, tab.name, tab.x + tab.width / 2, tab.y + tab.height / 2 - 4, color);
                }
        );
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        tabs.forEach(tab -> {
            if (mouseX >= tab.x && mouseX <= tab.x + tab.width && mouseY >= tab.y && mouseY <= tab.y + tab.height) {
                selectedTab = tab.index;
                onClickTab.onClick(tab.index);
            }
        });
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }

    public interface OnClickTab {
        void onClick(int tab);
    }
    record Tab(int index, String name, int x, int y, int width, int height){}
}
