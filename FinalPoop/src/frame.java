import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

interface frame {
	int poopCount = 15;
	JFrame frame = new JFrame("PoopSweeper ���K�K"); // �������D
	JLabel lb_num = new JLabel("�Ѿl�K�K�ơG" + poopCount);
	JLabel lb_time = new JLabel("�w�g�L�ɶ��G0"); // ��ܥثe�w�g�L�ɶ�(��)
	JPanel panel = new JPanel();

	JButton button[][] = new JButton[9][9];
	int map[][] = new int[9][9]; // �ܼƫŧi�G�K�K��m�a��
	int mapAroundPoop[][] = new int[9][9];
	JPanel centerButtonPanel = new JPanel();

	int direct[][] = { { -1, -1 }, { -1, 0 }, { -1, 1 }, { 0, -1 }, { 0, 0 }, { 0, 1 }, { 1, -1 }, { 1, 0 }, { 1, 1 } }; // �H(0�A0)���ۤv�A����8�Ӥ��

	boolean buttonIsPress[][] = new boolean[9][9];

	int timeCount = 0;
	//int timeContinue = 1; // �ɶ��~��p��or����C1:�~��B0:����

}
