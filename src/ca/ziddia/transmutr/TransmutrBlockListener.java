package ca.ziddia.transmutr;

import java.util.concurrent.CopyOnWriteArrayList;

import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockRightClickEvent;
import org.bukkit.inventory.ItemStack;

public class TransmutrBlockListener extends BlockListener {
	private final Transmutr plugin;
	
	public TransmutrBlockListener(final Transmutr plugin) {
		this.plugin = plugin;
	}
	
	public static String convertSimple (int i) {
		return "" + i;
	}

	public void onBlockRightClick(BlockRightClickEvent event) {
		CopyOnWriteArrayList<String> transmutes = plugin.GetBlocks();
		int blockId = event.getBlock().getTypeId();
		for ( String blockid : transmutes ) {
			String[] parts = blockid.split(";");
			if (convertSimple(blockId).equalsIgnoreCase(parts[0]))
				
			{
		Integer index = 1;
		while (index < parts.length) {
			String[] params = parts[index].split(":");
			
		if(Integer.valueOf(params[0]) == 0)
		{
			//event.setCancelled(true);
			//blockIdd.setTypeId(0);
		}
		else
		{
			if (Math.random() < Double.valueOf(params[2]))
			{
				if(event.getPlayer().getInventory().getItemInHand().getTypeId() == Integer.parseInt(params[0]))
				{
	        		event.getBlock().setTypeId(Integer.valueOf(params[1]));
	                ItemStack old = new ItemStack(event.getPlayer().getItemInHand().getTypeId(), event.getPlayer().getItemInHand().getAmount() - 1);
	                event.getPlayer().setItemInHand(old);
	          			 }
	          		}
		          	 else
		          	 {
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