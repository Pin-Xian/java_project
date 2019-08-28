import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
//import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
//import java.awt.Label;
import java.awt.Toolkit;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
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
	JFrame frame = new JFrame("PoopSweeper 掃便便"); // 視窗標題
	JLabel lb_num = new JLabel("剩餘便便數：" + poopCount);
	JLabel lb_time = new JLabel("已經過時間：0"); // 顯示目前已經過時間(秒)
	JPanel panel = new JPanel();

	JButton button[][] = new JButton[400][400]; 
	int map[][] = new int[9][9]; // 變數宣告：便便位置地圖
	int mapAroundPoop[][] = new int[9][9];
	JPanel centerButtonPanel = new JPanel();

	int direct[][] = { { -1, -1 }, { -1, 0 }, { -1, 1 }, { 0, -1 }, { 0, 0 }, { 0, 1 }, { 1, -1 }, { 1, 0 }, { 1, 1 } }; // 以(0，0)為自己，附近的8個方位

	boolean buttonIsPress[][] = new boolean[9][9];

	int timeCount = 0;
	int timeContinue = 1; // 時間繼續計數or停止。1:繼續、0:停止

	Game() {
		frame.setBounds(350, 50, 600, 650);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);

		// 視窗圖示
		Image icon = Toolkit.getDefaultToolkit().getImage("frametitle.png");
		frame.setIconImage(icon);

		// 第一橫列
		lb_num.setBounds(100, 0, 150, 50);
		lb_num.setForeground(Color.YELLOW);
		lb_num.setFont(new Font("微軟正黑體", Font.BOLD, 15));
		lb_num.setText("剩餘便便數：" + poopCount);
		lb_time.setBounds(350, 0, 150, 50);
		lb_time.setForeground(Color.YELLOW);
		lb_time.setFont(new Font("微軟正黑體", Font.BOLD, 15));

		timeCount = 0;
		TimerTask timertask = new TimerTask() {
			public void run() {
				if (timeContinue == 1)
					lb_time.setText("已經過時間： " + (timeCount++));
			}
		};
		new Timer().scheduleAtFixedRate(timertask, 0, 1000);

		// 群組第一橫列（較好移動）
		panel.setSize(600, 50);
		panel.setBackground(Color.BLACK);
		panel.setLayout(null);
		panel.add(lb_num);
		panel.add(lb_time);

		// 便便區設定按鈕
		centerButtonPanel.setBounds(52, 75, 480, 450);
		centerButtonPanel.setLayout(new GridLayout(9, 9));
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				button[i][j] = new JButton();
				button[i][j].setBackground(Color.LIGHT_GRAY); // 設定按鈕背景顏色
				button[i][j].setActionCommand(i + " " + j); // ***設定按鈕指令
				button[i][j].addMouseListener(this); // 設定按鈕點擊連結
				centerButtonPanel.add(button[i][j]);
			}
		}

		// 新局按鈕
		JButton btn_restart = new JButton("新局");
		btn_restart.setBounds(250, 540, 100, 40);
		btn_restart.setBackground(Color.BLACK);
		btn_restart.setForeground(Color.YELLOW);
		btn_restart.setFont(new Font("微軟正黑體", Font.BOLD, 15));
		btn_restart.setActionCommand("r"); // ***設定按鈕指令
		btn_restart.addMouseListener(this);

		setMap(); // 執行便便位置地圖
		aroundPoop(); // 執行附近便便位置地圖

		// 視窗內容
		frame.add(panel);
		frame.add(centerButtonPanel, BorderLayout.SOUTH);
		frame.add(btn_restart);
		frame.setLayout(null);
		frame.setVisible(true);

		// 重新（開新局）
		poopCount = 15;
		lb_num.setText("剩餘便便數：" + poopCount);
	}

	private void setMap() { // 便便位置地圖設定
		int count = 0;
		while (count != 15) { // 設定便便數量
			int x = (int) (Math.random() * 9);
			int y = (int) (Math.random() * 9); // 隨機選取便便的地理位置
			if (map[x][y] == 0) {
				map[x][y] = 1; // 0:無大便、1：有大便；不重複
				count++;
			}
		}
	}

	private void aroundPoop() { // 附近便便數量的地理位置設定
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (map[i][j] == 1) // 該地理位置為便便
					mapAroundPoop[i][j] = -1; // 便便本身設定為-1
				else {
					for (int k = 0; k < 9; k++) { // 該地理位置非便便，找附近8個方位便便的數量，假設（2，3）不為便便
						int row = i + direct[k][0], col = j + direct[k][1]; //
						if ((row >= 0 && row < 9 && col >= 0 && col < 9) && map[row][col] == 1) // 判斷是否位於邊界（最上、下、左、右邊），若是，減少計算方位
							mapAroundPoop[i][j]++;
					}
				}
			}
		}
	}

	private void restart() { // 開新局
		timeCount = 1;
		timeContinue = 1;
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
		setMap();
		aroundPoop();
		poopCount = 15;
		lb_num.setText("剩餘便便數：" + poopCount);
	}

	@Override
	public void mouseClicked(MouseEvent e) { // 待修改（代入圖片）
		String command[] = ((JButton) e.getSource()).getActionCommand().split(" "); // ***與上面的set command連結
		if (command[0].equals("r")) {
			// 開新局
			restart();
		} else {
			int row = Integer.parseInt(command[0]), col = Integer.parseInt(command[1]); // ***與上面的set command連結
			if (e.getButton() == MouseEvent.BUTTON1) { // ***找JAVA Mouse Click的API
				if (map[row][col] == 1 && !buttonIsPress[row][col]) { // 為便便，且按鈕沒按過
					button[row][col].setBackground(Color.PINK); // 設定便便按鈕背景
					for (int i = 0; i < 9; i++)
						for (int j = 0; j < 9; j++)
							if (map[i][j] == 1) {
								button[i][j].setFont(new Font(" ", Font.BOLD, 18));
								button[i][j].setBackground(new Color(250, 255, 239));
								button[i][j].setForeground(new Color(51, 0, 0));
								button[i][j].setText("\ud83d\udca9"); // 印出所有便便
							}
					timeContinue = 0; // 時間停止計時
					JLabel label1 = new JLabel("你踩到便便了！！");
					label1.setFont(new Font("微軟正黑體", Font.BOLD, 14));
					JOptionPane.showMessageDialog(null, label1);// 顯示破關失敗的訊息
					restart(); // 重新開始
				} else { // =========搞懂這個！！
					if (mapAroundPoop[row][col] == 0 && !buttonIsPress[row][col]) { // 當按到周圍沒便便的按鈕則擴散，且按鈕沒按過
						Vector<postion> vector = new Vector<postion>(); // ***查！！紀錄需要擴散的點
						vector.add(new postion(row, col)); // ****判斷點是否符合擴散的需求，直到vector的資料都處理完
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
						// ***開始擴散
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
				poopCount++; // 便便數+1
				lb_num.setText("剩餘便便數：" + poopCount);
			} else if (e.getButton() == MouseEvent.BUTTON3 && !buttonIsPress[row][col]) { // 標記便便，並判斷是否結束遊戲
				button[row][col].setFont(new Font("微軟正黑體", Font.BOLD, 18));
				button[row][col].setText("禁");
				button[row][col].setForeground(Color.RED);
				button[row][col].setBackground(new Color(255, 239, 250));
				buttonIsPress[row][col] = true; // 設定按鈕為按過
				poopCount--; // 便便數-1
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

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {

	}
}

class postion { // venter用
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

public class PoopGame { // 主程式
	public static void main(String[] args) {
		new Game();
	}
}

