import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class CasePage extends JFrame {

	private JFrame frame;
	private JPanel MainPanel = new JPanel();
	private User user;

		
	
	/**
	public void run() {
		try {
			CasePage window = new CasePage();
			window.frame.setVisible(true);       
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	*/

	public CasePage(User user) {
		//連 SQL
		this.user = user;
		String server = "jdbc:mysql://140.119.19.73:3315/";
        String database = "109208079";
        String url = server + database + "?useSSL=false";
        String username = "109208079";
        String password = "uka1z";
		
		/* 刪除所有Case資料的指令（備用）
		try (Connection conn = DriverManager.getConnection(url, username, password)) {
			System.out.println("DB Connected");
			Statement stat = conn.createStatement();
			String que = "DELETE FROM CASE;";
			stat.executeUpdate(que);
		} catch (SQLException a) {
			a.printStackTrace();
		}
		*/
		
		
		
		//設定整體顏色與尺寸
		getContentPane().setBackground(new Color(176,224,230));
		setBounds(100, 100, 450, 300);
		setFont(new Font("微軟正黑體", Font.BOLD, 16));
		this.setSize(600, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);  // 目前暫定置中，顯示視窗
		setBackground(new Color(225, 241, 231)); // 色票#E1F1E7
		getContentPane().setLayout(null);
		
		//設定標題與架構的Panel、ScrollPane
		JLabel lblNewLabel = new JLabel("案件瀏覽頁面");
		lblNewLabel.setFont(new Font("微軟正黑體", Font.BOLD, 16));
		lblNewLabel.setBounds(248, 26, 100, 29);
		getContentPane().add(lblNewLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(84, 94, 434, 224);
		getContentPane().add(scrollPane);
		
		MainPanel.setBackground(new Color(176,224,230));
		scrollPane.setViewportView(MainPanel);
		MainPanel.setLayout(new GridLayout(0, 1, 0, 10));
				
		
	//從 DB 匯入案件資訊
		
		try (Connection conn = DriverManager.getConnection(url, username, password)) {
			System.out.println("DB Connected");
			Statement stat = conn.createStatement();
			String query;
			boolean success;
		//開始查
			query = "SELECT Case_ID, Case_Name, Price, Case_Date FROM CASES WHERE Status = 'Waiting';";
			success = stat.execute(query);
			
			if(success) {
				ResultSet rs = stat.getResultSet();
		//讀取結果並加入Case列表
				while(rs.next()) {
					addCase(rs.getInt("Case_ID"), rs.getString("Case_Name"), rs.getInt("Price"), rs.getString("Case_Date"));
				}
			}
	
		} catch (SQLException a) {
			a.printStackTrace();
		}
	// DB 匯入案件結束
		
		//上一頁按鈕
		JButton btnLastPage = new JButton("上一頁");
		btnLastPage.setFont(new Font("微軟正黑體", Font.PLAIN, 14));
		btnLastPage.setBounds(249, 351, 117, 29);
		btnLastPage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HomePage h = new HomePage(user);
				h.setVisible(true);
				h.show();
				dispose();
			}
		});
		getContentPane().add(btnLastPage);
	}
	
	public void addCase(int CaseID, String CaseName, int price, String date) {
		
		JPanel addedPanel = new JPanel();
		MainPanel.add(addedPanel);
		addedPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel1 = new JPanel();
		addedPanel.add(panel1, BorderLayout.WEST);
		panel1.setLayout(new GridLayout(3, 0, 0, 0));
		
		JLabel lblNewLabel1 = new JLabel(String.format("    案件名稱：%s", CaseName));
		lblNewLabel1.setFont(new Font("微軟正黑體", Font.PLAIN, 14));
		lblNewLabel1.setHorizontalAlignment(SwingConstants.LEFT);
		panel1.add(lblNewLabel1);
		
		JLabel lblNewLabel2 = new JLabel(String.format("    酬勞：%d元",price));
		lblNewLabel2.setFont(new Font("微軟正黑體", Font.PLAIN, 14));
		lblNewLabel2.setHorizontalAlignment(SwingConstants.LEFT);
		panel1.add(lblNewLabel2);
		
		JLabel lblNewLabel3 = new JLabel(String.format("    日期：%s", date));
		lblNewLabel3.setFont(new Font("微軟正黑體", Font.PLAIN, 14));
		lblNewLabel3.setHorizontalAlignment(SwingConstants.LEFT);
		panel1.add(lblNewLabel3);
		
		//helpBetton 製作
		JButton helpButton = new JButton("我來幫忙！");
		helpButton.setFont(new Font("微軟正黑體", Font.PLAIN, 14));
		helpButton.setForeground(new Color(0,71,125));
		helpButton.setHorizontalAlignment(SwingConstants.RIGHT);
		
		//根據 Case＿ID ，按下我要幫忙按鈕後，會跑出對應的 CaseDetailPage
		helpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				CaseDetailPage d = new CaseDetailPage(user, CaseID);
				d.setVisible(true);
				dispose();
				
			}
		});
		//ActionListener 結束
		
		addedPanel.add(helpButton,BorderLayout.EAST);
	}
	
}

