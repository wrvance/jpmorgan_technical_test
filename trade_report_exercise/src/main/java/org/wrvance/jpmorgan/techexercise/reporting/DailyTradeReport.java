package org.wrvance.jpmorgan.techexercise.reporting;

import java.io.PrintStream;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import org.wrvance.jpmorgan.techexercise.trading.entity.TradeEntity;

public class DailyTradeReport implements PrintStreamPrintable {
	
	public static class DailyTradeTotal {
		
		private final BigDecimal tradeAmount;
		private final LocalDate tradeDate;
		
		public DailyTradeTotal(LocalDate tradeDate, BigDecimal tradeAmount){
			this.tradeDate = tradeDate;
			this.tradeAmount = tradeAmount;
		}

		public BigDecimal getTradeAmount() {
			return tradeAmount;
		}

		public LocalDate getTradeDate() {
			return tradeDate;
		}
		

		public void print(PrintStream printStream, Locale locale) {
			BigDecimal scaledTradeAmount = tradeAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
			printStream.println(tradeDate.toString() + ": " + NumberFormat.getCurrencyInstance(locale).format(scaledTradeAmount));
			
		}
		
	}

	private final List<TradeEntity> buyersEntityRankingByHighestTradeAmountOnSingleInstruction;
	private final List<DailyTradeTotal> dailyTradeTotalBuyersList;
	
	private final List<DailyTradeTotal> dailyTradeTotalSellersList;
	private final Locale reportLocale;
	private final List<TradeEntity> sellersEntityRankingByHighestTradeAmountOnSingleInstruction;
	
	public DailyTradeReport(List<DailyTradeTotal> dailyTradeTotalBuyersList, List<DailyTradeTotal> dailyTradeTotalSellersList, List<TradeEntity> buyersEntityRanking, List<TradeEntity> sellersEntityRanking, Locale locale) {
		this.dailyTradeTotalBuyersList = Objects.requireNonNull(dailyTradeTotalBuyersList);
		this.dailyTradeTotalSellersList = Objects.requireNonNull(dailyTradeTotalSellersList);
		buyersEntityRankingByHighestTradeAmountOnSingleInstruction = Objects.requireNonNull(buyersEntityRanking);
		sellersEntityRankingByHighestTradeAmountOnSingleInstruction = Objects.requireNonNull(sellersEntityRanking);
		reportLocale = Objects.requireNonNull(locale);
	}

	public List<DailyTradeTotal> getDailyTradeTotalBuyersList() {
		return dailyTradeTotalBuyersList;
	}

	public List<DailyTradeTotal> getDailyTradeTotalSellersList() {
		return dailyTradeTotalSellersList;
	}

	public List<TradeEntity> getBuyersEntityRankingByHighestTradeAmountOnSingleInstruction() {
		return buyersEntityRankingByHighestTradeAmountOnSingleInstruction;
	}
	
	public List<TradeEntity> getSellersEntityRankingByHighestTradeAmountOnSingleInstruction() {
		return sellersEntityRankingByHighestTradeAmountOnSingleInstruction;
	}

	@Override
	public void print(PrintStream printStream){
		
		DailyTradeReportPrintingUtility.printReportTitleSection(printStream);
		
		DailyTradeReportPrintingUtility.printDailyIncomingSectionHeader(printStream, reportLocale);
		
		DailyTradeReportPrintingUtility.printDailyTradeTotals(printStream, reportLocale, dailyTradeTotalSellersList);
		
		DailyTradeReportPrintingUtility.printDailyOutgoingSectionHeader(printStream, reportLocale);
		
		DailyTradeReportPrintingUtility.printDailyTradeTotals(printStream, reportLocale, dailyTradeTotalBuyersList);
		
		DailyTradeReportPrintingUtility.printOutgoingEntityRankingSectionHeader(printStream, reportLocale);
		
		DailyTradeReportPrintingUtility.printEntityRankings(printStream, buyersEntityRankingByHighestTradeAmountOnSingleInstruction);
		
		DailyTradeReportPrintingUtility.printIncomingEntityRankingSectionHeader(printStream, reportLocale);
		
		DailyTradeReportPrintingUtility.printEntityRankings(printStream, sellersEntityRankingByHighestTradeAmountOnSingleInstruction);
		
	}
	

}
