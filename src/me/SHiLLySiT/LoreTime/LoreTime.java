   package me.SHiLLySiT.LoreTime;

   import java.util.logging.Logger;
   import org.bukkit.Server;
   import org.bukkit.command.Command;
   import org.bukkit.command.CommandSender;
   import org.bukkit.event.Event;
   import org.bukkit.event.Event.Priority;
   import org.bukkit.plugin.PluginManager;
   import org.bukkit.plugin.java.JavaPlugin;

   public class LoreTime extends JavaPlugin {
      private String version;
      private LTimeCheck timeCheck = null;
      private LCommands commands = null;
      private LPermissionsCore pc = new LPermissionsCore();
      private LPlayerListener playerListener = new LPlayerListener(this);
      public static boolean hasPermissions = false;
      public static Server server;
   
      public void onEnable() {
         LLogger.initialize(this, Logger.getLogger("Minecraft"));
         version = this.getDescription().getVersion();
         server = this.getServer();
      
         LConfig.config = getConfiguration();
         LConfig.init();
      
         pc.setupPermissions();
      
         this.commands = new LCommands(this);
       //For reference:
       //300 * 20L = 300 seconds (1L = 1 tick, 20 ticks per second)
         this.timeCheck = new LTimeCheck(this);
         this.getServer().getScheduler().scheduleAsyncRepeatingTask(this, this.timeCheck, 20L * 5, 20L * LConfig.configInterval);
      
         LLogger.info("version " + version + " is enabled!");
       
         PluginManager pm = getServer().getPluginManager();
         pm.registerEvent(Event.Type.PLAYER_JOIN, playerListener, Priority.Low, this);
         LPluginHelper.addDailyEvent(
               new Runnable() { 
                  public void run() {
                     for (org.bukkit.entity.Player player : getServer().getOnlinePlayers())
                        if(player != null)
                           player.sendMessage(LConfig.color + LCommands.displayString());
                     LLogger.info("Sent date change to players.");
                  }}, -1);
      }
   
      public void onDisable() {
         LConfig.saveConfig();
         LLogger.info(" has been disabled!");
      }
   
      public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
         return commands.executeCommand(sender, cmd, commandLabel, args);
      }
   
   
   }
