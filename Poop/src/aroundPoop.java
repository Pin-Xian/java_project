abstract class aroundPoop implements frame{
	public void aroundPoop() { // ����K�K�ƶq���a�z��m�]�w
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (map[i][j] == 1) // �Ӧa�z��m���K�K
					mapAroundPoop[i][j] = -1; // �K�K�����]�w��-1
				else {
					for (int k = 0; k < 9; k++) { // �Ӧa�z��m�D�K�K�A�����8�Ӥ��K�K���ƶq
						int row = i + direct[k][0], col = j + direct[k][1]; //
						if ((row >= 0 && row < 9 && col >= 0 && col < 9) && map[row][col] == 1) // �P�_�O�_�����ɡ]�̤W�B�U�B���B�k��^�A�Y�O�A��֭p����
							mapAroundPoop[i][j]++;
					}
				}
			}
		}
	}


}
