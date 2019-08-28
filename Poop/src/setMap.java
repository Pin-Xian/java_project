abstract class setMap implements frame{
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

}
