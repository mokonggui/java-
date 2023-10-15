package dazuoye;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
class webjian{
void show() throws IOException{
	try{
		JFrame game = new JFrame();
		   game.setTitle("8008121274-莫孔贵");
		   game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		   game.setSize(699, 748);
		   game.setLocationRelativeTo(null);
		   game.setVisible(true);
		   JTextArea ta = new JTextArea();
			JScrollPane jsp = new JScrollPane(ta);
			ta.setText(null);
		   File f=new File("src/image/yyy");
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
			String line = reader.readLine();
			while (line != null) {	
				ta.append(line);
				ta.append("\r\n");
				game.add(jsp);
				game.validate();
				line = reader.readLine();
		}
			reader.close();
	}catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}	
			
}
}