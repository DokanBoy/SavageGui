package ru.zenegix.menu.processor;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Constructor;

public class SeamlessMenuOpenProcessorFactory {

    private static AbstractSeamlessMenuOpenProcessor currentVersion;

    public static AbstractSeamlessMenuOpenProcessor get() {
        return currentVersion == null
                ? currentVersion = lazyInit()
                : currentVersion;
    }

    private static AbstractSeamlessMenuOpenProcessor lazyInit() {
        try {
            Constructor<?> constructor;
            String name = Bukkit.getServer().getClass().getPackage().getName();
            int intVersion = Integer.parseInt((name.substring(name.lastIndexOf('.') + 1) + ".").split("_")[1]);

            switch (intVersion) {
                case 12: {
                    constructor = ru.zenegix.menu.processor.v1_12.SeamlessMenuOpenProcessor.class.getConstructor();
                    break;
                }
                case 8: {
                    constructor = ru.zenegix.menu.processor.v1_8.SeamlessMenuOpenProcessor.class.getConstructor();
                    break;
                }
                default: {
                    constructor = UnsupportSeamlessMenuOpenProcessor.class.getConstructor();
                    break;
                }
            }

            return (AbstractSeamlessMenuOpenProcessor) constructor.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
