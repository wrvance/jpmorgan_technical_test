package org.wrvance.jpmorgan.techexercise.test.reporting;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.wrvance.jpmorgan.techexercise.reporting.DailyTradeReport;
import org.wrvance.jpmorgan.techexercise.reporting.DailyTradeReportPrintingUtility;
import org.wrvance.jpmorgan.techexercise.reporting.ReportService;
import org.wrvance.jpmorgan.techexercise.reporting.DailyTradeReport.DailyTradeTotal;
import org.wrvance.jpmorgan.techexercise.trading.entity.TradeEntity;
import org.wrvance.jpmorgan.techexercise.trading.instruction.TradingInstructionDAO;

import junit.framework.TestCase;


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
		
		//We'll create a no-data report. And just check that the title & section headers are all present and correct.
		List<DailyTradeTotal> nonNullDailyTradeTotalList = new ArrayList<DailyTradeTotal>();
		List<TradeEntity> nonNullTradeEntityList = new ArrayList<TradeEntity>();
		
		DailyTradeReport report = new DailyTradeReport(nonNullDailyTradeTotalList, nonNullDailyTradeTotalList, nonNullTradeEntityList, nonNullTradeEntityList, TradingInstructionDAO.getBaseTradingLocale());
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		PrintStream stream = new PrintStream(outputStream);
	
		report.print(stream);
		
		byte[] outputBytes = outputStream.toByteArray();
		
		String reportOutputString = new String(outputBytes);
		
		Map<ReportSectionHeaderName, Integer> sectionHeaderIndexMap = getSectionHeaderIndices(reportOutputString);
		
		assertNotSame(-1, sectionHeaderIndexMap.get(ReportSectionHeaderName.TITLE));
		assertNotSame(-1, sectionHeaderIndexMap.get(ReportSectionHeaderName.DAILY_INCOMING));
		assertNotSame(-1, sectionHeaderIndexMap.get(ReportSectionHeaderName.DAILY_OUTGOING));
		assertNotSame(-1, sectionHeaderIndexMap.get(ReportSectionHeaderName.INCOMING_ENTITY_RANKING));
		assertNotSame(-1, sectionHeaderIndexMap.get(ReportSectionHeaderName.OUTGOING_ENTITY_RANKING));
		
		assertTrue(sectionHeaderIndexMap.get(ReportSectionHeaderName.TITLE) < sectionHeaderIndexMap.get(ReportSectionHeaderName.DAILY_INCOMING) &&
				sectionHeaderIndexMap.get(ReportSectionHeaderName.DAILY_INCOMING) < sectionHeaderIndexMap.get(ReportSectionHeaderName.DAILY_OUTGOING) &&
				sectionHeaderIndexMap.get(ReportSectionHeaderName.DAILY_OUTGOING) < sectionHeaderIndexMap.get(ReportSectionHeaderName.INCOMING_ENTITY_RANKING) &&
				sectionHeaderIndexMap.get(ReportSectionHeaderName.INCOMING_ENTITY_RANKING) < sectionHeaderIndexMap.get(ReportSectionHeaderName.OUTGOING_ENTITY_RANKING));
		
				
	}
	
	@Test
	public void testPrint_3_checkDailyTotalsSection(){
		
		List<DailyTradeTotal> dailyTradeTotalList = new ArrayList<DailyTradeTotal>();
		dailyTradeTotalList.add(new DailyTradeTotal(LocalDate.of(2017, Month.JANUARY, 6), BigDecimal.valueOf(1000)));
		dailyTradeTotalList.add(new DailyTradeTotal(LocalDate.of(2017, Month.JANUARY, 5), BigDecimal.valueOf(100)));
		
		String correctSectionString = "     2017-01-06: $1,000.00\r\n\r\n     2017-01-05: $100.00\r\n\r\n";
		String dailyTradeTotalSection = getDailyTradeTotalSectionAsString(dailyTradeTotalList);
		
		assertEquals(correctSectionString, dailyTradeTotalSection);
	}
	
	
	@Test
	public void testPrint_4_checkTradeEntityRankingSection(){
		
		List<TradeEntity> tradeEntities = new ArrayList<TradeEntity>();
		tradeEntities.add(TradeEntity.MATX);
		tradeEntities.add(TradeEntity.DAL);
		tradeEntities.add(TradeEntity.RAD);
		
		String correctSectionString = "     Rank 1: MATX\r\n\r\n     Rank 2: DAL\r\n\r\n     Rank 3: RAD\r\n\r\n";
		
		String tradeEntitySection = getEntityRankingSectionAsString(tradeEntities);
		
		assertEquals(correctSectionString, tradeEntitySection);
		
	}
	
	@Test
	public void testPrint_5_testReportSectionHeadersAndBody(){
				
		ReportService reportService = new ReportService(new DailyTradeReportCalculationsStub());
		
		DailyTradeReport report = reportService.generateDailyTradeReport();
		
		String incomingDailyTradeSection = getDailyTradeTotalSectionAsString(report.getDailyTradeTotalSellersList());
		String outgoingDailyTradeSection = getDailyTradeTotalSectionAsString(report.getDailyTradeTotalBuyersList());
		String incomingEntityRankingSection = getEntityRankingSectionAsString(report.getSellersEntityRankingByHighestTradeAmountOnSingleInstruction());
		String outgoingEntityRankingSection = getEntityRankingSectionAsString(report.getBuyersEntityRankingByHighestTradeAmountOnSingleInstruction());
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		PrintStream printStream = new PrintStream(outputStream);
		
		report.print(printStream);
		
		byte[] outputBytes = outputStream.toByteArray();
		
		String reportAsString = new String(outputBytes);
		
		Map<ReportSectionHeaderName, Integer> sectionHeaderIndexMap = getSectionHeaderIndices(reportAsString);
		
		int incomingDailyTradeSectionIndex = reportAsString.indexOf(incomingDailyTradeSection);
		int outgoingDailyTradeSectionIndex = reportAsString.indexOf(outgoingDailyTradeSection);
		int incomingEntityRankingSectionIndex = reportAsString.indexOf(incomingEntityRankingSection);
		int outgoingEntityRankingSectionIndex = reportAsString.indexOf(outgoingEntityRankingSection);
		
		assertTrue(sectionHeaderIndexMap.get(ReportSectionHeaderName.DAILY_INCOMING) < incomingDailyTradeSectionIndex &&
				incomingDailyTradeSectionIndex < sectionHeaderIndexMap.get(ReportSectionHeaderName.DAILY_OUTGOING) &&
				sectionHeaderIndexMap.get(ReportSectionHeaderName.DAILY_OUTGOING) < outgoingDailyTradeSectionIndex &&
				outgoingDailyTradeSectionIndex < sectionHeaderIndexMap.get(ReportSectionHeaderName.INCOMING_ENTITY_RANKING) &&
				sectionHeaderIndexMap.get(ReportSectionHeaderName.INCOMING_ENTITY_RANKING) < incomingEntityRankingSectionIndex &&
				incomingEntityRankingSectionIndex < sectionHeaderIndexMap.get(ReportSectionHeaderName.OUTGOING_ENTITY_RANKING) &&
				sectionHeaderIndexMap.get(ReportSectionHeaderName.OUTGOING_ENTITY_RANKING) < outgoingEntityRankingSectionIndex);
		
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
	
	private enum ReportSectionHeaderName{
		TITLE,
		DAILY_INCOMING,
		DAILY_OUTGOING,
		INCOMING_ENTITY_RANKING,
		OUTGOING_ENTITY_RANKING
	};
	
	private enum ReportSectionBodyName{
		DAILY_TRADE_TOTALS,
		ENTITY_RANKING
	}
	
	private String getSectionHeaderAsString(ReportSectionHeaderName section){
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		PrintStream printStream = new PrintStream(outputStream);
		
		switch(section){
			case TITLE:	DailyTradeReportPrintingUtility.printReportTitleSection(printStream); break;
			case DAILY_INCOMING: DailyTradeReportPrintingUtility.printDailyIncomingSectionHeader(printStream, TradingInstructionDAO.getBaseTradingLocale()); break;
			case DAILY_OUTGOING: DailyTradeReportPrintingUtility.printDailyOutgoingSectionHeader(printStream, TradingInstructionDAO.getBaseTradingLocale());	break;
			case INCOMING_ENTITY_RANKING: DailyTradeReportPrintingUtility.printIncomingEntityRankingSectionHeader(printStream, TradingInstructionDAO.getBaseTradingLocale()); break;
			case OUTGOING_ENTITY_RANKING: DailyTradeReportPrintingUtility.printOutgoingEntityRankingSectionHeader(printStream, TradingInstructionDAO.getBaseTradingLocale()); break;
		}
		
		byte[] outputBytes = outputStream.toByteArray();
		
		String outputString = new String(outputBytes);
		
		printStream.close();
		
		return outputString;
		
	}
	
	private String getDailyTradeTotalSectionAsString(List<DailyTradeTotal> totalsList){
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		PrintStream printStream = new PrintStream(outputStream);
		
		DailyTradeReportPrintingUtility.printDailyTradeTotals(printStream, TradingInstructionDAO.getBaseTradingLocale(), totalsList);
		
		byte[] outputBytes = outputStream.toByteArray();
		
		String outputString = new String(outputBytes);
		
		printStream.close();
		
		return outputString;
	}
	
	private String getEntityRankingSectionAsString(List<TradeEntity> rankingList){
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		PrintStream printStream = new PrintStream(outputStream);
		
		DailyTradeReportPrintingUtility.printEntityRankings(printStream, rankingList);
		
		byte[] outputBytes = outputStream.toByteArray();
		
		String outputString = new String(outputBytes);
		
		printStream.close();
		
		return outputString;
		
	}
	
	private Map<ReportSectionHeaderName, Integer> getSectionHeaderIndices(String report){
		
		Map<ReportSectionHeaderName, Integer> sectionHeaderIndexMap = new HashMap<ReportSectionHeaderName, Integer>();
		
		String title = getSectionHeaderAsString(ReportSectionHeaderName.TITLE);
		String dailyIncomingSectionHeader = getSectionHeaderAsString(ReportSectionHeaderName.DAILY_INCOMING);
		String dailyOutgoingSectionHeader = getSectionHeaderAsString(ReportSectionHeaderName.DAILY_OUTGOING);
		String incomingEntitySectionHeader = getSectionHeaderAsString(ReportSectionHeaderName.INCOMING_ENTITY_RANKING);
		String outgoingEntitySectionHeader = getSectionHeaderAsString(ReportSectionHeaderName.OUTGOING_ENTITY_RANKING);
		
		int titleIndex = report.indexOf(title);
		int dailyIncomingSectionHeaderIndex = report.indexOf(dailyIncomingSectionHeader);
		int dailyOutgoingSectionHeaderIndex = report.indexOf(dailyOutgoingSectionHeader);
		int incomingEntitySectionHeaderIndex = report.indexOf(incomingEntitySectionHeader);
		int outgoingEntitySectionHeaderIndex = report.indexOf(outgoingEntitySectionHeader);
		
		sectionHeaderIndexMap.put(ReportSectionHeaderName.TITLE, titleIndex);
		sectionHeaderIndexMap.put(ReportSectionHeaderName.DAILY_INCOMING, dailyIncomingSectionHeaderIndex);
		sectionHeaderIndexMap.put(ReportSectionHeaderName.DAILY_OUTGOING, dailyOutgoingSectionHeaderIndex);
		sectionHeaderIndexMap.put(ReportSectionHeaderName.INCOMING_ENTITY_RANKING, incomingEntitySectionHeaderIndex);
		sectionHeaderIndexMap.put(ReportSectionHeaderName.OUTGOING_ENTITY_RANKING, outgoingEntitySectionHeaderIndex);
		
		return sectionHeaderIndexMap;
	}
}
