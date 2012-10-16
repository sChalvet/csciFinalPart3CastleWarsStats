package edu.unca.cburris.Demo;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.*;

import com.google.common.base.Joiner;

/*
 * This is a sample CommandExectuor
 */
public class DemoCommandExecutor implements CommandExecutor {
	private final Demo plugin;

	/*
	 * This command executor needs to know about its plugin from which it came
	 * from
	 */
	public DemoCommandExecutor(Demo plugin) {
		this.plugin = plugin;
	}

	/*
	 * On command set the sample message
	 */
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 0) {
			return false;
		} else if (!(sender instanceof Player)) {
			return false;
			// the cake will appear on the ground but not
			// necessarily where the player is looking
		} else if (args[0].equalsIgnoreCase("spider")) {
			Player p = (Player) sender;
			 // player gets a spider's eye
			p.getInventory().addItem(new ItemStack(Material.SPIDER_EYE, 1));
			return true;
			// the stored message now always begins with
			// the word "message"--do you know how to easily
			// fix that problem?
		} else if (args[0].equalsIgnoreCase("xp")
				&& sender.hasPermission("demo.xp")) {
			Player p = (Player)sender;
			//send a message to the player about how much experience is needed to level up 
			p.sendMessage("Experience total is " + p.getTotalExperience());
			
			p.getExpToLevel();
			return true;
		} else if (args[0].equalsIgnoreCase("sword")
				&& sender.hasPermission("demo.sword")) {
			Item.class.getMethods();
			Player p = (Player)sender;
			//give player an iron sword
			p.setItemInHand(new ItemStack(Material.IRON_SWORD, 1));
	        // send a message to inform player they received a sword
			p.sendMessage(ChatColor.RED + "A sword for battle... "); 
			return true;
		
		//playNote(Location loc, byte instrument, byte note); play sound method
		
		
	}else if (args[0].equalsIgnoreCase("armor")
			&& sender.hasPermission("demo.sword")) {
		Item.class.getMethods();
		
		Player p = (Player)sender;
		PlayerInventory inventory = p.getInventory();
		//give the player an iron set of armor 
		inventory.addItem(new ItemStack(Material.IRON_HELMET, 1));
		inventory.addItem(new ItemStack(Material.IRON_CHESTPLATE, 1));
		inventory.addItem(new ItemStack(Material.IRON_LEGGINGS, 1));
		inventory.addItem(new ItemStack(Material.IRON_HELMET, 1));
		
		
		InventoryView ce = p.openInventory(inventory);
		ce.getCursor();
		//send a message to inform player they received a set of armor
		p.sendMessage(ChatColor.RED + "Armor is in Inventory... ");
		return true;
	
	
	}else if (args[0].equalsIgnoreCase("message")
				&& sender.hasPermission("demo.message")) {
			this.plugin.getConfig().set("sample.message",
					Joiner.on(' ').join(args));
			return true;
		} else if (args[0].equalsIgnoreCase("bed")
				&& sender.hasPermission("demo.bed")) {
			Player p = (Player)sender;
			Location l = p.getBedSpawnLocation();
			p.teleport(l);
			p.sendMessage("It's Bedtime");
			return true;
		}
	
	else {
			return false;
		}
	}

}
