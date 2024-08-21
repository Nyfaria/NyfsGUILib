package com.nyfaria.nyfsguilib;

import net.minecraftforge.fml.common.Mod;

@Mod(Constants.MODID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class NyfsGUILibMod {
    
    public NyfsGUILibMod() {
        CommonClass.init();
    }

}