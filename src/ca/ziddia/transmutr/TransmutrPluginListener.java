package ca.ziddia.transmutr;

import com.nijikokun.bukkit.Permissions.Permissions;

import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.event.server.ServerListener;
import org.bukkit.plugin.Plugin;

/**
 * Transmutr PluginListener
 *
 * @author Ziddia
 */
public class TransmutrPluginListener extends ServerListener {

    private Transmutr plugin;

    public TransmutrPluginListener(Transmutr plugin) {
        this.plugin = plugin;
    }

    public void onPluginEnabled(PluginEnableEvent event) {
        if (plugin.getPermissions() == null) {
            Plugin Permissions = plugin.getServer().getPluginManager().getPlugin("Permissions");

            if (Permissions != null) {
                if (Permissions.isEnabled()) {
                    plugin.setPermissions(((Permissions) Permissions).getHandler());
                    System.out.println("[Transmutr] Successfully linked with Permissions.");
                }
            }
        }
    }
}
