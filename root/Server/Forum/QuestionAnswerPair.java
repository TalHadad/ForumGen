package Forum;

import Forum.*;
import Posts.*;
public class QuestionAnswerPair {
	
	private String questions;
	private String answers;
	
	
	public String getQuestions() {
		return questions;
	}


	public void setQuestions(String questions) {
		this.questions = questions;
	}


	public String getAnswers() {
		return answers;
	}


	public void setAnswers(String answers) {
		this.answers = answers;
	}


	public QuestionAnswerPair(String questions, String answers) {
		this.questions = questions;
		this.answers = answers;
	}

}
