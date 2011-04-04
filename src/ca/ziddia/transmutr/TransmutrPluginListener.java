package ca.ziddia.transmutr;

import com.nijikokun.bukkit.Permissions.Permissions;

import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.event.server.ServerListener;
import org.bukkit.plugin.Plugin;

/**
 *
 * @Author Ziddia
 */
public class TransmutrPluginListener extends ServerListener {

    private Transmutr plugin;

    public TransmutrPluginListener(Transmutr plugin) {
        this.plugin = plugin;
    }

    public void onPluginEnable(PluginEnableEvent event) {
        if (plugin.getPermissions() == null) {
            Plugin permissions = plugin.getServer().getPluginManager().getPlugin("Permissions");

            if (permissions != null) {
                if (permissions.isEnabled()) {
                    plugin.setPermissions(((Permissions) permissions).getHandler());
                    System.out.println("[Transmutr] Successfully linked with Permissions.");
                }
            }
        }
    }
}
