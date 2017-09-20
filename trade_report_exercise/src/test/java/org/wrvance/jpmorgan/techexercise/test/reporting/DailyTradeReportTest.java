package org.wrvance.jpmorgan.techexercise.test.reporting;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.wrvance.jpmorgan.techexercise.reporting.DailyTradeReport;
import org.wrvance.jpmorgan.techexercise.trading.entity.TradeEntity;
import org.wrvance.jpmorgan.techexercise.trading.instruction.TradingInstructionDAO;

import junit.framework.TestCase;
import static org.wrvance.jpmorgan.techexercise.reporting.DailyTradeReport.DailyTradeTotal;;

public class DailyTradeReportTest extends TestCase{

	@Test
	public void testNullConstructorParametersThrowsException(){
		
		List<DailyTradeTotal> nonNullDailyTradeTotalList = new ArrayList<DailyTradeTotal>();
		List<TradeEntity> nonNullTradeEntityList = new ArrayList<TradeEntity>();
		
		DailyTradeReport report = null;
		
		try{
			report = new DailyTradeReport(null, nonNullDailyTradeTotalList, nonNullTradeEntityList, nonNullTradeEntityList, TradingInstructionDAO.getBaseTradingLocale());
		}catch(Exception e){
			
		}finally{
			assertNull("DailyTradeReport should be null", report);
		}
		
		
		try{
			report = new DailyTradeReport(nonNullDailyTradeTotalList, null, nonNullTradeEntityList, nonNullTradeEntityList, TradingInstructionDAO.getBaseTradingLocale());
		}catch(Exception e){
			
		}finally{
			assertNull("DailyTradeReport should be null", report);
		}
		
		
		try{
			report = new DailyTradeReport(nonNullDailyTradeTotalList, nonNullDailyTradeTotalList, null, nonNullTradeEntityList, TradingInstructionDAO.getBaseTradingLocale());
		}catch(Exception e){
			
		}finally{
			assertNull("DailyTradeReport should be null", report);
		}
		
		try{
			report = new DailyTradeReport(nonNullDailyTradeTotalList, nonNullDailyTradeTotalList, nonNullTradeEntityList, null, TradingInstructionDAO.getBaseTradingLocale());
		}catch(Exception e){
			
		}finally{
			assertNull("DailyTradeReport should be null", report);
		}
		
		try{
			report = new DailyTradeReport(nonNullDailyTradeTotalList, nonNullDailyTradeTotalList, nonNullTradeEntityList, nonNullTradeEntityList, null);
		}catch(Exception e){
			
		}finally{
			assertNull("DailyTradeReport should be null", report);
		}
		
		
		
	}
	
	@Test
	public void testPropertyGetters(){
		
		List<DailyTradeTotal> dailyTradeTotalBuyersList = new ArrayList<DailyTradeTotal>();
		dailyTradeTotalBuyersList.add(new DailyTradeTotal(LocalDate.of(2017, Month.JANUARY, 5), BigDecimal.valueOf(100)));
		
		List<DailyTradeTotal> dailyTradeTotalSellersList = new ArrayList<DailyTradeTotal>();
		dailyTradeTotalSellersList.add(new DailyTradeTotal(LocalDate.of(2017, Month.JANUARY, 6), BigDecimal.valueOf(1000)));
		
		List<TradeEntity> buyersEntityRankingList = new ArrayList<TradeEntity>();
		buyersEntityRankingList.add(TradeEntity.DAL);
		
		List<TradeEntity> sellersEntityRankingList = new ArrayList<TradeEntity>();
		sellersEntityRankingList.add(TradeEntity.MATX);
		
		DailyTradeReport report = new DailyTradeReport(dailyTradeTotalBuyersList, dailyTradeTotalSellersList, buyersEntityRankingList, sellersEntityRankingList, TradingInstructionDAO.getBaseTradingLocale());
		
		assertEquals("getDailyTradeTotalBuyersList should return the correct data", LocalDate.of(2017, Month.JANUARY, 5), report.getDailyTradeTotalBuyersList().get(0).getTradeDate());
		assertEquals("getDailyTradeTotalSellersList should return the correct data", LocalDate.of(2017, Month.JANUARY, 6), report.getDailyTradeTotalSellersList().get(0).getTradeDate());
		assertEquals("getBuyersEntityRankingByHighestTradeAmountOnSingleInstruction should return the correct data", TradeEntity.DAL, report.getBuyersEntityRankingByHighestTradeAmountOnSingleInstruction().get(0));
		assertEquals("getSellersEntityRankingByHighestTradeAmountOnSingleInstruction should return the correct data", TradeEntity.MATX, report.getSellersEntityRankingByHighestTradeAmountOnSingleInstruction().get(0));
		
	}
	
	@Test
	public void testPrint_1_checkNoDataHandling(){
		
		List<DailyTradeTotal> nonNullDailyTradeTotalList = new ArrayList<DailyTradeTotal>();
		List<TradeEntity> nonNullTradeEntityList = new ArrayList<TradeEntity>();
		
		DailyTradeReport report = new DailyTradeReport(nonNullDailyTradeTotalList, nonNullDailyTradeTotalList, nonNullTradeEntityList, nonNullTradeEntityList, TradingInstructionDAO.getBaseTradingLocale());
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		PrintStream stream = new PrintStream(outputStream);
		
		report.print(stream);
		
		byte[] outputBytes = outputStream.toByteArray();
		
		String outputString = new String(outputBytes);
		
		String noTradingDataString = "NO TRADING DATA!!";
		
		assertEquals("For empty data there should be 4 counts of 'NO TRADING DATA' in the report printout", 4, count(outputString, noTradingDataString));
				
		
		stream.close();
	}
	
	@Test
	public void testPrint_2_checkForTitleAndSectionHeaders(){
		
		List<DailyTradeTotal> nonNullDailyTradeTotalList = new ArrayList<DailyTradeTotal>();
		List<TradeEntity> nonNullTradeEntityList = new ArrayList<TradeEntity>();
		
		DailyTradeReport report = new DailyTradeReport(nonNullDailyTradeTotalList, nonNullDailyTradeTotalList, nonNullTradeEntityList, nonNullTradeEntityList, TradingInstructionDAO.getBaseTradingLocale());
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		PrintStream stream = new PrintStream(outputStream);
		
		report.print(stream);
		
		byte[] outputBytes = outputStream.toByteArray();
		
		String outputString = new String(outputBytes);
		
		String noTradingDataString = "NO TRADING DATA!!";
		
		assertEquals("For empty data there should be 4 counts of 'NO TRADING DATA' in the report printout", 4, count(outputString, noTradingDataString));
				
		
		stream.close();
		
	}
	
	@Test
	public void testPrint_3_checkOutgoingDailyTotals(){
		
		
		
	}
	
	@Test
	public void testPrint_4_checkIncomingDailyTotals(){
		
	}
	
	@Test
	public void testPrint_5_checkIncomingRanking(){
		
	}
	
	@Test
	public void testPrint_6_checkOutgoingRanking(){
		
	}
	
	
	private static int count(final String string, final String substring)
	{
	     int count = 0;
	     int idx = 0;

	     while ((idx = string.indexOf(substring, idx)) != -1)
	     {
	        idx++;
	        count++;
	     }

	     return count;
	}
	
	

}
