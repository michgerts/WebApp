package WebAppPkg;

public class TopicRating implements Comparable<TopicRating>
{
	private float Rating;
	private String Topic;
	
	public TopicRating (String topic, float rating)
	{
		Rating = rating;
		Topic = topic;
	}
	public float getRating ()
	{
		return Rating;
	}
	public String getTopic()
	{
		return Topic;
	}
	@Override
	public int compareTo(TopicRating comparestq)
	{
	        float comparerate=((TopicRating)comparestq).getRating();
	        if (this.Rating>comparerate)
	        	return -1;
	        else if (this.Rating<comparerate)
	        	return 1;
	        else
	        	return 0;
	}
}

