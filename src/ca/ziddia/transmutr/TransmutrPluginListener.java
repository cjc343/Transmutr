package ca.ziddia.transmutr;
 
import java.util.logging.Logger;

import org.bukkit.event.server.ServerListener;
import org.bukkit.event.server.PluginEvent;
import com.nijikokun.bukkit.Permissions.Permissions;
 
public class TransmutrPluginListener extends ServerListener {
       
    private Transmutr plugin;
    private final Logger log = Logger.getLogger("Minecraft");
    
    public TransmutrPluginListener(Transmutr instance){
        this.plugin = instance;
    }
    
    public void onPluginEnabled(PluginEvent event){
    	if(event.getPlugin().getDescription().getName().equals("Permissions")) {
    		Transmutr.Permissions = ((Permissions) plugin.getServer().getPluginManager().getPlugin("Permissions")).getHandler();
    		log.info("Transmutr - Found Permissions");
    	}
    }
    
}