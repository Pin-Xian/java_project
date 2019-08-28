import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

class function implements frame {
	int count;
	int timeContinue;

	public void restart() { // �}�s��
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
		setMap(count);
		aroundPoop();
		lb_num.setText("�Ѿl�K�K�ơG" + poopCount);
	}

	public void setMap(int count) { // �K�K��m�a�ϳ]�w
		while (count != 15) { // �]�w�K�K�ƶq
			int x = (int) (Math.random() * 9);
			int y = (int) (Math.random() * 9); // �H������K�K���a�z��m
			if (map[x][y] == 0) {
				map[x][y] = 1; // 0:�L�j�K�B1�G���j�K�F������
				count++;
			}
		}
	}

	public void aroundPoop() { // ����K�K�ƶq���a�z��m�]�w
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (map[i][j] == 1) // �Ӧa�z��m���K�K
					mapAroundPoop[i][j] = -1; // �K�K�����]�w��-1
				else {
					for (int k = 0; k < 9; k++) { // �Ӧa�z��m�D�K�K�A�����8�Ӥ��K�K���ƶq
						int row = i + direct[k][0], col = j + direct[k][1]; //
						if ((row >= 0 && row < 9 && col >= 0 && col < 9) && map[row][col] == 1) // �P�_�O�_�����ɡ]�̤W�B�U�B���B�k��^�A�Y�O�A��֭p����
							mapAroundPoop[i][j]++;
					}
				}
			}
		}
	}

	public void mouseClicked(MouseEvent e) {
		String command[] = ((JButton) e.getSource()).getActionCommand().split(" "); // �P�W����set command�s��
		if (command[0].equals("r")) {
			// �}�s��
			restart();
		} else {
			int row = Integer.parseInt(command[0]), col = Integer.parseInt(command[1]); // �P�W����set command�s��
			if (e.getButton() == MouseEvent.BUTTON1) { //
				if (map[row][col] == 1 && !buttonIsPress[row][col]) { // ���K�K�A�B���s�S���L
					button[row][col].setFont(new Font(" ", Font.BOLD, 18));
					button[row][col].setBackground(new Color(250, 255, 239));
					button[row][col].setForeground(new Color(51, 0, 0));
					button[row][col].setText("\ud83d\udca9"); // �L�X�Ҧ��K�K
					timeContinue = 0; // �ɶ�����p��
					JLabel label1 = new JLabel("�A���K�K�F�I�I");
					label1.setFont(new Font("�L�n������", Font.BOLD, 14));
					JOptionPane.showMessageDialog(null, label1);// ��ܯ}�����Ѫ��T��
					restart(); // ���s�}�l
				} else {
					if (mapAroundPoop[row][col] == 0 && !buttonIsPress[row][col]) { // �����P��S�K�K�����s�h�X���A�B���s�S���L
						Vector<postion> vector = new Vector<postion>(); // �����ݭn�X�����I
						vector.add(new postion(row, col)); // �P�_�I�O�_�ŦX�X�����ݨD�A����vector����Ƴ��B�z��
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
						// �}�l�X��
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
				poopCountAdd(1); // �K�K��+1
				lb_num.setText("�Ѿl�K�K�ơG" + poopCount);
			} else if (e.getButton() == MouseEvent.BUTTON3 && !buttonIsPress[row][col]) { // �аO�K�K�A�çP�_�O�_�����C��
				button[row][col].setFont(new Font("�L�n������", Font.BOLD, 18));
				button[row][col].setText("�T");
				button[row][col].setForeground(Color.RED);
				button[row][col].setBackground(new Color(255, 239, 250));
				buttonIsPress[row][col] = true; // �]�w���s�����L
				poopCountSubstract(1); // �K�K��-1
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
	
	public void poopCountAdd(int value) {
		// ������
	}
	
	public void poopCountSubstract(int value) {
		// ������
	}
}
