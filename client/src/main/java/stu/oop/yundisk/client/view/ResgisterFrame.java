package stu.oop.yundisk.client.view;


import stu.oop.yundisk.servercommon.entity.User;
import stu.oop.yundisk.servercommon.model.MethodValue;
import stu.oop.yundisk.servercommon.model.Request;
import stu.oop.yundisk.servercommon.model.Response;
import stu.oop.yundisk.servercommon.model.ResponseMessage;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

public class ResgisterFrame extends JFrame {

	private JPanel contentPane;
	private JTextField username_text;
	private JPasswordField password_text;
	private JPasswordField repassword_text;

	private User user=new User();
	private JLabel lblNewLabel;

	private ObjectOutputStream out;
	private ObjectInputStream in;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ObjectOutputStream getOut() {
		return out;
	}

	public void setOut(ObjectOutputStream out) {
		this.out = out;
	}

	public ObjectInputStream getIn() {
		return in;
	}

	public void setIn(ObjectInputStream in) {
		this.in = in;
	}

	/**
	 * Launch the application.
	 */
//	static {
//		try {
//			ResgisterFrame frame = new ResgisterFrame();
//			frame.setVisible(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					ResgisterFrame frame = new ResgisterFrame();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
	/**
	 * Create the frame.
	 */
	public ResgisterFrame(ObjectOutputStream out, ObjectInputStream in) {
		this.out=out;
		this.in=in;

		setIconImage(Toolkit.getDefaultToolkit().getImage(ResgisterFrame.class.getResource("/images/registered.png")));
		setResizable(false);
		setVisible(true);
		setTitle("注册界面");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JLabel label = new JLabel("用户名");
		label.setIcon(new ImageIcon(ResgisterFrame.class.getResource("/images/username.png")));

		username_text = new JTextField("");
		username_text.setColumns(10);

		JLabel label_1 = new JLabel("密码");
		label_1.setIcon(new ImageIcon(ResgisterFrame.class.getResource("/images/password.png")));

		password_text = new JPasswordField("");

		JLabel label_2 = new JLabel("确认密码");

		repassword_text = new JPasswordField("");

		JButton btnNewButton = new JButton("注册");
		btnNewButton.setIcon(new ImageIcon(ResgisterFrame.class.getResource("/images/registered.png")));
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				register();

			}
		});

		lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(ResgisterFrame.class.getResource("/images/office.png")));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(68)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(label_2)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
									.addComponent(label_1)
									.addComponent(label)))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(repassword_text, Alignment.LEADING)
								.addComponent(password_text)
								.addComponent(username_text, GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(180)
							.addComponent(lblNewLabel)))
					.addContainerGap(101, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addContainerGap(204, Short.MAX_VALUE)
					.addComponent(btnNewButton)
					.addGap(173))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(27)
					.addComponent(lblNewLabel)
					.addGap(34)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(label)
						.addComponent(username_text, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(password_text, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_1))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(repassword_text, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(btnNewButton)
					.addGap(22))
		);
		contentPane.setLayout(gl_contentPane);

		try {
			String style = "com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel";
			UIManager.setLookAndFeel(style);
			String lookAndFeel = UIManager.getSystemLookAndFeelClassName();
			UIManager.setLookAndFeel(lookAndFeel);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 更新窗体样式
		SwingUtilities.updateComponentTreeUI(this);
	}

	/**
	 * 将注册信息包装发送到服务器
	 */
	public void register() {
		String username=username_text.getText();
		String password=new String(password_text.getPassword());
		String repassword=new String(repassword_text.getPassword());

		if(username.equals("")||password.equals("")) {
			JOptionPane.showMessageDialog(this,"账号密码不能为空！","温馨提示",JOptionPane.ERROR_MESSAGE);
			return;
		}
		if(!password.equals(repassword)) {
			JOptionPane.showMessageDialog(this,"两次输入密码不相同，请重新输入！","温馨提示",JOptionPane.ERROR_MESSAGE);
			password_text.setText("");
			repassword_text.setText("");
			return;
		}

		user.setUsername(username);
		user.setPassword(password);

		Request request=new Request();
		Map<String,Object> params=new HashMap<>();
		params.put("user",user);
		request.setParams(params);
		request.setMethodValue(MethodValue.REGISTER);
		try {
			out.writeObject(request);
			out.flush();
			out.reset();

			Response response=(Response) in.readObject();

			if(ResponseMessage.REGISTER_SUCCESS.equals(response.getResponseMessage())) {
				JOptionPane.showMessageDialog(this,ResponseMessage.REGISTER_SUCCESS,"温馨提示",JOptionPane.INFORMATION_MESSAGE);
				this.dispose();
				return;
			}
			else if (ResponseMessage.EXIST_USER.equals(response.getResponseMessage())) {
				JOptionPane.showMessageDialog(this,ResponseMessage.EXIST_USER,"温馨提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
