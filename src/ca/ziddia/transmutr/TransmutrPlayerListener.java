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
	
	//remove one of item in hand
	private void decrementItemInHand(PlayerInteractEvent event) {
		ItemStack old = new ItemStack(event.getPlayer().getItemInHand().getTypeId(), event.getPlayer().getItemInHand().getAmount() - 1);
		event.getPlayer().setItemInHand(old);
	}

	@Override
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (event.getAction().compareTo(Action.RIGHT_CLICK_BLOCK) != 0) {
			return;//stop if not a right click on block
		}
		Player p = event.getPlayer();
		if (!plugin.getPermissions().has(p, "Transmutr")) {//check permission
			p.sendMessage(ChatColor.RED + "You don't have permssions to transmute blocks!");
			return;
		}
		int blockId = event.getClickedBlock().getTypeId();//get clicked block
		LinkedList<TransmutrTransmuteCase> caseList = plugin.GetBlocks().get(blockId);//get clicked block transmutes
		if (caseList == null) {
			return;//return if no transmutes
		}
		for (TransmutrTransmuteCase transCase : caseList) {//for each transmute otherwise:
			if (p.getItemInHand().getTypeId() == transCase.getTool()) {//check if the item in hand matches the 'tool'
				if (Math.random() <= transCase.getChance()) {//check if the odds are in favor
					event.getClickedBlock().setTypeId(Integer.valueOf(transCase.getResult()));//transmute the block
					decrementItemInHand(event);//decrement tool
				} else {
					decrementItemInHand(event);//decrement tool
				}
				if (event.getPlayer().getItemInHand().getAmount() == 0) {
					event.getPlayer().getInventory().remove(event.getPlayer().getItemInHand());//remove if it's gone
				}
			}
		}
	}
}
