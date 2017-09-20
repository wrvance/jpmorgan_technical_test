package org.wrvance.jpmorgan.techexercise.reporting;

import java.io.PrintStream;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

import org.wrvance.jpmorgan.techexercise.reporting.DailyTradeReport.DailyTradeTotal;
import org.wrvance.jpmorgan.techexercise.trading.entity.TradeEntity;

public class DailyTradeReportPrintingUtility {
	
	public static final String reportTitle = "                      Daily Trade Report";
	public static final String separator   = "--------------------------------------------------------------------------";
	public static final String noTradingDataMessage = "     NO TRADING DATA!!";
	
	public static void printReportTitleSection(PrintStream printStream){
		
		printStream.println(reportTitle);
		printStream.println();
		printStream.println(separator);
		
	}
	
	public static void printDailyIncomingSectionHeader(PrintStream printStream, Locale reportLocale){
		printStream.println();
		printStream.println("Daily Totals - Incoming (" + Currency.getInstance(reportLocale).getDisplayName() + ")");
		printStream.println();
	}
	
	public static void printDailyOutgoingSectionHeader(PrintStream printStream, Locale reportLocale){
		printStream.println(separator);
		printStream.println();
		printStream.println("Daily Totals - Outgoing (" + Currency.getInstance(reportLocale).getDisplayName() + ")");
		printStream.println();
	}
	
	public static void printOutgoingEntityRankingSectionHeader(PrintStream printStream, Locale reportLocale){
		printStream.println(separator);
		printStream.println();
		printStream.println("Entity Ranking By Highest Trade Value Per Single Instruction (OUTGOING)");
		printStream.println();
	}
	
	public static void printIncomingEntityRankingSectionHeader(PrintStream printStream, Locale reportLocale){
		printStream.print(separator);
		printStream.println();
		printStream.println("Entity Ranking By Highest Trade Value Per Single Instruction (INCOMING)");
		printStream.println();
	}
	
	public static void printDailyTradeTotals(PrintStream printStream, Locale reportLocale, List<DailyTradeTotal> dailyTradeTotals){
		
		if(dailyTradeTotals.size() == 0){
			printStream.println(noTradingDataMessage); 
			printStream.println();
		}else{
			dailyTradeTotals.forEach(dt -> { 
				printStream.print("     ");
				dt.print(printStream, reportLocale); 
				printStream.println();
			});	
		}
		
	}
	
	public static void printEntityRankings(PrintStream printStream, List<TradeEntity> rankedEntities){
		
		if(rankedEntities.size() == 0){
			printStream.println(noTradingDataMessage);
			printStream.println();
		}else{
			int ranking = 1;
			
			for(TradeEntity entity : rankedEntities){
				printStream.print("     ");
				printStream.println("Rank " + ranking + ": " + entity);
				printStream.println();
				ranking++;
			}
		}
	
	}

}
