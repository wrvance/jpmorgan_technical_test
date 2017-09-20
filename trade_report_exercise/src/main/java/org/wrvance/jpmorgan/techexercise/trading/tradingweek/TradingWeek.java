package org.wrvance.jpmorgan.techexercise.trading.tradingweek;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class TradingWeek {

	public static final int NUM_DAYS_IN_WEEK = 7;
	private final DayOfWeek endOfWeek;
	private final DayOfWeek startOfWeek;
	
	public TradingWeek(DayOfWeek startOfWeek, DayOfWeek endOfWeek) {
		if(startOfWeek.equals(endOfWeek)){
			throw new IllegalArgumentException("week cannot start and end on the same day");
		}
		this.startOfWeek = startOfWeek;
		this.endOfWeek = endOfWeek;
	}

	public DayOfWeek getEndOfWeek() {
		return endOfWeek;
	}

	public DayOfWeek getStartOfWeek() {
		return startOfWeek;
	}
	
	
	/**
	 * @return - a list containing the days of the work week. The list's values represent corresponding to the enum values in java.time.DayOfWeek
	 */
	public List<Integer> getWeekDays(){
		
		List<Integer> weekDayList = new ArrayList<Integer>();
		
		if(this.spansOrBeginsOnSunday()){
			int numDaysBeforeSunday = NUM_DAYS_IN_WEEK - startOfWeek.getValue();
			int numDaysAfterSunday  = NUM_DAYS_IN_WEEK - (NUM_DAYS_IN_WEEK - endOfWeek.getValue());
			int numDaysInWeek = numDaysBeforeSunday + numDaysAfterSunday + 1;
			
			int currentDay = startOfWeek.getValue();
			
			for(int i = 0 ; i < numDaysInWeek; i++){
				weekDayList.add(currentDay > 7 ? currentDay - 7 : currentDay);
				currentDay++;
			}
		
		}else{
			weekDayList = IntStream.rangeClosed(startOfWeek.getValue(), endOfWeek.getValue()).boxed().collect(Collectors.toList());
		}
		
		return weekDayList;
	}
	
	private boolean spansOrBeginsOnSunday(){
		return startOfWeek.getValue() > endOfWeek.getValue();
	}
	
}
