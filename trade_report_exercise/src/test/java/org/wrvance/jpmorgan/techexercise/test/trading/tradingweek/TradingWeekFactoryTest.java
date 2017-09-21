package org.wrvance.jpmorgan.techexercise.test.trading.tradingweek;

import java.time.DayOfWeek;

import org.junit.Test;
import org.wrvance.jpmorgan.techexercise.currency.Currency;
import org.wrvance.jpmorgan.techexercise.trading.tradingweek.TradingWeek;
import org.wrvance.jpmorgan.techexercise.trading.tradingweek.TradingWeekFactory;

import junit.framework.TestCase;

public class TradingWeekFactoryTest extends TestCase {


	@Test
	public void testAEDWeek(){
		
		TradingWeek AEDTradeWeek = TradingWeekFactory.getTradingWeek(Currency.AED);
		
		assertEquals(DayOfWeek.SUNDAY, AEDTradeWeek.getStartOfWeek());
		assertEquals(DayOfWeek.THURSDAY, AEDTradeWeek.getEndOfWeek());
		
	}
	
	@Test
	public void testSARWeek(){
		
		TradingWeek AEDTradeWeek = TradingWeekFactory.getTradingWeek(Currency.SAR);
		
		assertEquals(DayOfWeek.SUNDAY, AEDTradeWeek.getStartOfWeek());
		assertEquals(DayOfWeek.THURSDAY, AEDTradeWeek.getEndOfWeek());
		
	}
	
	@Test
	public void testAllOtherCurrenciesWeek(){
		
		TradingWeek AEDTradeWeek = TradingWeekFactory.getTradingWeek(Currency.ALL_OTHER_CURRENCIES);
		
		assertEquals(DayOfWeek.MONDAY, AEDTradeWeek.getStartOfWeek());
		assertEquals(DayOfWeek.FRIDAY, AEDTradeWeek.getEndOfWeek());
		
	}
	

}
