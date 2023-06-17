import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.SwingConstants;
import javax.swing.event.CaretEvent;

import com.mysql.cj.jdbc.result.ResultSetMetaData;
import javax.swing.JScrollPane;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class AccountPage extends JFrame {
	
	private JLabel userNameLabel ,userNameLabel_1 ;
	private JPanel IssuerPanel, WorkerPanel;
	private JButton btnNewButton ;
	private User user; 
	private Connection conn;
	private java.sql.Statement stat ;
    private String query,statusStr;
    //private String[] output_text;
    private boolean success;
    private JLabel userNameLabel_2;
    //private CaretEvent ce;
	/**
	 * Launch the application.

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AccountPage frame = new AccountPage(user);
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
	public AccountPage(User user) throws SQLException{
		getContentPane().setBackground(new Color(176,224,230));
		String server = "jdbc:mysql://140.119.19.73:3315/";
        String database = "109208079";
        String url = server + database + "?useSSL=false";
        String username = "109208079";
        String password = "uka1z";
    	try {
    		conn = DriverManager.getConnection(url, username, password);
    		System.out.println("DB Connectd");
    	}
    	catch (SQLException e) {
    		e.printStackTrace();
    	}
		stat = conn.createStatement();
    	this.user=user;
		this.setSize(600, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		
		userNameLabel = new JLabel("   ");
		setUserName(); //抓取SQL的USER_NAME資料
		userNameLabel.setFont(new Font("微軟正黑體", Font.BOLD, 30));
		userNameLabel.setBounds(84, 64, 155, 49);
		getContentPane().add(userNameLabel);
		
		userNameLabel_1 = new JLabel("評價：");
		userNameLabel_1.setFont(new Font("微軟正黑體", Font.PLAIN, 16));
		userNameLabel_1.setBounds(328,44, 184, 49);
		getContentPane().add(userNameLabel_1);
		
		userNameLabel_2 = new JLabel("餘額： ");
		userNameLabel_2.setFont(new Font("Dialog", Font.PLAIN, 16));
		userNameLabel_2.setBounds(328, 82, 184, 49);
		getContentPane().add(userNameLabel_2);
		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(84, 190, 434, 184);
		getContentPane().add(scrollPane);
		
		IssuerPanel = new JPanel();
		IssuerPanel.setBackground(new Color(176,224,230));
		IssuerPanel.setBounds(63, 158, 467, 332);
		scrollPane.setViewportView(IssuerPanel);
		IssuerPanel.setLayout(new GridLayout(0, 1, 0, 10));
		
		updateLabel(); //把資料庫的資料放入label中
		updateRank(); //把資料庫的資料放入 rank label 中
		updateBalance();//把資料庫的資料放入 balance label 中
		
		btnNewButton = new JButton("返回");
		btnNewButton.setFont(new Font("微軟正黑體", Font.PLAIN, 12));
		btnNewButton.setBounds(433, 448, 85, 23);
		getContentPane().add(btnNewButton);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"已發案件", "已接案件"}));
		comboBox.setBounds(204, 446, 114, 27);
		getContentPane().add(comboBox);
		
		comboBox.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				
				if (comboBox.getSelectedItem().toString().equals("已接案件")) {
		               
		 
					    IssuerPanel.setVisible(false);
		        		WorkerPanel = new JPanel();
		        		WorkerPanel.setBounds(63, 158, 467, 332);
		        		WorkerPanel.setBackground(new Color(176,224,230));
		        		scrollPane.setViewportView(WorkerPanel);
		        		WorkerPanel.setLayout(new GridLayout(0, 1, 0, 10));
		        		try {
		                    WorkerPanel.setVisible(true);
							updateWorkerLabel();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} //把資料庫的資料放入label中
		        		
        		}else if (comboBox.getSelectedItem().toString().equals("已發案件")) {
        			
        			WorkerPanel.setVisible(false);
        			IssuerPanel = new JPanel();
        			IssuerPanel.setBounds(63, 158, 467, 332);
        			IssuerPanel.setBackground(new Color(176,224,230));
        			scrollPane.setViewportView(IssuerPanel);
        			IssuerPanel.setLayout(new GridLayout(0, 1, 0, 10));
        			
        			try {
        				 IssuerPanel.setVisible(true);
						updateLabel();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} //把資料庫的資料放入label中
        			
        		}

			}
		});
		
		JLabel lblNewLabel = new JLabel("顯示案件類型：");
		lblNewLabel.setBounds(109, 450, 99, 16);
		getContentPane().add(lblNewLabel);
		
		btnNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
            	try {
                    HomePage homepage = new HomePage(user);
                    homepage.show();               
                    dispose();
            	}
        		catch (Exception a) {
        			a.printStackTrace();
        		}

           }
        });
    }
	
	public void setUserName() throws SQLException{

		try {
			//stat = conn.createStatement();
	    	query="SELECT User_Name FROM USER WHERE User_ID = "+user.getID();
			success = stat.execute(query);
			if (success) {
				userNameLabel.setText(showResultSet(stat.getResultSet()));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
    public void updateLabel() throws SQLException{ //將USER案件抓下來到String array的方法    	
		try {
			//stat = conn.createStatement();
	    	
	    	query="SELECT Case_Name,Status,Case_ID FROM CASES WHERE I_ID = "+user.getID();
			success = stat.execute(query);
			if (success) {
				ResultSet rs = stat.getResultSet();
				while(rs.next()) {
					addCase(rs.getString("Case_Name"),rs.getString("Status"),rs.getInt("Case_ID"));
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	
    }
    public void updateWorkerLabel() throws SQLException{ //將USER案件抓下來到String array的方法    	
		try {
			//stat = conn.createStatement();
	    	
	    	query="SELECT Case_Name,Status,Case_ID FROM CASES WHERE W_ID = "+user.getID();
			success = stat.execute(query);
			if (success) {
				ResultSet rs = stat.getResultSet();
				while(rs.next()) {
					addWorkerCase(rs.getString("Case_Name"),rs.getString("Status"),rs.getInt("Case_ID"));
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	
    }
    
    public void updateRank() throws SQLException{
		try {
			//stat = conn.createStatement();
	    	
			query="SELECT ROUND(AVG(Rank_Score),2) FROM RANKS WHERE W_ID = "+user.getID();
			success = stat.execute(query);
			
			if (success) {
				ResultSet rs = stat.getResultSet();
				userNameLabel_1.setText("評價："+showResultSet(stat.getResultSet())); //為評價label設定值
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
    }
    public void updateBalance() throws SQLException{
		try {
			//stat = conn.createStatement();
	    	
			query="SELECT Balance FROM USER WHERE User_ID = "+user.getID();
			success = stat.execute(query);
			
			if (success) {
				ResultSet rs = stat.getResultSet();
				userNameLabel_2.setText("餘額："+showResultSet(stat.getResultSet())); //為評價label設定值
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
    }
    /*
    
	public String checkStatus(String caseName) throws SQLException{ //檢查案件的狀態
    	query="SELECT Status FROM CASES WHERE Case_Name = "+caseName;
    	//Boolean checkFinished=false;
		try {			
			success = stat.execute(query);
			if (success) {
				statusStr=showResultSet(stat.getResultSet()); 				
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return statusStr;
	}
	*/
	public static String showResultSet(ResultSet result) throws SQLException {
		ResultSetMetaData metaData =  (ResultSetMetaData)result.getMetaData();
		int columnCount = metaData.getColumnCount();
		String output = "";
		while (result.next()) {
		for (int i = 1; i <= columnCount; i++) {
			output += String.format(result.getString(i));
		}
		}
		return output;
	}
	

	
	public static String showResultSetMuitiple(ResultSet result) throws SQLException {
		ResultSetMetaData metaData = (ResultSetMetaData) result.getMetaData();
		int columnCount = metaData.getColumnCount();
		String output = "";
		while (result.next()) {
		for (int i = 1; i <= columnCount; i++) {
			output += String.format(result.getString(i));
		}
			output+=",";
		}
		return output;
	}
	
	public void addCase(String CaseName , String status, int case_id ) {
		
		JPanel addedPanel = new JPanel(); 
		IssuerPanel.add(addedPanel);
		addedPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel1 = new JPanel();
		addedPanel.add(panel1, BorderLayout.WEST);
		panel1.setLayout(new GridLayout(3, 0, 0, 0));
		
		JLabel lblNewLabel1 = new JLabel(String.format("    案件名稱：%s", CaseName));
		lblNewLabel1.setFont(new Font("微軟正黑體", Font.PLAIN, 14));
		lblNewLabel1.setHorizontalAlignment(SwingConstants.LEFT);
		panel1.add(lblNewLabel1);
	
		
		//helpBetton 製作
		JButton detailButton = new JButton("詳細資訊");
		detailButton.setFont(new Font("微軟正黑體", Font.PLAIN, 14));
		detailButton.setForeground(new Color(0,71,125));
		detailButton.setHorizontalAlignment(SwingConstants.RIGHT);
		
		//根據 Case_ID ，按下細節按鈕後，會跑出對應的 CaseDetailPage
		detailButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		    	if (status.equals("finished")) {
					JFrame jFrame = new JFrame();
					JOptionPane.showMessageDialog(jFrame, "此案件已經完成","案件完成",JOptionPane.INFORMATION_MESSAGE);				    	
		    	}
		    	else if (status.equals("waiting")) {
					JFrame jFrame = new JFrame();
					JOptionPane.showMessageDialog(jFrame, "正在尋找小幫手中","等待中",JOptionPane.INFORMATION_MESSAGE);
		    	//其他狀態的處裡待討論
		    	}
		    	else if (status.equals("matching")){
		    		try {
			    		MatchPage matchPage =new MatchPage(user,case_id);
		                matchPage.setVisible(true);            
		                dispose();			    			
		    		}
	        		catch (Exception a) {
	        			a.printStackTrace();
	        		}

				}
		    	else if (status.equals("working")) {
		    		try {
			    		ChatRoom chat =new ChatRoom(user, case_id, "發案者");
		                chat.setVisible(true);            
		                dispose();			    			
		    		}
	        		catch (Exception a) {
	        			a.printStackTrace();
	        		}
		    	}
				
				
			}
		});
		//ActionListener 結束
		
		addedPanel.add(detailButton,BorderLayout.EAST);
	}
	public void addWorkerCase(String CaseName , String status, int case_id ) {
		
		JPanel addedPanel = new JPanel(); 
		WorkerPanel.add(addedPanel);
		addedPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel1 = new JPanel();
		addedPanel.add(panel1, BorderLayout.WEST);
		panel1.setLayout(new GridLayout(3, 0, 0, 0));
		
		JLabel lblNewLabel1 = new JLabel(String.format("    案件名稱：%s", CaseName));
		lblNewLabel1.setFont(new Font("微軟正黑體", Font.PLAIN, 14));
		lblNewLabel1.setHorizontalAlignment(SwingConstants.LEFT);
		panel1.add(lblNewLabel1);
	
		
		//helpBetton 製作
		JButton detailButton = new JButton("詳細資訊");
		detailButton.setFont(new Font("微軟正黑體", Font.PLAIN, 14));
		detailButton.setForeground(new Color(0,71,125));
		detailButton.setHorizontalAlignment(SwingConstants.RIGHT);
		
		//根據 Case_ID ，按下細節按鈕後，會跑出對應的 CaseDetailPage
		detailButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		    	if (status.equals("finished")) {
					JFrame jFrame = new JFrame();
					JOptionPane.showMessageDialog(jFrame, "This case is finished","Case Finished",JOptionPane.INFORMATION_MESSAGE);				    	
		    	}
		    	else if (status.equals("waiting")) {
					JFrame jFrame = new JFrame();
					JOptionPane.showMessageDialog(jFrame, "Still finding helper for you","Waiting",JOptionPane.INFORMATION_MESSAGE);
		    	//其他狀態的處裡待討論
		    	}
		    	else if (status.equals("matching")){
		    	
		    	JFrame jFrame = new JFrame();
			JOptionPane.showMessageDialog(jFrame, "發案者已收到配對通知，請稍等~","系統通知",JOptionPane.INFORMATION_MESSAGE);

		    	}
		    	else if (status.equals("working")) {
		    		try {
			    		ChatRoom chat =new ChatRoom(user, case_id, "接案者");
		                chat.setVisible(true);            
		                dispose();			    			
		    		}
	        		catch (Exception a) {
	        			a.printStackTrace();
	        		}
		    	}
				
				
			}
		});
		//ActionListener 結束
		
		addedPanel.add(detailButton,BorderLayout.EAST);
	}
}