package org.wrvance.jpmorgan.techexercise.reporting;

import java.util.List;

import org.wrvance.jpmorgan.techexercise.reporting.DailyTradeReport.DailyTradeTotal;
import org.wrvance.jpmorgan.techexercise.trading.entity.TradeEntity;
import org.wrvance.jpmorgan.techexercise.trading.instruction.TradingInstructionDAO;
import org.wrvance.jpmorgan.techexercise.trading.instruction.TradingInstruction.Action;

public class ReportService {

	
	private final IDailyTradeReportCalculations tradeReportCalculations;
	
	public ReportService(IDailyTradeReportCalculations tradeReportCalculations) {

		this.tradeReportCalculations = tradeReportCalculations;
		
	}

	
	public DailyTradeReport generateDailyTradeReport(){
		
		List<DailyTradeTotal> outgoingTradeList = tradeReportCalculations.getDailyTradeTotalByType(TradingInstructionDAO.getAllInstructions(), Action.BUY);
		List<DailyTradeTotal> incomingTradeList = tradeReportCalculations.getDailyTradeTotalByType(TradingInstructionDAO.getAllInstructions(), Action.SELL);
		List<TradeEntity> buyersEntityRanking = tradeReportCalculations.rankEntitiesByHighestTradeAmountForSingleInstructionForType(TradingInstructionDAO.getAllInstructions(), Action.BUY);
		List<TradeEntity> sellersEntityRanking = tradeReportCalculations.rankEntitiesByHighestTradeAmountForSingleInstructionForType(TradingInstructionDAO.getAllInstructions(), Action.SELL);
		
		return new DailyTradeReport(outgoingTradeList, incomingTradeList, buyersEntityRanking, sellersEntityRanking, TradingInstructionDAO.getBaseTradingLocale());
	
	}
	
}
