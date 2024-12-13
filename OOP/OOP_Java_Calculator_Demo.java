package calculator;

import support.cse131.ArgsProcessor;

public class Calculator {
	public static void main(String[] args) {
		ArgsProcessor ap = new ArgsProcessor(args);

		
		String text = ap.nextString();
		String textWOWhite = "";
		String textA = "";
		String textB = "";
		char thingBetween = ' ';
		int result = 0;
		int n = 0;
	
		for (int i=0; i<text.length(); i++) {
			if (Character.isWhitespace(text.charAt(i))==false) {
				textWOWhite = textWOWhite +(text.charAt(i));
			}
		}
		
		for (int j=0; j<textWOWhite.length(); j++) {
			if(Character.isDigit(textWOWhite.charAt(j)) == false) {
				 n = j;
				 thingBetween = textWOWhite.charAt(n);
			 }
				 
		}
		for (int k=0; k<n;k++) {
			textA = textA+ textWOWhite.charAt(k);
		}
		for (int l=n+1; l<textWOWhite.length(); l++) {
			textB = textB + textWOWhite.charAt(l);
		}
		
		
		int numberA = Integer.parseInt(textA);	
		int numberB = Integer.parseInt(textB);
		
	
		if (thingBetween == '+') {
			result = numberA + numberB;
		}
		else if (thingBetween == '/') {
			result = numberA / numberB;
		}
		else if (thingBetween == '*') {
			result = numberA * numberB;
		}
		else if (thingBetween == '-') {
			result = numberA - numberB;
		}
		
		System.out.println(numberA + " "+  thingBetween +" "+ numberB + " "+ "=" + " "+ result);
		}
		
		// TODO:
	}

