package WebAppPkg;

/** This class represents a user and all of his details
 * @author Michal Kogan
 * @author Rita Kaufman
 *
 */
public class User
{
	private int ID;
	private String Name;
	private String Password;
	private String NickName;
	private String Description;	
	private String Pic;
	
	/** Constructor for the User class
	 * @param  id		The user's id
	 * @param  name 	The user's name
	 * @param  pass		The user's password
	 * @param  nick		The user's nickname
	 * @param  desc		The user's description
	 * @param  pic		The user's profile picture
	 */
	public User (int id, String name, String pass, String nick, String desc, String pic)
	{
		ID = id;
		Name = name;
		Password = pass;
		NickName = nick;
		Description = desc;
		Pic = pic;
	}
	
	public User()
	{
		
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
	
	public void setNickName (String nick)
	{
		 NickName = nick;
	}
	public void setPic(String pic)
	{
		Pic = pic;
	}
	
	public void setName(String name)
	{
		Name = name;
	}

	public void setDescription(String description2) {
		Description = description2;
		
	}
	
}

