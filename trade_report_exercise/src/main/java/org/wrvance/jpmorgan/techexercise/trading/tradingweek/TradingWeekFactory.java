package org.wrvance.jpmorgan.techexercise.trading.tradingweek;

import java.time.DayOfWeek;

import org.wrvance.jpmorgan.techexercise.currency.Currency;


public class TradingWeekFactory {

	public static TradingWeek getTradingWeek(Currency currency){
		
		DayOfWeek workWeekBeginning = null;
		DayOfWeek workWeekEnd = null;
		
		if(currency.equals(Currency.AED) || currency.equals(Currency.SAR)){
			workWeekBeginning = DayOfWeek.SUNDAY;
			workWeekEnd = DayOfWeek.THURSDAY;
		}else{
			workWeekBeginning = DayOfWeek.MONDAY;
			workWeekEnd = DayOfWeek.FRIDAY;
		}
		
		return new TradingWeek(workWeekBeginning, workWeekEnd);
		
	}
}
