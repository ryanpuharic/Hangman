package application;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class Hangman{
	  static ArrayList<String> correctGuesses;
	  static ArrayList<String> incorrectGuesses;
	  static ArrayList<String> leadersList;
	  static ArrayList<Integer> nums;
	  ArrayList<String> dict;
	  boolean win;
	  static int numGuesses;
	  static String word;
	  static String blanks;
	  String[] blanksArr;
	  String incorrects;
	  String currentGuess;
	  String playAgain;
	  Scanner scan = new Scanner(System.in);

	  /**
	  Instantiates all global variables in a Hangman game
	  */
	  public Hangman()throws IOException
	  {
	    Scanner file = new Scanner(new File("input.txt"));
	    win = false;
	    numGuesses = 0;
	    correctGuesses = new ArrayList<String>();
	    incorrectGuesses = new ArrayList<String>();
	    leadersList = new ArrayList<String>();
	    nums = new ArrayList<Integer>();
	    dict = new ArrayList<String>();
	    word = "";
	    blanks = "";
	    incorrects = "";
	    currentGuess = "";
	    playAgain = "YES";
	    

	    while(file.hasNext())
	      dict.add(file.next());
	  }

	  public void pickWord()
	  {
	    String str = "";
	    Random rand = new Random();
	    int randNum = rand.nextInt(dict.size());

	    for(int i = 0; i <= randNum; i++)
	      str = dict.get(i);
	    word = str.toLowerCase();  
	    blanksArr = new String[word.length()];   
	  }
	  
	  public void reset()
	  {
	    ArrayList<String> newCorrect = new ArrayList<String>();
	    ArrayList<String> newIncorrect = new ArrayList<String>();
	    correctGuesses = newCorrect;
	    incorrectGuesses = newIncorrect;
	    numGuesses = 0;
	    word = "";
	    blanks = "";
	    incorrects = "";
	    currentGuess = "";
	    win = false;
	  }
	  
	  public void setBlanks()
	  {
	    for(int i = 0; i < word.length(); i++)
	    {
	      blanksArr[i] = "_";
	      blanks += "_ ";
	    }
	  }
	  
	  public boolean checkLetter(char input)
	  {
	    for(int i = 0; i < word.length(); i++)
	      if(input == word.charAt(i))
	      {
	        correctGuesses.add("" + input);
	        return true;
	      }

	    incorrectGuesses.add("" + input);
	    return false;
	  }
	  
	  public void updateBlanks(char input)
	  {
	    blanks = "";
	    for(int i = 0; i < blanksArr.length; i++)
	      if(input == word.charAt(i))
	        blanksArr[i] = input + "";

	    for(int i = 0; i < blanksArr.length; i++)
	      blanks += blanksArr[i] + " ";
	    
	  }
	  
	  public boolean checkIfValid(String guess)
	  {
		boolean returnVal = false;
		if(guess.length() == word.length())
		{
			returnVal = true;
			for (int i = 0; i < guess.length(); i++)
			{
				if (guess.charAt(i) < 97 || guess.charAt(i) > 122) // if letter is not a-z or A-Z
					return false;
			}
		}
		return returnVal;
	  }
	  
	  public boolean checkInitial(String guess)
	  {
		guess = guess.toLowerCase();
		for (int i = 0; i < guess.length(); i++)
			if (guess.charAt(i) < 97 || guess.charAt(i) > 122) // if letter is not a-z or A-Z
				return false;
		return true;
	  }
	  
	  public boolean checkWin()
	  {
	    boolean result = false;
	    int count = 0;
	    for(int i = 0; i < word.length(); i++)
	      if(word.charAt(i) == blanksArr[i].charAt(0) || win)
	        count++;
	    if(count == word.length())
	      result = true;
	    return result;
	  }
	  
	  public void addLeader(String input)
	  {
		  try
			{
				FileWriter writer = new FileWriter("leaderboard.txt", true);
				BufferedWriter out = new BufferedWriter(writer); 
		        out.write(input + "\n"); 
		        out.close(); 
			}
			catch(IOException e)
			{
			    e.printStackTrace();
			}
	  }
	  
	  public void updateLeaders() throws IOException
	  {
		  File file = new File("leaderboard.txt"); 
		  
		  BufferedReader br = new BufferedReader(new FileReader(file)); 
		  String st; 
		  while ((st = br.readLine()) != null) 
		    leadersList.add(st);
	  }
	  
	  public String getLeaders()
	  {
		  ArrayList<String> move = new ArrayList<String>();
		  ArrayList<String> result = new ArrayList<String>();
		  int count = 0;
		  String str = "";
		  boolean flag = true;
		  for(int i = 0; i < leadersList.size(); i++)
		  {
			  flag = true;
			  count = 0;

			  for(int j = 0; j < leadersList.size(); j++)
				  if(leadersList.get(j).equals(leadersList.get(i)))
					  count++;
			  
			  for(int j = 0; j < move.size(); j++)
				  if(leadersList.get(i).equals(move.get(j)))
					  flag = false;
			
			  if(flag)
			  {
				  move.add(leadersList.get(i));
				  nums.add(count);
				  Collections.sort(nums);
				  Collections.reverse(nums);
				  result.add(nums.indexOf(count), leadersList.get(i) + "   \t\t\t\t\t\t\t\t" + count);
			  }
			  
		  }
		  
		  for(int i = 0; i < result.size(); i++)
			  str+= result.get(i) + "\n";
		  
		  nums = new ArrayList<Integer>();
		  leadersList = new ArrayList<String>(); 

		  return str;
	  }

}
