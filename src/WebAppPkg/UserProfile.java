package WebAppPkg;
import java.util.Collections;
import java.util.List;
/** This class represents a user profile and all of its properties
 * @author Michal Kogan
 * @author Rita Kaufman
 *
 */
public class UserProfile implements Comparable
{
	private User user;
	private List<Question> askedQuestions;
	private List<UserAnsweredQuestion> userAnsweredQuestions;
	private float rating;
	private List<String> fiveTopTopics;
	
	/** Constructor #1 for the UserProfile class
	 * @param  user						The user's info
	 * @param  askedQuestions 			The questions that this user asked
	 * @param  rating					The rating of this user
	 * @param  fiveTopTopics			User's expertise
	 * @param  userAnsweredQuestions	Five last answers this user gave and their questions
	 */
	public UserProfile(User user, List<Question> askedQuestions, int rating, List<String> fiveTopTopics, List<UserAnsweredQuestion> userAnsweredQuestions)
	{
		this.user = user;
		this.askedQuestions = askedQuestions;
		this.userAnsweredQuestions = userAnsweredQuestions;
		this.rating = rating;
		this.fiveTopTopics = fiveTopTopics;
		
	}
	
	/** Constructor #2 for the UserProfile class (empty constructor)
	 */
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
	/** Comparator for the class UserProfile. It compares both objects' ratings.
	 * @param  userP	a user
	 * @return 			1 if this rating is greater then the given one. -1 if the opposite is correct. 0 if they're equal.
	 */
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