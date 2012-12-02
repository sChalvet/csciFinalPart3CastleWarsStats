package edu.unca.cburris.bukkit.castlewars;

import java.text.MessageFormat;
import java.util.ArrayList;

import org.bukkit.Statistic;
import org.bukkit.conversations.Conversation;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventException;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.yaml.snakeyaml.Loader;

import com.avaje.ebean.SqlUpdate;
import com.avaje.ebeaninternal.server.type.DataBind;

import edu.unca.cburris.bukkit.castlewars.*;
import edu.unca.cburris.bukkit.castlewars.util.ListStore;

/*
 * This is a sample event listener
 */
public class CWSListener implements Listener {
    private final CastleWars plugin;
    private int count, deaths, deathIncrement = 0;
    public ListStore bannedPlayers;
    private Player player;
   
    

    /*
     * This listener needs to know about the plugin which it came from
     */
    public CWSListener(CastleWars plugin) {
        // Register the listener
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        
        this.plugin = plugin;
       
    }

    
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onPlayerLogin(PlayerLoginEvent event) {
       String playerName = event.getPlayer().getName();
       
       if(plugin.bannedPlayers.contains(playerName)){
    	   event.setKickMessage("You are banned from this server!");
    	   event.setResult(Result.KICK_BANNED);
    	   
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
   
    @EventHandler
    public void onPlayerExpChangeEvent(PlayerExpChangeEvent event) {
    	Player plr = event.getPlayer(); 				//get the player 
    	int experiencePoints = plr.getTotalExperience();  // get the total experience points
    	 String plrName = plr.getName();					//get the player's name 
        plr.sendMessage(plrName + "your xp changed by: " + event.getAmount() + "your total xp is :" + experiencePoints); // test message 
        
        /*
         * get the database by a unique playerName 
         * since this is an event listener when a player's experience changes the set the Xp column for that particular row to the updated experiencePoints.
         * and finally save the changes to the database. the two lines below update the SQL database however a regular sql update doesn't work...
         * 
         */
        
       Stats statClass = plugin.getDatabase().find(Stats.class).where().ieq("playerName", plr.getName()).findUnique();	
       
        statClass.setXp(experiencePoints);
        plugin.getDatabase().save(statClass);
        
        /* Raw SQL update attempt */
        /*String dbID = statClass.getId();
        String sql = "Update" + dbID + "SET xp=" + experiencePoints + "WHERE playerName=" + plr.getName() + "AND Id=" + plr.getName();
        SqlUpdate sqlUpdate = plugin.getDatabase().createNamedSqlUpdate(sql);
        sqlUpdate.execute();*/
        
      
       /* 
        *UPDATE table_name
        *SET column1=value, column2=value2,...
        *WHERE some_column=some_value
        */ 
    }
    @EventHandler
    public void onPlayerDeathEvent(PlayerDeathEvent event){
    	//deathIncrement = 0;
    	
    	Player plr = event.getEntity().getPlayer();
    	 
    	
    	 String plrName = plr.getName();					//get the player's name 
    
        
        	//deaths = deaths + 1;
        	deathIncrement++;
        	
        	plr.sendMessage(plrName + "your deaths changed by: "+ deathIncrement  + "entity is"+ plr.getEntityId()); // test message 
        	/*Stats statClass = plugin.getDatabase().find(Stats.class).where().ieq("playerName", plrName).findUnique();	
           	
           	statClass.setDeaths(deathIncrement);
            plugin.getDatabase().save(statClass);*/
        	if(deathIncrement == 2){
        		
        		setDeaths(deathIncrement);
        		plr.sendMessage(plrName + "your deaths changed by: "+ deaths  + "entity is"+ plr.getEntityId()); // test message 
        		Stats statClass = plugin.getDatabase().find(Stats.class).where().ieq("playerName", plrName).findUnique();	
               	
                statClass.setDeaths(deaths);
                plugin.getDatabase().save(statClass);
        	}
        		
        	
        
    } 
    
    public void setDeaths(int dea){
      	
   	 
    	deaths = deaths + 1;
    	
		
    	deathIncrement = 0;
    }
    /*@EventHandler
    public void onPlayerRespawnEvent(PlayerRespawnEvent event){
    	Player plr = event.getPlayer();
    	String plrName = plr.getName();
    	Stats statClass = plugin.getDatabase().find(Stats.class).where().ieq("playerName", plr.getName()).findUnique();	
        
    	
    	*/
    	// plr.sendMessage(plrName + "your deaths changed by: "+ deaths + totalDeaths + "entity is"+ plr.getEntityId()); // test message 
                
                /*
                 * get the database by a unique playerName 
                 * since this is an event listener when a player's experience changes the set the Xp column for that particular row to the updated experiencePoints.
                 * and finally save the changes to the database. the two lines below update the SQL database however a regular sql update doesn't work...
                 * 
                 */
          /*      
        		plr.sendMessage(plrName + "your deaths changed by: "+ deaths+ "totalDeaths ="+ totalDeaths + "entity is"+ plr.getEntityId()); // test message 
               
                statClass.setDeaths(totalDeaths);
                plugin.getDatabase().save(statClass);
        	
        	
        
    } */
    
    	
    }
    
    

