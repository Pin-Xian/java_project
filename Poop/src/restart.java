import java.awt.Color;
import java.util.Arrays;

abstract class restart implements frame{
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
		setMap();
		aroundPoop();
		lb_num.setText("剩餘便便數：" + poopCount);
	}

	protected abstract void aroundPoop();

	protected abstract void setMap();


}
