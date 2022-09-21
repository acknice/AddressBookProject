import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main 
{

	//variables for the top-level program
	static boolean isRunning = true;
	static ArrayList<Contact> AddressBook = new ArrayList<Contact>();
	static Scanner input = new Scanner(System.in);
	
	
	@SuppressWarnings("unchecked")
	static ArrayList<Contact> loadBook()
	{
		ArrayList<Contact> addressBook = new ArrayList<Contact>();
		try
		{
			FileInputStream fileIn = new FileInputStream("src/saved_books/my_address_book.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			addressBook = (ArrayList<Contact>) in.readObject();
			in.close();
			fileIn.close();
			System.out.println("Your address book has loaded successfully.");
		}
		catch (IOException i)
		{
			System.out.println("Address book could not be loaded or was not found.");
		}
		catch (ClassNotFoundException c)
		{
			System.out.println("Address book could not be loaded due to a serialization error.");
		}
		return addressBook;
		
	}
	
	static void saveBook(ArrayList<Contact> addressBook)
	{
		try
		{
			FileOutputStream fileOut = new FileOutputStream("src/saved_books/my_address_book.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(addressBook);
			out.close();
			fileOut.close();
			System.out.println("Your address book has been saved successfully.") ;
		}
		catch (IOException i)
		{
			System.out.println("Address book could not be saved.");
			i.printStackTrace();
		}
		
	}
	
	static void printBook(ArrayList<Contact> addressBook)
	{
		addressBook.forEach(Contact -> {System.out.println(Contact.toString());});
	}
	
	static int findEntry(ArrayList<Contact> addressBook)
	{	
		ArrayList<Integer> resultList = new ArrayList<Integer>();
		int searchResult = -1;
		
		System.out.println("How would you like to search for a contact?\n");
		System.out.println
			(
				"Options:\n"
				+ "1) First Name\n"
				+ "2) Last Name\n"
				+ "3) Phone\n"
				+ "4) Email\n"
			);
		System.out.println("\n");
		System.out.println("Please provide a selection (1-4):");
		
		int searchOption = 0;
		while (searchOption == 0)
		{
			try 
			{
				searchOption = input.nextInt();
			}
			catch (InputMismatchException e)
			{
				searchOption = 0;
			}
			input.nextLine();	

			switch (searchOption)
			{
				case 1:
					resultList = Search.firstNameSearch(addressBook);
					break;
				case 2:
					resultList = Search.lastNameSearch(addressBook);
					break;
				case 3:
					resultList = Search.phoneSearch(addressBook);
					break;
				case 4:
					resultList = Search.emailSearch(addressBook);
					break;
				default:
					System.out.println("Invalid input, please try again.");
					break;
			}
		}
		if (resultList.isEmpty() == false)
		{
			System.out.println("The below entries were found matching your search:\n");
			for (int i=0; i < resultList.size(); i++)
			{
				System.out.println("Entry " + (i+1) + ":\n" + (addressBook.get(resultList.get(i))).toString());
			}
				System.out.println("Please select the desired entry (1-" + resultList.size() +"). If none of these is the entry you were looking for, please enter 0.\n");
				int selection = -1;
				while (selection == -1)
				{
					try 
					{
						selection = input.nextInt();
					}
					catch (InputMismatchException e)
					{
						System.out.println("Invalid input, please try again.");
						selection = -1;
					}
					input.nextLine();
					
					if (selection > resultList.size())
					{
						System.out.println("Invalid input, please try again.");
						selection = -1;
					}
					else if (selection == 0)
					{
						System.out.println("Sorry we couldn't find what you were looking for.\n");
					}
				}
				if (selection == 0) searchResult = -1;
				else searchResult = resultList.get(selection - 1);	
		}
		else System.out.println("Sorry, no matching entry was found.\n");
		
		return searchResult;
	}

	static void addEntry(ArrayList<Contact> addressBook)
	{
		System.out.println("Please provide a first name:");
		String newFirstName = input.nextLine();
		System.out.println("And a last name:");
		String newLastName = input.nextLine();
		System.out.println("What is the contact phone number?");
		String newPhone = input.nextLine();
		System.out.println("What is their email address?");
		String newEmail = input.nextLine();
		boolean unique;
		ArrayList<Integer> dupeCheck = new ArrayList<Integer>();
		dupeCheck = Search.emailCheck(addressBook, newEmail);
		if (dupeCheck.isEmpty() == true)
		{
			unique = true;
		}
		else unique = false;
		while (unique == false)
		{
			System.out.println("Each entry must have a unique email address. \n Please provide a unique email address:");
			newEmail = input.nextLine();
			dupeCheck = Search.emailCheck(addressBook, newEmail);
			if (dupeCheck.isEmpty() == true)
			{
				unique = true;
			}
		}
		Contact newContact = new Contact(newFirstName, newLastName, newPhone, newEmail);//, newFavorite);
		System.out.println("\nA new contact has been added with the details below:\n" + newContact.toString());
		addressBook.add(newContact);
	}
	
	static void editEntry(ArrayList<Contact> addressBook, int editIndex)
	{
		System.out.println("Which field would you like to update?\n");
		System.out.print
			(	
			"Options:\n"
			+ "1) First Name\n"
			+ "2) Last Name\n"
			+ "3) Phone\n"
			+ "4) Email\n"
			);
		System.out.println("\n");
		System.out.println("Please provide a selection (1-4):");
		int fieldOption = 0;
		while (fieldOption == 0)
		{
			try 
			{
				fieldOption = input.nextInt();
			}
			catch (InputMismatchException e)
			{
				fieldOption = 0;
			}
			input.nextLine();	

			switch (fieldOption)
			{
				case 1:
					System.out.println("Please provide the new first name of the contact:");
					String newFirstName = input.nextLine();
					addressBook.get(editIndex).setFirstName(newFirstName);
					break;
				case 2:
					System.out.println("Please provide the new last name of the contact:");
					String newLastName = input.nextLine();
					addressBook.get(editIndex).setLastName(newLastName);
					break;
				case 3:
					System.out.println("Please provide the new phone number of the contact:");
					String newPhone = input.nextLine();
					addressBook.get(editIndex).setPhone(newPhone);
					break;
				case 4:
					System.out.println("Please provide the new email address of the contact:");
					String newEmail = input.nextLine();
					ArrayList<Integer> dupeCheck = new ArrayList<Integer>();
					boolean unique;
					dupeCheck = Search.emailCheck(addressBook, newEmail);
					if (dupeCheck.isEmpty() == true)
					{
						unique = true;
					}
					else unique = false;
					while (unique == false)
					{
						System.out.println("Each entry must have a unique email address. \n Please provide a unique email address:");
						newEmail = input.nextLine();
						dupeCheck = Search.emailCheck(addressBook, newEmail);
						if (dupeCheck.isEmpty() == true)
						{
							unique = true;
						}
					}
					addressBook.get(editIndex).setEmail(newEmail);
					break;
				default:
					System.out.println("Invalid option.");
					break;
			}
		}
		System.out.println("Your entry has been updated:\n");
		System.out.println((addressBook.get(editIndex)).toString());
	}
					
	static void deleteEntry(ArrayList<Contact> addressBook, int deleteIndex)
	{
		System.out.println("Are you sure you want to delete this entry?\n");
		System.out.println("Please input Y or yes to confirm deletion:"); 
		String confirmation = input.nextLine();
		boolean confirmed = false;
		switch (confirmation)
		{
			case "Y":
				confirmed = true;
				break;
			case "y":
				confirmed = true;
				break;
			case "Yes":
				confirmed = true;
				break;
			case "yes":
				confirmed = true;
				break;
			default:
				break;
		}
		if (confirmed == true) 
		{
			addressBook.remove(deleteIndex);
			System.out.println("Entry deleted. \n");
		}
		else System.out.println("Deletion canceled.\n");
	}
				
	static void deleteBook(ArrayList<Contact> book)

	{
		System.out.println("This will delete all entries in the book. Are you sure? (Y to confirm)\n");
		System.out.println("Please input Y or yes to confirm deletion:"); 
		String confirmation = input.nextLine();
		boolean confirmed;
		switch (confirmation)
		{
		case "Y":
			confirmed = true;
			break;
		case "y":
			confirmed = true;
			break;
		case "Yes":
			confirmed = true;
			break;
		case "yes":
			confirmed = true;
			break;
		default:
			confirmed = false;
			break;
		}
		if (confirmed == true)
		{
			book.removeAll(book);
			System.out.println("All entries deleted.");
		}
		else System.out.println("Deletion canceled. \n");
	}
	
		
		
	public static void main(String[] args) 
	{
		
		//begin program
		System.out.println("Welcome!\n");
		
		AddressBook = loadBook();
		
		while (isRunning == true)  															
		{
			System.out.println("What would you like to do?\n");								
			System.out.println
				(
					"Options:\n"
					+ "1) View your Address Book\n"
					+ "2) Find an entry\n"
					+ "3) Add a new entry\n"
					+ "4) Delete your address book\n"
					+ "5) Quit program\n"
				);
			System.out.println("\n");
			System.out.println("Please provide a selection (1-5):");
			
			int option = 0;
			
			try
			{
				option = input.nextInt();													
			}
			catch (InputMismatchException e)
			{
				option = 0;
			}
			input.nextLine();
			
			switch (option)																	
			{																					
				case 1:
					if (AddressBook.isEmpty() == true) System.out.println("Your address book is empty, there are no entries to display.\n");
					else 
					{
						System.out.println("Here are the current entries in your address book:\n");
						printBook(AddressBook);
					}
					break;
				case 2:
					if (AddressBook.isEmpty() == true) System.out.println("Your address book is currently empty.\n");
					else
					{
						System.out.println("Sure thing!\n");
						int searchResult = findEntry(AddressBook);
						if (searchResult != -1)
						{
							System.out.println("What would you like to do with this entry?\n");
							System.out.println(AddressBook.get(searchResult));
							System.out.println
								(
									"Options:\n"
									+ "1) Update this entry\n"
									+ "2) Delete this entry\n"
									+ "3) Nothing\n"
								);
							System.out.println("\n");
							System.out.println("Please provide a selection (1-3):");
							
							int searchAction = 0;
							while (searchAction == 0)
							{
								try
								{
									searchAction = input.nextInt();													
								}
								catch (InputMismatchException e)
								{
									searchAction = 0;
								}
								input.nextLine();
								
								switch (searchAction)
								{
									case 1:
										editEntry(AddressBook, searchResult);
										break;
									case 2:
										deleteEntry(AddressBook, searchResult);
										break;
									case 3:
										break;
									default:
										System.out.println("Invalid option, please try again");
										break;
								}
							}	
						}
					}
					break;
				case 3:
					System.out.println("Sure! Who would you like to add?\n");
					addEntry(AddressBook);
					break;
				case 4:
					if (AddressBook.isEmpty() == true) System.out.println("Your address book is empty, there is nothing to delete.\n");
					else
					{
						System.out.println("You have chosen to delete your address book.\n");
						deleteBook(AddressBook);
					}
					break;
				case 5:
					saveBook(AddressBook);
					System.out.println("Goodbye!\n");
					isRunning = false;
					break;
				default:
					System.out.println("Invalid option, please try again");
					break;
			}
		}
		input.close();
	}
}
