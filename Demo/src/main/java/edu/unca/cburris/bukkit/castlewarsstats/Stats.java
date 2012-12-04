package edu.unca.cburris.bukkit.castlewarsstats;

import com.avaje.ebean.validation.Length;
import com.avaje.ebean.validation.NotEmpty;
import com.avaje.ebean.validation.NotNull;

import javax.persistence.*;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@Entity()
@Table(name = "g_stats")

public class Stats{
	@Id
	private String id;
	@NotNull
	private String playerName;
	@NotNull
	private int achievements;
	@NotNull
	private int xp;
	@NotNull
	private int deaths;
	
	public void setId(String id){
		this.id = id;		
	}
	
	public String getId() {
		return id;
	}
	
	
	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}


	public Player getPlayer(){
		return Bukkit.getServer().getPlayer(playerName);
	}
	
	public void setPlayer(Player player){
		this.playerName = player.getName(); 
	}

	public int getXp() {
		return xp;
	}

	public void setXp(int xp) {
		this.xp = xp;
	}

	public int getAchievements() {
		return achievements;
	}

	public void setAchievements(int achievements) {
		this.achievements = achievements;
	}

	public int getDeaths() {
		return deaths;
	}

	public void setDeaths(int deaths) {
		this.deaths = deaths;
	}

	

}
