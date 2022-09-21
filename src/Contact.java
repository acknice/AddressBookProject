
public class Contact implements java.io.Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//variable declarations
	private String firstName;
	private String lastName;
	private String phone;
	private String email;

	
	//constructor
	public Contact(String firstName, String lastName, String phone, String email)
	{
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.email = email;
	}
	
	//getters
	public String getFirstName()
	{
		return this.firstName;
	}
	
	public String getLastName()
	{
		return this.lastName;
	}
	public String getPhone()
	{
		return this.phone;
	}	
	public String getEmail()
	{
		return this.email;
	}
	
	//setters
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}	
	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}
	public void setPhone(String phone)
	{
		this.phone = phone;
	}
	public void setEmail(String email)
	{
		this.email = email;
	}
	
	@Override
	public String toString()
	{
		return
		(
			"Name: " + this.firstName + " " + this.lastName + "\n" +
			"Phone: " + this.phone + "\n" +
			"Email: " + this.email + "\n" 
		);
	}
}
