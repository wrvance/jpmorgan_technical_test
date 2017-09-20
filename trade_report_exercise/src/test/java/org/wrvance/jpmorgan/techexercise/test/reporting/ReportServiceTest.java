package org.wrvance.jpmorgan.techexercise.test.reporting;

import java.time.LocalDate;
import java.time.Month;

import org.junit.Test;
import org.wrvance.jpmorgan.techexercise.reporting.DailyTradeReport;
import org.wrvance.jpmorgan.techexercise.reporting.IDailyTradeReportCalculations;
import org.wrvance.jpmorgan.techexercise.reporting.ReportService;
import org.wrvance.jpmorgan.techexercise.trading.entity.TradeEntity;

import junit.framework.TestCase;

public class ReportServiceTest extends TestCase{

	@Test
	public void testGenerateDailyTradeReportTest(){
		
		IDailyTradeReportCalculations dailyReportCalculationsStub = new DailyTradeReportCalculationsStub();
        
		ReportService reportService = new ReportService(dailyReportCalculationsStub);
		
		DailyTradeReport report = reportService.generateDailyTradeReport();
		
		assertEquals("buyers list should have a date of 5 january", LocalDate.of(2017, Month.JANUARY, 5), report.getDailyTradeTotalBuyersList().get(0).getTradeDate());
        assertEquals("sellers list should have a date of 6 january", LocalDate.of(2017,  Month.JANUARY, 6), report.getDailyTradeTotalSellersList().get(0).getTradeDate());
        assertEquals("beyers entity ranking should have one trade entity - MATX", TradeEntity.MATX, report.getBuyersEntityRankingByHighestTradeAmountOnSingleInstruction().get(0));
        assertEquals("sellers entity ranking should have one trade entity - MATX", TradeEntity.RAD, report.getSellersEntityRankingByHighestTradeAmountOnSingleInstruction().get(0));
		
	}

}
