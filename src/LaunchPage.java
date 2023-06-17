import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Date;

public class LaunchPage extends JFrame {
    private JFrame frame;
    private JTextField projectNameTextField;
    private JTextField rewardTextField;
    private JTextArea descriptionTextArea;
    private JButton backButton;

    public LaunchPage(User user) {
        // Layout 設定，一律使用微軟正黑體
    	frame = new JFrame("發案資訊");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(new Color(176, 224, 230));
        frame.getContentPane().setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);
        frame.setSize(600, 600);

        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(new Color(176, 224, 230));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10); // 按鈕之間的間隔

        // 案件名稱
        JLabel projectNameLabel = new JLabel("案件名稱：");
        projectNameLabel.setFont(new Font("微軟正黑體", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(projectNameLabel, gbc);

        projectNameTextField = new JTextField();
        projectNameTextField.setPreferredSize(new Dimension(600, 25)); // 調整大小
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        inputPanel.add(projectNameTextField, gbc);

        // 任務日期
        JLabel taskDateLabel = new JLabel("任務日期：" );
        taskDateLabel.setFont(new Font("微軟正黑體", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        inputPanel.add(taskDateLabel, gbc);

        JSpinner taskDateSpinner = new JSpinner(new SpinnerDateModel());
        taskDateSpinner.setEditor(new JSpinner.DateEditor(taskDateSpinner, "yyyy-MM-dd"));
        gbc.gridx = 1;
        gbc.gridy = 1;
        inputPanel.add(taskDateSpinner, gbc);

        // 酬勞
        JLabel rewardLabel = new JLabel("酬勞：");
        rewardLabel.setFont(new Font("微軟正黑體", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(rewardLabel, gbc);

        rewardTextField = new JTextField();
        rewardTextField.setPreferredSize(new Dimension(600, 25)); // 調整大小
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        inputPanel.add(rewardTextField, gbc);

        // 案件說明
        JLabel descriptionLabel = new JLabel("案件說明：");
        descriptionLabel.setFont(new Font("微軟正黑體", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        inputPanel.add(descriptionLabel, gbc);

        descriptionTextArea = new JTextArea();
        descriptionTextArea.setLineWrap(true);
        JScrollPane descriptionScrollPane = new JScrollPane(descriptionTextArea);
        descriptionScrollPane.setPreferredSize(new Dimension(600, 100)); // 調整大小
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        inputPanel.add(descriptionScrollPane, gbc);

        frame.getContentPane().add(inputPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());

        backButton = new JButton("回上一頁");
        backButton.setFont(new Font("微軟正黑體", Font.PLAIN, 14));
        buttonPanel.add(backButton);

        backButton.setFont(new Font("微軟正黑體", Font.PLAIN, 14));
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showOptionDialog(
                        frame,
                        "確認要返回上一頁嗎？",
                        "Confirm",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        new Object[]{"捨棄內容並回到主頁", "繼續發案"},
                        "繼續發案"
                );

                if (choice == JOptionPane.YES_OPTION) {
                    HomePage homePage = new HomePage(user);
                    homePage.show();
                    frame.dispose();  // 關閉發案資訊視窗
                }
            }

        });

        JButton launchButton = new JButton("匿名發案");
        launchButton.setFont(new Font("微軟正黑體", Font.PLAIN, 14));
        buttonPanel.add(launchButton);

        launchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 先確認輸入是否為空值
                if (projectNameTextField.getText().equals("")) {
                    JOptionPane.showMessageDialog(frame, "請輸入案件名稱", "error", JOptionPane.ERROR_MESSAGE);
                } else if (rewardTextField.getText().equals("")) {
                    JOptionPane.showMessageDialog(frame, "請輸入酬勞", "error", JOptionPane.ERROR_MESSAGE);
                } else if (taskDateSpinner.getValue() == null) {
                    JOptionPane.showMessageDialog(frame, "請輸入日期", "error", JOptionPane.ERROR_MESSAGE);
                } else {

                    // 處理匿名發案按鈕的事件
                    JOptionPane.showMessageDialog(frame, "恭喜您已成功刊登", "Success", JOptionPane.INFORMATION_MESSAGE);

            		String server = "jdbc:mysql://140.119.19.73:3315/";
                    String database = "109208079";
                    String url = server + database + "?useSSL=false";
                    String username = "109208079";
                    String password = "uka1z";
                    String I_ID = user.getID();

                    String caseName = projectNameTextField.getText();
                    int price = Integer.parseInt(rewardTextField.getText());
                    Date taskDate = (Date) taskDateSpinner.getValue();
                    String caseDes = descriptionTextArea.getText();

                    try (Connection conn = DriverManager.getConnection(url, username, password)) {
                        System.out.println("DB Connected");
                        
                        // 將發佈的案件新增至資料庫
                        String insertQuery = "INSERT INTO CASES "
                        		+ "(Case_Name, Case_Date, Price, Case_Description, I_ID, W_ID, status, reply) "
                        		+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                        PreparedStatement statement = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
                        statement.setString(1, caseName);
                        statement.setDate(2, new java.sql.Date(taskDate.getTime()));
                        statement.setInt(3, price);
                        statement.setString(4, caseDes);
                        statement.setString(5, I_ID);
                        statement.setString(6, "0");
                        statement.setString(7, "waiting");
                        statement.setString(8, null);
                        
                        statement.executeUpdate();

                        ResultSet rs = statement.getGeneratedKeys();
                        if (rs.next()) {
                            int caseId = rs.getInt(1);
                            System.out.println("Case ID: " + caseId);
                        }

                        statement.close();
                        conn.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }

                    HomePage homePage = new HomePage(user);
                    homePage.show();
                    frame.dispose(); // 關閉發案資訊視窗
                }
            }
        });

        frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

    }

    public void show() {
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}

