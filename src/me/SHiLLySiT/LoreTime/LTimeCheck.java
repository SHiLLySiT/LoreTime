   package me.SHiLLySiT.LoreTime;

   import org.bukkit.Server;
   import org.bukkit.entity.Player;

   public class LTimeCheck implements Runnable {
      public static LoreTime plugin;
      public static Server server = LoreTime.server;
      public static String worldName = LConfig.configWorld;
      private int rangeCheck = LConfig.configInterval * 20; //dynamic range depends on interval
      private boolean handled = false;
   
      public LTimeCheck(LoreTime instance) {
         plugin = instance;
      }
   
   //for reference:
   //Sunrise is around 23000, noon 6000, sunset 13000, and midnight 18000.
   
      @Override
      public void run() {
         if (LConfig.debug) { LLogger.log.info("Checking time..."); }
      
      //time range is check instead of exact to ensure that it is caught
         if (server.getWorld(worldName).getTime() > (18000 - rangeCheck) && server.getWorld(worldName).getTime() < (18000 + rangeCheck)) {
         
         //has the time already been checked?
            if (!handled) {
            
            //flip flag so this doesn't happen again
               handled = true;
            
               if (LConfig.debug) { LLogger.log.info("Date Change!"); }
            
            //where the magic happens
               if (LConfig.configCurrentDay < (LConfig.configDaysInAMonth - 1)) {
                  LConfig.configCurrentDay++;
               } 
               else {
                  LConfig.configCurrentDay = 0;
                        
                  if (LConfig.configCurrentMonth < (LConfig.configMonths.size() - 1)) {
                     LConfig.configCurrentMonth++;
                     //Run all tasks that run every month
                     for(Runnable run : LPluginHelper.runMonth.get(-1))
                        new Thread(run).start();
                     //Run all Tasks that execute this month
                     for(Runnable run : LPluginHelper.runMonth.get(LConfig.configCurrentMonth))
                        new Thread(run).start();
                  }
                  else{
                     LConfig.configYear++;
                     //Run all yearly tasks
                     for(Runnable run : LPluginHelper.runYear)
                        new Thread(run).start();
                     LConfig.configCurrentMonth = 0;
                  }
               }
               //Run all daily tasks
               for(Runnable run : LPluginHelper.runDay.get(-1))
                  new Thread(run).start();
                  //Run all tasks that run this day
               for(Runnable run : LPluginHelper.runDay.get(LConfig.configCurrentDay))
                  new Thread(run).start();
                  
               if (LConfig.configSaveOnDateChange) { LConfig.saveConfig(); }
            } 
            else {
               if (LConfig.debug) { LLogger.log.info("Already changed date!"); }
            }
         
         } 
         else {
         //once outside of time range, reset handled flag
            handled = false;
         }
      }
   }
