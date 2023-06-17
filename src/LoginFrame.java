import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;

import java.awt.Color;
import java.sql.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.SwingConstants;

public class LoginFrame extends JFrame{
	private JFrame frame;
	private JTextField loginID;
	private JPasswordField loginPassword;
	private JTextField enrollID;
	private JPasswordField enrollPassword;
	private JTextField enrollName;
	private User user ;
	private HomePage homePage;
	/**
	 * Launch the application.
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginFrame window = new LoginFrame();
					window.frame.setVisible(true);
			
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	 * Create the application.
	 */
	public LoginFrame() {
		setLocationRelativeTo(null);
		getContentPane().setBackground(new Color(176,224,230));
	   
	    setBackground(new Color(176,224,230));
		setBounds(100, 100, 450, 513);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setFont(new Font("微軟正黑體", Font.BOLD, 16));
		getContentPane().setLayout(null);
		this.setSize(600, 600);
	
		
		JLabel lblNewLabel = new JLabel("請先登入以刊登或接案");
		lblNewLabel.setFont(new Font("微軟正黑體", Font.BOLD, 16));
		lblNewLabel.setBounds(215, 131, 160, 16);
		getContentPane().add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBounds(176, 159, 244, 36);
		getContentPane().add(panel);
		
		JLabel lblNewLabel_1 = new JLabel("帳號：");
		panel.add(lblNewLabel_1);
		panel.setBackground(new Color(176,224,230));
		
		loginID = new JTextField();
		panel.add(loginID);
		loginID.setColumns(10);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(176, 194, 244, 36);
		getContentPane().add(panel_2);
		
		JLabel lblNewLabel_1_1 = new JLabel("密碼：");
		panel_2.add(lblNewLabel_1_1);
		panel_2.setBackground(new Color(176,224,230));
		
		loginPassword = new JPasswordField();
		loginPassword.setColumns(10);
		panel_2.add(loginPassword);
		
		JButton LoginBtn = new JButton("登入");
		LoginBtn.setBounds(242, 241, 117, 29);
		getContentPane().add(LoginBtn);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(new Color(209, 251, 253));
		panel_3.setBounds(0, 292, 600, 300);
		getContentPane().add(panel_3);
		panel_3.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("還不是用戶嗎？趕快註冊加入我們吧！");
		lblNewLabel_2.setFont(new Font("微軟正黑體", Font.BOLD, 16));
		lblNewLabel_2.setBounds(160, 31, 272, 16);
		panel_3.add(lblNewLabel_2);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(176, 95, 244, 36);
		panel_3.add(panel_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("設立帳號：");
		panel_1.add(lblNewLabel_1_2);
		panel_1.setBackground(new Color(209, 251, 253));
		
		enrollID = new JTextField();
		enrollID.setColumns(10);
		panel_1.add(enrollID);
		
		JPanel panel_2_1 = new JPanel();
		panel_2_1.setBounds(176, 131, 244, 36);
		panel_3.add(panel_2_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("設立密碼：");
		
		panel_2_1.add(lblNewLabel_1_1_1);
		
		panel_2_1.setBackground(new Color(209, 251, 253));
		
		enrollPassword = new JPasswordField();
		enrollPassword.setColumns(10);
		panel_2_1.add(enrollPassword);
		
		JPanel panel_1_1 = new JPanel();
		panel_1_1.setBounds(176, 59, 244, 36);
		panel_3.add(panel_1_1);
		
		JLabel lblNewLabel_1_2_1 = new JLabel("您的姓名：");
		panel_1_1.add(lblNewLabel_1_2_1);
		panel_1_1.setBackground(new Color(209, 251, 253));
		
		enrollName = new JTextField();
		enrollName.setColumns(10);
		panel_1_1.add(enrollName);
		
		// 新增一行密碼限制的提示
		int x = (600-100) / 2; //絕對位置定位
		JTextArea passwordRequirements = new JTextArea("密碼至少需 8 位數");
		passwordRequirements.setFont(new Font("微軟正黑體", Font.PLAIN, 12));
		passwordRequirements.setForeground(Color.BLACK);
		passwordRequirements.setBackground(new Color(209, 251, 253));
		passwordRequirements.setEditable(false);
		passwordRequirements.setBounds(x, 168, 244, 20);
		panel_3.add(passwordRequirements);
		
		
		JButton EnrollBtn = new JButton("註冊");
		EnrollBtn.setBounds(247, 193, 117, 29);
		panel_3.add(EnrollBtn);
		
		JLabel lblNewLabel_3 = new JLabel("歡迎使用政大幫幫忙");
		lblNewLabel_3.setFont(new Font("Dialog", Font.BOLD, 30));
		lblNewLabel_3.setBounds(169, 48, 270, 53);
		getContentPane().add(lblNewLabel_3);
		getContentPane().setBackground(new Color(176,224,230));
		
		EnrollBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String server = "jdbc:mysql://140.119.19.73:3315/";
		        String database = "109208079";
		        String url = server + database + "?useSSL=false";
		        String username = "109208079";
		        String password = "uka1z";
				
				String eID = enrollID.getText();
				String eName = enrollName.getText();
				char[] ePW = enrollPassword.getPassword();
				String ePassword = new String(ePW);
			
				
				try (Connection conn = DriverManager.getConnection(url, username, password)) {
					System.out.println("DB Connectd");
					 
					// use Connection object conn to create statement in try block
					String query = "SELECT COUNT(*) FROM USER WHERE User_ID = ? ";
					PreparedStatement stat = conn.prepareStatement(query);
					stat.setString(1,eID);
			
				    ResultSet resultSet = stat.executeQuery();
			        resultSet.next();
			        int count = resultSet.getInt(1);
			        resultSet.close();
			         
			        stat.close();
			        if (count>0) {
			        	 JOptionPane.showMessageDialog(null, "已存在相同的帳號", "Error",JOptionPane.ERROR_MESSAGE);
			        	 
			        } else if(ePassword.length()<8) {
			        	 JOptionPane.showMessageDialog(null, "密碼必須多於8碼", "Error",JOptionPane.ERROR_MESSAGE);
			        	 
			        } else if(eID.length()==0) {
			        	JOptionPane.showMessageDialog(null, "帳號不得空白", "Error",JOptionPane.ERROR_MESSAGE);
			        } else {
						try (Connection connn = DriverManager.getConnection(url, username, password)) {
						System.out.println("DB Connectd");
						 
						// 存入 USER
						Statement stat_u = connn.createStatement();
						String query_u;
						query_u = String.format("INSERT INTO USER (User_ID, User_Name, Password,Balance) VALUES('%s','%s','%s',%d)",eID,eName,ePassword,5000);
						stat_u.executeUpdate(query_u);
						stat_u.close();
						
						 //存入 ISSUER
						Statement stat_I = connn.createStatement();
						String query_I;
						query_I = String.format("INSERT INTO ISSUER (I_ID) VALUES('%s')", eID);
						stat_I.executeUpdate(query_I);
						stat_I.close();
						
						 //存入 WORKER
						Statement stat_w = connn.createStatement();
						String query_w;
						query_w = String.format("INSERT INTO WORKER (W_ID) VALUES('%s')", eID);
						stat_w.executeUpdate(query_w);
						stat_w.close();
						
						// 給初始5分評價
						Statement stat_r = connn.createStatement();
						String query_r;
						query_r = String.format("INSERT INTO RANKS (I_ID,W_ID,Rank_Score,Rank_Description) VALUES('','%s',5, '初始給予5星評價' )", eID);
						stat_r.executeUpdate(query_r);
						stat_r.close();
						
						JOptionPane.showMessageDialog(null, "註冊成功");  
					
					
				
					
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
			
			        }
			        
			     }catch (SQLException e1) {
						e1.printStackTrace();
				}
			}
			});
		
		
		LoginBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String server = "jdbc:mysql://140.119.19.73:3315/";
		        String database = "109208079";
		        String url = server + database + "?useSSL=false";
		        String username = "109208079";
		        String password = "uka1z";
				
				String LID = loginID.getText();
				char[] LPW = loginPassword.getPassword();
				String LPassword = new String(LPW);


		
				
				try (Connection conn = DriverManager.getConnection(url, username, password)) {
					System.out.println("DB Connectd");
					 
					// use Connection object conn to create statement in try block
					String query = "SELECT COUNT(*) FROM USER WHERE User_ID = ? AND Password = ? ";
					PreparedStatement stat = conn.prepareStatement(query);
					stat.setString(1,LID);
					stat.setString(2,LPassword);
				    ResultSet resultSet = stat.executeQuery();
			        resultSet.next();
			        int count = resultSet.getInt(1);
			         resultSet.close();
			         
			         stat.close();
			         if (count>0) {
							JOptionPane.showMessageDialog(null, "登入成功");  
							user = new User(LID);
							homePage = new HomePage(user);
			                homePage.show(); // 顯示HomePage
							dispose();  // 關閉 LoginFrame
			         }else {
			        	 JOptionPane.showMessageDialog(null, "帳號或密碼有錯誤", "Error",JOptionPane.ERROR_MESSAGE);
			         }
					 	
					  }
			
					
				 catch (SQLException e1) {
					e1.printStackTrace();
					
				}
			
			
				}
			});
		
	}
	
}