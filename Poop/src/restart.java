import java.awt.Color;
import java.util.Arrays;

abstract class restart implements frame{
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
		setMap();
		aroundPoop();
		lb_num.setText("�Ѿl�K�K�ơG" + poopCount);
	}

	protected abstract void aroundPoop();

	protected abstract void setMap();


}
