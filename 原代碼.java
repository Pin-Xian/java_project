import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

class Game extends JFrame implements MouseListener {
	int poopCount = 15;
	JFrame frame = new JFrame("PoopSweeper ���K�K"); // �������D
	JLabel lb_num = new JLabel("�Ѿl�K�K�ơG" + poopCount);
	JLabel lb_time = new JLabel("�w�g�L�ɶ��G0"); // ��ܥثe�w�g�L�ɶ�(��)
	JPanel panel = new JPanel();

	JButton button[][] = new JButton[9][9];
	int map[][] = new int[9][9]; // �ܼƫŧi�G�K�K��m�a��
	int mapAroundPoop[][] = new int[9][9];
	JPanel centerButtonPanel = new JPanel();

	int direct[][] = { { -1, -1 }, { -1, 0 }, { -1, 1 }, 
			{ 0, -1 }, { 0, 0 }, { 0, 1 }, 
			{ 1, -1 }, { 1, 0 }, { 1, 1 } }; // �H(0�A0)���ۤv�A����8�Ӥ��

	boolean buttonIsPress[][] = new boolean[9][9];

	int timeCount = 0;
	int timeContinue = 1; // �ɶ��~��p��or����C1:�~��B0:����

	Game() {
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
				button[i][j].setActionCommand(i + " " + j); // ***�]�w���s���O
				button[i][j].addMouseListener(this); // �]�w���s�I���s��
				centerButtonPanel.add(button[i][j]);
			}
		}

		// �s�����s
		JButton btn_restart = new JButton("�s��");
		btn_restart.setBounds(250, 540, 100, 40);
		btn_restart.setBackground(Color.BLACK);
		btn_restart.setForeground(Color.YELLOW);
		btn_restart.setFont(new Font("�L�n������", Font.BOLD, 15));
		btn_restart.setActionCommand("r"); // ***�]�w���s���O
		btn_restart.addMouseListener(this);

		setMap(); // ����K�K��m�a��
		aroundPoop(); // �������K�K��m�a��

		// �������e
		frame.add(panel);
		frame.add(centerButtonPanel, BorderLayout.SOUTH);
		frame.add(btn_restart);
		frame.setLayout(null);
		frame.setVisible(true);

		// ���s�]�}�s���^
		poopCount = 15;
		lb_num.setText("�Ѿl�K�K�ơG" + poopCount);
	}

	private void setMap() { // �K�K��m�a�ϳ]�w
		int count = 0;
		while (count != 15) { // �]�w�K�K�ƶq
			int x = (int) (Math.random() * 9);
			int y = (int) (Math.random() * 9); // �H������K�K���a�z��m
			if (map[x][y] == 0) {
				map[x][y] = 1; // 0:�L�j�K�B1�G���j�K�F������
				count++;
			}
		}
	}

	private void aroundPoop() { // ����K�K�ƶq���a�z��m�]�w
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (map[i][j] == 1) // �Ӧa�z��m���K�K
					mapAroundPoop[i][j] = -1; // �K�K�����]�w��-1(�K�K��1�A�n���^��)
				else {
					for (int k = 0; k < 9; k++) { // �Ӧa�z��m�D�K�K�A�����8�Ӥ��K�K���ƶq�A���]�]2�A3�^�����K�K
						int row = i + direct[k][0], col = j + direct[k][1]; //
						if ((row >= 0 && row < 9 && col >= 0 && col < 9) && map[row][col] == 1) // �P�_�O�_�����ɡ]�̤W�B�U�B���B�k��^�A�Y�O�A��֭p����
							mapAroundPoop[i][j]++;
					}
				}
			}
		}
	}

	private void restart() { // �}�s��
		timeCount = 1;
		timeContinue = 1;
		for (int i = 0; i < 9; i++)
			Arrays.fill(map[i], 0); // ���]���D�K�K��m
		for (int i = 0; i < 9; i++)
			Arrays.fill(buttonIsPress[i], false); // ���]�����I��
		for (int i = 0; i < 9; i++)
			Arrays.fill(mapAroundPoop[i], 0); // �����k�s
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				button[i][j].setBackground(Color.LIGHT_GRAY);
				button[i][j].setText(" "); // ����ܥ���F��
			}
		}
		setMap();
		aroundPoop();
		poopCount = 15;
		lb_num.setText("�Ѿl�K�K�ơG" + poopCount);
	}

	@Override
	public void mouseClicked(MouseEvent e) { // �ݭק�]�N�J�Ϥ��^
		String command[] = ((JButton) e.getSource()).getActionCommand().split(" "); // ***�P�W����set command�s��
		if (command[0].equals("r")) {
			// �}�s��
			restart();
		} else {
			int row = Integer.parseInt(command[0]), col = Integer.parseInt(command[1]); // ***�P�W����set command�s��
			if (e.getButton() == MouseEvent.BUTTON1) { // ***��JAVA Mouse Click��API
				if (map[row][col] == 1 && !buttonIsPress[row][col]) { // ���K�K�A�B���s�S���L
					//button[row][col].setBackground(Color.PINK); // �]�w�K�K���s�I��
					for (int i = 0; i < 9; i++)
						for (int j = 0; j < 9; j++)
							if (map[i][j] == 1) {
								button[i][j].setFont(new Font(" ", Font.BOLD, 18));
								button[i][j].setBackground(new Color(250, 255, 239));
								button[i][j].setForeground(new Color(51, 0, 0));
								button[i][j].setText("\ud83d\udca9"); // �L�X�Ҧ��K�K
							}
					timeContinue = 0; // �ɶ�����p��
					JLabel label1 = new JLabel("�A���K�K�F�I�I");
					label1.setFont(new Font("�L�n������", Font.BOLD, 14));
					JOptionPane.showMessageDialog(null, label1);// ��ܯ}�����Ѫ��T��
					restart(); // ���s�}�l
				} else { // =========�d���o�ӡI�I
					if (mapAroundPoop[row][col] == 0 && !buttonIsPress[row][col]) { // �����P��S�K�K�����s�h�X���A�B���s�S���L
						Vector<postion> vector = new Vector<postion>(); // ***�d�I�I�����ݭn�X�����I
						vector.add(new postion(row, col)); // ****�P�_�I�O�_�ŦX�X�����ݨD�A����vector����Ƴ��B�z��
						for (int i = 0; i < vector.size(); i++) {
							for (int j = 0; j < 9; j++) {
								int tempRow = direct[j][0] + vector.get(i).getRow();
								int tempCol = direct[j][1] + vector.get(i).getCol();
								if ((tempRow >= 0 && tempRow < 9) && (tempCol >= 0 && tempCol < 9)
										&& mapAroundPoop[tempRow][tempCol] == 0) { //
									boolean flag = false;
									// �ˬd�O�_�w�g�x�s�������
									for (int k = 0; k < vector.size(); k++) {
										if (tempRow == vector.get(k).getRow() && tempCol == vector.get(k).getCol()) {
											flag = true;
											break;
										}
									}
									if (!flag)
										vector.add(new postion(tempRow, tempCol)); // ���X���I���s�b�h�x�s�_�ӡC
								}
							}
						}
						// ***�}�l�X��
						for (int i = 0; i < vector.size(); i++) {
							for (int j = 0; j < 9; j++) {
								int tempRow = direct[j][0] + vector.get(i).getRow();
								int tempCol = direct[j][1] + vector.get(i).getCol();
								if ((tempRow >= 0 && tempRow < 9) && (tempCol >= 0 && tempCol < 9)) {
									if (mapAroundPoop[tempRow][tempCol] != 0) {// �D0�Ʀr����m�~�L�X��
										button[tempRow][tempCol].setFont(new Font(" ", Font.BOLD, 18));
										button[tempRow][tempCol]
												.setText(Integer.toString(mapAroundPoop[tempRow][tempCol]));
									}
									button[tempRow][tempCol].setForeground(new Color(244, 239, 255));
									button[tempRow][tempCol].setBackground(Color.DARK_GRAY); // �]�w���s�I���C��
									buttonIsPress[tempRow][tempCol] = true; // �]�w���s�����L
								}
							}
						}
					} else if (!buttonIsPress[row][col]) { // ���񦳫K�K���a�z��m
						button[row][col].setFont(new Font("", Font.BOLD, 18));
						button[row][col].setText(Integer.toString(mapAroundPoop[row][col])); // �L�X�Ʀr
						button[row][col].setForeground(new Color(244, 239, 255));
						button[row][col].setBackground(Color.DARK_GRAY); // �]�w���s�I���C��
						buttonIsPress[row][col] = true; // �]�w���s�����L
					}
				}
			} else if (buttonIsPress[row][col] && e.getButton() == MouseEvent.BUTTON2) { // �����аO���K�K����
				buttonIsPress[row][col] = false; // ���������C
				button[row][col].setText("");
				button[row][col].setBackground(Color.LIGHT_GRAY); // �]�w���s�I���C��
				poopCount++; // �K�K��+1
				lb_num.setText("�Ѿl�K�K�ơG" + poopCount);
			} else if (e.getButton() == MouseEvent.BUTTON3 && !buttonIsPress[row][col]) { // �аO�K�K�A�çP�_�O�_�����C��
				button[row][col].setFont(new Font("�L�n������", Font.BOLD, 18));
				button[row][col].setText("�T");
				button[row][col].setForeground(Color.RED);
				button[row][col].setBackground(new Color(255, 239, 250));
				buttonIsPress[row][col] = true; // �]�w���s�����L
				poopCount--; // �K�K��-1
				lb_num.setText("�Ѿl�K�K�ơG" + poopCount);

				if (poopCount == 0) { // �P�_�O�_�����C��
					boolean endGame = true;
					for (int i = 0; i < 9; i++) { // �P�_���K�K�����s�O�_�w�g�аO
						for (int j = 0; j < 9; j++) {
							if (map[i][j] == 1)
								if (buttonIsPress[i][j] != true)
									endGame = false;
						}
					}
					if (endGame) {
						timeContinue = 0; // �ɶ�����p��
						JLabel label2 = new JLabel("���߯}���I�I");
						label2.setFont(new Font("�L�n������", Font.BOLD, 14));
						JOptionPane.showMessageDialog(null, label2); // ��ܯ}���T��
						restart(); // ���s�}�l�C��
					}
				}
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}

class postion { // venter��
	private int row, col;

	postion(int row, int col) {
		this.row = row;
		this.col = col;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}
}

public class PoopGame { // �D�{��
	public static void main(String[] args) {
		Game game = new Game();
	}
}
