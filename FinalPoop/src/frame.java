import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

class Frame {
	static int poopCount = 15;
	static JFrame frame = new JFrame("PoopSweeper ���K�K"); // �������D
	static JLabel lb_num = new JLabel("�Ѿl�K�K�ơG" + poopCount);
	static JLabel lb_time = new JLabel("�w�g�L�ɶ��G0"); // ��ܥثe�w�g�L�ɶ�(��)
	static JPanel panel = new JPanel();

	static JButton button[][] = new JButton[9][9];
	int map[][] = new int[9][9]; // �ܼƫŧi�G�K�K��m�a��
	int mapAroundPoop[][] = new int[9][9];
	static JPanel centerButtonPanel = new JPanel();

	int direct[][] = { { -1, -1 }, { -1, 0 }, { -1, 1 }, { 0, -1 }, { 0, 0 }, { 0, 1 }, { 1, -1 }, { 1, 0 }, { 1, 1 } }; // �H(0�A0)���ۤv�A����8�Ӥ��

	boolean buttonIsPress[][] = new boolean[9][9];

	static int timeCount = 0;
	static int timeContinue = 1; // �ɶ��~��p��or����C1:�~��B0:����
	static int count = 0;
}
