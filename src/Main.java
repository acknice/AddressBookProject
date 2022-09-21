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
		ArrayList<Contact> book = new ArrayList<Contact>();
		try
		{
			FileInputStream fileIn = new FileInputStream("src/saved_books/my_address_book.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			book = (ArrayList<Contact>) in.readObject();
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
		return book;
		
	}
	
	static void saveBook(ArrayList<Contact> book)
	{
		try
		{
			FileOutputStream fileOut = new FileOutputStream("src/saved_books/my_address_book.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(book);
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
	
	static void printBook(ArrayList<Contact> book)
	{
		book.forEach(Contact -> {System.out.println(Contact.toString());});
	}
	
	static void findEntry(ArrayList<Contact> book)
	{	
		System.out.println("How would you like to search for a contact?\n");
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
		int searchOption =  input.nextInt();
		input.nextLine();
		String searched;
		ArrayList<Integer> resultList = new ArrayList<Integer>();
		switch (searchOption)
		{
			case 1:
				System.out.println("Please provide the first name of the contact you would like to find:");
				searched = input.nextLine();
				resultList = Search.firstNameSearch(book, searched);
				if (resultList.isEmpty() == false)
				{
					System.out.println("Entry found!\n");
					for (int i=0; i < resultList.size(); i++)
					{
						System.out.println((book.get(resultList.get(i))).toString());
					}
				}
				else System.out.println("Sorry, no entry was found with that first name.\n");
				break;
			case 2:
				System.out.println("Please provide the last name of the contact you would like to find:");
				searched = input.nextLine();
				resultList = Search.lastNameSearch(book, searched);
				if (resultList.isEmpty() == false)
				{
					System.out.println("Entry found!\n");
					for (int i=0; i < resultList.size(); i++)
					{
						System.out.println((book.get(resultList.get(i))).toString());
					}
				}
				else System.out.println("Sorry, no entry was found with that last name.\n");
				break;
			case 3:
				System.out.println("Please provide the phone number of the contact you would like to find:");
				searched = input.nextLine();
				resultList = Search.phoneSearch(book, searched);
				if (resultList.isEmpty() == false)
				{
					System.out.println("Entry found!\n");
					for (int i=0; i < resultList.size(); i++)
					{
						System.out.println((book.get(resultList.get(i))).toString());
					}
				}
				else System.out.println("Sorry, no entry was found with that phone number.\n");
				break;
			case 4:
				System.out.println("Please provide the email of the contact you would like to find:");
				searched = input.nextLine();
				resultList = Search.emailSearch(book, searched);
				if (resultList.isEmpty() == false)
				{
					System.out.println("Entry found!\n");
					for (int i=0; i < resultList.size(); i++)
					{
						System.out.println((book.get(resultList.get(i))).toString());
					}
				}
				else System.out.println("Sorry, no entry was found with that email address.\n");
				break;
			default:
				System.out.println("Invalid option.");
				break;
		}	
		
	}

	static void addEntry(ArrayList<Contact> book)
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
		dupeCheck = Search.emailSearch(book, newEmail);
		if (dupeCheck.isEmpty() == true)
		{
			unique = true;
		}
		else unique = false;
		while (unique == false)
		{
			System.out.println("Each entry must have a unique email address. \n Please provide a unique email address:");
			newEmail = input.nextLine();
			dupeCheck = Search.emailSearch(book, newEmail);
			if (dupeCheck.isEmpty() == true)
			{
				unique = true;
			}
		}
		Contact newContact = new Contact(newFirstName, newLastName, newPhone, newEmail);//, newFavorite);
		System.out.println("\nA new contact has been added with the details below:\n" + newContact.toString());
		book.add(newContact);
	}
	
	static void editEntry(ArrayList<Contact> book)
	{
		System.out.println("Please provide the email of the contact you would like to update:");
		String searched = input.nextLine();
		ArrayList<Integer> resultList = new ArrayList<Integer>();
		resultList = Search.emailSearch(book, searched);
		if (resultList.isEmpty() == false)
		{
			int toEdit=-1;
			boolean confirmed = false;
			while (confirmed == false)
			{
				for(int i=0; i < resultList.size(); i++)
				{
					System.out.println("Would you like to update this entry?\n");
					System.out.println((book.get(resultList.get(i))).toString());
					System.out.println("Please input Y or yes to confirm selection:"); 
					String confirmation = input.nextLine();
					switch (confirmation)
					{
						case "Y":
							toEdit = resultList.get(i);
							confirmed = true;
							break;
						case "y":
							toEdit = resultList.get(i);
							confirmed = true;
							break;
						case "Yes":
							toEdit = resultList.get(i);
							confirmed = true;
							break;
						case "yes":
							toEdit = resultList.get(i);
							confirmed = true;
							break;
						default:
							confirmed = false;
							break;
					}
					if (confirmed == true) break;
				}
				
				if (confirmed == false)
				{
					System.out.println("End of matches. Update canceled. \n");
					confirmed = true;
				}
				
				else 
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
					int fieldOption =  input.nextInt();
					input.nextLine();
					
					switch (fieldOption)
					{
						case 1:
							System.out.println("Please provide the new first name of the contact:");
							String newFirstName = input.nextLine();
							book.get(toEdit).setFirstName(newFirstName);
							break;
						case 2:
							System.out.println("Please provide the new last name of the contact:");
							String newLastName = input.nextLine();
							book.get(toEdit).setLastName(newLastName);
							break;
						case 3:
							System.out.println("Please provide the new phone number of the contact:");
							String newPhone = input.nextLine();
							book.get(toEdit).setPhone(newPhone);
							break;
						case 4:
							System.out.println("Please provide the new email address of the contact:");
							String newEmail = input.nextLine();
							ArrayList<Integer> dupeCheck = new ArrayList<Integer>();
							boolean unique;
							dupeCheck = Search.emailSearch(book, newEmail);
							if (dupeCheck.isEmpty() == true)
							{
								unique = true;
							}
							else unique = false;
							while (unique == false)
							{
								System.out.println("Each entry must have a unique email address. \n Please provide a unique email address:");
								newEmail = input.nextLine();
								dupeCheck = Search.emailSearch(book, newEmail);
								if (dupeCheck.isEmpty() == true)
								{
									unique = true;
								}
							}
							book.get(toEdit).setEmail(newEmail);
							break;
						default:
							System.out.println("Invalid option.");
							break;
					}
					System.out.println("Your entry has been updated:\n");
					System.out.println((book.get(toEdit)).toString());
					
				}
				
			}
			
		}
		else System.out.println("Sorry, no entry was found with that email address.\n");
	
	}
	
	static void deleteEntry(ArrayList<Contact> book)
	{
		System.out.println("Please provide the email of the contact you would like to delete:");
		String searched = input.nextLine();
		ArrayList<Integer> resultList = new ArrayList<Integer>();
		resultList = Search.emailSearch(book, searched);
		if (resultList.isEmpty() == false)
		{
			boolean confirmed = false;
			while (confirmed == false)
			{
				for(int i=0; i < resultList.size(); i++)
				{
					System.out.println("Would you like to delete this entry?\n");
					System.out.println((book.get(resultList.get(i))).toString());
					System.out.println("Please input Y or yes to confirm deletion:"); 
					String confirmation = input.nextLine();
					int toDelete;
					switch (confirmation)
					{
						case "Y":
							toDelete = resultList.get(i);
							book.remove(toDelete);
							System.out.println("Entry deleted. \n");
							confirmed = true;
							break;
						case "y":
							toDelete = resultList.get(i);
							book.remove(toDelete);
							System.out.println("Entry deleted. \n");
							confirmed = true;
							break;
						case "Yes":
							toDelete = resultList.get(i);
							book.remove(toDelete);
							System.out.println("Entry deleted. \n");
							confirmed = true;
							break;
						case "yes":
							toDelete = resultList.get(i);
							book.remove(toDelete);
							System.out.println("Entry deleted. \n");
							confirmed = true;
							break;
						default:
							confirmed = false;
							break;
					}
					if (confirmed == true) break;
				}
				if (confirmed == false)
				{
					System.out.println("End of matches. Deletion canceled. \n");
					confirmed = true;
				}
			}
			
		}
		else System.out.println("Sorry, no entry was found with that email address.\n");
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
			System.out.print
				(
					"Options:\n"
					+ "1) View your Address Book\n"
					+ "2) Search for an entry\n"
					+ "3) Add a new entry\n"
					+ "4) Update an entry\n"
					+ "5) Delete an entry\n"
					+ "6) Delete your address book\n"
					+ "7) Quit program\n"
				);
			System.out.println("\n");
			System.out.println("Please provide a selection (1-7):");
			
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
						System.out.println("Who are you trying to find?\n");
						findEntry(AddressBook);
					}
					break;
				case 3:
					System.out.println("Sure! Who would you like to add?\n");
					addEntry(AddressBook);
					break;
				case 4:
					if (AddressBook.isEmpty() == true) System.out.println("Your address book is empty, there are no entries to update.\n");
					else
					{
						System.out.println("Sure! Which entry would you like to edit?\n");
						editEntry(AddressBook);
					}
					break;
				case 5:
					if (AddressBook.isEmpty() == true) System.out.println("Your address book is empty, there are no entries to delete.\n");
					else
					{
						System.out.println("Which entry are you trying to remove?\n");
						deleteEntry(AddressBook);
					}
					break;
				case 6:
					if (AddressBook.isEmpty() == true) System.out.println("Your address book is empty, there is nothing to delete.\n");
					else
					{
						System.out.println("You have chosen to delete your address book.\n");
						deleteBook(AddressBook);
					}
					break;
				case 7:
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
