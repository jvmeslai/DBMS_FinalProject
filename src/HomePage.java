import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class HomePage extends JFrame {
    private JFrame frame;
    private User user;
    
    public HomePage(User user) {
	    setLocationRelativeTo(null);
    	this.user = user;
        frame = new JFrame("首頁");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        JPanel contentPanel = new JPanel(new GridLayout(5, 1));
        contentPanel.setBackground(new Color(176,224,230));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(60, 60, 60, 60));
        frame.setBackground(new Color(176,224,230));

        JLabel welcomeLabel = new JLabel("歡迎使用政大幫幫忙，請選擇欲使用的功能");
        welcomeLabel.setFont(new Font("微軟正黑體", Font.BOLD, 16));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPanel.add(welcomeLabel);

        
        // 新增按鈕：我的帳號
        
        JButton accountButton = createButton("我的帳號");
        contentPanel.add(accountButton);

        JButton launchButton = createButton("我要發案");
        contentPanel.add(launchButton);

        JButton caseButton = createButton("我要接案");
        contentPanel.add(caseButton);

        /*
        JButton matchButton = createButton("我的案件進度");
        contentPanel.add(matchButton);
        */
        
        JButton LogoutButton = createButton("登出");
        contentPanel.add(LogoutButton);
        

        frame.getContentPane().add(contentPanel, BorderLayout.CENTER);
        frame.setSize(600, 600);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("微軟正黑體", Font.PLAIN, 16));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) { // 滑鼠效果
                button.setBackground(Color.LIGHT_GRAY);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(null);
            }
        });

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (text.equals("我的帳號")) {
                	AccountPage accountPage;
					try {
					accountPage = new AccountPage(user);
                	accountPage.setVisible(true);
                	accountPage.setLocationRelativeTo(null);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
                } else if (text.equals("我要發案")) {
                    LaunchPage launchPage = new LaunchPage(user);
                    launchPage.setVisible(true);
                    launchPage.setLocationRelativeTo(null);
                } else if (text.equals("我要接案")) {
                	// 串SQL好像該留給CasePage？
                    CasePage casePage = new CasePage(user);
                    casePage.setVisible(true);
                    casePage.setLocationRelativeTo(null);
                 /*else if (text.equals("我的案件進度")) {
                    MatchPage matchPage = new MatchPage(user);
                    matchPage.setVisible(true);
                    matchPage.setLocationRelativeTo(null);*/
                } else if (text.equals("登出")){
                	LoginFrame loginFrame = new LoginFrame();
                	loginFrame.setVisible(true);
                	loginFrame.setLocationRelativeTo(null);

                	
                	
                }
                
                frame.dispose();
            }
        });

        return button;
    }

    public void show() {
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
