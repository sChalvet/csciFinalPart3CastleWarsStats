package edu.unca.cburris.bukkit.accesscontrol;

import java.text.MessageFormat;
import java.util.ArrayList;

import org.bukkit.conversations.Conversation;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventException;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import edu.unca.cburris.bukkit.accesscontrol.*;
import edu.unca.cburris.bukkit.accesscontrol.util.ListStore;

/*
 * This is a sample event listener
 */
public class ACListener implements Listener {
    private final AccessControl plugin;
    private int count = 0;
    public ListStore bannedPlayers;
    

    /*
     * This listener needs to know about the plugin which it came from
     */
    public ACListener(AccessControl plugin) {
        // Register the listener
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        
        this.plugin = plugin;
    }

    /*
     * Send the sample message to all players that join
     */
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onPlayerLogin(PlayerLoginEvent event) {
       String playerName = event.getPlayer().getName();
       
       if(plugin.bannedPlayers.contains(playerName)){
    	   event.setKickMessage("You are banned from this server!");
    	   event.setResult(Result.KICK_BANNED);
    	   
       }
       
       
    }
    /*
     * Check for vulgar language in player chat, if a cuss word is used from the ArrayList then the player's
     * chat message is not displayed.
     */
    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onPlayerChat(AsyncPlayerChatEvent event) throws EventException {
       
        Player p = event.getPlayer();
		String playerMessage = event.getMessage();
		ArrayList<String> c = new ArrayList<String>(20);
		 c.add("fuck");
		 c.add("shit");
		 c.add("ass");
		 c.add("damn");
		 c.add("penis");
		 c.add("bitch");
		 c.add("weed");
		 c.add("piss");
		 c.add("cunt");
		 c.add("bullocks");
		 c.add("duche");
		 c.add("pussy");
		 c.add("sex");
		 c.add("bastard");
		 c.add("hoe");
		 c.add("hooker");
		 c.add("blunts");
		 
		 //Conversation conversation = null;
		// boolean convo = event.getPlayer().beginConversation(conversation);
		 String e = "Vulgar language is not tolerated on this server keep it clean!";
		 String playerName = p.getName();
      for(int i = 0; i < c.size(); i++){
		
		if(playerMessage.contains(c.get(i))){
			event.setMessage(e);
			event.getRecipients().remove(playerMessage);
			count++;
			if(count >= 2){
				p.kickPlayer("Warning for language");
				
			}
			
			throw new EventException(e);
			
			
			
      } 
		
		
      }
        if(count > 5){
        	p.setBanned(true);
        	bannedPlayers.add(playerName);
        }
    	   
       }
       
       
    
    /*
     * Another example of a event handler. This one will give you the name of
     * the entity you interact with, if it is a Creature it will give you the
     * creature Id.
     */
    @EventHandler
    public void onPlayerInteract(PlayerInteractEntityEvent event) {
        final EntityType entityType = event.getRightClicked().getType();

        event.getPlayer().sendMessage(MessageFormat.format(
                "You interacted with a {0} it has an id of {1}",
                entityType.getName(),
                entityType.getTypeId()));
    }
}
