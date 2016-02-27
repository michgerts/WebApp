package WebAppPkg;

public class Question
{
	private int ID;
	private String Text;
	private String Time;
	private String Asker;
	private int Likes;	
	private boolean Answered;
	
	public Question (int id, String text, String time, String asker, int likes, boolean answered)
	{
		ID = id;
		Text = text;
		Time = time;
		Asker = asker;
		Likes = likes;
		Answered = answered;
	}
	public int getID ()
	{
		return ID;
	}
	
	public String getText()
	{
		return Text;
	}
	public String getTime ()
	{
		return Time;
	}
	public String getAsker ()
	{
		return Asker;
	}
	public int getLikes ()
	{
		return Likes;
	}
	public boolean getAnswered ()
	{
		return Answered;
	}
	public void setID (int id)
	{
		ID=id;
	}
	public void setLikes (int likes)
	{
		Likes=likes;
	}
	public void setAnswered (boolean ans)
	{
		Answered=ans;
	}
	public void setText (String text)
	{
		Text= text;
	}
	public void setTime (String time)
	{
		Time= time;
	}
	public void setAsker (String asker)
	{
		Asker=asker;
	}
}

