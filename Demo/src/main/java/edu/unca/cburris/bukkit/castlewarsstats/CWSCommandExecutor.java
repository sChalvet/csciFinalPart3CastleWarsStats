package edu.unca.cburris.bukkit.castlewarsstats;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
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
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		String cmd = command.getName().toLowerCase();
		 if (cmd.equals("stat")) {
			
			return statsMethod(sender,args);
		}
	
	else {
			return true;
		}
	}
	
	private boolean statsMethod(CommandSender cs, String[] strings){
			Player plr = (Player) cs;
		
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
				statClass.setAchievements(numAchievements);
				statClass.setXp(exp);
				plugin.getDatabase().save(statClass);
			}	else if(strings[0].equalsIgnoreCase("view")){
					String name = strings[1];
					Stats statClass = plugin.getDatabase().find(Stats.class).where().ieq("playerName", plr.getName()).findUnique();
					if(statClass == null){
						plr.sendMessage(ChatColor.RED + "The entry you requested doesn't exist.");
						return true;
					}
					plr.sendMessage(ChatColor.GRAY + "ID is: " + statClass.getId());
				//	plr.sendMessage(ChatColor.GRAY + "DB Name is: " + statClass.getName());
					plr.sendMessage(ChatColor.GRAY + "Your Player Name is: " + statClass.getPlayerName());
					plr.sendMessage(ChatColor.GRAY + "Number of Achievements: " + statClass.getAchievements());
					plr.sendMessage(ChatColor.GRAY + "Exp is : " + statClass.getXp());
					plr.sendMessage(ChatColor.GRAY + "Deaths : " + statClass.getDeaths());
				}
	
			
			
			return true;
		
		
	}

}
