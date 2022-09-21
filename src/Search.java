import java.util.ArrayList;

public class Search 
{

	static ArrayList<Integer> firstNameSearch(ArrayList<Contact> book, String searched)
	{
		ArrayList<Integer> matches = new ArrayList<Integer>();
		for (int i = 0; i < book.size(); i++)
		{
			String full = (book.get(i).getFirstName()).toUpperCase();
			int partial = full.indexOf(searched.toUpperCase());
			if (partial != -1) matches.add(i);
		}
		return matches;
	}
	
	static ArrayList<Integer> lastNameSearch(ArrayList<Contact> book, String searched)
	{
		ArrayList<Integer> matches = new ArrayList<Integer>();
		for (int i = 0; i < book.size(); i++)
		{
			String full = (book.get(i).getLastName()).toUpperCase();
			int partial = full.indexOf(searched.toUpperCase());
			if (partial != -1) matches.add(i);
		}
		return matches;
	}
		
	static ArrayList<Integer> phoneSearch(ArrayList<Contact> book, String searched)
	{
		ArrayList<Integer> matches = new ArrayList<Integer>();
		for (int i = 0; i < book.size(); i++)
		{
			String full = book.get(i).getPhone();
			int partial = full.indexOf(searched);
			if (partial != -1) matches.add(i);
		}
		return matches;
	}
		
	static ArrayList<Integer> emailSearch(ArrayList<Contact> book, String searched)
	{
		ArrayList<Integer> matches = new ArrayList<Integer>();
		for (int i = 0; i < book.size(); i++)
		{
			String full = (book.get(i).getEmail()).toUpperCase();
			int partial = full.indexOf(searched.toUpperCase());
			if (partial != -1) matches.add(i);
		}
		return matches;
	}

}
