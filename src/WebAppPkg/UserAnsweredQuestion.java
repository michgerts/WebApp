package WebAppPkg;

import java.util.List;

public class UserAnsweredQuestion
{
	//private String Text;
	//private String Time;
	//private String Asker;
	//private int Likes;	
	//private boolean Answered;
	Question Question;
	private List<String> Topics;
	private String UserAnswerText;
	private int UserAnswerRating;
	
	
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

