package dazuoye;

import java.awt.CardLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class chuangkou {

	 public static void game(){
		 SnakeDemo t = new SnakeDemo();
	      t.Thread(); 
	      JFrame game = new JFrame();
	      game.setTitle("8008121274-莫孔贵");
	      //窗口可关
	      game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	      //游戏窗口大小
	      game.setSize(802, 807);
	      ImageIcon bg=new ImageIcon("src/zhiyuan/background.jpg");
	      //图片添加到标签
			JLabel label=new JLabel(bg);
			//图片大小
			label.setSize(699, 748);
			//设置所在层
			game.getLayeredPane().add(label,new Integer(Integer.MIN_VALUE));
			JPanel pan=(JPanel) game.getContentPane();
			//窗口是否不透明
			pan.setOpaque(false);
			pan.setLayout(new CardLayout());//流动
	      game.setResizable(false);
	      //窗口居中
	      game.setLocationRelativeTo(null);
	      game.add(t);
	    //窗口可见
	      game.setVisible(true);
	 }
}