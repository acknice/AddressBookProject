import java.util.ArrayList;
import java.util.Scanner;

public class Search 
{

	static Scanner input = new Scanner(System.in);
	
	static ArrayList<Integer> firstNameSearch(ArrayList<Contact> addressBook)
	{
		System.out.println("Please provide the first name of the contact you would like to find (partial names may be searched):");
		String searched = input.nextLine();
		
		ArrayList<Integer> resultsList = new ArrayList<Integer>();
		for (int i = 0; i < addressBook.size(); i++)
		{
			String full = (addressBook.get(i).getFirstName()).toUpperCase();
			int partial = full.indexOf(searched.toUpperCase());
			if (partial != -1) resultsList.add(i);
		}
		return resultsList;
	}
	
	static ArrayList<Integer> lastNameSearch(ArrayList<Contact> addressBook)
	{
		System.out.println("Please provide the last name of the contact you would like to find (partial names may be searched):");
		String searched = input.nextLine();
		
		ArrayList<Integer> resultsList = new ArrayList<Integer>();
		for (int i = 0; i < addressBook.size(); i++)
		{
			String full = (addressBook.get(i).getLastName()).toUpperCase();
			int partial = full.indexOf(searched.toUpperCase());
			if (partial != -1) resultsList.add(i);
		}
		return resultsList;
	}
		
	static ArrayList<Integer> phoneSearch(ArrayList<Contact> addressBook)
	{
		System.out.println("Please provide the phone number of the contact you would like to find (partial numbers may be searched):");
		String searched = input.nextLine();
		
		ArrayList<Integer> matches = new ArrayList<Integer>();
		for (int i = 0; i < addressBook.size(); i++)
		{
			String full = addressBook.get(i).getPhone();
			int partial = full.indexOf(searched);
			if (partial != -1) matches.add(i);
		}
		return matches;
	}
		
	static ArrayList<Integer> emailSearch(ArrayList<Contact> addressBook)
	{
		System.out.println("Please provide the email address of the contact you would like to find (partial addresses may be searched:");
		String searched = input.nextLine();
		ArrayList<Integer> matches = new ArrayList<Integer>();
		for (int i = 0; i < addressBook.size(); i++)
		{
			String full = (addressBook.get(i).getEmail()).toUpperCase();
			int partial = full.indexOf(searched.toUpperCase());
			if (partial != -1) matches.add(i);
		}
		return matches;
	}
	
	static ArrayList<Integer> emailCheck(ArrayList<Contact> addressBook, String newEmail)
	{
		ArrayList<Integer> matches = new ArrayList<Integer>();
		for (int i = 0; i < addressBook.size(); i++)
		{
			if (addressBook.get(i).getEmail().toUpperCase().equals(newEmail) == true) matches.add(i);
		}
		return matches;
	}

}
