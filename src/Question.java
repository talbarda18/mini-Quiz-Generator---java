
public class Question {
	
	private String questionBody;
	private String answerA;
	private String answerB;
	private String answerC;
	private String answerD;
	private String correctAns;
	
	//Question constructor
	public Question(String questionBody, String answerA, String answerB,
			String answerC, String answerD) {
		this.questionBody = questionBody;
		this.answerA = answerA;
		this.answerB = answerB;
		this.answerC = answerC;
		this.answerD = answerD;
		this.correctAns = answerA;
	}
	
	//return correct answer
	public String getCorrectAnswer() {
		return this.correctAns;
	}
	
	//return the question string
	public String getQuestion() {
		return this.questionBody;
	}
	
	//return first answer
	public String getanswerA() {
		return this.answerA;
	}
	
	//return second answer
	public String getanswerB() {
		return this.answerB;
	}
	
	//return third answer
	public String getanswerC() {
		return this.answerC;
	}
	
	//return fourth answer
	public String getanswerD() {
		return this.answerD;
	}

	
	
}
