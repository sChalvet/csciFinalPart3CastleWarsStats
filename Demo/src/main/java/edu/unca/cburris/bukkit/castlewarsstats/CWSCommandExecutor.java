package edu.unca.cburris.bukkit.castlewarsstats;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.PluginManager;
import org.bukkit.*;

/*
 * This is a sample CommandExectuor
 */
public class CWSCommandExecutor implements CommandExecutor {
	private final CastleWarsStats plugin;

	/*
	 * This command executor needs to know about its plugin from which it came
	 * from
	 */
	public CWSCommandExecutor(CastleWarsStats plugin) {
		this.plugin = plugin;
	}

	/*
	 * On command set the sample message
	 */
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) throws ArrayIndexOutOfBoundsException{
	
		try {
		String cmd = command.getName().toLowerCase();
		 if (cmd.equals("stat")) {
			if (args[0] == "" || args[1] == "" || args[0] == "null" || args[1] == "null"){
				throw new ArrayIndexOutOfBoundsException("type /stat reg to register or /stat view playerName to view stats");
				
			}
			return statsMethod(sender,args);
		}
			if (args[0].equalsIgnoreCase("armor")) {
				Item.class.getMethods();

				Player p = (Player)sender;
				PlayerInventory inventory = p.getInventory();
				//give the player an iron set of armor 
				inventory.addItem(new ItemStack(Material.IRON_HELMET, 1));
				inventory.addItem(new ItemStack(Material.IRON_CHESTPLATE, 1));
				inventory.addItem(new ItemStack(Material.IRON_LEGGINGS, 1));
				inventory.addItem(new ItemStack(Material.IRON_HELMET, 1));

				//send a message to inform player they received a set of armor
				p.sendMessage(ChatColor.RED + "Armorset is in Inventory... ");
				

				return true;
				
			}
	else {
			return false;
		}
		}
		catch(ArrayIndexOutOfBoundsException ie){
			ie.printStackTrace();
			//System.out.println("type /stat view playerName to view your statistics");
			sender.sendMessage("type /stat view playerName to view your statistics or /stat reg playerName to register ");
		}
		return true;
	}
	
	private boolean statsMethod(CommandSender cs, String[] strings) throws ArrayIndexOutOfBoundsException{
			Player plr = (Player) cs;
		try{
			if(strings[0].equalsIgnoreCase("reg")){
				String playerName = plr.getName();
				Achievement[] pm = Achievement.values();
				int numAchievements = pm.length;
				
				int exp = plr.getTotalExperience();
				Stats statClass = plugin.getDatabase().find(Stats.class).where().ieq("playerName", plr.getName()).findUnique();
				if(statClass == null){
					statClass = new Stats();
					statClass.setId(playerName);
					statClass.setPlayer(plr);
					statClass.setPlayerName(playerName);
				}
				statClass.setDamage(0);
				statClass.setXp(exp);
				plugin.getDatabase().save(statClass);
			}	else if(strings[0].equalsIgnoreCase("view")){
					String name = strings[1];
					Stats statClass = plugin.getDatabase().find(Stats.class).where().ieq("playerName", plr.getName()).findUnique();
					if(statClass == null){
						
						//throw new IndexOutOfBoundsException("type /stat view playerName to view your statistics" );
						
						plr.sendMessage(ChatColor.RED + "You must first register to view player statistice simply type /stat reg");
						
					}
					plr.sendMessage(ChatColor.DARK_AQUA + "ID is: " + statClass.getId());
					plr.sendMessage(ChatColor.DARK_AQUA + "Damage Dealt: " + statClass.getDamage());
					plr.sendMessage(ChatColor.DARK_AQUA+ "Damage Taken: " + statClass.getDamagetaken());
					plr.sendMessage(ChatColor.DARK_BLUE + "Exp is : " + statClass.getXp());
					plr.sendMessage(ChatColor.GOLD + "Deaths : " + statClass.getDeaths());
					plr.sendMessage(ChatColor.GREEN + "Kills : " + statClass.getKills());
				}
		}
		catch(ArrayIndexOutOfBoundsException ie){
			ie.printStackTrace();
			//System.out.println("type /stat view playerName to view your statistics");
			cs.sendMessage("type /stat view playerName to view your statistics or /stat reg to register ");
		}
			
			
			return true;
		
		
	}

}
