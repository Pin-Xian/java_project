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

	public void restart() { // 開新局
		for (int i = 0; i < 9; i++)
			Arrays.fill(map[i], 0); // 全設為非便便位置
		for (int i = 0; i < 9; i++)
			Arrays.fill(buttonIsPress[i], false); // 全設為未點擊
		for (int i = 0; i < 9; i++)
			Arrays.fill(mapAroundPoop[i], 0); // 全部歸零
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				button[i][j].setBackground(Color.LIGHT_GRAY);
				button[i][j].setText(" "); // 不顯示任何東西
			}
		}
		setMap(count);
		aroundPoop();
		lb_num.setText("剩餘便便數：" + poopCount);
	}

	public void setMap(int count) { // 便便位置地圖設定
		while (count != 15) { // 設定便便數量
			int x = (int) (Math.random() * 9);
			int y = (int) (Math.random() * 9); // 隨機選取便便的地理位置
			if (map[x][y] == 0) {
				map[x][y] = 1; // 0:無大便、1：有大便；不重複
				count++;
			}
		}
	}

	public void aroundPoop() { // 附近便便數量的地理位置設定
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (map[i][j] == 1) // 該地理位置為便便
					mapAroundPoop[i][j] = -1; // 便便本身設定為-1
				else {
					for (int k = 0; k < 9; k++) { // 該地理位置非便便，找附近8個方位便便的數量
						int row = i + direct[k][0], col = j + direct[k][1]; //
						if ((row >= 0 && row < 9 && col >= 0 && col < 9) && map[row][col] == 1) // 判斷是否位於邊界（最上、下、左、右邊），若是，減少計算方位
							mapAroundPoop[i][j]++;
					}
				}
			}
		}
	}

	public void mouseClicked(MouseEvent e) {
		String command[] = ((JButton) e.getSource()).getActionCommand().split(" "); // 與上面的set command連結
		if (command[0].equals("r")) {
			// 開新局
			restart();
		} else {
			int row = Integer.parseInt(command[0]), col = Integer.parseInt(command[1]); // 與上面的set command連結
			if (e.getButton() == MouseEvent.BUTTON1) { //
				if (map[row][col] == 1 && !buttonIsPress[row][col]) { // 為便便，且按鈕沒按過
					button[row][col].setFont(new Font(" ", Font.BOLD, 18));
					button[row][col].setBackground(new Color(250, 255, 239));
					button[row][col].setForeground(new Color(51, 0, 0));
					button[row][col].setText("\ud83d\udca9"); // 印出所有便便
					timeContinue = 0; // 時間停止計時
					JLabel label1 = new JLabel("你踩到便便了！！");
					label1.setFont(new Font("微軟正黑體", Font.BOLD, 14));
					JOptionPane.showMessageDialog(null, label1);// 顯示破關失敗的訊息
					restart(); // 重新開始
				} else {
					if (mapAroundPoop[row][col] == 0 && !buttonIsPress[row][col]) { // 當按到周圍沒便便的按鈕則擴散，且按鈕沒按過
						Vector<postion> vector = new Vector<postion>(); // 紀錄需要擴散的點
						vector.add(new postion(row, col)); // 判斷點是否符合擴散的需求，直到vector的資料都處理完
						for (int i = 0; i < vector.size(); i++) {
							for (int j = 0; j < 9; j++) {
								int tempRow = direct[j][0] + vector.get(i).getRow();
								int tempCol = direct[j][1] + vector.get(i).getCol();
								if ((tempRow >= 0 && tempRow < 9) && (tempCol >= 0 && tempCol < 9)
										&& mapAroundPoop[tempRow][tempCol] == 0) { //
									boolean flag = false;
									// 檢查是否已經儲存此筆資料
									for (int k = 0; k < vector.size(); k++) {
										if (tempRow == vector.get(k).getRow() && tempCol == vector.get(k).getCol()) {
											flag = true;
											break;
										}
									}
									if (!flag)
										vector.add(new postion(tempRow, tempCol)); // 此擴散點不存在則儲存起來。
								}
							}
						}
						// 開始擴散
						for (int i = 0; i < vector.size(); i++) {
							for (int j = 0; j < 9; j++) {
								int tempRow = direct[j][0] + vector.get(i).getRow();
								int tempCol = direct[j][1] + vector.get(i).getCol();
								if ((tempRow >= 0 && tempRow < 9) && (tempCol >= 0 && tempCol < 9)) {
									if (mapAroundPoop[tempRow][tempCol] != 0) {// 非0數字的位置才印出來
										button[tempRow][tempCol].setFont(new Font(" ", Font.BOLD, 18));
										button[tempRow][tempCol]
												.setText(Integer.toString(mapAroundPoop[tempRow][tempCol]));
									}
									button[tempRow][tempCol].setForeground(new Color(244, 239, 255));
									button[tempRow][tempCol].setBackground(Color.DARK_GRAY); // 設定按鈕背景顏色
									buttonIsPress[tempRow][tempCol] = true; // 設定按鈕為按過
								}
							}
						}
					} else if (!buttonIsPress[row][col]) { // 附近有便便的地理位置
						button[row][col].setFont(new Font("", Font.BOLD, 18));
						button[row][col].setText(Integer.toString(mapAroundPoop[row][col])); // 印出數字
						button[row][col].setForeground(new Color(244, 239, 255));
						button[row][col].setBackground(Color.DARK_GRAY); // 設定按鈕背景顏色
						buttonIsPress[row][col] = true; // 設定按鈕為按過
					}
				}
			} else if (buttonIsPress[row][col] && e.getButton() == MouseEvent.BUTTON2) { // 取消標記的便便按紐
				buttonIsPress[row][col] = false; // 取消按壓。
				button[row][col].setText("");
				button[row][col].setBackground(Color.LIGHT_GRAY); // 設定按鈕背景顏色
				poopCountAdd(1); // 便便數+1
				lb_num.setText("剩餘便便數：" + poopCount);
			} else if (e.getButton() == MouseEvent.BUTTON3 && !buttonIsPress[row][col]) { // 標記便便，並判斷是否結束遊戲
				button[row][col].setFont(new Font("微軟正黑體", Font.BOLD, 18));
				button[row][col].setText("禁");
				button[row][col].setForeground(Color.RED);
				button[row][col].setBackground(new Color(255, 239, 250));
				buttonIsPress[row][col] = true; // 設定按鈕為按過
				poopCountSubstract(1); // 便便數-1
				lb_num.setText("剩餘便便數：" + poopCount);

				if (poopCount == 0) { // 判斷是否結束遊戲
					boolean endGame = true;
					for (int i = 0; i < 9; i++) { // 判斷有便便的按鈕是否已經標記
						for (int j = 0; j < 9; j++) {
							if (map[i][j] == 1)
								if (buttonIsPress[i][j] != true)
									endGame = false;
						}
					}
					if (endGame) {
						timeContinue = 0; // 時間停止計時
						JLabel label2 = new JLabel("恭喜破關！！");
						label2.setFont(new Font("微軟正黑體", Font.BOLD, 14));
						JOptionPane.showMessageDialog(null, label2); // 顯示破關訊息
						restart(); // 重新開始遊戲
					}
				}
			}
		}
	}
	
	public void poopCountAdd(int value) {
		// 未完成
	}
	
	public void poopCountSubstract(int value) {
		// 未完成
	}
}
