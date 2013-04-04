package com.gonzalovazquez.crystalball;

import java.util.Random;

public class CrystalBall {
	// Member variables(properties about the object)
	public String[] answers = {
			"It is certain",
			"It is decidely so",
			"All signs say Yes",
			"The stars are not aligned",
			"My reply is no",
			"It is doubtful",
			"Better not tell you now",
			"Concentrate and ask again",
			"Unable to answer no"};
	
	//Methods(abilities: things the object can do)
	
	public String getAnAnswer(){
	
		// The button was clicked, so update the answer label with an answer.
		String answer = "";
		
		//Randomly select one of three answer: Yes, No, or Maybe
		Random randomGenerator = new Random(); //Construct a new Random number generator
		int randomNumber = randomGenerator.nextInt(answers.length);
		
		//We randomly select from the array.
		answer = answers[randomNumber];
		
		return answer;

	}
	
}
