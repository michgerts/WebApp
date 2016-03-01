package WebAppPkg;

import java.util.Collections;
import java.util.List;

public class UserProfile implements Comparable
{
	private User user;
	private List<Question> askedQuestions;
	private List<Answer> userAnswers;
	private float rating;
	private List<String> fiveTopTopics;
	
	
	public UserProfile(User user, List<Question> askedQuestions, List<Answer> userAnswers, int rating, List<String> fiveTopTopics)
	{
		this.user = user;
		this.askedQuestions = askedQuestions;
		this.userAnswers = userAnswers;
		this.rating = rating;
		this.fiveTopTopics = fiveTopTopics;
		
	}
	
	public UserProfile() 
	{		
		this.fiveTopTopics = Collections.emptyList();
		this.askedQuestions= Collections.emptyList();
		this.userAnswers=  Collections.emptyList();
	}

	public User getUser()
	{
		return user;
	}
	
	public List<Question> getAskedQuestions()
	{
		return askedQuestions;
	}
	
	public List<Answer> getUserAnswers()
	{
		return userAnswers;
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
	
	public void setUserAnswers(List<Answer> userAnswers)
	{
		this.userAnswers = userAnswers;
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

