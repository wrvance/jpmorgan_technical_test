package org.wrvance.jpmorgan.techexercise.trading.instruction;

import java.time.LocalDate;
import java.util.List;

import org.wrvance.jpmorgan.techexercise.trading.tradingweek.TradingWeek;

public class SettlementDateAdjustor {
	
	public static LocalDate getAdjustedSettlementDate(LocalDate initialSettlementDate, TradingWeek tradeWeek){
		
		LocalDate adjustedSettlementDate = initialSettlementDate;
		
		List<Integer> weekDays = tradeWeek.getWeekDays();
		
		boolean initialSettlementDateIsDuringTradeWeek = false;
		
		if(weekDays.contains(initialSettlementDate.getDayOfWeek().getValue())){
			initialSettlementDateIsDuringTradeWeek = true;
		}
		
		if(!initialSettlementDateIsDuringTradeWeek){
			adjustedSettlementDate = moveSettlementDateToBeginningOfNextTradeWeek(initialSettlementDate, tradeWeek);	
		}
	
		
		return adjustedSettlementDate;
		
	}
	
	private static LocalDate moveSettlementDateToBeginningOfNextTradeWeek(LocalDate initialSettlementDate, TradingWeek tradeWeek){
		
		long daysUntilBeginningOfCurrencyWeek = 0l;
		
		if(tradeWeek.getStartOfWeek().getValue() > initialSettlementDate.getDayOfWeek().getValue()){
			daysUntilBeginningOfCurrencyWeek = tradeWeek.getStartOfWeek().getValue() - initialSettlementDate.getDayOfWeek().getValue();
		}else{
			daysUntilBeginningOfCurrencyWeek = TradingWeek.NUM_DAYS_IN_WEEK - (initialSettlementDate.getDayOfWeek().getValue() - tradeWeek.getStartOfWeek().getValue());
		}
		
		return initialSettlementDate.plusDays(daysUntilBeginningOfCurrencyWeek);
		
	}

}
