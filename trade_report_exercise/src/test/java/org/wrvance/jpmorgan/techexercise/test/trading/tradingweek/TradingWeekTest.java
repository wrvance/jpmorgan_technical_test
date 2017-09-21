package org.wrvance.jpmorgan.techexercise.test.trading.tradingweek;

import java.time.DayOfWeek;
import java.util.List;

import org.junit.Test;
import org.wrvance.jpmorgan.techexercise.trading.tradingweek.TradingWeek;

import junit.framework.TestCase;

public class TradingWeekTest extends TestCase {


	@Test
	public void testNullParametersThrowsException(){
		
		boolean exceptionThrown = false;
		
		try{
			new TradingWeek(null, DayOfWeek.FRIDAY);
		}catch(Exception e){
			exceptionThrown = true;
		}
		
		assertTrue(exceptionThrown);
		
		exceptionThrown = false;
		
		try{
			new TradingWeek(DayOfWeek.MONDAY, null);
		}catch(Exception e){
			exceptionThrown = true;
		}
		
	}
	
	@Test
	public void testWeekStartsAndEndsOnSameDayThrowsException(){
		
		boolean exceptionThrown = false;
		
		try{
			new TradingWeek(DayOfWeek.WEDNESDAY, DayOfWeek.WEDNESDAY);
		}catch(Exception e){
			exceptionThrown = true;
		}
		
		assertTrue(exceptionThrown);
		
	}
	
	@Test
	public void testPropertyGetters(){
		
		TradingWeek tradeWeek = new TradingWeek(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY);
		
		assertEquals(DayOfWeek.MONDAY, tradeWeek.getStartOfWeek());
		assertEquals(DayOfWeek.WEDNESDAY, tradeWeek.getEndOfWeek());
	}
	
	@Test
	public void testGetWeekDays_1_startsSunday(){
		
		TradingWeek tradeWeek = new TradingWeek(DayOfWeek.SUNDAY, DayOfWeek.WEDNESDAY);
		
		List<Integer> days = tradeWeek.getWeekDays();
		
		assertEquals(4, days.size());
		assertTrue(days.contains(DayOfWeek.SUNDAY.getValue()));
		assertTrue(days.contains(DayOfWeek.MONDAY.getValue()));
		assertTrue(days.contains(DayOfWeek.TUESDAY.getValue()));
		assertTrue(days.contains(DayOfWeek.WEDNESDAY.getValue()));
		
	}
	
	@Test
	public void testGetWeekDays_2_spansSunday(){

		TradingWeek tradeWeek = new TradingWeek(DayOfWeek.SATURDAY, DayOfWeek.MONDAY);
		
		List<Integer> days = tradeWeek.getWeekDays();
		
		assertEquals(3, days.size());
		assertTrue(days.contains(DayOfWeek.SATURDAY.getValue()));
		assertTrue(days.contains(DayOfWeek.SUNDAY.getValue()));
		assertTrue(days.contains(DayOfWeek.MONDAY.getValue()));
		
	}
	
	@Test
	public void testGetWeeekDays_3_doesNotSpanOrBeginOnSunday(){
		
		TradingWeek tradeWeek = new TradingWeek(DayOfWeek.WEDNESDAY, DayOfWeek.SUNDAY);
		
		List<Integer> days = tradeWeek.getWeekDays();
		
		assertEquals(5, days.size());
		assertTrue(days.contains(DayOfWeek.WEDNESDAY.getValue()));
		assertTrue(days.contains(DayOfWeek.THURSDAY.getValue()));
		assertTrue(days.contains(DayOfWeek.FRIDAY.getValue()));
		assertTrue(days.contains(DayOfWeek.SATURDAY.getValue()));
		assertTrue(days.contains(DayOfWeek.SUNDAY.getValue()));
		
	}
	

}
