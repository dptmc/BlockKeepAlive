package io.dpteam.PluginName;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{
	public void onEnable() {
		System.out.println("Enabled PluginName");
	}

	@Override
	public void onDisable() {
		System.out.println("Disabled PluginName");
	}
}