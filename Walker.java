
public class Walker {

	static int[][] buildMap(char[][] map, char wall){
		int[][] ret = new int[map.length][map[0].length];

		for (int i = 0; i < map.length; i++){
			for (int j = 0; j < map[i].length); j++){
				if (map[i][j] == wall){
					ret[i][j] = -1;
				}else {
					ret[i][j] = 0;
				}
			}
		}
		return ret;
	}

	static void doLee(int[][] map, int fromX, int fromY, int toX, int toY, int prev) {
		if (fromX < 0 || fromX >= map[0].length || fromY >= map.length || fromY < 0) {
			return;
		}
		if (map[fromY][fromX] == 0 || (prev + 1 < map[fromY][fromX])) {
			map[fromY][fromX] = prev + 1;
			doLee(map, fromX + 1, fromY, toX, toY, map[fromY][fromX]);
			doLee(map, fromX - 1, fromY, toX, toY, map[fromY][fromX]);
			doLee(map, fromX, fromY + 1, toX, toY, map[fromY][fromX]);
			doLee(map, fromX, fromY - 1, toX, toY, map[fromY][fromX]);
		}
		if (fromX == toX && fromY == toY) {
			return;
		}
	}

	static boolean stepBack(int[][] map, int toX, int toY, int prev) {
		if (toX < 0 || toX >= map[0].length || toY >= map.length || toY < 0) {
			return false;
		}
		if (map[toY][toX] == prev - 1) {
			return true;
		}
		return false;
	}

	static void traceBack(int[][] map, int toX, int toY, int fromX, int fromY) {
		int prev = map[toY][toX];
		map[toY][toX] = -2;
		while (toX != fromX || toY != fromY) {
			if (stepBack(map, toX + 1, toY, prev)) {
				toX += 1;
				prev = map[toY][toX];
				map[toY][toX] = -2;
				continue;
			}
			if (stepBack(map, toX - 1, toY, prev)) {
				toX -= 1;
				prev = map[toY][toX];
				map[toY][toX] = -2;
				continue;
			}
			if (stepBack(map, toX, toY + 1, prev)) {
				toY += 1;
				prev = map[toY][toX];
				map[toY][toX] = -2;
				continue;
			}
			if (stepBack(map, toX, toY - 1, prev)) {
				toY -= 1;
				prev = map[toY][toX];
				map[toY][toX] = -2;
				continue;
			}
			break;
		}
		map[toY][toX] = -2;
	}

	static void reverse(int[][] map){
		for (int i = 0; i < map.length; i++){
			for (int j = 0; j < map[i].length; j++){
				if(map[i][j] > 0){
					map[i][j] = 0;
				}
			}
		}
	}

	static public int getDir(int[][] map, int fromX, int fromY, int toX, int toY) {
		doLee(map, toX, toY, fromX, fromY, 0);
		if (stepBack(map, fromX + 1, fromY, map[fromY][fromX])) {
			reverse(map);
			return 1;
		}
		if (stepBack(map, fromX - 1, fromY, map[fromY][fromX])) {
			reverse(map);
			return 2;
		}
		if (stepBack(map, fromX, fromY + 1, map[fromY][fromX])) {
			reverse(map);
			return 3;
		}
		if (stepBack(map, fromX, fromY - 1, map[fromY][fromX])) {
			reverse(map);
			return 4;
		}
		return -1;
	}

	public static void main(String[] args) {
		int[][] a = {{0, -1, 0, 0, -1, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, -1, 0},
				{0, -1, 0, -1, 0, -1, 0, 0},
				{0, 0, -1, 0, 0, 0, 0, -1},
				{0, 0, -1, 0, 0, -1, 0, 0}};
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				System.out.print(a[i][j] + "\t");
			}
			System.out.println();
		}
		doLee(a, 0, 0, 3, 4, 0);
		System.out.println();
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				System.out.print(a[i][j] + "\t");
			}
			System.out.println();
		}
		traceBack(a, 3, 4, 0, 0);
		reverse(a);
		System.out.println();
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				if (a[i][j] == -2) {
					System.out.print("*" + "\t");
				} else {
					System.out.print(a[i][j] + "\t");
				}
			}
			System.out.println();
		}
		int[][] b = {{0, -1, 0, 0, -1, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, -1, 0},
				{0, -1, 0, -1, 0, -1, 0, 0},
				{0, 0, -1, 0, 0, 0, 0, -1},
				{0, 0, -1, 0, 0, -1, 0, 0}};
		int dir = getDir(b, 0, 0, 3, 4);
		switch (dir) {
			case (1):
				System.out.println("Go Right");
				break;
			case (2):
				System.out.println("Go Left");
				break;
			case (3):
				System.out.println("Go Down");
				break;
			case (4):
				System.out.println("Go Up");
				break;
			case (-1):
				System.out.println("No way");
				break;
		}
	}
}