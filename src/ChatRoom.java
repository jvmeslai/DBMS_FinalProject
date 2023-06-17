import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ChatRoom extends JFrame { // 目前不做聊天室即時聯繫功能

	private static User user;
	private static int CaseID;
	private JTextField textField;
	private static String type;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChatRoom frame = new ChatRoom(user, CaseID, type);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public ChatRoom(User user, int CaseID, String type) {
		this.user = user;
		this.CaseID = CaseID;
		this.type = type;
		
		getContentPane().setBackground(new Color(176,224,230)); 
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(600, 600);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(123, 109, 362, 343);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(34, 297, 216, 40);
		panel.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("送出訊息");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setBounds(262, 304, 94, 29);
		panel.add(btnNewButton);
		
		JLabel lblTitle = new JLabel("聊天室");
		lblTitle.setBounds(280, 37, 112, 44);
		lblTitle.setFont(new Font("微軟正黑體", Font.BOLD, 16));
		getContentPane().add(lblTitle);

		JButton btnComplete = new JButton("案件完成");
		btnComplete.setBounds(325, 490, 128, 40);
		if (type.equals("發案者")) {
			getContentPane().add(btnComplete);
		}
		
		JButton backbtn = new JButton("回到個人頁面");
		backbtn.setBounds(164, 490, 128, 40);
		getContentPane().add(backbtn);
		
		
		
		btnComplete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
     
            	CommentPage commentpage = new CommentPage(user, CaseID);
            	commentpage.setVisible(true);
            	dispose();
            }
		});
		backbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
     
            	AccountPage accountpage;
				try {
					accountpage = new AccountPage(user);
				 	accountpage.setVisible(true);
	            	dispose();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
           
            }
		});
            
	}
}
