package com.sindercube.serverUnpacker;

import com.sindercube.serverUnpacker.util.NativePackExtractor;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServerUnpacker implements ClientModInitializer {

	public static final Logger LOGGER = LogManager.getLogger("Server Unpacker");

	@Override
	public void onInitializeClient() {
		ServerUnpacker.LOGGER.info("Server upacker Initialized!");
	}

	public static void extractServerPack(File file) {
		ServerUnpacker.LOGGER.info("Extracting server pack {}", file);

		var info = MinecraftClient.getInstance().getCurrentServerEntry();
		String name = info == null ? file.getName() : info.address;

		String date = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
		String folderName = name + "_" + date;

		Path destination = FabricLoader.getInstance().getGameDir().resolve("extracted-packs/").resolve(folderName);

		try {
			NativePackExtractor.INSTANCE.extractPack(destination, file, name);
		} catch (Exception exception) {
			LOGGER.error(exception);
		}
	}
}
