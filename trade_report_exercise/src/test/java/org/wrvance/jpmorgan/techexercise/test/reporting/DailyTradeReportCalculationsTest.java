package org.wrvance.jpmorgan.techexercise.test.reporting;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import org.junit.Test;
import org.wrvance.jpmorgan.techexercise.currency.Currency;
import org.wrvance.jpmorgan.techexercise.reporting.DailyTradeReport.DailyTradeTotal;
import org.wrvance.jpmorgan.techexercise.reporting.DailyTradeReportCalculations;
import org.wrvance.jpmorgan.techexercise.reporting.IDailyTradeReportCalculations;
import org.wrvance.jpmorgan.techexercise.trading.entity.TradeEntity;
import org.wrvance.jpmorgan.techexercise.trading.instruction.TradingInstruction.Action;
import org.wrvance.jpmorgan.techexercise.trading.instruction.TradingInstructionDAO;

import junit.framework.TestCase;

public class DailyTradeReportCalculationsTest extends TestCase {

	@SuppressWarnings("rawtypes")
	@Test
	public void testGetDailyTradeTotalByType_testNoData(){
		
		TradingInstructionDAO.clearInstructions();
		
		IDailyTradeReportCalculations tradeReportCalculations = new DailyTradeReportCalculations();
		
		List dailyTradeList = tradeReportCalculations.getDailyTradeTotalByType(TradingInstructionDAO.getAllInstructions(), Action.BUY);
		
		assertTrue("The returned list should not be null", dailyTradeList != null);
		assertTrue("The returned list should be empty", dailyTradeList.size() == 0);
		
	}
	
	@Test
	public void testGetDailyTradeTotalByType(){
		
		//TEST STRATEGY
		//We want to test BUY on 2 dates - to make sure they are ordered correctly in the returned list. 
		//On each date, test both actions (BUY, SELL) are represented to ensure actions are filtered out correctly.
        //And, on each date, ensure more than 1 BUY instruction, to test trading value summation.
		
    	LocalDate thursday5January2017 = LocalDate.of(2017, Month.JANUARY, 5);
        LocalDate friday6January2017 = LocalDate.of(2017, Month.JANUARY, 6);
         
        double aSingleAgreedFx = 0.25d;
        
        Object[][] tradingInstructionData = new Object[][]{
        		  { TradeEntity.DAL, Action.BUY, aSingleAgreedFx, Currency.AED, thursday5January2017, thursday5January2017, 100, 10 },  //trade amount: 250, settlement on thursday 5 january 2017
        		  { TradeEntity.DAL, Action.BUY, aSingleAgreedFx, Currency.AED, thursday5January2017, thursday5January2017, 100, 10 },  //trade amount: 250, settlement on thursday 5 january 2017
        		  { TradeEntity.DAL, Action.SELL, aSingleAgreedFx, Currency.AED, thursday5January2017, thursday5January2017, 100, 10.31 }, //SELL - so will be filtered out
        		  { TradeEntity.DAL, Action.BUY, aSingleAgreedFx, Currency.ALL_OTHER_CURRENCIES, friday6January2017, friday6January2017, 100, 20 }, //trade amount: 500, settlement on friday 6 january 2017
        		  { TradeEntity.DAL, Action.BUY, aSingleAgreedFx, Currency.ALL_OTHER_CURRENCIES, friday6January2017, friday6January2017, 100, 20 }, //trade amount: 500, settlement on friday 6 january 2017
        		  { TradeEntity.DAL, Action.SELL, aSingleAgreedFx, Currency.ALL_OTHER_CURRENCIES, friday6January2017, friday6January2017, 1000, 30 } //SELL - so will be filtered out
        };
        
        TradingInstructionDAO.addInstructions(tradingInstructionData);
        
        IDailyTradeReportCalculations tradeReportCalculations = new DailyTradeReportCalculations();
        
		List<DailyTradeTotal> dailyTradeTotals = tradeReportCalculations.getDailyTradeTotalByType(TradingInstructionDAO.getAllInstructions(), Action.BUY);
		
		assertEquals(2, dailyTradeTotals.size());
		assertEquals(friday6January2017, dailyTradeTotals.get(0).getTradeDate());
		assertEquals(thursday5January2017, dailyTradeTotals.get(1).getTradeDate());
		assertEquals(0, BigDecimal.valueOf(1000).compareTo(dailyTradeTotals.get(0).getTradeAmount()));
		assertEquals(0, BigDecimal.valueOf(500).compareTo(dailyTradeTotals.get(1).getTradeAmount()));
		
	}
	
	@Test
	public void testRankEntitiesByHighestTradeAmountForSingleInstructionForType_testNoData(){
		
		TradingInstructionDAO.clearInstructions();
		
		IDailyTradeReportCalculations tradeReportCalculations = new DailyTradeReportCalculations();
		
		List<TradeEntity> tradeEntities = tradeReportCalculations.rankEntitiesByHighestTradeAmountForSingleInstructionForType(TradingInstructionDAO.getAllInstructions(), Action.BUY);
		
		assertNotNull("The list should NOT be null", tradeEntities);
		assertTrue(tradeEntities.size() == 0);
		
	}
	
	/*
	@Test
	public void testRankEntitiesByHighestTradeAmountForSingleInstructionForType_testEntitiesWithSameTradeAmount(){
		//Don't worry about this for now - assuming that the amounts will be different!
	}
	*/
	
	@Test
	public void testRankEntitiesByHighestTradeAmountForSingleInstructionForType_testNormalConditions(){
		
		//TEST STRATEGY
		//We want to test BUY on 2 dates - to make sure they are ordered correctly in the returned list. 
		//On each date, test both actions (BUY, SELL) are represented to ensure actions are filtered out correctly.
        //And, on each date, ensure more than 1 BUY instruction, to test trading value summation.
		
		
    	LocalDate thursday5January2017 = LocalDate.of(2017, Month.JANUARY, 5);
        LocalDate friday6January2017 = LocalDate.of(2017, Month.JANUARY, 6);
         
        double aSingleAgreedFx = 0.25d;
        
        Object[][] tradingInstructionData = new Object[][]{
        		  { TradeEntity.DAL, Action.BUY, aSingleAgreedFx, Currency.AED, thursday5January2017, thursday5January2017, 100, 10 },  //trade amount: 250, settlement on thursday 5 january 2017
        		  { TradeEntity.MATX, Action.SELL, aSingleAgreedFx, Currency.AED, thursday5January2017, thursday5January2017, 100, 15 },  //trade amount: 250, settlement on thursday 5 january 2017
        		  { TradeEntity.RAD, Action.SELL, aSingleAgreedFx, Currency.AED, thursday5January2017, thursday5January2017, 100, 20 }, //SELL - so will be filtered out
        		  { TradeEntity.DAL, Action.BUY, aSingleAgreedFx, Currency.ALL_OTHER_CURRENCIES, friday6January2017, friday6January2017, 100, 25 }, //trade amount: 500, settlement on friday 6 january 2017
        		  { TradeEntity.MATX, Action.BUY, aSingleAgreedFx, Currency.ALL_OTHER_CURRENCIES, friday6January2017, friday6January2017, 100, 30 }, //trade amount: 500, settlement on friday 6 january 2017
        		  { TradeEntity.RAD, Action.SELL, aSingleAgreedFx, Currency.ALL_OTHER_CURRENCIES, friday6January2017, friday6January2017, 1000, 35 } //SELL - so will be filtered out
        };
		
        TradingInstructionDAO.addInstructions(tradingInstructionData);
        
        IDailyTradeReportCalculations tradeReportCalculations = new DailyTradeReportCalculations();
        
        List<TradeEntity> tradeEntities = tradeReportCalculations.rankEntitiesByHighestTradeAmountForSingleInstructionForType(TradingInstructionDAO.getAllInstructions(), Action.BUY);
        assertEquals("number of ranked trade entities for buying should be 2", 2, tradeEntities.size());
        assertEquals("The first in the list should be MATX", TradeEntity.MATX, tradeEntities.get(0));
        assertEquals("The second in the list should be DAL", TradeEntity.DAL, tradeEntities.get(1));
		
	}


}
