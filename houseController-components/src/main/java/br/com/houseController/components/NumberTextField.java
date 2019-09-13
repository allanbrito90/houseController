package br.com.houseController.components;

import java.math.BigDecimal;

import com.jfoenix.controls.JFXTextField;

/**
 * Hello world!
 *
 */
public class NumberTextField extends JFXTextField
{
    @Override
    public void replaceText(int start, int end, String text) {
    	if(text.matches("[0-9]") || text.matches("[.]") || text.isEmpty()){
    		if(!(text.matches("[.]") && start==0 && end==0)){
    			if (!(this.getText().contains(".") && text.matches("[.]"))) {
					super.replaceText(start, end, text);
				}
    		}
    	}
    	
    }
    
    @Override
    public void replaceSelection(String replacement) {
    	super.replaceSelection(replacement);
    }
    
    public BigDecimal getValor(){
    	return new BigDecimal(getText());
    }
}