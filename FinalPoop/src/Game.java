import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;

class Game extends JFrame implements MouseListener, frame {
	static function function = new function();
	static int timeCount = 0;
	static int timeContinue = 0;
	static int count = 0;

	public static void main(String[] args, MouseListener me) {
		frame.setBounds(350, 50, 600, 650);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);

		// �����ϥ�
		Image icon = Toolkit.getDefaultToolkit().getImage("frametitle.png");
		frame.setIconImage(icon);

		// �Ĥ@��C
		lb_num.setBounds(100, 0, 150, 50);
		lb_num.setForeground(Color.YELLOW);
		lb_num.setFont(new Font("�L�n������", Font.BOLD, 15));
		lb_num.setText("�Ѿl�K�K�ơG" + poopCount);
		lb_time.setBounds(350, 0, 150, 50);
		lb_time.setForeground(Color.YELLOW);
		lb_time.setFont(new Font("�L�n������", Font.BOLD, 15));

		// �ɶ��p�ɳ]�w
		timeCount = 0;
		TimerTask timertask = new TimerTask() {
			public void run() {
				if (timeContinue == 1)
					lb_time.setText("�w�g�L�ɶ��G " + (timeCount++));
			}
		};
		new Timer().scheduleAtFixedRate(timertask, 0, 1000);

		// �s�ղĤ@��C�]���n���ʡ^
		panel.setSize(600, 50);
		panel.setBackground(Color.BLACK);
		panel.setLayout(null);
		panel.add(lb_num);
		panel.add(lb_time);

		// �K�K�ϳ]�w���s
		centerButtonPanel.setBounds(52, 75, 480, 450);
		centerButtonPanel.setLayout(new GridLayout(9, 9));
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				button[i][j] = new JButton();
				button[i][j].setBackground(Color.LIGHT_GRAY); // �]�w���s�I���C��
				button[i][j].setActionCommand(i + " " + j); // �]�w���s���O
				button[i][j].addMouseListener(me); // �]�w���s�I���s��
				centerButtonPanel.add(button[i][j]);
			}
		}

		// �s�����s
		JButton btn_restart = new JButton("�s��");
		btn_restart.setBounds(250, 540, 100, 40);
		btn_restart.setBackground(Color.BLACK);
		btn_restart.setForeground(Color.YELLOW);
		btn_restart.setFont(new Font("�L�n������", Font.BOLD, 15));
		btn_restart.setActionCommand("r"); // �]�w���s���O
		btn_restart.addMouseListener(me);

		function.setMap(count); // ����K�K��m�a��
		function.aroundPoop(); // �������K�K��m�a��

		// �������e
		frame.add(panel);
		frame.add(centerButtonPanel, BorderLayout.SOUTH);
		frame.add(btn_restart);
		frame.setLayout(null);
		frame.setVisible(true);

		// ���s�]�}�s���^
		// poopCount = 15;
		lb_num.setText("�Ѿl�K�K�ơG" + poopCount);
	}

	@Override
	public void mouseClicked(MouseEvent me) {
		function.mouseClicked(me);

	}

	@Override
	public void mousePressed(MouseEvent me) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent me) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent me) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent me) {
		// TODO Auto-generated method stub

	}
}
