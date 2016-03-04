package WebAppPkg;

/** This class represents a topic 
 * @author Michal Kogan
 * @author Rita Kaufman
 *
 */
public class Topic
{
	private int QID;
	private String Topic;
	
	
	/** Constructor for the Topic class
	 * @param  qid		The question ID that's related to this topic
	 * @param  topic 	The topic's text
	 */
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

