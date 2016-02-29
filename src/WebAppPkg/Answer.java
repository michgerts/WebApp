package WebAppPkg;

public class Answer
{
	private int QID;//question ID
	private String UID;//User's ID
	private String Text;//Answer's text
	private String Time;
	private int Likes;

	
	public Answer (int Qid, String Uid, String text)
	{
		QID = Qid;
		UID = Uid;
		Text = text;
		Likes= 0;
		Time = "";		
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
<<<<<<< HEAD
}
=======
}

>>>>>>> 3ece6ff7bcc457a1f1661c1ec2faad19bf8cfd03
