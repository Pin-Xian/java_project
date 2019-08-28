import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

interface frame {
	int poopCount = 15;
	JFrame frame = new JFrame("PoopSweeper 掃便便"); // 視窗標題
	JLabel lb_num = new JLabel("剩餘便便數：" + poopCount);
	JLabel lb_time = new JLabel("已經過時間：0"); // 顯示目前已經過時間(秒)
	JPanel panel = new JPanel();

	JButton button[][] = new JButton[9][9];
	int map[][] = new int[9][9]; // 變數宣告：便便位置地圖
	int mapAroundPoop[][] = new int[9][9];
	JPanel centerButtonPanel = new JPanel();

	int direct[][] = { { -1, -1 }, { -1, 0 }, { -1, 1 }, { 0, -1 }, { 0, 0 }, { 0, 1 }, { 1, -1 }, { 1, 0 }, { 1, 1 } }; // 以(0，0)為自己，附近的8個方位

	boolean buttonIsPress[][] = new boolean[9][9];

	int timeCount = 0;
	int timeContinue = 1; // 時間繼續計數or停止。1:繼續、0:停止
	
	
}
