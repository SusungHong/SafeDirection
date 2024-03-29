import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

class LoginUI extends JFrame {
	TextField IDBox = new TextField(30);
	TextField passwordBox = new TextField(30);
	int maxLoginCount = 0;

	public LoginUI() {
		setTitle("안전 길찾기 ver1.0");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocation(550, 200);
		setLayout(null);

		JLabel loginLabel = new JLabel("LOGIN");
		loginLabel.setFont(new Font("돋움", Font.PLAIN, 50));
		loginLabel.setBounds(10, 10, 200, 50);
		add(loginLabel);

		JLabel IDLabel = new JLabel("ID               : ");
		JLabel passwordLabel = new JLabel("Password : ");
		IDLabel.setBounds(40, 100, 70, 20);
		passwordLabel.setBounds(40, 150, 70, 20);
		add(IDLabel);
		add(passwordLabel);

		IDBox.setFont(new Font("돋움", Font.PLAIN, 20));
		passwordBox.setFont(new Font("돋움", Font.PLAIN, 20));
		IDBox.setBounds(110, 97, 300, 30);
		passwordBox.setBounds(110, 147, 300, 30);
		passwordBox.setEchoChar('*');
		passwordBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (maxLoginCount++ == 5) {
					JOptionPane.showMessageDialog(null, "로그인 횟수가 초과되었습니다.");
					System.exit(0);
				} else
					checkIdPassword();
			}
		});
		add(IDBox);
		add(passwordBox);

		JButton registerBtn = new JButton("회원가입");
		JButton signInBtn = new JButton("로그인");
		registerBtn.setBounds(20, 200, 220, 40);
		signInBtn.setBounds(240, 200, 220, 40);
		registerBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new RegisterFrame();
			}
		});
		signInBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (maxLoginCount++ == 5) {
					JOptionPane.showMessageDialog(null, "로그인 횟수가 초과되었습니다.");
					System.exit(0);
				} else
					checkIdPassword();
			}
		});
		add(registerBtn);
		add(signInBtn);

		setSize(500, 300);
		setResizable(false);
		setVisible(true);
	}

	private void checkIdPassword() {
		String ID = IDBox.getText();
		String password = passwordBox.getText();

		if (Utils.isProper(ID, true) && Utils.isProper(password, false)) {
			int userSet[] = Utils.findUser(ID, password);
			if (userSet[0] != -1) {
				Utils.ID = ID;
				dispose();
				MapGraph graph = new MapGraph();
				new SafeDirectionGUI(graph, userSet);
			} else
				JOptionPane.showMessageDialog(null, "아이디와 비밀번호가 다릅니다.");
		} else
			JOptionPane.showMessageDialog(null, "아이디나 비밀번호가 적절하지 않습니다.");
	}
	
}

class RegisterFrame extends JFrame {
	Container container;

	public RegisterFrame() {
		setTitle("안전 길찾기 ver1.0");
		setLocation(530, 180);
		setLayout(null);
		setAlwaysOnTop(true);
		container = getContentPane();

		JLabel registerLabel = new JLabel("회원가입");
		registerLabel.setFont(new Font("돋움", Font.PLAIN, 50));
		registerLabel.setBounds(140, 10, 400, 50);
		add(registerLabel);

		JLabel IDLabel = new JLabel("ID                              : ");
		JLabel passwordLabel = new JLabel("Password                : ");
		JLabel passwordConfirmLabel = new JLabel("Password confirm :");
		IDLabel.setBounds(40, 100, 120, 20);
		passwordLabel.setBounds(40, 150, 120, 20);
		passwordConfirmLabel.setBounds(40, 200, 120, 20);
		add(IDLabel);
		add(passwordLabel);
		add(passwordConfirmLabel);

		TextField IDBox = new TextField(30);
		TextField passwordBox = new TextField(30);
		TextField passwordConfirmBox = new TextField(30);
		IDBox.setFont(new Font("돋움", Font.PLAIN, 20));
		passwordBox.setFont(new Font("돋움", Font.PLAIN, 20));
		passwordConfirmBox.setFont(new Font("돋움", Font.PLAIN, 20));
		IDBox.setBounds(160, 97, 300, 30);
		passwordBox.setBounds(160, 147, 300, 30);
		passwordConfirmBox.setBounds(160, 197, 300, 30);
		passwordBox.setEchoChar('*');
		passwordConfirmBox.setEchoChar('*');
		add(IDBox);
		add(passwordBox);
		add(passwordConfirmBox);

		JLabel IDCondition = new JLabel("아이디는 10자 이상 15자 이내 입니다.");
		JLabel passwordCondition = new JLabel("비밀번호는 10에서 15자 사이이며 특수기호를 포함해야 합니다.");
		IDCondition.setOpaque(true);
		passwordCondition.setOpaque(true);
		IDCondition.setForeground(Color.RED);
		passwordCondition.setForeground(Color.RED);
		IDCondition.setFont(new Font("돋움", Font.PLAIN, 10));
		passwordCondition.setFont(new Font("돋움", Font.PLAIN, 10));
		IDCondition.setBounds(160, 125, 300, 20);
		passwordCondition.setBounds(160, 175, 300, 20);
		add(IDCondition);
		add(passwordCondition);

		JButton registerBtn = new JButton("가입하기");
		JButton cancelBtn = new JButton("취소");
		registerBtn.setBounds(20, 250, 245, 40);
		cancelBtn.setBounds(265, 250, 245, 40);
		registerBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String ID = IDBox.getText();
				String password = passwordBox.getText();

				if (Utils.isProper(ID, true) && Utils.isProper(password, false)) {
					if (ID.length() > 15 || ID.length() < 10)
						JOptionPane.showMessageDialog(container, "ID는 10에서 15자리의 문자열이어야 합니다.");
					else if (password.length() > 15 || password.length() < 10)
						JOptionPane.showMessageDialog(container, "비밀번호는 10에서 15자리의 문자열이어야 합니다.");
					else if (password.equals(passwordConfirmBox.getText())) {
						if (Utils.isComplicate(password)) {
							if (Utils.addUser(container, ID, password)) {
								JOptionPane.showMessageDialog(container, "회원가입 성공!");
								dispose();
							}
						} else
							JOptionPane.showMessageDialog(container, "비밀번호는 적어도 하나의 특수기호를 포함해야 합니다.");
					} else
						JOptionPane.showMessageDialog(container, "똑같은 비밀번호를 입력해주세요.");
				} else if (Utils.isProper(ID, true))
					JOptionPane.showMessageDialog(container, "비밀번호로 사용할 수 없는 문자가 포함되어 있습니다.");
				else
					JOptionPane.showMessageDialog(container, "아이디로 사용할 수 없는 문자가 포함되어 있습니다.");

			}
		});
		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		add(registerBtn);
		add(cancelBtn);

		setSize(550, 350);
		setResizable(false);
		setVisible(true);
	}
}
