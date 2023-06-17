import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class CaseDetailPage extends JFrame {
	private JTextField textField;
	private User user;

	/**
	 * Launch the application.
	 
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CaseDetailPage window = new CaseDetailPage();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	*/
	
	//陳家祥現在要根據CaseID製作對應的DetailPage
	public CaseDetailPage(User user, int CaseID) {
		getContentPane().setBackground(new Color(176,224,230));
		
		this.user = user;
		
		//連線資料庫
		String server = "jdbc:mysql://140.119.19.73:3315/";
        String database = "109208079";
        String url = server + database + "?useSSL=false";
        String username = "109208079";
        String password = "uka1z";
		
		//設置整體顏色與尺寸
		//getContentPane().setBackground(new Color(176,224,230));
		setBounds(100, 100, 450, 300);
		setFont(new Font("微軟正黑體", Font.BOLD, 16));
		this.setSize(600, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);  // 目前暫定置中，顯示視窗
		setBackground(new Color(176,224,230)); // 淺藍色背景
		getContentPane().setLayout(null);
		
		//開始串資料庫
		try (Connection conn = DriverManager.getConnection(url, username, password)) {
			System.out.println("DB Connected");
			Statement stat = conn.createStatement();
			String query;
			boolean success;
		//開始查
			query = String.format("SELECT Case_Name, Case_Date, Price, Case_Description FROM CASES WHERE Case_ID = %d ;", CaseID);
			success = stat.execute(query);
			
			if(success) {
				ResultSet rs = stat.getResultSet();
				//讀取結果並加入Case列表
				while(rs.next()) {


			//設定標題與架構的Panel、ScrollPane
			JLabel TitleLabel = new JLabel("詳細資訊頁面");
			TitleLabel.setFont(new Font("微軟正黑體", Font.BOLD, 16));
			TitleLabel.setBounds(247, 26, 101, 16);
			getContentPane().add(TitleLabel);
		
		
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(78, 74, 434, 218);
			getContentPane().add(scrollPane);
		
			JPanel MainPanel = new JPanel();
			//MainPanel.setBackground(new Color(176,224,230));
			scrollPane.setViewportView(MainPanel);
			MainPanel.setLayout(null);
		
			//前四行
			JLabel lblNewLabel = new JLabel("有同學匿名發布：");
			lblNewLabel.setFont(new Font("微軟正黑體", Font.PLAIN, 14));
			lblNewLabel.setBounds(0, 0, 430, 33);
			MainPanel.add(lblNewLabel);
		
			JLabel lblNewLabel_2 = new JLabel("案件名稱：" + rs.getString("Case_Name") );
			lblNewLabel_2.setFont(new Font("微軟正黑體", Font.PLAIN, 14));
			lblNewLabel_2.setBounds(0, 53, 430, 16);
			MainPanel.add(lblNewLabel_2);
		
			JLabel lblNewLabel_3 = new JLabel("酬勞：" + rs.getInt("Price") + "元");
			lblNewLabel_3.setFont(new Font("微軟正黑體", Font.PLAIN, 14));
			lblNewLabel_3.setBounds(0, 76, 430, 16);
			MainPanel.add(lblNewLabel_3);
		
			JLabel lblNewLabel_4 = new JLabel("日期：" + rs.getString("Case_Date"));
			lblNewLabel_4.setFont(new Font("微軟正黑體", Font.PLAIN, 14));
			lblNewLabel_4.setBounds(0, 97, 430, 16);
			MainPanel.add(lblNewLabel_4);
		
			//詳細資訊
			JPanel panel = new JPanel();
			panel.setBounds(0, 125, 430, 93);
			MainPanel.add(panel);
			panel.setBackground(new Color(238,238,238));
			panel.setLayout(null);
		
			JLabel lblNewLabel_5 = new JLabel("案件說明：");
			lblNewLabel_5.setFont(new Font("微軟正黑體", Font.PLAIN, 14));
			lblNewLabel_5.setBounds(0, 6, 75, 16);
			lblNewLabel_5.setHorizontalAlignment(SwingConstants.LEFT);
			lblNewLabel_5.setBackground(new Color(238,238,238));
			panel.add(lblNewLabel_5);
		
		
			JTextArea textArea = new JTextArea();
			panel.add(textArea);
			textArea.setBounds(69, 6, 355, 79);
			textArea.setBackground(new Color(238,238,238));
			textArea.setText(rs.getString("Case_Description"));
			textArea.setEditable(false);
			textArea.setFont(new Font("微軟正黑體", Font.PLAIN, 14));
			textArea.setColumns(29);
			textArea.setLineWrap(true);        //自動換行
			textArea.setWrapStyleWord(true);
		
			//裝載「留言、發送配對、上一頁」的Panel
			JPanel panel_1 = new JPanel();
			panel_1.setBounds(44, 332, 510, 62);
			getContentPane().add(panel_1);
			panel_1.setLayout(null);
		
			//上一頁
			JButton btnLastPage = new JButton("上一頁");
			btnLastPage.setFont(new Font("微軟正黑體", Font.PLAIN, 14));
			btnLastPage.setBackground(new Color(176,224,230));
			btnLastPage.setBounds(394, 16, 72, 29);
			btnLastPage.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					CasePage c = new CasePage(user);
					c.setVisible(true);
					c.setVisible(true);
					dispose();
				}
			});
			panel_1.add(btnLastPage);
		
			//配對按鈕
			JButton btnMatchPage = new JButton("進行配對");
			btnMatchPage.setFont(new Font("微軟正黑體", Font.PLAIN, 14));
			btnMatchPage.setBackground(new Color(176,224,230));
			btnMatchPage.setBounds(297, 16, 100, 29);
			btnMatchPage.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//把留的言寫進資料庫的「Case資料表」的「reply」，也把 W_ID 更新成這個接案者的ID，和 Status 更新成「Matching」
					//MatchPage 按「拒絕按鈕」時，幫我把CASES 的「reply」、「W_ID」設為null，和 Status 更新成「Waiting」
					try (Connection conn = DriverManager.getConnection(url, username, password)) {
						System.out.println("DB Connected");
						Statement stat = conn.createStatement();
						String sql;
						int success;
						
						sql = String.format("UPDATE CASES SET reply = '%s', Status = 'matching',W_ID = %s  WHERE Case_ID = %d;", textField.getText(), user.getID(),CaseID);
						success = stat.executeUpdate(sql);
						//executeUpdate()用於執行「增、刪、改」，回傳值是 int ，是「受影響的列的數量」
						if (success == 1) {
						System.out.println("update reply success");
						}
					
						
					} catch (SQLException a) {
						a.printStackTrace();
					}
					
					//跳出「請等待系統配對」視窗，並回到 homePage
					JFrame jFrame = new JFrame();
					JOptionPane.showMessageDialog(jFrame, "請等待系統配對","Issued!",JOptionPane.INFORMATION_MESSAGE);
					dispose();
					HomePage h = new HomePage(user);
					h.show();
				}
			});
			panel_1.add(btnMatchPage);
		
			//留言 Label
			JLabel lblNewLabel_1 = new JLabel("留言：");
			lblNewLabel_1.setFont(new Font("微軟正黑體", Font.PLAIN, 14));
			lblNewLabel_1.setBounds(11, 21, 100, 16);
			panel_1.add(lblNewLabel_1);
		
			//讓他留言的 TextField
			textField = new JTextField();
			textField.setFont(new Font("微軟正黑體", Font.PLAIN, 14));
			textField.setBounds(62, 6, 234, 50);
			panel_1.add(textField);
			textField.setColumns(30);
		
				}
			}
		
		} catch (SQLException a) {
			a.printStackTrace();
		}
	
	}
}