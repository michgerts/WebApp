package WebAppPkg;

public class Answer
{
	private int QID;//question ID
	private int UID;//User's ID
	private String Text;//Answer's text

	
	public Answer (int Qid, int Uid, String text)
	{
		QID = Qid;
		UID = Uid;
		Text = text;
	}
	
	public int getQID ()
	{
		return QID;
	}
	
	public int getUID()
	{
		return UID;
	}
	
	public String getText()
	{
		return Text;
	}
	
	public void setQID(int Qid)
	{
		QID = Qid;
	}
	
	public void setUID(int Uid)
	{
		UID = Uid;
	}
	
	public void setUID(String text)
	{
		Text = text;
	}
}

