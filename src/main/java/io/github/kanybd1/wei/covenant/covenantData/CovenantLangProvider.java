package io.github.kanybd1.wei.covenant.covenantData;

import io.github.kanybd1.wei.WeiModMain;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class CovenantLangProvider extends LanguageProvider {
    public CovenantLangProvider(PackOutput output) {
        super(output, WeiModMain.MODID, "zh_cn");
    }
    @Override
    protected void addTranslations() {
        registerCovenantLevels("fortress", "堡垒", 999);
        registerCovenantLevels("end", "末地", 999);
        registerCovenantLevels("miner", "矿工", 999);
        registerCovenantLevels("ocean", "海洋", 999);
        registerCovenantLevels("knowledge", "知识", 999);
        registerCovenantLevels("forest", "森林", 999);
    }

    private void registerCovenantLevels(String effectName, String baseDisplayName, int maxLevel) {
        for (int i = 1; i <= maxLevel; i++) {
            String key = "effect." + WeiModMain.MODID + "." + effectName;
            String value = baseDisplayName + " " + i;

            add(key, value);
        }
    }
}
