package com.forum.units;

public class Reply extends AbstractEntity {

	//reply body
	private String message;
	//user who replied to the question
	private User user;
	//question for which user has replied
	private Question question;
	//id of the last reply posted on discussion forum
	private static Long lastEntry = 0L;
	//Implementation of the abstract class defined in its parent class
	public void autoGenerateId() {
		lastEntry = lastEntry + 1L;
		super.setId(lastEntry);
	}
	//getter and setter methods defined to access the  private variables of the class
	public String getMessage() { return message; }

	public void setMessage(String message) { this.message=message; }

	public User getUser() { return user; }

	public void setUser(User user) { this.user=user; }

	public Question getQuestion() { return question; }

	public void setQuestion(Question question) { this.question=question; }
}
