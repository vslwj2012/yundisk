package stu.oop.yundisk.client.view;


import stu.oop.yundisk.servercommon.entity.User;
import stu.oop.yundisk.servercommon.model.*;
import stu.oop.yundisk.servercommon.properties.BaseProperties;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class LoginFrame extends JFrame {

    private JPanel contentPane;
    private JTextField username_text;
    private JPasswordField password_text;

    private User user = new User();

    private Socket client;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    /**
     * Launch the application.
     */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					LoginFrame frame = new LoginFrame();
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

    /**
     * Create the frame.
     */
    public LoginFrame() {
        setResizable(false);
        setTitle("登陆界面");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 350);
        setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setIcon(new ImageIcon(LoginFrame.class.getResource("/images/office.png")));

        JLabel label = new JLabel("用户名");
        label.setIcon(new ImageIcon(LoginFrame.class.getResource("/images/username.png")));

        JLabel label_1 = new JLabel("密码");
        label_1.setIcon(new ImageIcon(LoginFrame.class.getResource("/images/password.png")));

        username_text = new JTextField("");
        username_text.setColumns(10);

        JButton button = new JButton("登陆");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                String username = username_text.getText();
                String password = new java.lang.String(password_text.getPassword());
                if (username.equals("") || password.equals("")) {
                    JOptionPane.showMessageDialog(LoginFrame.this, "用户名密码不可为空！", "温馨提示", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                Map<String, Object> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                Request request = new Request();
                request.setParams(params);
                request.setMethodValue(MethodValue.LOGIN);
                try {
                    out.writeObject(request);
                    out.flush();
                    out.reset();
                    Response response = (Response) in.readObject();
                    user = (User) response.getParams().get("user");
                    if (ResponseMessage.LOGIN_SUCCESS.equals(response.getResponseMessage())) {
                        new IndexFrame(user, out, in).setVisible(true);
                        LoginFrame.this.dispose();
                    } else {
                        JOptionPane.showMessageDialog(LoginFrame.this, response.getResponseMessage(), "温馨提示", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(LoginFrame.this, ErrorCodeConstants.CONNECTION_ERROR, "温馨提示", JOptionPane.ERROR_MESSAGE);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        button.setIcon(new ImageIcon(LoginFrame.class.getResource("/images/login.png")));

        JButton button_1 = new JButton("注册");
        button_1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ResgisterFrame(out, in);
            }
        });
        button_1.setIcon(new ImageIcon(LoginFrame.class.getResource("/images/registered.png")));

        password_text = new JPasswordField("");
        GroupLayout groupLayout = new GroupLayout(getContentPane());
        groupLayout.setHorizontalGroup(
                groupLayout.createParallelGroup(Alignment.TRAILING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addGap(74)
                                .addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
                                        .addGroup(groupLayout.createSequentialGroup()
                                                .addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
                                                        .addComponent(label_1)
                                                        .addComponent(label))
                                                .addGap(18)
                                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                                        .addComponent(password_text, GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
                                                        .addComponent(username_text, GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)))
                                        .addGroup(groupLayout.createSequentialGroup()
                                                .addGap(32)
                                                .addComponent(button)
                                                .addPreferredGap(ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                                                .addComponent(button_1)
                                                .addGap(10)))
                                .addContainerGap(99, GroupLayout.PREFERRED_SIZE))
                        .addGroup(groupLayout.createSequentialGroup()
                                .addContainerGap(182, Short.MAX_VALUE)
                                .addComponent(lblNewLabel)
                                .addGap(180))
        );
        groupLayout.setVerticalGroup(
                groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addContainerGap(41, Short.MAX_VALUE)
                                .addComponent(lblNewLabel)
                                .addGap(33)
                                .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(label)
                                        .addComponent(username_text, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(18)
                                .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(label_1)
                                        .addComponent(password_text, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(41)
                                .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(button_1)
                                        .addComponent(button))
                                .addGap(25))
        );
        getContentPane().setLayout(groupLayout);
//		setVisible(true);

        try {
            java.lang.String style = "com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel";
            UIManager.setLookAndFeel(style);
            java.lang.String lookAndFeel = UIManager.getSystemLookAndFeelClassName();
            UIManager.setLookAndFeel(lookAndFeel);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 更新窗体样式
        SwingUtilities.updateComponentTreeUI(this);

        connect();
    }

    /**
     * 控制客户端连接到服务器，若连接失败则返回错误信息
     */
    private void connect() {
        try {
            client = new Socket(BaseProperties.SERVER_IP, BaseProperties.SERVER_PORT);
            out = new ObjectOutputStream(client.getOutputStream());
            in = new ObjectInputStream(client.getInputStream());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "连接服务器失败，请检查网络连接！", "温馨提示", JOptionPane.ERROR_MESSAGE);
        }
    }
}
