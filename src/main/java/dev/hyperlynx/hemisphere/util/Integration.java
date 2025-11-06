package dev.hyperlynx.hemisphere.util;

import dev.hyperlynx.hemisphere.remorphed.MPMWrapperImpl;
import dev.hyperlynx.hemisphere.remorphed.MorphModWrapper;
import dev.hyperlynx.hemisphere.remorphed.MorphModWrapperImpl;
import dev.hyperlynx.hemisphere.reskin.ReSkinWrapper;
import net.minecraftforge.fml.ModList;

public class Integration {
    public static MorphModWrapper morph() {
        if(ModList.get().isLoaded("morph")) {
            return new MorphModWrapperImpl();
        }
        if(ModList.get().isLoaded("moreplayermodels")) {
            return new MPMWrapperImpl();
        }
        return new MorphModWrapper.Stub();
    }

    public static ReSkinWrapper reskin() {
        return new ReSkinWrapper.Stub();
    }
}
