/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.ziddia.transmutr;

import com.nijikokun.bukkit.Permissions.Permissions;
import org.bukkit.event.server.PluginEvent;
import org.bukkit.event.server.ServerListener;
import org.bukkit.plugin.Plugin;

/**
 *
 * @author ziddia
 */
public class TransmutrPluginListener extends ServerListener {

    private Transmutr plugin;

    public TransmutrPluginListener(Transmutr plugin) {
        this.plugin = plugin;
    }

    public void onPluginEnabled(PluginEvent event) {
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