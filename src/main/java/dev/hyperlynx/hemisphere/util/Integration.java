package dev.hyperlynx.hemisphere.util;

import dev.hyperlynx.hemisphere.remorphed.WalkersWrapper;
import dev.hyperlynx.hemisphere.remorphed.WalkersWrapperImpl;
import dev.hyperlynx.hemisphere.reskin.ReSkinWrapper;
import dev.hyperlynx.hemisphere.reskin.ReSkinWrapperImpl;
import net.minecraftforge.fml.ModList;

public class Integration {
    public static WalkersWrapper walkers() {
        if(ModList.get().isLoaded("walkers")) {
            return new WalkersWrapperImpl();
        }
        return new WalkersWrapper.Stub();
    }

    public static ReSkinWrapper reskin() {
        if(ModList.get().isLoaded("reskin")) {
            return new ReSkinWrapperImpl();
        }
        return new ReSkinWrapper.Stub();
    }
}
