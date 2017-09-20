package org.wrvance.jpmorgan.techexercise.trading.instruction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.wrvance.jpmorgan.techexercise.currency.Currency;
import org.wrvance.jpmorgan.techexercise.trading.entity.TradeEntity;
import org.wrvance.jpmorgan.techexercise.trading.instruction.TradingInstruction.Action;

public class TradingInstructionDAO {

	private static List<TradingInstruction> instructions = new ArrayList<TradingInstruction>();
	
	
	public static void addInstruction(TradingInstruction instruction){
		TradingInstructionDAO.instructions.add(instruction);
	}
	
	public static void clearInstructions(){
		TradingInstructionDAO.instructions.clear();
	}
	
	public static java.util.Locale getBaseTradingLocale(){
		return java.util.Locale.US;
	}
	
	public static List<TradingInstruction> getAllInstructions(){
		int size = TradingInstructionDAO.instructions.size();
		List<TradingInstruction> instructionsCopy = Arrays.asList(new TradingInstruction[size]);
		Collections.copy(instructionsCopy, TradingInstructionDAO.instructions);
		return instructionsCopy;
	}
	
	public static void addInstructions(Object[][] tradingInstructionData){
		
		TradingInstructionDAO.clearInstructions();
		
        for(int i = 0; i < tradingInstructionData.length; i++){
        	
        	Object[] entityArray = tradingInstructionData[i];
        	
        	TradeEntity entity = TradeEntity.valueOf(entityArray[0].toString());
        	Action type = Action.valueOf(entityArray[1].toString());
        	double agreedFx = Double.valueOf(entityArray[2].toString());
        	Currency currency = Currency.valueOf(entityArray[3].toString());
        	LocalDate instructionDate = (LocalDate)entityArray[4];
        	LocalDate initialSettlementDate = (LocalDate)entityArray[5];
        	long units = Long.valueOf(entityArray[6].toString());
        	double unitPrice = Double.valueOf(entityArray[7].toString());
        	
        	TradingInstructionDAO.addInstruction(new TradingInstruction(entity, type, agreedFx, currency, instructionDate, initialSettlementDate, units, unitPrice));
        	  	
        }
		
	}
	
}
