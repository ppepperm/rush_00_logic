
public class Walker {
	int[][] map;
	int[][] calcMap;
	int[][]	enCoords;
	int		enemyNum;
	int		whichTurn = 0;
	int		plX;
	int		plY;

	public Walker (int[][] map, int enemyNum){
		this.map = map;
		this.enemyNum = enemyNum;
		this.calcMap = new int[map.length][map[0].length];
		this.enCoords = new int[enemyNum][2];
		int k = 0;

		for (int i = 0; i < calcMap.length; i++){
			for (int j = 0; j < calcMap[i].length; j++){
				if (map[i][j] == -1){
					calcMap[i][j] = -1;
				} else {
					if (map[i][j] == -4){
						plX = j;
						plY = i;
					}
					if (map[i][j] == -2){
						enCoords[k][0] = j;
						enCoords[k][1] = i;
						k++;
					}
					calcMap[i][j] = 0;
				}
			}
		}
	}

	public void walk(){
		int dir;

		if (whichTurn == enemyNum){
			whichTurn = 0;
		}
		dir = getDir(calcMap, enCoords[whichTurn][0], enCoords[whichTurn][1], plX, plY);
		switch (dir) {
			case (1):
				tryWalk(enCoords[whichTurn][0], enCoords[whichTurn][1], enCoords[whichTurn][0] + 1, enCoords[whichTurn][1]);
				break;
			case (2):
				tryWalk(enCoords[whichTurn][0], enCoords[whichTurn][1], enCoords[whichTurn][0] - 1, enCoords[whichTurn][1]);
				break;
			case (3):
				tryWalk(enCoords[whichTurn][0], enCoords[whichTurn][1], enCoords[whichTurn][0], enCoords[whichTurn][1] + 1);
				break;
			case (4):
				tryWalk(enCoords[whichTurn][0], enCoords[whichTurn][1], enCoords[whichTurn][0], enCoords[whichTurn][1] - 1);
				break;
			case (-1):
				break;
		}
		whichTurn++;
	}

	void doLee(int[][] map, int fromX, int fromY, int toX, int toY, int prev) {
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

	boolean stepBack(int[][] map, int toX, int toY, int prev) {
		if (toX < 0 || toX >= map[0].length || toY >= map.length || toY < 0) {
			return false;
		}
		if (map[toY][toX] == prev - 1 && prev - 1 > 0) {
			return true;
		}
		return false;
	}

	void traceBack(int[][] map, int toX, int toY, int fromX, int fromY) {
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

	void reverse(int[][] map){
		for (int i = 0; i < map.length; i++){
			for (int j = 0; j < map[i].length; j++){
				if(map[i][j] > 0){
					map[i][j] = 0;
				}
			}
		}
	}

	public int getDir(int[][] map, int fromX, int fromY, int toX, int toY) {
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

	private boolean check(int x, int y){
		if (x < 0 || x >= map[0].length || x >= map.length || y < 0) {
			return false;
		}
		if (map[y][x] == 0){
			return true;
		}
		return false;

	}

	private int tryWalk(int fromX, int fromY, int toX, int toY){

		if (map[toY][toX] == -2){
			if (check(fromX + 1, fromY)){
				map[fromY][fromX + 1] = -2;
				map[fromY][fromX] = 0;
				return 1;
			}
			if (check(fromX - 1, fromY)){
				map[fromY][fromX - 1] = -2;
				map[fromY][fromX] = 0;
				return 1;
			}
			if (check(fromX, fromY + 1)){
				map[fromY + 1][fromX] = -2;
				map[fromY][fromX] = 0;
				return 1;
			}
			if (check(fromX, fromY - 1)){
				map[fromY - 1][fromX] = -2;
				map[fromY][fromX] = 0;
				return 1;
			}
			return -1;
		}
		if (map[toY][toX] == -4){
			for (int i = 0; i < map.length; i++) {
				for (int j = 0; j < map[i].length; j++) {
					System.out.print(map[i][j] + "\t");
				}
				System.out.println();
			}
			System.out.println();
			System.out.println("Ti proigral");
			System.exit(1);
		}
		map[toY][toX] = -2;
		map[fromY][fromX] = 0;
		enCoords[whichTurn][0] = toX;
		enCoords[whichTurn][1] = toY;
		return 1;
	}

	public static void main(String[] args) {
		int[][] a = {{-2, -2, 0, 0, -1, -2, 0, 0},
					 {-2, 0, 0, 0, 0, 0, -1, 0},
					 {0, -1, 0, -1, 0, -1, 0, 0},
					 {0, -2, -1, 0, -4, 0, 0, -1},
					 {0, 0, -1, 0, 0, -1, 0, 0}};
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				System.out.print(a[i][j] + "\t");
			}
			System.out.println();
		}
		System.out.println();
		Walker w = new Walker(a, 5);
		while (true){
			w.walk();
		}
	}
}