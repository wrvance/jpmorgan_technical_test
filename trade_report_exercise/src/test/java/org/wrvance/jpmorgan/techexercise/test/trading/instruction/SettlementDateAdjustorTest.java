package org.wrvance.jpmorgan.techexercise.test.trading.instruction;

import java.time.LocalDate;
import java.time.Month;

import org.junit.Test;
import org.wrvance.jpmorgan.techexercise.currency.Currency;
import org.wrvance.jpmorgan.techexercise.trading.instruction.SettlementDateAdjustor;
import org.wrvance.jpmorgan.techexercise.trading.tradingweek.TradingWeek;
import org.wrvance.jpmorgan.techexercise.trading.tradingweek.TradingWeekFactory;

import junit.framework.TestCase;

public class SettlementDateAdjustorTest extends TestCase {

	
	@Test
	public void testSettlementDateWithinStandardTradeWeek(){
		
		//initial settlement Date is on Wednesday
		//Trading week is Monday - Friday
		//Settlement date should be on the wednesday
		
		LocalDate wednesday4January2017 = LocalDate.of(2017, Month.JANUARY, 4);
		TradingWeek tradeWeek = TradingWeekFactory.getTradingWeek(Currency.ALL_OTHER_CURRENCIES);
		
		assertEquals(wednesday4January2017, SettlementDateAdjustor.getAdjustedSettlementDate(wednesday4January2017, tradeWeek));
		
	}
	
	@Test
	public void testSettlementDateOutsideStandardTradeWeek(){
		
		//initial settlement date is on Saturday
		//Trading week is Monday - Friday
		//Settlement date should be on the following monday
		
		LocalDate saturday7January2017 = LocalDate.of(2017, Month.JANUARY, 7);
		LocalDate monday9January2017 = LocalDate.of(2017, Month.JANUARY, 9);
		
		TradingWeek tradeWeek = TradingWeekFactory.getTradingWeek(Currency.ALL_OTHER_CURRENCIES);
		
		assertEquals(monday9January2017, SettlementDateAdjustor.getAdjustedSettlementDate(saturday7January2017, tradeWeek));
	}
	
	@Test
	public void testSettlementDateWithinNonStandardTradeWeek(){
		
		//initial settlement Date is on Wednesday
		//Trading week is Sunday - Thursday
		//Settlement date should be on the wednesday
		
		LocalDate wednesday4January2017 = LocalDate.of(2017, Month.JANUARY, 4);
		TradingWeek tradeWeek = TradingWeekFactory.getTradingWeek(Currency.SAR);
		
		assertEquals(wednesday4January2017, SettlementDateAdjustor.getAdjustedSettlementDate(wednesday4January2017, tradeWeek));
		
		
	}
	
	@Test
	public void testSettlementDateOutideNonStandardTradeWeek(){
		
		//initial settlement date is on Saturday
		//Trading week is Sunday - Thursday
		//Settlement date should be on the following sunday
		
		LocalDate saturday7January2017 = LocalDate.of(2017, Month.JANUARY, 7);
		LocalDate sunday8January2017 = LocalDate.of(2017, Month.JANUARY, 8);
		
		TradingWeek tradeWeek = TradingWeekFactory.getTradingWeek(Currency.SAR);
		
		assertEquals(sunday8January2017, SettlementDateAdjustor.getAdjustedSettlementDate(saturday7January2017, tradeWeek));
		
	}

}
