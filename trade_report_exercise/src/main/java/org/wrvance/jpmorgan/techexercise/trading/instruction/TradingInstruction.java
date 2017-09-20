package org.wrvance.jpmorgan.techexercise.trading.instruction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import org.wrvance.jpmorgan.techexercise.currency.Currency;
import org.wrvance.jpmorgan.techexercise.trading.entity.TradeEntity;
import org.wrvance.jpmorgan.techexercise.trading.tradingweek.TradingWeekFactory;

public class TradingInstruction {
	
	public enum Action{
		BUY,
		SELL
	}
	
	
	private final LocalDate adjustedSettlementDate;
	private final Double agreedFx;
	private final Currency currency;
	private final TradeEntity entity;
	private final LocalDate initialSettlementDate;
	private final LocalDate instructionDate;
	private final Action action;
	private final Double unitPrice;
	private final Long units;
	

	public TradingInstruction(TradeEntity entity, Action action, double agreedFx, Currency currency, LocalDate instructionDate, LocalDate initialSettlementDate, long units, double unitPrice) {
		

		if(instructionDate.compareTo(initialSettlementDate) > 0){
			throw new IllegalArgumentException("instruction date cannot be after the initial settlement date");
		}
		
		if(agreedFx <= 0 || units <= 0 || unitPrice <= 0){
			throw new IllegalArgumentException("agreedFx, units and unitPrice must all be positive and non-zero");
		}
		
		
		this.entity = Objects.requireNonNull(entity);
		this.action = Objects.requireNonNull(action);
		this.agreedFx = agreedFx;
		this.currency = Objects.requireNonNull(currency);
		this.instructionDate = Objects.requireNonNull(instructionDate);
		this.initialSettlementDate = Objects.requireNonNull(initialSettlementDate);
		adjustedSettlementDate = SettlementDateAdjustor.getAdjustedSettlementDate(initialSettlementDate, TradingWeekFactory.getTradingWeek(currency));
		this.units = units;
		this.unitPrice = unitPrice;
	
	}


	public LocalDate getAdjustedSettlementDate() {
		return adjustedSettlementDate;
	}


	public Double getAgreedFx() {
		return agreedFx;
	}


	public Currency getCurrency() {
		return currency;
	}


	public TradeEntity getEntity() {
		return entity;
	}


	public LocalDate getInitialSettlementDate() {
		return initialSettlementDate;
	}


	public LocalDate getInstructionDate() {
		return instructionDate;
	}


	public BigDecimal getTradeAmount(){
		
		return BigDecimal.valueOf(agreedFx).multiply(BigDecimal.valueOf(units)).multiply(BigDecimal.valueOf(unitPrice));
		
	}


	public Action getType() {
		return action;
	}
	
	public Double getUnitPrice() {
		return unitPrice;
	}


	public Long getUnits() {
		return units;
	}

}
