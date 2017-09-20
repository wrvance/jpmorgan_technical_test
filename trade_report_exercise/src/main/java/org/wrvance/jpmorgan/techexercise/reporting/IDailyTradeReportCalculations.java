package org.wrvance.jpmorgan.techexercise.reporting;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.wrvance.jpmorgan.techexercise.reporting.DailyTradeReport.DailyTradeTotal;
import org.wrvance.jpmorgan.techexercise.trading.entity.TradeEntity;
import org.wrvance.jpmorgan.techexercise.trading.instruction.TradingInstruction;

public interface IDailyTradeReportCalculations {
	
	/**
	 * @param type
	 * @return reverse-ordered list of DailyTradeAmount (most recent day first)
	 */
	public default List<DailyTradeTotal> getDailyTradeTotalByType(List<TradingInstruction> instructions, TradingInstruction.Action type){
		
		List<DailyTradeTotal> dailyTradeAmountList = new ArrayList<DailyTradeTotal>();
		
		List<LocalDate> sortedDateList = instructions.stream().filter(i -> i.getType().equals(type)).map(i -> i.getAdjustedSettlementDate()).distinct().sorted((d1, d2) -> -1 * d1.compareTo(d2)).collect(Collectors.toList());
		
		for(LocalDate date : sortedDateList){
			BigDecimal sum = instructions.stream().filter(i -> i.getAdjustedSettlementDate().equals(date) && i.getType().equals(type)).map(TradingInstruction::getTradeAmount).reduce(BigDecimal.ZERO, (a, b) -> a.add(b));
			dailyTradeAmountList.add(new DailyTradeTotal(date, sum));
		}
		
		return dailyTradeAmountList;
	}
	
	/**
	 * @param type
	 * @return - ascending ordered list, by rank, of entities where 1 is the highest rank (and therefore first)
	 */
	public default List<TradeEntity> rankEntitiesByHighestTradeAmountForSingleInstructionForType(List<TradingInstruction> instructions, TradingInstruction.Action type){
				
		List<TradeEntity> entities = instructions.stream().filter(i -> i.getType().equals(type)).map(i -> i.getEntity()).distinct().collect(Collectors.toList());
	
		HashMap<TradeEntity, BigDecimal> entityMaxTradeValueMap = new HashMap<TradeEntity, BigDecimal>();
		
		for(TradeEntity entity : entities){
			
			BigDecimal maxDailyTradeForEntity = instructions.stream().filter(i -> i.getEntity().equals(entity) && i.getType().equals(type)).map(i -> i.getTradeAmount()).reduce(BigDecimal.ZERO, (a, b) -> a.max(b));
			
			entityMaxTradeValueMap.put(entity,  maxDailyTradeForEntity);
					
		}
		
		List<TradeEntity> orderedEntityList = new ArrayList<TradeEntity>();
		entityMaxTradeValueMap.entrySet().stream().sorted(Map.Entry.<TradeEntity, BigDecimal>comparingByValue().reversed()).forEachOrdered(e -> orderedEntityList.add(e.getKey()));
		return orderedEntityList;
		
		
	}
}
