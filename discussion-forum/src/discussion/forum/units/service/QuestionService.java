package discussion.forum.units.service;

import com.forum.units.Question;
import com.forum.units.User;
//interface defined for certain activities of UserActivity
public interface QuestionService {
	Question createQuestion(String title, String message, User user);
	
	Question getQuestionById(long id);
	
	void deleteQuestion(Question question);
}
