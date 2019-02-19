package com.forum.units;

public class Question extends AbstractEntity {

	//brief description of question
	private String title;
	//detail question
	private String message;
	//user who asked the question
	private User user;
	//number of upvotes for the question.
	private int upvoteCount = 0;
	//id of the last question asked on discussion forum
	private static Long lastEntry = 0L;
    //Implementation of the abstract class defined in its parent class
	public void autoGenerateId() {
		lastEntry = lastEntry + 1L;
		super.setId(lastEntry);
	}
    //getter and setter methods defined to access the  private variables of the class
	public String getTitle() { return title; }

	public void setTitle(String title) { this.title=title; }

	public String getMessage() { return message; }

	public void setMessage(String message) { this.message=message; }

	public User getUser() { return user; }

	public void setUser(User user) { this.user=user; }

	public int getUpvoteCount() { return upvoteCount; }

	public void increaseUpvoteCount() { upvoteCount++; }

}
