package edu.unca.cburris.bukkit.accesscontrol.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ListStore {

	private File storageFile;
	private ArrayList<String> values;
	public ListStore(File file){
		this.storageFile = file;
		this.values = new ArrayList<String>();
		
		if(this.storageFile.exists() == false){
			try {
				this.storageFile.createNewFile();
			}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
		public void load(){
			try {
				DataInputStream in = new DataInputStream(new FileInputStream(this.storageFile));
				BufferedReader read = new BufferedReader(new InputStreamReader(in));
				String line;
				
				while((line = read.readLine()) != null){
					if(this.contains(line) == false){
						this.values.add(line);
					}
				}
				read.close();
				in.close();
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		public void save(){
			try{
			FileWriter stream = new FileWriter(this.storageFile);
			BufferedWriter output = new BufferedWriter(stream);
			
			for(String value : this.values){
				output.write(value);
				output.newLine();
			}
			
			output.close();
			stream.close();
			
		}catch(IOException e){
				e.printStackTrace();
			}
			
			
		}
	
		public boolean contains(String value){
			return this.values.contains(value);
			
		}
		
		public void add(String value){
			if(this.contains(value) == false){
				this.values.add(value);
			}
		}
		
		public void remove(String value){
			this.values.remove(value);
		}
		
		public ArrayList<String> getValues(){
			return this.values;
		}
	
	
	
}
