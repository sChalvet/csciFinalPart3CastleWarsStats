CastleWarsStats-BukkitPlugin
======

This is a simple plug-in for the Bukkit API. It requires a CraftBukkit server.
Bukkit can be found at [http://bukkit.org](http://bukkit.org) 

The goal of this sample plug-in is to provide a base for other plug-ins. 

<p>
This plug-in provides the ability for a player's statistics to be viewed
while online. 
</p>

<p>
Originally this plugin was to serve as the basis of a Maven 3 Archetype for
quickly building Bukkit plug-ins.
</p>

Compilation
-----------

This plugin has a Maven 3 pom.xml and uses Maven to compile. Dependencies are 
therefore managed by Maven. You should be able to build it with Maven by running

    mvn package

a jar will be generated in the target folder. For those unfa1milliar with Maven
it is a build system, see http://maven.apache.org/ for more information.
