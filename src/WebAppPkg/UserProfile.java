package WebAppPkg;
import java.util.Collections;
import java.util.List;
public class UserProfile implements Comparable
{
	private User user;
	private List<Question> askedQuestions;
	private List<UserAnsweredQuestion> userAnsweredQuestions;
	private float rating;
	private List<String> fiveTopTopics;
	
	
	public UserProfile(User user, List<Question> askedQuestions, List<Answer> userAnswers, int rating, List<String> fiveTopTopics, List<UserAnsweredQuestion> userAnsweredQuestions)
	{
		this.user = user;
		this.askedQuestions = askedQuestions;
		this.userAnsweredQuestions = userAnsweredQuestions;
		this.rating = rating;
		this.fiveTopTopics = fiveTopTopics;
		
	}
	
	public UserProfile() 
	{		
		this.fiveTopTopics = Collections.emptyList();
		this.askedQuestions= Collections.emptyList();
		this.userAnsweredQuestions=  Collections.emptyList();
	}
	public User getUser()
	{
		return user;
	}
	
	public List<Question> getAskedQuestions()
	{
		return askedQuestions;
	}
	
	public List<UserAnsweredQuestion> getUserAnsweredQuestions()
	{
		return userAnsweredQuestions;
	}
	
	public float getRating()
	{
		return rating;
	}
	
	public List<String> getTopFiveTopics()
	{
		return fiveTopTopics;
	}
	
	public void setUser(User user)
	{
		this.user = user;
	}
	
	public void setAskedQuestions(List<Question> askedQuestions)
	{
		this.askedQuestions = askedQuestions;
	}
	
	public void setUserAnsweredQuestions(List<UserAnsweredQuestion> userAnsweredQuestions)
	{
		this.userAnsweredQuestions = userAnsweredQuestions;
	}
	
	public void setRating(float rating)
	{
		 this.rating = rating;
	}
	
	public void setTopFiveTopics(List<String> fiveTopTopics)
	{
		 this.fiveTopTopics = fiveTopTopics;
	}
	@Override
	public int compareTo(Object userP) {
		float compareage=((UserProfile)userP).getRating();
		if((this.rating - compareage)> 0)
			return -1;
		else
			if ((this.rating - compareage)< 0)
				return 1;
			else
				return 0;
	}
}