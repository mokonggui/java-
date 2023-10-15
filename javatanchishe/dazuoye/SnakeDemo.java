package dazuoye;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Random;

import javax.swing.*;

class Tile{
    int x;
    int y;
    
    public Tile(int x0,int y0){
        x = x0;
        y = y0;
    }
}

public class SnakeDemo extends JComponent{
    private static final long serialVersionUID = 3794762291171148906L;
    private final int MAX_SIZE = 700;//蛇身体最长为400节
    private Tile temp = new Tile(0,0);
    private Tile temp2 = new Tile(0,0);
    private Tile head = new Tile(227,100);//头部的位置初始化为(227,100)
    private Tile[] body = new Tile[MAX_SIZE];
    private String direction = "R";//默认向右走
    private String current_direction = "R";//当前方向
    private boolean first_launch = false;
    private boolean iseaten = false;
    private boolean isrun = true;
    private int randomx,randomy;
    private int body_length = 5;//身体长度初始化为5
    private Thread run;
    private JLabel label1 = new JLabel("当前长度：");
    private JLabel label2 = new JLabel("所花时间：");
    private JLabel label3 = new JLabel("说          明：");
    private JTextArea explain = new JTextArea("此贪吃蛇，实现简单地移动，得分，判断撞墙和撞自己的功能，"
            + "初始长度为6，头部为红色，身体的颜色渐变。\n"
            + "游戏界面按上下左右键实现移动，按ESC重新开始，按空格键暂停");
    private JLabel Score = new JLabel("6");
    private JLabel Time = new JLabel("");
    private Font f = new Font("微软雅黑",Font.PLAIN,15);
    private Font f2 = new Font("微软雅黑",Font.PLAIN,13);
    private JPanel p = new JPanel();
    private int hour =0;
    private int min =0;
    private int sec =0 ;
    private boolean pause = false;
    
    public SnakeDemo(){
        String lookAndFeel =UIManager.getSystemLookAndFeelClassName();
        try {
            UIManager.setLookAndFeel(lookAndFeel);
        } 
        catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        } 
        catch (InstantiationException e1) {
            e1.printStackTrace();
        } 
        catch (IllegalAccessException e1) {
            e1.printStackTrace();
        } 
        catch (UnsupportedLookAndFeelException e1) {
            e1.printStackTrace();
        }
        
        //右边提示布局
        add(label1);
        label1.setBounds(700, 10, 800, 20);
        label1.setFont(f);
        add(Score);
        Score.setBounds(700, 35, 80, 20);
        Score.setFont(f);
        add(label2);
        label2.setBounds(700, 60, 80, 20);
        label2.setFont(f);
        add(Time);
        Time.setBounds(700, 85, 80, 20);
        Time.setFont(f);
        add(p);
        p.setBounds(700, 110, 93, 1);
        p.setBorder(BorderFactory.createLineBorder(Color.black));
        
        add(label3);
        label3.setBounds(700, 115, 80, 20);
        label3.setFont(f);
        add(explain);
        explain.setBounds(700, 138, 80, 350);
        explain.setFont(f2);
        explain.setLineWrap(true);
        explain.setOpaque(false); 
        
        for(int i = 0; i < MAX_SIZE;i++)
        {
            body[i] = new Tile(0,0);
        }
        
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if(e.getKeyCode() == KeyEvent.VK_RIGHT)
                {
                    if(isrun && current_direction != "L")
                    {
                        direction = "R";
                    }
                }
                if(e.getKeyCode() == KeyEvent.VK_LEFT)
                {
                    if(isrun && current_direction != "R")
                    {
                        direction = "L";
                    }
                }
                if(e.getKeyCode() == KeyEvent.VK_UP)
                {
                    if(isrun && current_direction != "D")
                    {
                        direction = "U";
                    }
                }
                if(e.getKeyCode() == KeyEvent.VK_DOWN)
                {
                    if(isrun && current_direction != "U")
                    {
                        direction = "D";
                    }
                }
                if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
                {
                    direction = "R";//默认向右走
                    current_direction = "R";//当前方向
                    first_launch = false;
                    iseaten = false;
                    isrun = true;
                    body_length = 5;
                    head = new Tile(227,100);
                    Score.setText("6");
                    hour =0;
                    min =0;
                    sec =0 ;
                    for(int i = 0; i < MAX_SIZE;i++)
                    {
                        body[i].x = 0;
                        body[i].y = 0;
                    }
                    
                    run = new Thread();
                    run.start();
                    System.out.println("Start again");
                }
              //按空格键暂停
                if(e.getKeyCode() == KeyEvent.VK_SPACE)
                {               
                	//暂停
                    if(true)
                    {                  	
                        	pause = true;
                            isrun = false;                          
                            	int result=JOptionPane.showConfirmDialog(null, "游戏暂停，是否继续？", "Information", JOptionPane.YES_NO_OPTION);
                                //开始
                                  if(result==JOptionPane.YES_NO_OPTION){
                                  	pause = false;
                                    isrun = true;                                     
                                  }
                                  //退出游戏
                                  else{
                                  	System.exit(0);
                                  }                           
                    }                 
                }
            }
        }
        );
        
        new Timer();
        
        setFocusable(true);
    }
    
    public void paintComponent(Graphics g1){
        super.paintComponent(g1);
        Graphics2D g = (Graphics2D) g1;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,RenderingHints.VALUE_STROKE_NORMALIZE);
        
        //画头部
        g.setColor(Color.red);
        g.fillRoundRect(head.x, head.y, 20, 20, 10, 10);
        
        g.setPaint(new GradientPaint(115,135,Color.CYAN,230,135,Color.MAGENTA,true));
        if(!first_launch)
        {
            //初始化身体
            int x = head.x;
            for(int i = 0;i < body_length;i++)
            {
                x -= 22;//相邻两个方块的间距为2个像素，方块宽度都为20像素
                body[i].x = x;
                body[i].y = head.y;
                g.fillRoundRect(body[i].x, body[i].y, 20, 20, 10, 10);
            }
            //初始化食物位置
            ProduceRandom();
            g.fillOval(randomx, randomy, 19, 19);
        }
        else
        {
            //每次刷新身体
            for(int i = 0;i < body_length;i++)
            {
                g.fillRoundRect(body[i].x, body[i].y, 20, 20, 10, 10);
            }
            
            if(EatFood())//被吃了重新产生食物
            {
                ProduceRandom();
                g.fillOval(randomx, randomy, 19, 19);
                iseaten = false;
            }
            else
            {
                g.fillOval(randomx, randomy, 19, 19);
            }
        }
        first_launch = true;
        //墙
        g.setStroke( new BasicStroke(4,BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL));
        g.setBackground(Color.black);
        g.drawRect(2, 7, 700, 769);
        
        //网格线
        //纵线
        for(int i = 1;i < 32;i++)
        {
            g.setStroke( new BasicStroke(1,BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL));
            g.setColor(Color.black);
            g.drawLine(5+i*22,9,5+i*22,800);
        }
      //横线          
        for(int n=1;n<35;n++){
        	g.setStroke( new BasicStroke(1,BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL));
            g.setColor(Color.black);
        	g.drawLine(4,10+n*22,700,10+n*22);
        }
    }
    
    //产生随机数
    public void ProduceRandom(){
        boolean flag = true;
        Random rand = new Random();
        randomx = (rand.nextInt(25) + 1) * 22 + 7;
        randomy = (rand.nextInt(30) + 1) *22 + 12;
        while(flag)
        {
            for(int i = 0;i < body_length; i++)
            {
                if(body[i].x == randomx && body[i].y == randomy)
                {
                    randomx = (rand.nextInt(25) + 1) * 22 + 7;
                    randomy = (rand.nextInt(30) + 1) *22 + 12;
                    flag = true;
                    break;
                }
                else
                {
                    if(i == body_length - 1)
                    {
                        flag = false;
                    }
                }
            }
        }
    }
    
  //判断是否撞墙
    public void HitWall() {
        if(current_direction == "L")
        {
            if(head.x < 0)
            {
                isrun = false;			
					gg q = new gg();
		            q.gg(body_length,hour,min,sec);
                int result=JOptionPane.showConfirmDialog(null, "游戏结束，是否再次尝试？", "Information", JOptionPane.YES_NO_OPTION);
                if(result==JOptionPane.YES_NO_OPTION)
                {
                    direction = "R";//默认向右走
                    current_direction = "R";//当前方向
                    first_launch = false;
                    iseaten = false;
                    isrun = true;
                    body_length = 5;
                    head = new Tile(227,100);
                    Score.setText("6");
                    hour =0;
                    min =0;
                    sec =0 ;
                    for(int i = 0; i < MAX_SIZE;i++)
                    {
                        body[i].x = 0;
                        body[i].y = 0;
                    }
                    
                    run = new Thread();
                    run.start();
                    System.out.println("Start again");
                }
                else
                {
                	System.exit(0);
                }        
            }
        }
        if(current_direction == "R")
        {
            if(head.x > 680)
            {
                isrun = false;
                gg q = new gg();
	            q.gg(body_length,hour,min,sec);
                int result=JOptionPane.showConfirmDialog(null, "游戏结束，是否再次尝试？", "Information", JOptionPane.YES_NO_OPTION);
                if(result==JOptionPane.YES_NO_OPTION)
                {
                    direction = "R";//默认向右走
                    current_direction = "R";//当前方向
                    first_launch = false;
                    iseaten = false;
                    isrun = true;
                    body_length = 5;
                    head = new Tile(227,100);
                    Score.setText("6");
                    hour =0;
                    min =0;
                    sec =0 ;
                    for(int i = 0; i < MAX_SIZE;i++)
                    {
                        body[i].x = 0;
                        body[i].y = 0;
                    }
                    
                    run = new Thread();
                    run.start();
                    System.out.println("Start again");
                }
                else
                {
                	System.exit(0);
                }
            }
        }
        if(current_direction == "U")
        {
            if(head.y < 12)
            {
                isrun = false;
                gg q = new gg();
	            q.gg(body_length,hour,min,sec);
                int result=JOptionPane.showConfirmDialog(null, "游戏结束，是否再次尝试？", "Information", JOptionPane.YES_NO_OPTION);
                if(result==JOptionPane.YES_NO_OPTION)
                {
                	//默认向右走
                    direction = "R";
                  //当前方向
                    current_direction = "R";
                    first_launch = false;
                    iseaten = false;
                    isrun = true;
                    body_length = 5;
                    head = new Tile(227,100);
                    Score.setText("6");
                    hour =0;
                    min =0;
                    sec =0 ;
                    for(int i = 0; i < MAX_SIZE;i++)
                    {
                        body[i].x = 0;
                        body[i].y = 0;
                    }
                    
                    run = new Thread();
                    run.start();
                    System.out.println("Start again");
                }
                else
                {
                	System.exit(0);
                }
            }
        }
        if(current_direction == "D")
        {
            if(head.y > 740)
            {
                isrun = false;
                gg q = new gg();
	            q.gg(body_length,hour,min,sec);
                int result=JOptionPane.showConfirmDialog(null, "游戏结束，是否再次尝试？", "Information", JOptionPane.YES_NO_OPTION);
                if(result==JOptionPane.YES_NO_OPTION)
                {
                	//默认向右走
                    direction = "R";
                  //当前方向
                    current_direction = "R";
                    first_launch = false;
                    iseaten = false;
                    isrun = true;
                    body_length = 5;
                    head = new Tile(227,100);
                    Score.setText("6");
                    hour =0;
                    min =0;
                    sec =0 ;
                    for(int i = 0; i < MAX_SIZE;i++)
                    {
                        body[i].x = 0;
                        body[i].y = 0;
                    }
                    
                    run = new Thread();
                    run.start();
                    System.out.println("Start again");
                }
                else
                {
                	System.exit(0);
                }
            }
        }
    }
    
  //判断是否撞到自己身上
    public void HitSelf(){
        for(int i = 0;i < body_length; i++)
        {
            if(body[i].x == head.x && body[i].y == head.y)
            {
                isrun = false;
                gg q = new gg();
	            q.gg(body_length,hour,min,sec);
                int result=JOptionPane.showConfirmDialog(null, "游戏结束，是否再次尝试？", "Information", JOptionPane.YES_NO_OPTION);
                if(result==JOptionPane.YES_NO_OPTION)
                {
                	//默认向右走
                    direction = "R";
                  //当前方向
                    current_direction = "R";
                    first_launch = false;
                    iseaten = false;
                    isrun = true;
                    body_length = 5;
                    head = new Tile(227,100);
                    Score.setText("6");
                    hour =0;
                    min =0;
                    sec =0 ;
                    for(int j = 0; j < MAX_SIZE;j++)
                    {
                        body[j].x = 0;
                        body[j].y = 0;
                    }
                    
                    run = new Thread();
                    run.start();
                    System.out.println("Start again");
                }
                else
                {
                    System.exit(0);
                }
                break;
            }
        }
    }
    
    public boolean  EatFood(){
        if(head.x == randomx && head.y == randomy)
        {
            iseaten = true;
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public void Thread(){
    	Thread  run = new Thread() {
            public void run() {
                while (true) 
                {
                    try {
                    	//速度、每隔millis毫秒刷新一次
                        long millis = 200;
                        Thread.sleep(millis);
                    } 
                    catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    
                    if(!pause)
                    {    
                        temp.x = head.x;
                        temp.y = head.y;
                        //头部移动
                        if(direction == "L")
                        {
                            head.x -= 22;
                        }
                        if(direction == "R")
                        {
                            head.x += 22;
                        }
                        if(direction == "U")
                        {
                            head.y -= 22;
                        }
                        if(direction == "D")
                        {
                            head.y += 22;
                        }
                      //刷新当前前进方向
                        current_direction = direction;
                        //身体移动
                        for(int i = 0;i < body_length;i++)
                        {
                            temp2.x = body[i].x;
                            temp2.y = body[i].y;
                            body[i].x = temp.x;
                            body[i].y = temp.y;
                            temp.x = temp2.x;
                            temp.y = temp2.y;
                        }
                        
                        if(EatFood())
                        {
                            body_length ++;
                            body[body_length-1].x = temp2.x;
                            body[body_length-1].y = temp2.y;
                            Score.setText("" + (body_length+1) );
                        }
                        
                        repaint();
                        
                        HitWall();
                        HitSelf();
                    }
                }
            }
        };
        
        run.start();
    }
   
    //计时器类
    class Timer extends Thread{  
            public Timer(){
                this.start();
            }
            @Override
            public void run() {
                while(true){
                    if(isrun){
                        sec +=1 ;
                        if(sec >= 60){
                            sec = 0;
                            min +=1 ;
                        }
                        if(min>=60){
                            min=0;
                            hour+=1;
                        }
                        showTime();
                    }
         
                    try {
                        Thread.sleep(1000);
                    } 
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                     
                }
            }

            private void showTime(){
                String strTime ="" ;
                if(hour < 10)
                    strTime = "0"+hour+":";
                else
                    strTime = ""+hour+":";
                 
                if(min < 10)
                    strTime = strTime+"0"+min+":";
                else
                    strTime =strTime+ ""+min+":";
                 
                if(sec < 10)
                    strTime = strTime+"0"+sec;
                else
                    strTime = strTime+""+sec;
                 
                //在窗体上设置显示时间
                Time.setText(strTime);
            }
        }    
}


class gg{
	public gg() {
		// TODO Auto-generated constructor stub
	}
	public void gg(int k,int hour,int min,int sec) {
	try{
		String strTime ="" ;
        if(hour < 10)
            strTime = "0"+hour+":";
        else
            strTime = ""+hour+":";
         
        if(min < 10)
            strTime = strTime+"0"+min+":";
        else
            strTime =strTime+ ""+min+":";
         
        if(sec < 10)
            strTime = strTime+"0"+sec;
        else
            strTime = strTime+""+sec;
		int l=k-5;
		File f=new File("src/zhiyuan/kkk");
    	BufferedWriter d=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f)));
			d.write("得分："+l);
	   	    d.write("，用时："+strTime);
	   	    d.newLine();
            d.close();    
	}catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
}