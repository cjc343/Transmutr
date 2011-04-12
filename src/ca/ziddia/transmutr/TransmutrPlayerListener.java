package ca.ziddia.transmutr;

import java.util.LinkedList;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.inventory.ItemStack;

/**
 * Transmutr Block Listener
 * 
 * @author Ziddia
 */
public class TransmutrPlayerListener extends PlayerListener {

	private final Transmutr plugin;

	public TransmutrPlayerListener(final Transmutr plugin) {
		this.plugin = plugin;
	}

	public static String convertSimple(int i) {
		return "" + i;
	}

	@Override
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (event.getAction().compareTo(Action.RIGHT_CLICK_BLOCK) != 0) {
			return;
		}
		int blockId = event.getClickedBlock().getTypeId();
		Player p = event.getPlayer();
		if (!plugin.getPermissions().has(p, "Transmutr")) {
			p.sendMessage(ChatColor.RED + "You don't have permssions to transmute blocks!");
			return;
		}
		LinkedList<TransmutrTransmuteCase> caseList = plugin.GetBlocks().get(blockId);
		if (caseList == null) {
			return;
		}
		for (TransmutrTransmuteCase transCase : caseList) {
			if (p.getItemInHand().getTypeId() == transCase.getTool()) {
				if (Math.random() < transCase.getChance()) {
					event.getClickedBlock().setTypeId(Integer.valueOf(transCase.getResult()));
					ItemStack old = new ItemStack(event.getPlayer().getItemInHand().getTypeId(), event.getPlayer().getItemInHand().getAmount() - 1);
					event.getPlayer().setItemInHand(old);
				} else {
					ItemStack old = new ItemStack(event.getPlayer().getItemInHand().getTypeId(), event.getPlayer().getItemInHand().getAmount() - 1);
					event.getPlayer().setItemInHand(old);
				}
				if (event.getPlayer().getItemInHand().getAmount() == 0) {
					event.getPlayer().getInventory().remove(event.getPlayer().getItemInHand());
				}
			}
		}
	}
}
