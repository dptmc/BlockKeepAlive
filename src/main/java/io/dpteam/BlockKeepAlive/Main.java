package io.dpteam.BlockKeepAlive;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.PacketType.Play.Client;
import com.comphenix.protocol.PacketType.Play.Server;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import java.lang.reflect.InvocationTargetException;
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
		this.prM.addPacketListener(new PacketAdapter(this.INSTANCE, new PacketType[]{Server.KEEP_ALIVE}) {
			public void onPacketSending(PacketEvent e) {
				e.setCancelled(true);
				PacketContainer pac = Main.this.prM.createPacket(Client.KEEP_ALIVE);
				pac.getLongs().write(0, (Long)e.getPacket().getLongs().read(0));
				Main.this.getServer().getScheduler().scheduleSyncDelayedTask(Main.this.INSTANCE, new Runnable(e, pac) {
					Player p;
					PacketContainer packet;

					{
						this.p = var2.getPlayer();
						this.packet = var3;
					}

					public void run() {
						try {
							Main.this.prM.recieveClientPacket(this.p, this.packet);
						} catch (InvocationTargetException | IllegalAccessException var2) {
						}

					}
				}, 2L);
			}
		});
		System.out.println("Enabled BlockKeepAlive");
	}

	@Override
	public void onDisable() {
		System.out.println("Disabled BlockKeepAlive");
	}
}
