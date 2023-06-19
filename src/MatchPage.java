import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingConstants;

import com.mysql.cj.jdbc.result.ResultSetMetaData;
import com.mysql.cj.xdevapi.Statement;

import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JPanel;

public class MatchPage extends JFrame {

    private JLabel lblTitle, lblSubTitle_2, lblSubTitle_2_1, lblUserIcon, identityLbl, lblComment, lblInfo, lblMessage;
    private JButton btnMatch, btnDecline,btnReturn ;
    private User user;
	private Connection conn;
	private java.sql.Statement stat ;
    private String query;
    private String[] output_text;
    private boolean success;
    private int CaseID;
    private JLabel lblNewLabel;
    private JLabel lblId;

    /*
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MatchPage frame = new MatchPage();
                    frame.setVisible(true);
                    frame.setLocationRelativeTo(null); // 視窗置於中間
                } catch (Exception e) {
                  e.printStackTrace();
                }
            }
        });
    }

	*/
    
    public MatchPage(User user, int CaseID)throws SQLException {
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
    	this.CaseID = CaseID;
    	System.out.println("配對 "+user.getID());
    	getContentPane().setBackground(new Color(176,224,230));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(600, 600);
        getContentPane().setLayout(null);
        createLabel();
        createButton();
        createPanel();
        setLocationRelativeTo(null);

    	

    }

    public void createPanel() {
        JPanel infoPanel = new JPanel();
        infoPanel.setBounds(52, 219, 501, 209);
        infoPanel.add(lblUserIcon);
        infoPanel.add(identityLbl);
        infoPanel.add(lblComment);
        infoPanel.add(lblInfo);
        infoPanel.add(lblMessage);
        getContentPane().add(infoPanel);
        infoPanel.setLayout(null);
        
        lblNewLabel = new JLabel("評價：");
        lblNewLabel.setBounds(32, 61, 46, 16);
        lblNewLabel.setFont(new Font("微軟正黑體", Font.PLAIN, 15));
        infoPanel.add(lblNewLabel);
        
        lblId = new JLabel("ID :");
        lblId.setBounds(32, 33, 37, 16);
        lblId.setFont(new Font("微軟正黑體", Font.PLAIN, 15));
        infoPanel.add(lblId);
    }

    public void createLabel() throws SQLException{
        lblTitle = new JLabel("配對頁面");
        lblTitle.setFont(new Font("微軟正黑體", Font.BOLD, 32));
        lblTitle.setBounds(208, 35, 167, 35);
        getContentPane().add(lblTitle);

        lblSubTitle_2 = new JLabel("恭喜！您的案件：<    >");
        lblSubTitle_2.setFont(new Font("微軟正黑體", Font.BOLD, 15));
        lblSubTitle_2.setBounds(54, 116, 488, 47);
        getContentPane().add(lblSubTitle_2);

        lblSubTitle_2_1 = new JLabel("已有一人接案，請問是否要接受案件配對？");
        lblSubTitle_2_1.setFont(new Font("微軟正黑體", Font.PLAIN, 15));
        lblSubTitle_2_1.setBounds(54, 156, 508, 47);
        getContentPane().add(lblSubTitle_2_1);

        lblUserIcon = new JLabel("");
        lblUserIcon.setBounds(398, 26, 46, 46);
        // ImageIcon addIcon = setImage("C:\\Users\\User\\eclipse-workspace\\DBMS_Final\\img\\userIconnnn.png", 46, 46);
        // lblUserIcon.setIcon(addIcon);

        identityLbl = new JLabel("     ");
        identityLbl.setFont(new Font("微軟正黑體", Font.PLAIN, 15));
        identityLbl.setBounds(78, 31, 218, 21);

        lblComment = new JLabel("評價：    ");
        lblComment.setFont(new Font("微軟正黑體", Font.PLAIN, 15));
        lblComment.setBounds(78, 59, 218, 21);

        lblInfo = new JLabel("留言：    ");
        lblInfo.setFont(new Font("微軟正黑體", Font.PLAIN, 15));
        lblInfo.setBounds(32, 84, 400, 40);

        lblMessage = new JLabel("");
        lblMessage.setVerticalAlignment(SwingConstants.TOP);
        lblMessage.setFont(new Font("微軟正黑體", Font.PLAIN, 15));
        lblMessage.setBounds(142, 92, 317, 99);
        
        updateLabel(); 

    }

    public void createButton() throws SQLException{
        btnMatch = new JButton("配對");
        btnMatch.setBounds(160, 490, 128, 40);
        getContentPane().add(btnMatch);

        btnDecline = new JButton("拒絕");
        btnDecline.setBounds(390, 490, 128, 40);
        getContentPane().add(btnDecline);
        
        btnReturn = new JButton("返回上一頁");
        btnMatch.setBounds(250, 490, 128, 40);
        getContentPane().add(btnMatch);
        
        btnReturn.addActionListener(new ActionListener() {
        	       	
            @Override
            public void actionPerformed(ActionEvent e) {
       		
            	try {
                	AccountPage accountPage = new AccountPage(user);
                	accountPage.setVisible(true);
                    dispose();
            	}
        		catch (Exception a) {
        			a.printStackTrace();
        		}
            }
        });

        btnMatch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
        		String server = "jdbc:mysql://140.119.19.73:3315/";
                String database = "109208079";
                String url = server + database + "?useSSL=false";
                String username = "109208079";
                String password = "uka1z";
            	try {
            		conn = DriverManager.getConnection(url, username, password);
            		System.out.println("DB Connectd @ match page");
            		query="UPDATE CASES SET Status = 'working' WHERE Case_ID ="+CaseID;
        			success = stat.execute(query);
        			if (!success) {
        				System.out.println("update working status");
                    	ChatRoom chatroom = new ChatRoom(user, CaseID,"發案者");
                        chatroom.setVisible(true);
                        dispose();
        			}
            	}
            	catch (SQLException a) {
            		a.printStackTrace();
            	}


            }
        });

        btnDecline.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
        		String server = "jdbc:mysql://140.119.19.73:3315/";
                String database = "109208079";
                String url = server + database + "?useSSL=false";
                String username = "109208079";
                String password = "uka1z";
        		try {
        			//stat = conn.createStatement();
        			conn = DriverManager.getConnection(url, username, password);
                	query="UPDATE CASES SET Status = 'waiting',reply=null,W_ID = '0' WHERE Case_ID ="+CaseID;
        			success = stat.execute(query);
        			if (!success) {
        				System.out.println("update working status");

                        HomePage homepage = new HomePage(user);
                        homepage.setVisible(true);               
                        dispose();
        			}

        		}
        		catch (Exception a) {
        			a.printStackTrace();
        		}

            }
        });
    }
    
    public void updateLabel() throws SQLException{
 	
		try {
			//stat = conn.createStatement();
	    	query="SELECT Case_Name,W_ID,reply FROM CASES WHERE Case_ID = "+CaseID;
			success = stat.execute(query);
			if (success) {
				ResultSet rs = stat.getResultSet();
				while(rs.next()) {
					lblSubTitle_2.setText(String.format("恭喜！您的案件：<%s>",rs.getString("Case_Name")));				
					lblInfo.setText(String.format("留言 : %s",rs.getString("reply")));
					identityLbl.setText(""+rs.getInt("W_ID"));
				}
			}
	    	query="SELECT AVG(Rank_Score) FROM RANKS WHERE W_ID = "+identityLbl.getText(); //透過使用者id 去Rank table裡面抓資料 
			success = stat.execute(query);
			if (success) {
				ResultSet rs = stat.getResultSet();
				while(rs.next()) {
				lblComment.setText(""+ rs.getInt(1));
			
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

    }
    
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
	
	public void show() {
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	} */