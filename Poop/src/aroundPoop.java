abstract class aroundPoop implements frame{
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


}
