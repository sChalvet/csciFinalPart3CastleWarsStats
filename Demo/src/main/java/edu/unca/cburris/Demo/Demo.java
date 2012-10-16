package edu.unca.cburris.Demo;

import org.bukkit.plugin.java.JavaPlugin;

/*
 * This is the main class of the sample plug-in
 */
public class Demo extends JavaPlugin {
	/*
	 * This is called when your plug-in is enabled
	 */
	//NewLogger logger; 
	@Override
	public void onEnable() {
		// save the configuration file
		saveDefaultConfig();
	    //logger = new NewLogger(this);
       // logger.info("Plugin enabled");
		// Create the SampleListener
		new DemoListener(this);

		// set the command executor for sample
		this.getCommand("demo").setExecutor(new DemoCommandExecutor(this));
	}

	/*
	 * This is called when your plug-in shuts down
	 */
	@Override
	public void onDisable() {
		saveDefaultConfig(); 
		
		//log.info("FirstPlugin has been disabled")
	}

}
