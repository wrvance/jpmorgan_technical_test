package org.wrvance.jpmorgan.techexercise.main;

import java.io.PrintStream;
import java.time.LocalDate;
import java.time.Month;

import org.wrvance.jpmorgan.techexercise.currency.Currency;
import org.wrvance.jpmorgan.techexercise.reporting.DailyTradeReportCalculations;
import org.wrvance.jpmorgan.techexercise.reporting.PrintStreamPrintable;
import org.wrvance.jpmorgan.techexercise.reporting.ReportService;
import org.wrvance.jpmorgan.techexercise.trading.entity.TradeEntity;
import org.wrvance.jpmorgan.techexercise.trading.instruction.TradingInstructionDAO;
import org.wrvance.jpmorgan.techexercise.trading.instruction.TradingInstruction.Action;


public class Main 
{
    public static void main( String[] args )
    {
    	LocalDate thursday5January2017 = LocalDate.of(2017, Month.JANUARY, 5);
        LocalDate friday6January2017 = LocalDate.of(2017, Month.JANUARY, 6);
        LocalDate saturday7January2017 = LocalDate.of(2017, Month.JANUARY, 7);
        LocalDate sunday8January2017 = LocalDate.of(2017, Month.JANUARY, 8);
        LocalDate monday9January2017 = LocalDate.of(2017, Month.JANUARY, 9);
        
        double aSingleAgreedFx = 0.25d;
        
        Object[][] tradingInstructionData = new Object[][]{
        		  { TradeEntity.DAL, Action.BUY, aSingleAgreedFx, Currency.AED, thursday5January2017, thursday5January2017, 100, 10.31 }, //trade amount: 257.75, settlement on thursday 5 january 2017
        		  { TradeEntity.DAL, Action.SELL, aSingleAgreedFx, Currency.SAR, friday6January2017, sunday8January2017, 100, 20 }, //trade amount: 500, settlement on sunday  8 january 2017
        		  { TradeEntity.MATX, Action.BUY, aSingleAgreedFx, Currency.ALL_OTHER_CURRENCIES, saturday7January2017, sunday8January2017, 1000, 30 }, //trade amount: 7500, settlement on monday 9 january 2017
        		  { TradeEntity.MATX, Action.SELL, aSingleAgreedFx, Currency.AED, saturday7January2017, monday9January2017, 100, 40 }, //trade amount: 1000, settlement on monday 9 january 2017
        		  { TradeEntity.RAD, Action.BUY, aSingleAgreedFx, Currency.SAR, friday6January2017, monday9January2017, 100, 50 }, //trade amount: 1250, settlement on monday 9 january 2017
        		  { TradeEntity.RAD, Action.SELL, aSingleAgreedFx, Currency.ALL_OTHER_CURRENCIES, friday6January2017, sunday8January2017, 100, 60 }, //trade amount: 1500, settlement on monday 9 january 2017
        		};
        
        
        TradingInstructionDAO.addInstructions(tradingInstructionData);
        
        PrintStream ps = System.out;
    	
    	ReportService reportService = new ReportService(new DailyTradeReportCalculations());
    	
    	PrintStreamPrintable tradeReport = reportService.generateDailyTradeReport();
    	
    	tradeReport.print(ps);
    	
        
    }
}
