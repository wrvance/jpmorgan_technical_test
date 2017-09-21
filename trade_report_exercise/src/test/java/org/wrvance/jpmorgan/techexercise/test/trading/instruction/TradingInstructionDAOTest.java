package org.wrvance.jpmorgan.techexercise.test.trading.instruction;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import org.junit.Test;
import org.wrvance.jpmorgan.techexercise.currency.Currency;
import org.wrvance.jpmorgan.techexercise.trading.entity.TradeEntity;
import org.wrvance.jpmorgan.techexercise.trading.instruction.TradingInstruction;
import org.wrvance.jpmorgan.techexercise.trading.instruction.TradingInstructionDAO;
import org.wrvance.jpmorgan.techexercise.trading.instruction.TradingInstruction.Action;

import junit.framework.TestCase;

public class TradingInstructionDAOTest extends TestCase {

	
	@Test
	public void testClearInstructions(){
		
    	LocalDate thursday5January2017 = LocalDate.of(2017, Month.JANUARY, 5);
        LocalDate friday6January2017 = LocalDate.of(2017, Month.JANUARY, 6);
         
        double aSingleAgreedFx = 0.25d;
        
        Object[][] tradingInstructionData = new Object[][]{
        		  { TradeEntity.DAL, Action.BUY, aSingleAgreedFx, Currency.AED, thursday5January2017, friday6January2017, 100, 10 }
        };
        
        TradingInstructionDAO.addInstructions(tradingInstructionData);
        
        int initialSize = TradingInstructionDAO.getAllInstructions().size();
        
        TradingInstructionDAO.clearInstructions();
        
        int finalSize = TradingInstructionDAO.getAllInstructions().size();
        
        assertFalse(initialSize == finalSize);
		
	}
	
	@Test
	public void testBaseTradingLocale(){
		
		assertEquals(java.util.Locale.US, TradingInstructionDAO.getBaseTradingLocale());
	}
	
	@Test
	public void testAddInstructions(){
		
    	LocalDate thursday5January2017 = LocalDate.of(2017, Month.JANUARY, 5);
        LocalDate friday6January2017 = LocalDate.of(2017, Month.JANUARY, 6);
         
        double aSingleAgreedFx = 0.25d;
        
        Object[][] tradingInstructionData = new Object[][]{
        		  { TradeEntity.DAL, Action.BUY, aSingleAgreedFx, Currency.AED, thursday5January2017, friday6January2017, 100, 10 }
        };
        
        TradingInstructionDAO.addInstructions(tradingInstructionData);
        
        List<TradingInstruction> tradingInstructionList = TradingInstructionDAO.getAllInstructions();
        
        assertEquals(1, tradingInstructionList.size());
        TradingInstruction instruction = tradingInstructionList.get(0);
        assertEquals(TradeEntity.DAL, instruction.getEntity());
        assertEquals(Action.BUY, instruction.getAction());
        assertEquals(aSingleAgreedFx, instruction.getAgreedFx());
        assertEquals(Currency.AED, instruction.getCurrency());
        assertEquals(thursday5January2017, instruction.getInstructionDate());
        assertEquals(friday6January2017, instruction.getInitialSettlementDate());
        assertEquals(new Long(100), instruction.getUnits());
        assertEquals(new Double(10), instruction.getUnitPrice());
		
	}
	

}
