package dazuoye;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class zhulei {
	   public static void main(String[] args) {
		   JFrame game = new JFrame();
		   game.setTitle("8008121274-莫孔贵");
		   game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		   game.setSize(699, 748);
		   game.setLocationRelativeTo(null);
		   //手动布局
		   game.setLayout(null);	   
		   ImageIcon bg=new ImageIcon("src/image/background.jpg");
		      //图片添加到标签
				JLabel label=new JLabel(bg);
				//图片大小
				label.setSize(699, 748);
				//设置所在层
				game.getLayeredPane().add(label,new Integer(Integer.MIN_VALUE));
				JPanel pan=(JPanel) game.getContentPane();
				//窗口是否不透明
				pan.setOpaque(false);					   
		   JButton a0=new JButton("关于");
		   JButton a1=new JButton("游戏");
		   JButton a2=new JButton("退出");
		   a0.setBounds(340, 400, 100, 50);
		   a1.setBounds(340, 500, 100, 50);
		   a2.setBounds(340, 600, 100, 50);
		   game.add(a0);
		   game.add(a1);
		   game.add(a2);
		   a0.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub	
					webjian m=new webjian();					
						try {
							m.show();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}					
				}
			   });
		   a1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub	
				chuangkou m=new chuangkou();
				m.game();			
			}
		   });
		   a2.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub	
					System.exit(0);	
				}
			   });
		   game.setResizable(false);
		   game.setVisible(true);
	    }

}



