package io.dpteam.BlockKeepAlive;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.PacketType.Play.Client;
import com.comphenix.protocol.PacketType.Play.Server;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import java.lang.reflect.InvocationTargetException;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	private Main INSTANCE = null;
	private ProtocolManager prM = null;

	public Main() {
		super();
	}

	public void onEnable() {
		this.INSTANCE = this;
		this.prM = ProtocolLibrary.getProtocolManager();
		this.prM.addPacketListener(new PacketAdapter(this.INSTANCE, Server.KEEP_ALIVE) {
			public void onPacketSending(PacketEvent e) {
				e.setCancelled(true);
				PacketContainer pac = Main.this.prM.createPacket(Client.KEEP_ALIVE);
				pac.getLongs().write(0, e.getPacket().getLongs().read(0));
				int i = Main.this.getServer().getScheduler().scheduleSyncDelayedTask(Main.this.INSTANCE, new Runnable() {

					Player p = e.getPlayer();
					PacketContainer packet = pac;

					public void run() {
                        Main.this.prM.receiveClientPacket(this.p, this.packet, true);
                    }
				}, 2L);
			}
		});
		System.out.println("BlockKeepAlive Enabled");
	}

	@Override
	public void onDisable() {
		System.out.println("BlockKeepAlive Disabled");
	}
}
