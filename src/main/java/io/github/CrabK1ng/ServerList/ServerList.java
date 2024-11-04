package io.github.CrabK1ng.ServerList;

import dev.crmodders.cosmicquilt.api.entrypoint.ModInitializer;
import org.quiltmc.loader.api.ModContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;

import static finalforeach.cosmicreach.GameAssetLoader.loadJson;

public class ServerList implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("ServerList");

	public static ArrayList<String> serverList = new ArrayList<String>();

	public static String filePath = "server_ips.txt";

	@Override
	public void onInitialize(ModContainer mod) {
		loadServerIp();
		LOGGER.info("ServerList Initialized!");
	}

	public static void loadServerIp(){
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				if (!serverList.contains(line)){
					serverList.add(line);
				}
			}
		} catch (IOException e) {
			System.err.println("Error reading from file: " + e.getMessage());
		}
	}

	public static void saveServerIp(){
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
			for (String ip : serverList) {
				writer.write(ip);
				writer.newLine();
			}
		} catch (IOException e) {
			System.err.println("Error writing to file: " + e.getMessage());
		}
	}

}

