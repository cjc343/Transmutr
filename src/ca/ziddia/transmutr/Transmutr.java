package ca.ziddia.transmutr;

import com.nijiko.permissions.PermissionHandler;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
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

	
	private HashMap<Integer, LinkedList<TransmutrTransmuteCase>> cases = new HashMap<Integer, LinkedList<TransmutrTransmuteCase>>();
	

	HashMap<Integer, LinkedList<TransmutrTransmuteCase>> GetBlocks() {
		return cases;
	}

	public void onDisable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		System.out.println(pdfFile.getName() + " version " + pdfFile.getVersion() + " is disabled!");
	}

	public void setup() {
		try {
			if (!getDataFolder().exists()) {
				getDataFolder().mkdirs();
			}
			new File(getDataFolder().getPath() + "/Transmutr.properties").createNewFile();
		} catch (IOException ex) {
			System.out.println("Could not create Transmutr properties file. Create it manually!");
		}
	}

	public void onEnable() {
		// TODO: Place any custom enable code here including the registration of any events
		setup();

		String fname = getDataFolder().getPath() + "/Transmutr.properties";
		ArrayList<String> fileLines = new ArrayList<String>();
		try {
			BufferedReader input = new BufferedReader(new FileReader(fname));
			String line = null;
			while ((line = input.readLine()) != null) {
				line = line.trim();
				if (!line.matches("^#.*") && !line.matches("")) {
					fileLines.add(line);
				}
			}
			input.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for (String blockid : fileLines) {
			String[] parts = blockid.split(";");
			if (parts.length == 2) {
				String[] params = parts[1].split(":");
				if (Integer.valueOf(parts[0]) > 0 && params.length == 3) {
					TransmutrTransmuteCase newCase = new TransmutrTransmuteCase(Integer.valueOf(parts[0]), Integer.valueOf(params[0]), Integer.valueOf(params[1]), Double.valueOf(params[2]));
					if (cases.containsKey(Integer.valueOf(parts[0]))) {
						cases.get(Integer.valueOf(parts[0])).add(newCase);
					} else {
						LinkedList<TransmutrTransmuteCase> newList = new LinkedList<TransmutrTransmuteCase>();
						newList.add(newCase);
						cases.put(Integer.valueOf(parts[0]), newList);
					}
				} else {
					parseError(blockid);
				}
			} else {
				parseError(blockid);
			}
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
	
	private void parseError(String line) {
		System.out.println("There was an error parsing the following line of your Transmutr config:\n" + line);
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
