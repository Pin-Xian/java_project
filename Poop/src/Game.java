import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;

class Game extends JFrame implements MouseListener,frame{
	public static void main(String[] args) {
		
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

		// 時間計時設定
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
				button[i][j].setActionCommand(i + " " + j); // 設定按鈕指令
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
		btn_restart.setActionCommand("r"); // 設定按鈕指令
		btn_restart.addMouseListener(this);

		setMap setMap = new setMap(); // 執行便便位置地圖
		aroundPoop aroundPoop =new aroundPoop(); // 執行附近便便位置地圖

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

}
