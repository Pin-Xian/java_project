abstract class setMap implements frame{
	public void setMap(int count) { // �K�K��m�a�ϳ]�w
		while (count != 15) { // �]�w�K�K�ƶq
			int x = (int) (Math.random() * 9);
			int y = (int) (Math.random() * 9); // �H������K�K���a�z��m
			if (map[x][y] == 0) {
				map[x][y] = 1; // 0:�L�j�K�B1�G���j�K�F������
				count++;
			}
		}
	}

}
