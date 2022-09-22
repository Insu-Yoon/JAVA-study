/*todo N x N maze에서, 경로 상 숫자들을 합할 때 최대값을 구해라.
   미로의 좌 상단에서 시작해서 한 칸씩 이동하여 우 하단에 도달한다.
   1. 오른쪽 혹은 아래로만 이동이 가능하다.
   2. 왼쪽, 윗쪽, 대각선 이동은 허용하지 않는다.*/
public class Maze {
    static int[][] maze = {{3, 2, 5, 1, 2, 1}, {8, 2, 7, 5, 4, 1}, {2, 7, 5, 6, 4, 8}, {3, 5, 7, 3, 8, 2},
            {3, 2, 7, 6, 2, 9}, {3, 6, 6, 2, 7, 4}}; //주어진 maze

    public static void main(String[] args) {
        System.out.println(maxSumPath(3, 3));
    }

    static public int maxSumPath(int i, int j) { //(i, j) -> 시작점
        if (i == 0 && j == 0) return maze[i][j];
        if (i == 0) return maze[i][j] + maxSumPath(i, j - 1);
        if (j == 0) return maze[i][j] + maxSumPath(i - 1, j);
        return maze[i][j] + Math.max(maxSumPath(i - 1, j), maxSumPath(i, j - 1));
    }
}