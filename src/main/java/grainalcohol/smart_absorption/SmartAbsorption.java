package grainalcohol.smart_absorption;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SmartAbsorption implements ModInitializer {
	public static final String MOD_ID = "smart_absorption";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
//		LOGGER.info("====== Smart Absorption初始化开始 ======");
//		// 检查Mixin环境
//		try {
//			Class<?> mixinEnvironment = Class.forName("org.spongepowered.asm.mixin.MixinEnvironment");
//			Object currentEnv = mixinEnvironment.getMethod("getCurrentEnvironment").invoke(null);
//			Object phaseObj = currentEnv.getClass().getMethod("getPhase").invoke(currentEnv);
//			LOGGER.info("当前Mixin环境阶段: {}", phaseObj.toString());
//		} catch (Exception e) {
//			LOGGER.error("检查Mixin环境失败", e);
//		}
	}
}