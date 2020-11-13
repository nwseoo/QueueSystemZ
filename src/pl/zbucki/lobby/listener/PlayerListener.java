package cc.pvpheaven.Queue.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import cc.pvpheaven.Queue.Main;
import fr.xephi.authme.api.v3.AuthMeApi;

public class PlayerListener implements Listener {
	
	AuthMeApi authmeapi = AuthMeApi.getInstance();

	@EventHandler(priority = EventPriority.MONITOR)
	public void OnJoin(PlayerJoinEvent e) {
		Player p = (Player) e.getPlayer();
		
		if(Bukkit.getPluginManager().getPlugin("AuthMe") != null) {
				
			new Thread(new Runnable() {
					
				@Override
				public void run() {
						
					while(authmeapi.isAuthenticated(p) != true) {
						try {
							Thread.sleep(1250);
						} catch (InterruptedException ignored) {}
							
					}
					Main.getManager().addToQueue(p);
				}
			}).start();
		} 
		else {
			
			Main.getManager().addToQueue(p);
		}
			
	}		

	
	@EventHandler(priority = EventPriority.MONITOR)
	public void OnQuit(PlayerQuitEvent e) {
		Player p = (Player) e.getPlayer();
		
		Main.getManager().removeFromQueue(p);
	}

}
