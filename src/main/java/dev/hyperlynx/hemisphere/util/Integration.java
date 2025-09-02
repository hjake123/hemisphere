package dev.hyperlynx.hemisphere.util;

import dev.hyperlynx.hemisphere.remorphed.MorphModWrapper;
import dev.hyperlynx.hemisphere.remorphed.MorphModWrapperImpl;
import dev.hyperlynx.hemisphere.reskin.ReSkinWrapper;
import dev.hyperlynx.hemisphere.reskin.ReSkinWrapperImpl;
import net.minecraftforge.fml.ModList;

public class Integration {
    public static MorphModWrapper morph() {
        if(ModList.get().isLoaded("identity")) {
            return new MorphModWrapperImpl();
        }
        return new MorphModWrapper.Stub();
    }

    public static ReSkinWrapper reskin() {
        if(ModList.get().isLoaded("reskin")) {
            return new ReSkinWrapperImpl();
        }
        return new ReSkinWrapper.Stub();
    }
}
