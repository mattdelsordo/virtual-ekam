/*
 * Matt DelSordo
 * 11/6/15
 * This class implements a hash table
 * Based off my hash table lab
 */

import java.io.*;
import java.util.*;

public class HashTable
{
	private String[] table;
	private int size;
	private Random rand;
	private int lasthash; //used to make the randomizer not return the same object twice

	//constructor
	public HashTable()
	{
		this(7); //default size
	}

	public HashTable(int startingSize)
	{
		table = new String[startingSize]; //default table size is 13 but can be changed
		size = 0;
		lasthash = -1;
		rand = new Random();
	}
	
	public HashTable(InputStream file, int startingSize)
	{
		table = new String[startingSize]; //default table size is 13 but can be changed
		size = 0;
		lasthash = -1;
		rand = new Random();
		loadFromFile(file);
	}
	
	//hash function borrowed from 5.2 in Wiess
	private int hashFunction(String key)
	{
		int hashVal = 0;
		
		for(int i = 0; i < key.length(); i++)
			hashVal = 37 * hashVal + key.charAt(i);
		
		hashVal %= capacity();
		if (hashVal < 0)
			hashVal += capacity();
		
		return hashVal;
	}
	
	//insert
	public void insert(String key)
	{
		if(lookup(key) == false) //check if key is already in the table
		{
			int hashVal = hashFunction(key);
			while(table[hashVal] != null)
			{
				hashVal++;
				if(hashVal >= capacity())
					hashVal %= capacity();
			}
			
			table[hashVal] = key;
			size++;
			
			if(getLoadFactor() > 0.5) expand();
		}
		else
		{
			//System.out.println(key + " already in table.");
		}
	}
	
	//prints all non void elements of the table
	public void print()
	{
		for(int i = 0; i < capacity(); i++)
		{
			if(table[i] != null) System.out.print(table[i] + " ");
		}
		System.out.println();
	}
	
	//returns capacity
	public double capacity()
	{
		return table.length;
	}
	
	//returns number of unique items
	public double getSize()
	{
		return size;
	}
	
	//returns a random String from the table
	public String randomEntry()
	{
		int hash = rand.nextInt(size);

		while(table[hash] == null || hash == lasthash)
		{
			hash = rand.nextInt(size);
		}
		
		lasthash = hash;
		return table[hash];
	}
	
	//if key is in the table return true
	public boolean lookup(String key)
	{
		boolean found = false;
		int hash = hashFunction(key);
		while(table[hash] != null)
		{
			//System.out.println(table[hash]);
			if(table[hash].equals(key)){
				found = true;
				break;
			}
			hash++;
			if(hash >= capacity())
				hash %= capacity();
		}
		return found;
	}
	
	//returns load factor
	public double getLoadFactor()
	{
		return getSize() / capacity();
	}
	
	//expands the table to double it's size
	private void expand()
	{
		String[] temp = table;
		table = new String[(int) (capacity() * 2)]; //double length
		
		size = 0;
		for(int i = 0; i < temp.length; i++) //rehash into new table
		{
			if(temp[i] != null)
				insert(temp[i]);
		}
		
		//System.out.println("Table expanded.");
	}
	
	//loads a hash table from file
	public void loadFromFile(InputStream file)
	{		
		//read each word into the hash table
		String line = null;
		try
		{
			//FileReader reader = new FileReader(file);
			BufferedReader buffer = new BufferedReader(new InputStreamReader(file));
			
			while((line = buffer.readLine()) != null)
			{
				insert(line);
			}
			
			//dictionary.print();
			//System.out.println("Dictionary loaded.");
			buffer.close();	
		}
		catch(FileNotFoundException e)
		{
			System.out.println("Cannot find file " + file);
			System.exit(0);
		}
		catch(IOException e)
		{
			System.out.println("Error reading " + file);
			System.exit(0);
		}
	}
	
	//prints hash table to a file
	public void printToFile(String path)
	{
		//output all strings in totalSolutions to a .txt'
		try{
			FileWriter writer = new FileWriter(path);
			BufferedWriter buffer = new BufferedWriter(writer);
			
			for(int i  = 0; i < table.length; i++)
			{
				if(!table[i].equals(null))
				{
					buffer.write(table[i]);
					buffer.newLine();
				}
			}
			
			System.out.println("Successfully printed to file " + path);
			buffer.close();
			
		}
		catch(IOException e){
			System.out.println("Error writing to " + path);
			System.exit(0);
		}
	}
}
