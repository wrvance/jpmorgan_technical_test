package org.wrvance.jpmorgan.techexercise.test.trading.instruction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;

import org.junit.Test;
import org.wrvance.jpmorgan.techexercise.currency.Currency;
import org.wrvance.jpmorgan.techexercise.trading.entity.TradeEntity;
import org.wrvance.jpmorgan.techexercise.trading.instruction.TradingInstruction;
import org.wrvance.jpmorgan.techexercise.trading.instruction.TradingInstruction.Action;

import junit.framework.TestCase;

public class TradingInstructionTest extends TestCase {


	@Test
	public void testNullParametersThrowException(){
		//public TradingInstruction(TradeEntity entity, Action action, double agreedFx, Currency currency, LocalDate instructionDate, LocalDate initialSettlementDate, long units, double unitPrice)
		
		boolean exceptionThrown = false;
		
		try{
			new TradingInstruction(null, Action.BUY, 0.5d, Currency.AED, LocalDate.of(2017, Month.AUGUST, 5), LocalDate.of(2017, Month.AUGUST, 5), 100, 100);
		}catch(Exception e){
			exceptionThrown = true;
		}
		
		assertTrue(exceptionThrown);
		
		exceptionThrown = false;
		
		try{
			new TradingInstruction(TradeEntity.DAL, null, 0.5d, Currency.AED, LocalDate.of(2017, Month.AUGUST, 5), LocalDate.of(2017, Month.AUGUST, 5), 100, 100);
		}catch(Exception e){
			exceptionThrown = true;
		}
		
		exceptionThrown = false;
		
		try{
			new TradingInstruction(TradeEntity.DAL, Action.BUY, 0.5d, null, LocalDate.of(2017, Month.AUGUST, 5), LocalDate.of(2017, Month.AUGUST, 5), 100, 100);
		}catch(Exception e){
			exceptionThrown = true;
		}
		
		exceptionThrown = false;
		
		try{
			new TradingInstruction(TradeEntity.DAL, Action.BUY, 0.5d, Currency.AED, null, LocalDate.of(2017, Month.AUGUST, 5), 100, 100);
		}catch(Exception e){
			exceptionThrown = true;
		}
		
		exceptionThrown = false;
		
		try{
			new TradingInstruction(TradeEntity.DAL, Action.BUY, 0.5d, Currency.AED, LocalDate.of(2017, Month.AUGUST, 5), null, 100, 100);
		}catch(Exception e){
			exceptionThrown = true;
		}
	}
	
	@Test
	public void testEqualInstructionAndSettlementDate(){
		
		LocalDate date = LocalDate.of(2017, Month.JANUARY, 1);
		
		boolean exceptionThrown = false;
		
		try{
			new TradingInstruction(TradeEntity.DAL, Action.BUY, 0.5d, Currency.AED, date, date, 100, 100);
		}catch(Exception e){
			exceptionThrown = true;
		}
		
		assertFalse(exceptionThrown);
		
		
	}
	
	@Test
	public void testSettlementDateBeforeInstructionDateThrowsException(){
		
		LocalDate initialSettlementDate = LocalDate.of(2017, Month.JANUARY, 1);
		LocalDate instructionDate = LocalDate.of(2017, Month.JANUARY, 2);
		
		boolean exceptionThrown = false;
		
		try{
			new TradingInstruction(TradeEntity.DAL, Action.BUY, 0.5d, Currency.AED, instructionDate, initialSettlementDate, 100, 100);
		}catch(Exception e){
			exceptionThrown = true;
		}
		
		assertTrue(exceptionThrown);
		
		
		
	}
	
	@Test
	public void testNonPositiveParametersThrowException(){
		
		LocalDate date = LocalDate.of(2017, Month.JANUARY, 1);
		
		boolean exceptionThrown = false;
		
		try{
			new TradingInstruction(TradeEntity.DAL, Action.BUY, 0d, Currency.AED, date, date, 100, 100);
		}catch(Exception e){
			exceptionThrown = true;
		}
		
		assertTrue(exceptionThrown);
		
		exceptionThrown = false;
		
		try{
			new TradingInstruction(TradeEntity.DAL, Action.BUY, -1d, Currency.AED, date, date, 100, 100);
		}catch(Exception e){
			exceptionThrown = true;
		}
		
		assertTrue(exceptionThrown);
		
		exceptionThrown = false;
		
		try{
			new TradingInstruction(TradeEntity.DAL, Action.BUY, 1d, Currency.AED, date, date, 0, 100);
		}catch(Exception e){
			exceptionThrown = true;
		}
		
		assertTrue(exceptionThrown);
		
		exceptionThrown = false;
		
		try{
			new TradingInstruction(TradeEntity.DAL, Action.BUY, 1d, Currency.AED, date, date, -1, 100);
		}catch(Exception e){
			exceptionThrown = true;
		}
		
		assertTrue(exceptionThrown);
		
		exceptionThrown = false;
		
		try{
			new TradingInstruction(TradeEntity.DAL, Action.BUY, 1d, Currency.AED, date, date, 100, 0);
		}catch(Exception e){
			exceptionThrown = true;
		}
		
		assertTrue(exceptionThrown);
		
		exceptionThrown = false;
		
		try{
			new TradingInstruction(TradeEntity.DAL, Action.BUY, 1d, Currency.AED, date, date, 100, -1);
		}catch(Exception e){
			exceptionThrown = true;
		}
		
		assertTrue(exceptionThrown);
		
	}
	
	@Test
	public void testPropertyGetters(){
		
		LocalDate instructionDate = LocalDate.of(2017, Month.JANUARY, 1);
		LocalDate  initialSettlementDate = LocalDate.of(2017, Month.JANUARY, 2);
		

		TradingInstruction instruction = new TradingInstruction(TradeEntity.DAL, Action.BUY, 0.5d, Currency.AED, instructionDate, initialSettlementDate, 100, 150d);

		assertEquals(TradeEntity.DAL, instruction.getEntity());
		assertEquals(Action.BUY, instruction.getAction());
		assertEquals(0.5d, instruction.getAgreedFx());
		assertEquals(Currency.AED, instruction.getCurrency());
		assertEquals(instructionDate, instruction.getInstructionDate());
		assertEquals(initialSettlementDate, instruction.getInitialSettlementDate());
	}
	
	@Test
	public void testGetTradeAmount(){
		double agreedFx = 0.5d;
		long units = 100l;
		double unitPrice = 100d;
		
		BigDecimal expectedTradeAmount = BigDecimal.valueOf(agreedFx).multiply(BigDecimal.valueOf(units)).multiply(BigDecimal.valueOf(unitPrice));
		
		LocalDate date = LocalDate.of(2017, Month.JANUARY, 1);
		
		TradingInstruction instruction = new TradingInstruction(TradeEntity.DAL, Action.BUY, agreedFx, Currency.AED, date, date, units, unitPrice);
		
		assertEquals(expectedTradeAmount, instruction.getTradeAmount());
		
	}
	
	
	


}
