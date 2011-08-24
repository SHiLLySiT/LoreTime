   package me.SHiLLySiT.LoreTime;
   
   import java.util.HashMap;
   import java.util.ArrayList;
	
   public class LPluginHelper
   {
      protected static HashMap<Integer, ArrayList<Runnable>> runDay = new HashMap<Integer, ArrayList<Runnable>>();
      protected static HashMap<Integer, ArrayList<Runnable>> runMonth = new HashMap<Integer, ArrayList<Runnable>>();
      protected static ArrayList<Runnable> runYear = new ArrayList<Runnable>();
      public static void addDailyEvent(Runnable run, int day) throws IllegalArgumentException
      {
         if(day > LConfig.configDaysInAMonth)
            throw new IllegalArgumentException("Argument 'day' is greater than config's day in a month.");
         runDay.get(day).add(run);
      }
      public static void addMonthyEvent(Runnable run, int month) throws IllegalArgumentException
      {
         if(month > LConfig.configMonths.size() - 1)
            throw new IllegalArgumentException("Argument 'month' is greater than config's months in a year.");
         runMonth.get(month).add(run);
      }
      public static void addMonthyEvent(Runnable run, String month) throws IllegalArgumentException
      {
         if(!(LConfig.configMonths.contains(month)))
            throw new IllegalArgumentException("Argument 'month' is not in list of months!");
         runMonth.get(LConfig.configMonths.lastIndexOf(month)).add(run);
      }
      public static void addYearlyEvent(Runnable run)
      {
         runYear.add(run);
      }
      public static void removeDailyEvent(Runnable run, int day)
      {
         runDay.get(day).remove(run);	
      }
      public static void removeMonthlyEvent(Runnable run, int month)
      {
         runMonth.get(month).remove(run);	
      }
      public static void removeMonthlyEvent(Runnable run, String month)
      {
         runMonth.get(LConfig.configMonths.lastIndexOf(month)).remove(run);	
      }
      public static void removeYearlyEvent(Runnable run)
      {
         runYear.remove(run);	
      }
   }