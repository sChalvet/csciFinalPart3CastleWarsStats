package edu.unca.cburris.bukkit.castlewarsstats;

import java.text.MessageFormat;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Statistic;
import org.bukkit.conversations.Conversation;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventException;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.entity.Player;
import com.avaje.ebean.SqlUpdate;
import com.avaje.ebeaninternal.server.type.DataBind;

import edu.unca.cburris.bukkit.castlewarsstats.*;
import edu.unca.cburris.bukkit.castlewarsstats.util.ListStore;

/*
 * This is a sample event listener
 */
public class CWSListener implements Listener {
    private final CastleWarsStats plugin;
    private int deaths, deathIncrement, damagedealt, damageTaken, kills,pvpkills, PlayerEntityId;
    private String PlayersName;
    public ListStore bannedPlayers;
    /*
     * This listener needs to know about the plugin which it came from
     */
    public CWSListener(CastleWarsStats plugin) {
        // Register the listener
       // plugin.getServer().getPluginManager().registerEvents(this, plugin);
        
        this.plugin = plugin;
       
    }

       
       
    
    /*
     * Another example of a event handler. This one will give you the name of
     * the entity you interact with, if it is a Creature it will give you the
     * creature Id.
     */
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerInteract(PlayerInteractEntityEvent event) {
        final EntityType entityType = event.getRightClicked().getType();
        event.getPlayer().sendMessage(MessageFormat.format(
                "You interacted with a {0} it has an id of {1}",
                entityType.getName(),
                entityType.getTypeId()));
      

        
        		
    }
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        PlayerEntityId = event.getPlayer().getEntityId();
      
        PlayersName= event.getPlayer().getName();
        
        Player p = event.getPlayer();
        p.sendMessage(ChatColor.DARK_AQUA + "type /stat reg "+ PlayersName + "to record your stats.");
        		
    }
    
    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerExpChangeEvent(PlayerExpChangeEvent event) {
    	
    	try {
    	Player plr = event.getPlayer(); 				//get the player 
    	int experiencePoints = plr.getTotalExperience();  // get the total experience points
    	 String plrName = plr.getName();					//get the player's name 
        //plr.sendMessage(plrName + "your xp changed by: " + event.getAmount() + "your total xp is :" + experiencePoints); // test message 
        
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
        
    	}//end try
    	catch(NullPointerException e){
    		Player plr = event.getPlayer();
    		String ply = plr.getName();
    		
    		plr.sendMessage("Your stats are not being recorded. Please register with our database by typing" + "/stat reg "+ ply);
    		
    		
    	}
        
    }
    
   /* @EventHandler
    public void onEntityDeath(EntityDeathEvent event) throws NullPointerException{
    	
    		event.getEntityType()
    	try{	
    		if(event.getEntity().getEntityId() != PlayerEntityId){
    			plugin.getLogger().info("killerid is :" + event.getEntity().getKiller().getEntityId());
    			plugin.getLogger().info("killer name :" + event.getEntity().getKiller().getName());
        		if(event.getEntity().getKiller().getEntityId() == PlayerEntityId){
        			kills++;
        			String ePlayerName = event.getEntity().getKiller().getName();
        			
        			Stats statClass = plugin.getDatabase().find(Stats.class).where().ieq("playerName", ePlayerName).findUnique();	
                   	if(statClass == null){
                   		return;
                   	}
                    statClass.setKills(kills);
                    plugin.getDatabase().save(statClass);
        			
        		}
    	}
    	} 	catch (NullPointerException e){
    		e.printStackTrace();
    		//}
    		}
    	
    }*/
    
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerDeathEvent(PlayerDeathEvent event){
    	//deathIncrement = 0;
    	
    	try {
    		
    		deathIncrement++;
        	Player plr = event.getEntity().getPlayer();
       	
        	
       	    String plrName = plr.getName();
        	
        	plr.sendMessage(plrName + "your deaths changed by: "+ deathIncrement  + "entity is"+ plr.getEntityId()); // test message 
        	Stats statClassD = plugin.getDatabase().find(Stats.class).where().ieq("playerName", plrName).findUnique();	
           	
            statClassD.setDeaths(deathIncrement);
            plugin.getDatabase().save(statClassD);
        	/*if(deathIncrement == 2){
        		
        		//setDeaths(deathIncrement);
        		plr.sendMessage(plrName + "your deaths changed by: "+ deaths  + "entity is"+ plr.getEntityId()); // test message 
        		
        	}*/
    	}//end try  
    	catch (NullPointerException e){
    		Player plr = event.getEntity().getPlayer();
    		String ply = plr.getName();
    		
    		plr.sendMessage("Your stats are not being recorded. Please register with our database by typing" + "/stat reg "+ ply);
    		
    		//Player pk = event.getEntity().getKiller();
    		//if(pk.getName() != null){
    		//pk.sendMessage("Your stats are not being recorded. To register with our database type /stat reg playerName");
    		//}
    		}
    	try{
    	    Player pk = event.getEntity().getKiller();
    	    if(pk == null){
    	    	return;
    	    }
    	    plugin.getLogger().info("Killer is:" + event.getEntity().getKiller());
       	    String pkName = pk.getName();
       	    
       	    pvpkills++;
       	    Stats statClass = plugin.getDatabase().find(Stats.class).where().ieq("playerName", pkName).findUnique();	
       	  
       	    statClass.setKills(pvpkills);
       	    plugin.getDatabase().save(statClass);
       	    
       	    
    	}
    	catch (NullPointerException e){
    		e.printStackTrace();
    		//}
    		}
        
    } 
    
    public void setDeaths(int dea){
      	
   	 
    	deaths = deaths + 1;
    	
		
    	deathIncrement = 0;
    }
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerDamageEntity(EntityDamageByEntityEvent event) throws NullPointerException{
    				
    	
    				/*plugin.getLogger().info("UNIQUE ID is :" + event.getEntity().getEntityId());
        			plugin.getLogger().info("Damager is:" + event.getDamager().toString());
        			plugin.getLogger().info("Damager id is :" + event.getDamager().getUniqueId());*/
    	
    				//plugin.getLogger().info("Damager is CraftPlayer:" + event.getDamager().equals(CraftPlayer));
    			//	plugin.getLogger().info("Damager isnt Craft:" + (!event.getDamager().equals(CraftPlayer)));
    				//plugin.getLogger().info("Is Valid:" + event.getDamager().isValid());
    				//plugin.getLogger().info("Id is : " + event.getDamager().getEntityId() + "EntId= " + PlayerEntityId);
    				
        			if (event.getDamager().getEntityId() == PlayerEntityId){
        				
        				int damaged = event.getDamage();
        				damagedealt = damaged + damagedealt;
        				//plugin.getLogger().info("damaged " + damaged);
        				//damagedealt= damagedealt +1;
        				//plugin.getLogger().info("damagedealt " + damagedealt);
        				String playerName = event.getDamager().getType().getName();
        				//plugin.getLogger().info("playerName" + PlayersName);
        				Stats statClass = plugin.getDatabase().find(Stats.class).where().ieq("playerName", PlayersName).findUnique();	
        				//plugin.getLogger().info("statClass" + statClass);
        		       	if(statClass == null){
        		       		return;
        		       	}
        	       	    statClass.setDamage(damagedealt);
        	       	    plugin.getDatabase().save(statClass);
        				
    				
        			}
    				
        				else if (event.getDamager().getEntityId() != PlayerEntityId){
						
        			   
        			
        				int damageT = event.getDamage();
        				 damageTaken = damageTaken + damageT;
        				// plugin.getLogger().info("damage taken is : " + damageTaken);
        				 Stats statClass = plugin.getDatabase().find(Stats.class).where().ieq("playerName", PlayersName).findUnique();	
         				//plugin.getLogger().info("statClass" + statClass);
         		       	if(statClass == null){
         		       		return;
         		       	}
         	       	    statClass.setDamagetaken(damageTaken);
         	       	    plugin.getDatabase().save(statClass);
         				
        			//do nothing?	
        			}
        				else {   
        					
        					try{ 
        				//damageTaken = event.getDamage();
        				 //plugin.getLogger().info("damage taken is : "+ damageTaken);
        			}
    			
    			
    			catch(NullPointerException e){
    				e.printStackTrace();
    			}//end catch 
        				}
    			
    			
    			
    			
    			//end if 
    			}

  /* @EventHandler
   public void onPlayerHeldItemEvent(PlayerItemHeldEvent event){
	   
	   			Player pl =	event.getPlayer();
	   			String playerName = pl.getName();
	   			kills++;
	   			
	   			String pk = pl.getKiller().getName();
	   
	   			Stats statClass = plugin.getDatabase().find(Stats.class).where().ieq("playerName", pk).findUnique();	
	   			
	   			if(statClass == null){
	            	throw new NullPointerException("Please register with our database. /stat reg playerName");
	            }
	   			
	   			
	            statClass.setKills(kills);
	            plugin.getDatabase().save(statClass);
	            
	            plugin.getLogger().info("killer is : "+ pk);
	            plugin.getLogger().info("# of kills : "+ kills);
	            
	           
	   
	   
   }*/
 

/*    
   @EventHandler
   public void onBlockDamageEvent(BlockDamageEvent event){
    			
    			String item =	event.getItemInHand().toString();
    			Player ply = event.getPlayer();
    			String playerName = ply.getName();
    			
    			ply.sendMessage("You destroyed " + event.getBlock().getType() +"with " + item + "in your hand"+ playerName);
    				
    	}
  */ 
   
   
   
   
    }
    
    

