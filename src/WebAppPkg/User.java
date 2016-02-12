package WebAppPkg;

public class User
{
	private int ID;
	private String Name;
	private String Password;
	private String NickName;
	private String Description;	
	private String Pic;
	
	public User (int id, String name, String pass, String nick, String desc, String pic)
	{
		ID = id;
		Name = name;
		Password = pass;
		NickName = nick;
		Description = desc;
		Pic = pic;
	}
	public int getID ()
	{
		return ID;
	}
	public String getName ()
	{
		return Name;
	}
	public String getPassword ()
	{
		return Password;
	}
	public String getDescription ()
	{
		return Description;
	}
	public String getNickName ()
	{
		return NickName;
	}
	public String getPic ()
	{
		return Pic;
	}
}

