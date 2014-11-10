import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;



public class LZW extends JFrame{
	boolean flag =true;
    ArrayList<String>arr = new ArrayList();
    File file = new File("dic.txt");
	Font f = new Font("Kunstler ITC",Font.ITALIC,35);
	Font font = new Font("Arial", Font.BOLD,20);
	JLabel label = new JLabel("Enter text : ");
	JLabel label2 = new JLabel("Enter tags : ");
	JTextField text = new JTextField(20);
	JTextField text2 = new JTextField(20);
	Button c =new Button(" OK ");
	Button c1 =new Button(" OK ");
	JTextArea area  = new JTextArea("");
	JTextArea area2  = new JTextArea("");
	String input="",output="",s="",ss="", newS="";
	
	
	public LZW(){
		super("LZW System");
		setLayout(new FlowLayout());
		JButton c = new JButton("       Compress       ");
		JButton d = new JButton("     DeCompress     ");
		c.setBackground(Color.pink);
		d.setBackground(Color.pink);
		c.setFont(f);
		d.setFont(f);
		
		add(c);
		add(d);
		getContentPane().setBackground(Color.cyan);
		setSize(450, 450);
		setVisible(true);
		
		c.addActionListener(new Handler());
		d.addActionListener(new Handler());
	}
	public void writeToFile(){
		//write output to file
		try {
			
            FileWriter fw = new FileWriter(file);
            fw.write(output);
            fw.close();
			
		} catch (FileNotFoundException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void readFromFile(){
		//read output from file
		
		try {
			FileInputStream newTextFile = new FileInputStream("dic.txt");

            String h=read(newTextFile);
			area.setText(h);
			area2.setText(h);
		} catch (FileNotFoundException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		}
	
	public void Basic_Dictionary(){
		String s="";
		//to make basic dictionary
		for(int i=0;i<=127;i++){
			char a = (char)i;
			s=a+"";
			arr.add(s);
		}
	}
	public void Compress(){
		Basic_Dictionary();
		input = text.getText();
		boolean b = true;
		String newS="",newSS="";
		boolean v=true;
		for(int i=0;i<input.length();i++){
			if(!v)break;
			newS="";newSS="";//newSS stay before newS 1 step
			 newS+=input.charAt(i);
			
			  if(b==true){
				String n=newS+input.charAt(i+1);
				
				if(!arr.contains(n)){
				output+=arr.indexOf(newS);
				output+=" ";
				int ke= newS.length();
			    i+=(ke-1);
				}
				else{
					output+=arr.indexOf(n);
					output+=" ";
				}
				
				if(s.contains(newS)){
					b=false;}
				
				if(i+1<input.length()){//to take next char
					newS+=input.charAt(i+1);
					
					if(!arr.contains(newS))
						 {arr.add(newS);}
						
					}
				}
			
			else {
				if(i+1<input.length()){//to take next char
					for(int u=i;u<input.length()-1;u++){
					newS+=input.charAt(u+1);
					newSS+=input.charAt(u);
					if(!arr.contains(newS)&&arr.contains(newSS))
					   {arr.add(newS);
					   output+=arr.indexOf(newSS);
					   output+=" ";
					   i=u;
					   break;}
					else if(u==input.length()-2&&arr.contains(newS))
						 {output+=arr.indexOf(newS);
						 v=false;break;}
				}
					
			}
				else {
					output+=arr.indexOf(newS);
				    output+=" ";
				}
			}
			s+=newS;
			}
		writeToFile();
		readFromFile();
	}
	
	public void Decompress(){
		Basic_Dictionary();
		int x[]=new int[100];
		int count=0;
		input = text2.getText();
		String num = "";
		
		
		for(int i=0;i<input.length();i++){
			boolean b=true;
			char c = input.charAt(i);
					
		    if (c >= '0' && c <= '9') num+=c;
		    else {b=false;}
		if(b==false||i==input.length()-1){// space found "all number"
			
			x[count]=Integer.parseInt(num);
			if(x[count]>=arr.size()){//if i reseved number not in dictionary yet
				arr.add(arr.get(x[count-1])+arr.get(x[count-1]).substring(0,1));
			}
			else  if(count> 0&& x[count]<=arr.size()-1){
				String k=arr.get(x[count-1]);//last string
				k+=(arr.get(x[count]).substring(0,1));//+first char from new string
				arr.add(k);
			}
			output+=arr.get(x[count]);
			count++;
			num="";
		}
	
	}
    writeToFile();
    readFromFile();
	}
	
	public String read(FileInputStream in) throws IOException{
		String s="",result="";
		 try (BufferedReader reader = new BufferedReader(
		            new InputStreamReader(in, Charset.defaultCharset()))) {
		         
		            if ((s = reader.readLine()) != null) {
		               result+=s;
		            }
		            return (result);
}
}
	
	public class Handler implements ActionListener{

		
		Hand h = new Hand();
		
		
		@Override
		public void actionPerformed(ActionEvent a) {
			area.setDisabledTextColor(Color.pink);
			area.setSize(200, 400);
			c.addActionListener(h);
			c1.addActionListener(h);
			
			if(a.getActionCommand()=="       Compress       "){
				JFrame frame = new JFrame("Compression System");
				JPanel panel = new JPanel();
				
				label.setFont(font);
				label.setForeground(Color.red);
				
				c.setForeground(Color.yellow);
				c.setBackground(Color.pink);
				c.setFont(font);
				
				c.setForeground(Color.yellow);
				c.setBackground(Color.pink);
				c.setFont(font);
				
				panel.add(label);
				panel.add(text);
				panel.add(c);
				panel.add(area);
				panel.setBackground(Color.cyan);
				frame.add(panel);
				frame.setSize(470,500);
				frame.setVisible(true);
				frame.setResizable(false);
			}
			
			else if(a.getActionCommand()=="     DeCompress     "){
				JFrame frame = new JFrame("DeCompression System");
				JPanel panel = new JPanel();
				
				
				label2.setFont(font);
				label2.setForeground(Color.red);
				
				area2.setSize(1200, 1800);
				
				c1.setForeground(Color.yellow);
				c1.setBackground(Color.pink);
				c1.setFont(font);
				
				panel.add(label2);
				panel.add(text2);
				panel.add(c1);
				panel.add(area2);
				panel.setBackground(Color.cyan);
				frame.add(panel);
				frame.setSize(470,500);
				frame.setVisible(true);
				frame.setResizable(false);
				area2.setTabSize(1000);
			}
		  
		}
		  class  Hand implements ActionListener{

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					
						//Compression Function
					if(e.getSource()==c){
						Compress();
					}
						
					//DeCompression Function
					else if(e.getSource()==c1){
						Decompress();		
				}
				}			
	}}}
			
	