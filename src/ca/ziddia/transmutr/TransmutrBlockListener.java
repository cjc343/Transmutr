package ca.ziddia.transmutr;

import java.util.concurrent.CopyOnWriteArrayList;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import org.bukkit.event.block.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Transmutr BlockListener
 * @author Snowl
 */
public class TransmutrBlockListener extends BlockListener {

    private final Transmutr plugin;

    public TransmutrBlockListener(final Transmutr plugin) {
        this.plugin = plugin;
    }

    public static String convertSimple(int i) {
        return "" + i;
    }

    public void onPlayerInteract(PlayerInteractEvent event) {
        CopyOnWriteArrayList<String> transmutr = plugin.GetBlocks();
        int blockId = event.getClickedBlock().getTypeId();
        Player p = event.getPlayer();
        if(!plugin.getPermissions().has(p, "transmutr")){
            p.sendMessage(ChatColor.RED + "You don't have permssions to transmute blocks!");
            return;
        }
        for (String blockid : transmutr) {
            String[] parts = blockid.split(";");
            if (convertSimple(blockId).equalsIgnoreCase(parts[0])) {
                Integer index = 1;
                while (index < parts.length) {
                    String[] params = parts[index].split(":");
                    if (Integer.valueOf(params[0]) == 0) {
                        //event.setCancelled(true);
                        //blockIdd.setTypeId(0);
                    } else {

                        if (Math.random() < Double.valueOf(params[2])) {
                            if (event.getPlayer().getInventory().getItemInHand().getTypeId() == Integer.parseInt(params[0])) {
                                event.getClickedBlock().setTypeId(Integer.valueOf(params[1]));
                                ItemStack old = new ItemStack(event.getPlayer().getItemInHand().getTypeId(), event.getPlayer().getItemInHand().getAmount() - 1);
                                event.getPlayer().setItemInHand(old);
                            }
                        } else {
                            ItemStack old = new ItemStack(event.getPlayer().getItemInHand().getTypeId(), event.getPlayer().getItemInHand().getAmount() - 1);
                            event.getPlayer().setItemInHand(old);
                        }
                    }
                    index++;
                }
            }
        }
    }
}
