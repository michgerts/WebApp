package WebAppPkg;

import java.util.List;

/** This class represents a qestion that a certain user has answered. 
 * it contains the questions's topics, the question itself, user's answer and user's answer rating
 * @author Michal Kogan
 * @author Rita Kaufman
 *
 */
public class UserAnsweredQuestion
{
	Question Question;
	private List<String> Topics;
	private String UserAnswerText;
	private int UserAnswerRating;
	
	/** Constructor for the User class
	 * @param  question				The question this user answered
	 * @param  topics 				The topics of the question
	 * @param  userAnswerText		The user's answer
	 * @param  userAnswerRating		The answer's rating
	 */
	public UserAnsweredQuestion (Question question, List<String> topics, String userAnswerText, int userAnswerRating)
	{
		Question = question;
		Topics = topics;
		UserAnswerText = userAnswerText;
		UserAnswerRating = userAnswerRating;
		
	}
	
	public UserAnsweredQuestion() {
		
	}

	
	public String getUserAnswerText()
	{
		return UserAnswerText;
	}
	
	public int getUserAnswerRating()
	{
		return UserAnswerRating;
	}
	
	public List<String> getTopics ()
	{
		return Topics;
	}
	
	public WebAppPkg.Question getQuestion ()
	{
		return Question;
	}
	
	public void setTopics (List<String> topics)
	{
		Topics = topics;
	}
	
	public void setQuestion (Question qestion)
	{
		Question = qestion;
	}
	
	public void setUserAnswerText(String userAnswerText)
	{
		UserAnswerText = userAnswerText;
	}
	
	public void setUserAnswerRating(int userAnswerRating)
	{
		UserAnswerRating = userAnswerRating;
	}
}

