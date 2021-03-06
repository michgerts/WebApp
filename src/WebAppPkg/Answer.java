package WebAppPkg;

/** This class represents an Answer
 * @author Michal Kogan
 * @author Rita Kaufman
 *
 */
public class Answer
{
	private int AID;
	private int QID;//question ID
	private String UID;//User's ID
	private String Text;//Answer's text
	private String Time;
	private int Likes;

	/** Constructor for the Answer class
	 * @param  aid  The answer id
	 * @param  Qid 	The questions id
	 * @param  Uid  The User id (his name)
	 * @param  text The answer's text
	 */
	public Answer (int aid, int Qid, String Uid, String text)
	{
		AID= aid;
		QID = Qid;
		UID = Uid;
		Text = text;
		Likes= 0;
		Time = "";		
	}
	public int getAID ()
	{
		return AID;
	}
	public int getQID ()
	{
		return QID;
	}
	public int getLikes ()
	{
		return Likes;
	}
	public String getUID()
	{
		return UID;
	}
	
	public String getText()
	{
		return Text;
	}
	public String getTime()
	{
		return Time;
	}
	
	public void setQID(int Qid)
	{
		QID = Qid;
	}
	public void setAID(int aid)
	{
		AID = aid;
	}
	
	public void setUID(String Uid)
	{
		UID = Uid;
	}
	
	public void setText(String text)
	{
		Text = text;
	}
	public void setTime(String time)
	{
		Time = time;
	}
	public void setLikes(int likes)
	{
		Likes = likes;
	}

}
