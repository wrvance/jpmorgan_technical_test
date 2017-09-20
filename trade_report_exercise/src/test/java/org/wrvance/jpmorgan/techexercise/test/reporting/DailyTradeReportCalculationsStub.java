package org.wrvance.jpmorgan.techexercise.test.reporting;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.wrvance.jpmorgan.techexercise.reporting.IDailyTradeReportCalculations;
import org.wrvance.jpmorgan.techexercise.reporting.DailyTradeReport.DailyTradeTotal;
import org.wrvance.jpmorgan.techexercise.trading.entity.TradeEntity;
import org.wrvance.jpmorgan.techexercise.trading.instruction.TradingInstruction;

public class DailyTradeReportCalculationsStub implements
		IDailyTradeReportCalculations {

	public DailyTradeReportCalculationsStub() {
	}
	
	@Override
	public List<DailyTradeTotal> getDailyTradeTotalByType(List<TradingInstruction> instructions, TradingInstruction.Action type){
		
		List<DailyTradeTotal> list = new ArrayList<DailyTradeTotal>();
		
		if(TradingInstruction.Action.BUY.equals(type)){
			list.add(new DailyTradeTotal(LocalDate.of(2017, Month.JANUARY, 5), BigDecimal.valueOf(100)));
		}else{
			list.add(new DailyTradeTotal(LocalDate.of(2017, Month.JANUARY, 6), BigDecimal.valueOf(1000)));
		}
		
		return list;
	}
	
	@Override
	public List<TradeEntity> rankEntitiesByHighestTradeAmountForSingleInstructionForType(List<TradingInstruction> instructions, TradingInstruction.Action type){
		
		List<TradeEntity> list = new ArrayList<TradeEntity>();
		
		if(TradingInstruction.Action.BUY.equals(type)){
			list.add(TradeEntity.MATX);
		}else{
			list.add(TradeEntity.RAD);
		}
		
		return list;
	}
	
}
