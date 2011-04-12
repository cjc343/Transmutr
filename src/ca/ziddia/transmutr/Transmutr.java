package ca.ziddia.transmutr;

import com.nijiko.permissions.PermissionHandler;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginManager;

/**
 * Sample plugin for Bukkit
 * 
 * @author Snowl
 */
public class Transmutr extends JavaPlugin {

	private PermissionHandler permissions;
	private TransmutrPlayerListener playerListener;
	private TransmutrPluginListener pluginListener;
	private final HashMap<Player, Boolean> debugees = new HashMap<Player, Boolean>();
	private CopyOnWriteArrayList<String> Transmutr = new CopyOnWriteArrayList<String>();

	CopyOnWriteArrayList<String> GetBlocks() {
		return Transmutr;
	}

	public void onDisable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		System.out.println(pdfFile.getName() + " version " + pdfFile.getVersion() + " is disabled!");
	}

	public void setup() {
		try {
			new File("Transmutr.properties").createNewFile();
		} catch (IOException ex) {
			System.out.println("Could not create Transmutr properties file. Create it manually!");
		}
	}

	public void onEnable() {
		// TODO: Place any custom enable code here including the registration of any events
		setup();

		String fname = "Transmutr.properties";
		try {
			BufferedReader input = new BufferedReader(new FileReader(fname));
			String line = null;
			while ((line = input.readLine()) != null) {
				line = line.trim();
				if (!line.matches("^#.*") && !line.matches("")) {
					Transmutr.add(line);
				}
			}
			input.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Register our events
		PluginManager pm = getServer().getPluginManager();
		// final Server server = getServer();
		pm.registerEvent(Event.Type.PLAYER_INTERACT, playerListener, Priority.Normal, this);
		pm.registerEvent(Event.Type.PLUGIN_ENABLE, pluginListener, Priority.Monitor, this);
		// EXAMPLE: Custom code, here we just output some info so we can check all is well
		PluginDescriptionFile pdfFile = this.getDescription();
		System.out.println(pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!");
	}

	public boolean isDebugging(final Player player) {
		if (debugees.containsKey(player)) {
			return debugees.get(player);
		} else {
			return false;
		}
	}

	public void setDebugging(final Player player, final boolean value) {
		debugees.put(player, value);
	}

	public void onLoad() {
		playerListener = new TransmutrPlayerListener(this);
		pluginListener = new TransmutrPluginListener(this);
	}

	public PermissionHandler getPermissions() {
		return permissions;
	}

	public void setPermissions(PermissionHandler permissions) {
		this.permissions = permissions;
	}
}
