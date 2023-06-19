import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.JTextArea;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.sql.*;

public class CommentPage extends JFrame {
	private User user;
	private int CaseID;

	/**
	 * Launch the application.

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CommentPage frame = new CommentPage();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	 */
	/**
	 * Create the frame.
	 */
	public CommentPage(User user, int CaseID ) {
		this.user = user;
		this.CaseID = CaseID;
		
		String server = "jdbc:mysql://140.119.19.73:3315/";
        String database = "109208079";
        String url = server + database + "?useSSL=false";
        String username = "109208079";
        String password = "uka1z";
		
		getContentPane().setBackground(new Color(176,224,230)); 
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(600, 600);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
	
		getContentPane().setLayout(null);
		
		JLabel lblTitle = new JLabel("評價頁面");
		lblTitle.setFont(new Font("微軟正黑體", Font.BOLD, 16));
		lblTitle.setBounds(223, 28, 167, 35);
		getContentPane().add(lblTitle);
		
		JLabel lblStudentIcon = new JLabel("");
		lblStudentIcon.setBounds(74, 94, 74, 74);
		// ImageIcon addIcon=setImage("C:\\Users\\User\\eclipse-workspace\\DBMS_Final\\img\\userIconnnn.png",74,74);
		// lblStudentIcon.setIcon(addIcon);
		getContentPane().add(lblStudentIcon);
		
		JLabel lblSubTitle = new JLabel("請給予接案同學評價吧!");
		lblSubTitle.setFont(new Font("微軟正黑體", Font.PLAIN, 20));
		lblSubTitle.setBounds(183, 114, 325, 35);
		getContentPane().add(lblSubTitle);
		
		JTextArea CommentTextArea = new JTextArea();
		CommentTextArea.setBounds(183, 204, 339, 168);
		getContentPane().add(CommentTextArea);
		
		JLabel lblComment = new JLabel("您的評價:");
		lblComment.setFont(new Font("微軟正黑體", Font.PLAIN, 20));
		lblComment.setBounds(70, 204, 110, 35);
		getContentPane().add(lblComment);
		
		JLabel lblOptional = new JLabel("(選填)");
		lblOptional.setFont(new Font("微軟正黑體", Font.PLAIN, 20));
		lblOptional.setBounds(70, 240, 110, 35);
		getContentPane().add(lblOptional);
		
		JButton btnFiveStar = new JButton("五顆星\n結束案件");
		btnFiveStar.setBounds(74, 440, 128, 40);
		getContentPane().add(btnFiveStar);
		
		btnFiveStar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	// wire $$
            	String I_ID = user.getID();
            	String W_ID = "";
            	String Description = CommentTextArea.getText();
            	int price =0;

            	try (Connection conn = DriverManager.getConnection(url, username, password)) {
            		System.out.println("DB Connected");
        			Statement stat = conn.createStatement();
        			String query;
        			boolean success;
        		//開始查
        			query = String.format("SELECT W_ID,Price FROM CASES WHERE Case_ID = '%s';",CaseID);
        			success = stat.execute(query);
        			
        			if(success) {
        				ResultSet rs = stat.getResultSet();
        		//讀取結果並加入Case列表
        				while(rs.next()) {
        					W_ID = rs.getString("W_ID");
        					price = rs.getInt("Price");
        				}
        			}
        			}
			
					
				 catch (SQLException e1) {
					e1.printStackTrace();
					
				}
			
				
				try (Connection conn = DriverManager.getConnection(url, username, password)) {
					System.out.println("DB Connectd");
					Statement stat = conn.createStatement();

					String rquery = String.format("INSERT INTO RANKS(I_ID, W_ID, Rank_Score,Rank_Description) VALUES ('%s','%s',5,'%s');",I_ID,W_ID,Description);
					
					stat.execute(rquery);
				
					  }
			
					
				 catch (SQLException e1) {
					e1.printStackTrace();
					
				}
				try (Connection conn = DriverManager.getConnection(url, username, password)) {
					System.out.println("DB Connectd");
					Statement stat = conn.createStatement();

					
					String bquery = String.format("UPDATE USER SET balance = balance - %d WHERE User_ID = '%s' ;",price,I_ID);
					
					stat.execute(bquery);
				
					  }
			
					
				 catch (SQLException e1) {
					e1.printStackTrace();
					
				}
				try (Connection conn = DriverManager.getConnection(url, username, password)) {
					System.out.println("DB Connectd");
					Statement stat = conn.createStatement();

				
					String bquery = String.format("UPDATE USER SET balance = balance + %d WHERE User_ID = '%s' ;",price-5,W_ID);
					
					stat.execute(bquery);
				
					  }
			
					
				 catch (SQLException e1) {
					e1.printStackTrace();
					
				}
				try (Connection conn = DriverManager.getConnection(url, username, password)) {
					System.out.println("DB Connectd");
					Statement stat = conn.createStatement();

				
					String bquery = String.format("UPDATE CASES SET Status = 'finished' WHERE Case_ID = '%d' ;", CaseID);
					
					stat.execute(bquery);
				
					  }
			
					
				 catch (SQLException e1) {
					e1.printStackTrace();
					
				}
            	
            	// rank into SQL
            	// status into finished
            	
                HomePage homepage = new HomePage(user);
                JOptionPane.showMessageDialog(null, "此案件已結束，系統將會扣款並轉移至接案者帳戶","Success",JOptionPane.INFORMATION_MESSAGE);
                homepage.show();
                dispose();
            }
		});
		
		JButton btnThreeStar = new JButton("三顆星\n結束案件");
		btnThreeStar.setBounds(219, 440, 128, 40);
		getContentPane().add(btnThreeStar);
		
		btnThreeStar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	// wire $$
            	String I_ID = user.getID();
            	String W_ID = "";
            	String Description = CommentTextArea.getText();
            	int price =0;

            	try (Connection conn = DriverManager.getConnection(url, username, password)) {
            		System.out.println("DB Connected");
        			Statement stat = conn.createStatement();
        			String query;
        			boolean success;
        		//開始查
        			query = String.format("SELECT W_ID,Price FROM CASES WHERE Case_ID = '%s';",CaseID);
        			success = stat.execute(query);
        			
        			if(success) {
        				ResultSet rs = stat.getResultSet();
        		//讀取結果並加入Case列表
        				while(rs.next()) {
        					W_ID = rs.getString("W_ID");
        					price = rs.getInt("Price");
        				}
        			}
        			}
			
					
				 catch (SQLException e1) {
					e1.printStackTrace();
					
				}
			
				
				try (Connection conn = DriverManager.getConnection(url, username, password)) {
					System.out.println("DB Connectd");
					Statement stat = conn.createStatement();

					String rquery = String.format("INSERT INTO RANKS(I_ID, W_ID, Rank_Score,Rank_Description) VALUES ('%s','%s',3,'%s');",I_ID,W_ID,Description);
					
					stat.execute(rquery);
				
					  }
			
					
				 catch (SQLException e1) {
					e1.printStackTrace();
					
				}
				try (Connection conn = DriverManager.getConnection(url, username, password)) {
					System.out.println("DB Connectd");
					Statement stat = conn.createStatement();

					
					String bquery = String.format("UPDATE USER SET balance = balance - %d WHERE User_ID = '%s' ;",price,I_ID);
					
					stat.execute(bquery);
				
					  }
			
					
				 catch (SQLException e1) {
					e1.printStackTrace();
					
				}
				try (Connection conn = DriverManager.getConnection(url, username, password)) {
					System.out.println("DB Connectd");
					Statement stat = conn.createStatement();

				
					String bquery = String.format("UPDATE USER SET balance = balance + %d WHERE User_ID = '%s' ;",price-5,W_ID);
					
					stat.execute(bquery);
				
					  }
			
					
				 catch (SQLException e1) {
					e1.printStackTrace();
					
				}
				try (Connection conn = DriverManager.getConnection(url, username, password)) {
					System.out.println("DB Connectd");
					Statement stat = conn.createStatement();

				
					String bquery = String.format("UPDATE CASES SET Status = 'finished' WHERE Case_ID = '%d' ;", CaseID);
					
					stat.execute(bquery);
				
					  }
			
					
				 catch (SQLException e1) {
					e1.printStackTrace();
					
				}
            	
            	// rank into SQL
            	// status into finished
            	
                HomePage homepage = new HomePage(user);
                JOptionPane.showMessageDialog(null, "此案件已結束，系統將會扣款並轉移至接案者帳戶","Success",JOptionPane.INFORMATION_MESSAGE);
                homepage.show();
                dispose();
            }
		});
		
		JButton btnOneStar = new JButton("一顆星\n結束案件");
		btnOneStar.setBounds(369, 440, 128, 40);
		getContentPane().add(btnOneStar);
		
		btnOneStar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	// wire $$
            	String I_ID = user.getID();
            	String W_ID = "";
            	String Description = CommentTextArea.getText();
            	int price =0;

            	try (Connection conn = DriverManager.getConnection(url, username, password)) {
            		System.out.println("DB Connected");
        			Statement stat = conn.createStatement();
        			String query;
        			boolean success;
        		//開始查
        			query = String.format("SELECT W_ID,Price FROM CASES WHERE Case_ID = '%s';",CaseID);
        			success = stat.execute(query);
        			
        			if(success) {
        				ResultSet rs = stat.getResultSet();
        		//讀取結果並加入Case列表
        				while(rs.next()) {
        					W_ID = rs.getString("W_ID");
        					price = rs.getInt("Price");
        				}
        			}
        			}
			
					
				 catch (SQLException e1) {
					e1.printStackTrace();
					
				}
			
				
				try (Connection conn = DriverManager.getConnection(url, username, password)) {
					System.out.println("DB Connectd");
					Statement stat = conn.createStatement();

					String rquery = String.format("INSERT INTO RANKS(I_ID, W_ID, Rank_Score,Rank_Description) VALUES ('%s','%s',1,'%s');",I_ID,W_ID,Description);
					
					stat.execute(rquery);
				
					  }
			
					
				 catch (SQLException e1) {
					e1.printStackTrace();
					
				}
				try (Connection conn = DriverManager.getConnection(url, username, password)) {
					System.out.println("DB Connectd");
					Statement stat = conn.createStatement();

					
					String bquery = String.format("UPDATE USER SET balance = balance - %d WHERE User_ID = '%s' ;",price,I_ID);
					
					stat.execute(bquery);
				
					  }
			
					
				 catch (SQLException e1) {
					e1.printStackTrace();
					
				}
				try (Connection conn = DriverManager.getConnection(url, username, password)) {
					System.out.println("DB Connectd");
					Statement stat = conn.createStatement();

				
					String bquery = String.format("UPDATE USER SET balance = balance + %d WHERE User_ID = '%s' ;",price-5,W_ID);
					
					stat.execute(bquery);
				
					  }
			
					
				 catch (SQLException e1) {
					e1.printStackTrace();
					
				}
				try (Connection conn = DriverManager.getConnection(url, username, password)) {
					System.out.println("DB Connectd");
					Statement stat = conn.createStatement();

				
					String bquery = String.format("UPDATE CASES SET Status = 'finished' WHERE Case_ID = '%d' ;", CaseID);
					
					stat.execute(bquery);
				
					  }
			
					
				 catch (SQLException e1) {
					e1.printStackTrace();
					
				}
            	
            	// rank into SQL
            	// status into finished
            	
                HomePage homepage = new HomePage(user);
                JOptionPane.showMessageDialog(null, "此案件已結束，系統將會扣款並轉移至接案者帳戶","Success",JOptionPane.INFORMATION_MESSAGE);
                homepage.show();
                dispose();
            }
		});
		
		
		
		setLocationRelativeTo(null); 
	}
}
            
	
            
	
	//import img 用
	/*
	public ImageIcon setImage(String imgPath,int imgw,int imgh) {
		
		BufferedImage bufferedIcon=null;
		Image targetImage;
		ImageIcon targetIcon;
		try {
			bufferedIcon = ImageIO.read(new File(imgPath));		
		}
		catch(IOException e){
			e.printStackTrace();
		}
		targetImage  = bufferedIcon.getScaledInstance(imgw, imgh, Image.SCALE_DEFAULT);
		targetIcon = new ImageIcon(targetImage);
		return targetIcon;
		
	}
	*/

