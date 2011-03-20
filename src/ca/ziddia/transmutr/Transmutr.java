package ca.ziddia.transmutr;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

public class Transmutr extends JavaPlugin{
public static PermissionHandler Permissions = null;
 public static Transmutr instance;
 
 private final TransmutrBlockListener blockListener = new TransmutrBlockListener(this);
 private final TransmutrPluginListener pluginListener = new TransmutrPluginListener(this);
 private final HashMap<Player, Boolean> debugees = new HashMap<Player, Boolean>();
 private CopyOnWriteArrayList<String> transmutes = new CopyOnWriteArrayList<String>();
 
 CopyOnWriteArrayList<String> GetBlocks() {
 	return transmutes;
 }
 
 public void onDisable() {

 }

 
 public void setup() {
     try
     {
       new File("Transmutr.properties").createNewFile();
     } catch (IOException ex) {
       System.out.println("Could not create Transmutr properties file. Create it manually!");
     }
 }

 

public void onEnable() {
	setup();
	
	String fname = "Transmutr.properties";
	try {
    	BufferedReader input =  new BufferedReader(new FileReader(fname));
		String line = null;
		while (( line = input.readLine()) != null) {
			line = line.trim();
			if (!line.matches("^#.*") && !line.matches("")) {
				Transmutr.add(line);
			}
		}
		input.close();
	}
	catch (Exception e) {
		e.printStackTrace();
	}
	PluginManager pm = getServer().getPluginManager();
	pm.registerEvent(Event.Type.BLOCK_RIGHTCLICKED, blockListener, Priority.Normal, this);
	pm.registerEvent(Event.Type.PLUGIN_ENABLE, pluginListener, Priority.Monitor, this);

	 PluginDescriptionFile pdfFile = this.getDescription();
     System.out.println( pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );
     
     setupPermissions();
 }
 
 private void setupPermissions() {
	 {
		 Plugin p = this.getServer().getPluginManager().getPlugin("Permissions");
		 if (p != null && p.isEnabled()) {
			 Transmutr.Permissions = ((Permissions)p).getHandler();
		 }
	 }
	// TODO Auto-generated method stub
	
}

private static void add(String line) {
	// TODO Auto-generated method stub
	
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
}
