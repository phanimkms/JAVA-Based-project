package discusion.forum.activiy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.forum.main.DiscussionForum;
import com.forum.units.Question;
import com.forum.units.Reply;
import com.forum.units.User;
import com.forum.units.UserRole;
import com.forum.util.Utility;

import discussion.forum.units.service.QuestionService;
import discussion.forum.units.service.QuestionServiceImpl;
import discussion.forum.units.service.ReplyService;
import discussion.forum.units.service.ReplyServiceImpl;
import discussion.forum.units.service.UpvoteService;
import discussion.forum.units.service.UpvoteServiceImpl;
import discussion.forum.units.service.UserService;
import discussion.forum.units.service.UserServiceImpl;
//UserActivity divided into categories and creating variables of the respective interfaces type as defined
public class UserActivity {
	public UserService userService;
	public QuestionService questionService;
	public ReplyService replyService;
	public UpvoteService upvoteService;
//Constructor of userActivity to instantiate to respective objects of the classes which implemented the interfaces
	public UserActivity() {
		userService = new UserServiceImpl();
		questionService = new QuestionServiceImpl();
		replyService = new ReplyServiceImpl();
		upvoteService = new UpvoteServiceImpl();
	}

	public User loginActivity() {
		System.out.println("Welcome to discussion forum login");
		System.out.println("Enter your username : ");
		String username = Utility.inputFromUser();
		System.out.println("Enter your password : ");
		String password = Utility.inputFromUser();

		User user = userService.getUser(username, password);
		if (user != null) {
			return user;
		}
		System.out.println("You do not have the account. Request admin to get account in discussion forum");
		return null;
	}

	public void createNewUser() {
		System.out.println("Enter username : ");
		String username = Utility.inputFromUser();
		System.out.println("Enter password : ");
		String password = Utility.inputFromUser();
		System.out.println("Enter email : ");
		String email = Utility.inputFromUser();
		System.out.println("What role : ");
		UserRole role = DiscussionForum.roleFromMenu();
		userService.createUser(username, password, email, role);
	}

	public void postNewQuestion(User user) {
		System.out.println("Enter question title : ");
		String title = Utility.inputFromUser();
		System.out.println("Enter question : ");
		String message=Utility.inputFromUser();
		questionService.createQuestion(title, message, user);
	}

	public void seeAllQuestions(UserActivity userActivity, User user) throws NumberFormatException, IOException {
		ArrayList<Question> questions = QuestionServiceImpl.questions;
		if ( questions.size()==0 ) {
			System.out.println("No question posted yet");
		} else {
			sort(questions);
			for (Question question : questions) {
				System.out.println(question.getId() + ". Question Title - " + question.getTitle());
				System.out.println("Question - " + question.getMessage());
				System.out.println("Upvote - " + question.getUpvoteCount());
			}
			DiscussionForum.questionMenu(userActivity, user);//sending the control to question menu
		}
	}

	public void sort(ArrayList<Question> questions) {
		Collections.sort(questions, new Comparator<Question>() {
			public int compare(Question q1, Question q2) {
				if (q1.getUpvoteCount() == q2.getUpvoteCount())
					return 0;
				return q1.getUpvoteCount() < q1.getUpvoteCount() ? 1 : -1;
			}
		});
	}

	public void upvoteQuestion(User user) throws NumberFormatException {
		System.out.println("Enter question number you want to upvote : ");
		upvoteService.addUpvote(getQuestion(), user);
	}

	public void replyToQuestion(User user) {
		System.out.println("Enter question number you want to reply to : ");
		Question question = getQuestion();
		System.out.println("Question details :"+question.getMessage());//Displaying question details so that user can answer

		System.out.println("Post your reply");
		replyService.addReply(Utility.inputFromUser(), question, user);
	}

	public void deleteQuestion(UserActivity userActivity, User user) throws NumberFormatException {
		System.out.println("Enter question number you want to delete : ");
		Question question = getQuestion();
//Allows only authorized person to delete the question
		if (user.getUserRole() == UserRole.ADMIN) {
			questionService.deleteQuestion(question);
		} else if(user.getUserRole()==UserRole.MODERATOR) {
			if(question.getUser().getUserRole()==UserRole.USER) {
				questionService.deleteQuestion(question);
			} else if(question.getUser()==user) {
				questionService.deleteQuestion(question);
			} else{
				System.out.println("You are not authorised to delete this question");
			}
		} else {
			if(question.getUser()==user) {
				questionService.deleteQuestion(question);
			} else{
				System.out.println("You are not authorised to delete this question");
			}
		}
		if (QuestionServiceImpl.questions.size() == 0)
			DiscussionForum.menu(user, userActivity);//sending control to main menu
	}

	private Question getQuestion() {
		Question question;
		while (true) {
			question = questionService.getQuestionById(Long.parseLong(Utility.inputFromUser()));
			if (question != null)
				break;
			System.out.println("Enter correct question from displayed questions");
		}
		return question;
	}

	public void seeAllReplies(UserActivity userActivity, User user) throws NumberFormatException, IOException {
		System.out.println("For which question number you want to see replies : ");
		Question question = getQuestion();
		ArrayList<Reply> replies = replyService.getReplies(question);
		if (replies.size() == 0) {
			System.out.println("No reply posted yet");
		} else {
			for (Reply reply : replies) {
				System.out.println(reply.getId() + ". Comment - " + reply.getMessage());
				System.out.println("Upvote - " + upvoteService.upvoteCount(reply));
			}
			DiscussionForum.replyMenu(userActivity, user, question);//sending the control to reply menu
		}
	}

	public void upvoteReply(User user) throws NumberFormatException {
		System.out.println("Enter reply number you want to upvote : ");
		upvoteService.addUpvote(getReply(), user);
	}

	public void deleteReply(Question question, UserActivity userActivity, User user) throws NumberFormatException, IOException {
		System.out.println("Enter reply number you want to delete : ");
		Reply reply = getReply();
//Allows only authorised persons to delete the replies
		if (user.getUserRole() == UserRole.ADMIN) {
			replyService.deleteReply(reply);
		} else if (user.getUserRole() == UserRole.MODERATOR) {
			if (reply.getUser().getUserRole() == UserRole.USER) {
				replyService.deleteReply(reply);
			} else if (reply.getUser() == user) {
				replyService.deleteReply(reply);
			} else {
				System.out.println("You are not authorised to delete this reply");
			}
		} else {
			if (reply.getUser() == user) {
				replyService.deleteReply(reply);
			} else {
				System.out.println("You are not authorised to delete this reply");
			}
		}
		if (replyService.getReplies(question).size() == 0)
			DiscussionForum.questionMenu(userActivity, user);//sending control to question menu

	}

	private Reply getReply() throws NumberFormatException {
		Reply reply;
		while (true) {
			reply = replyService.getReply(Long.parseLong(Utility.inputFromUser()));
			if (reply != null)
				break;
			System.out.println("Enter correct reply from displayed replies");
		}
		return reply;
	}

}
