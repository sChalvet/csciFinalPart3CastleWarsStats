package edu.unca.cburris.bukkit.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import edu.unca.cburris.bukkit.accesscontrol.AccessControl;

public class BanExecutor implements CommandExecutor{
	
	private AccessControl plugin;
	
	public BanExecutor(AccessControl plugin){
		this.plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(args.length != 1 ){
			sender.sendMessage(ChatColor.RED + "Usage: /ban <player_name>");
			return true;
		}
		/* Check to see if the player trying to use the ban command is a server operator */
		if(sender.isOp() == false){
			sender.sendMessage(ChatColor.RED + "You don't have permission to use this command, it can only be used by server operators.");
			return true;
		}
		
		Player ban = plugin.getServer().getPlayer(args[0]);
		
		
		plugin.bannedPlayers.add(args[0]);
		
		if(ban != null){
			
			ban.kickPlayer("You've been banned from the server :(");
		}
		
		sender.sendMessage(ChatColor.GREEN + args[0] + " has been banned.");  //chat display for server op.
		return true;
	}
}


