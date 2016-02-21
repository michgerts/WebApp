package WebAppPkg;

public class Topic
{
	private int QID;
	private String Topic;
	
	public Topic (int qid, String topic)
	{
		QID = qid;
		Topic = topic;

	}
	public int getQID ()
	{
		return QID;
	}
	public String getTopic()
	{
		return Topic;
	}
}

