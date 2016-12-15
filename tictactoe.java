
import java.io.*;
import java.util.*;

class Point {

    int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "[" + x + ", " + y + "]";
    }
}

class PointsPlayer {

    int player;
    Point point;

    PointsPlayer(Point point, int player) {
        this.player = player;
        this.point = point;
    }
}

class Board {

    int n;
    int myplayer;
    int otherplayer;
    int[][] points;

    Board(int n, int myplayer, int otherplayer, int[][] points) {
        this.points = points;
        this.n = n;
        this.myplayer = myplayer;
        this.otherplayer = otherplayer;
    }
    List<Point> availablePoints;
    List<Point> availableRaidPoints;
    int mysize = 0;

    public List<Point> getAvailableStates(int[][] board, int player, int level) {
        availablePoints = new ArrayList<>();
        availableRaidPoints = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                if (board[i][j] == 0) {
                    availablePoints.add(new Point(i, j));
                }
            }
        }
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                if (board[i][j] == 0) {
                    int raid = place(board, new Point(i, j), player, 1, 1);
                    refreshBoard(board);
                    board[i][j] = 0;
                    if (raid == 1) {
                        availableRaidPoints.add(new Point(i, j));
                    }
                }
            }
        }
        for (int i = 0; i < availableRaidPoints.size(); i++) {
            availablePoints.add(availableRaidPoints.get(i));
        }
        mysize = availableRaidPoints.size();
        return availablePoints;
    }

    public int evaluate(int board[][]) {
        int sumx = 0, sumo = 0;
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                if (board[i][j] == 1) {
                    sumx += points[i][j];
                } else if (board[i][j] == 2) {
                    sumo += points[i][j];
                }
            }
        }
        if (myplayer == 2) {
            return (sumo - sumx);
        } else if (myplayer == 1) {
            return (sumx - sumo);
        } else {
            return 0;
        }
    }
    Stack<PointsPlayer> revert1 = new Stack<PointsPlayer>();

    public int place(int[][] board, Point point, int player, int caller, int decision) {
        int x = point.x;
        int y = point.y;
        board[x][y] = player;
        int raid = 0;
        if (decision == 1) {
            if (player == 1) {
                if ((y + 1 < n) && (board[x][y + 1] == 2)) {
                    int check = 0;
                    if ((y > 0) && (board[x][y - 1] == 1)) {
                        check = 1;
                    }
                    if ((x > 0) && (board[x - 1][y] == 1)) {
                        check = 1;
                    }
                    if ((x + 1 < n) && (board[x + 1][y] == 1)) {
                        check = 1;
                    }
                    if (check == 1) {
                        raid = 1;
                        board[x][y + 1] = 1;
                        if (caller == 1) {
                            revert1.push(new PointsPlayer(new Point(x, y + 1), 2));
                        }
                    }
                }
                if ((y > 0) && (board[x][y - 1] == 2)) {
                    int check = 0;
                    if ((y + 1 < n) && (board[x][y + 1] == 1)) {
                        check = 1;
                    }
                    if ((x > 0) && (board[x - 1][y] == 1)) {
                        check = 1;
                    }
                    if ((x + 1 < n) && (board[x + 1][y] == 1)) {
                        check = 1;
                    }
                    if (check == 1) {
                        raid = 1;
                        board[x][y - 1] = 1;
                        if (caller == 1) {
                            revert1.push(new PointsPlayer(new Point(x, y - 1), 2));
                        }
                    }
                }
                if ((x > 0) && (board[x - 1][y] == 2)) {
                    int check = 0;
                    if ((y + 1 < n) && (board[x][y + 1] == 1)) {
                        check = 1;
                    }
                    if ((y > 0) && (board[x][y - 1] == 1)) {
                        check = 1;
                    }
                    if ((x + 1 < n) && (board[x + 1][y] == 1)) {
                        check = 1;
                    }
                    if (check == 1) {
                        raid = 1;
                        board[x - 1][y] = 1;
                        if (caller == 1) {
                            revert1.push(new PointsPlayer(new Point(x - 1, y), 2));
                        }
                    }
                }
                if ((x + 1 < n) && (board[x + 1][y] == 2)) {
                    int check = 0;
                    if ((y + 1 < n) && (board[x][y + 1] == 1)) {
                        check = 1;
                    }
                    if ((y > 0) && (board[x][y - 1] == 1)) {
                        check = 1;
                    }
                    if ((x > 0) && (board[x - 1][y] == 1)) {
                        check = 1;
                    }
                    if (check == 1) {
                        raid = 1;
                        board[x + 1][y] = 1;
                        if (caller == 1) {
                            revert1.push(new PointsPlayer(new Point(x + 1, y), 2));
                        }
                    }
                }
            } else {
                if ((y + 1 < n) && (board[x][y + 1] == 1)) {
                    int check = 0;
                    if ((y > 0) && (board[x][y - 1] == 2)) {
                        check = 1;
                    }
                    if ((x > 0) && (board[x - 1][y] == 2)) {
                        check = 1;
                    }
                    if ((x + 1 < n) && (board[x + 1][y] == 2)) {
                        check = 1;
                    }
                    if (check == 1) {
                        raid = 1;
                        board[x][y + 1] = 2;
                        if (caller == 1) {
                            revert1.push(new PointsPlayer(new Point(x, y + 1), 1));
                        }
                    }
                }
                if ((y > 0) && (board[x][y - 1] == 1)) {
                    int check = 0;
                    if ((y + 1 < n) && (board[x][y + 1] == 2)) {
                        check = 1;
                    }
                    if ((x > 0) && (board[x - 1][y] == 2)) {
                        check = 1;
                    }
                    if ((x + 1 < n) && (board[x + 1][y] == 2)) {
                        check = 1;
                    }
                    if (check == 1) {
                        raid = 1;
                        board[x][y - 1] = 2;
                        if (caller == 1) {
                            revert1.push(new PointsPlayer(new Point(x, y - 1), 1));
                        }
                    }
                }
                if ((x > 0) && (board[x - 1][y] == 1)) {
                    int check = 0;
                    if ((y + 1 < n) && (board[x][y + 1] == 2)) {
                        check = 1;
                    }
                    if ((y > 0) && (board[x][y - 1] == 2)) {
                        check = 1;
                    }
                    if ((x + 1 < n) && (board[x + 1][y] == 2)) {
                        check = 1;
                    }
                    if (check == 1) {
                        raid = 1;
                        board[x - 1][y] = 2;
                        if (caller == 1) {
                            revert1.push(new PointsPlayer(new Point(x - 1, y), 1));
                        }
                    }
                }
                if ((x + 1 < n) && (board[x + 1][y] == 1)) {
                    int check = 0;
                    if ((y + 1 < n) && (board[x][y + 1] == 2)) {
                        check = 1;
                    }
                    if ((y > 0) && (board[x][y - 1] == 2)) {
                        check = 1;
                    }
                    if ((x > 0) && (board[x - 1][y] == 2)) {
                        check = 1;
                    }
                    if (check == 1) {
                        raid = 1;
                        board[x + 1][y] = 2;
                        if (caller == 1) {
                            revert1.push(new PointsPlayer(new Point(x + 1, y), 1));
                        }
                    }
                }
            }
        }
        return raid;
    }

    public void refreshBoard(int board[][]) {
        Iterator<PointsPlayer> iterator = revert1.iterator();
        while (iterator.hasNext()) {
            PointsPlayer pp = iterator.next();
            Point p = pp.point;
            board[p.x][p.y] = pp.player;
        }
        revert1 = new Stack<PointsPlayer>();
    }

    int[][] copy(int[][] board) {
        int[][] newboard = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                newboard[i][j] = board[i][j];
            }
        }
        return newboard;
    }

    public int[] minimax(int[][] board, int level, int turn, int depth) {
        List<Point> availablePoints = getAvailableStates(board, turn, level);
        int raid_size = mysize;
        int stake_size = availablePoints.size();
        int bestScore = (myplayer == turn) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int currentScore;
        int bestRow = -1;
        int bestCol = -1;
        int bestMove = 0;
        if ((level == depth) || (availablePoints.isEmpty())) {
            bestScore = evaluate(board);
            return new int[]{bestScore, bestRow, bestCol, bestMove};
        } else {
            for (int i = 0; i < availablePoints.size(); ++i) {
                int decision;
                if (i >= (stake_size - raid_size)) {
                    decision = 1;
                } else {
                    decision = 0;
                }
                int[][] newboard = new int[n][n];
                newboard = copy(board);
                Point point = availablePoints.get(i);
                place(newboard, point, turn, 0, decision);
                if (turn == myplayer) {
                    currentScore = minimax(newboard, level + 1, otherplayer, depth)[0];
                    if (currentScore > bestScore) {
                        bestScore = currentScore;
                        bestRow = point.x;
                        bestCol = point.y;
                        bestMove = decision;
                    }
                } else {
                    currentScore = minimax(newboard, level + 1, myplayer, depth)[0];
                    if (currentScore < bestScore) {
                        bestScore = currentScore;
                        bestRow = point.x;
                        bestCol = point.y;
                        bestMove = decision;
                    }
                }
            }
        }
        return new int[]{bestScore, bestRow, bestCol, bestMove};
    }

    public int[] minimax(int[][] board, int level, int turn, int depth, int alpha, int beta) {
        List<Point> availablePoints = getAvailableStates(board, turn, level);
        int bestScore = (myplayer == turn) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int raid_size = mysize;
        int stake_size = availablePoints.size();
        int currentScore;
        int bestRow = -1;
        int bestCol = -1;
        int bestMove = 0;
        if ((level == depth) || (availablePoints.isEmpty())) {
            bestScore = evaluate(board);
            return new int[]{bestScore, bestRow, bestCol, bestMove};
        } else {
            for (int i = 0; i < availablePoints.size(); ++i) {
                int decision;
                if (i >= (stake_size - raid_size)) {
                    decision = 1;
                } else {
                    decision = 0;
                }
                int[][] newboard = new int[n][n];
                newboard = copy(board);
                Point point = availablePoints.get(i);
                place(newboard, point, turn, 0, decision);
                if (turn == myplayer) {

                    currentScore = minimax(newboard, level + 1, otherplayer, depth, alpha, beta)[0];
                    if (((bestRow == -1) && (bestCol == -1)) || (currentScore > bestScore)) {
                        bestScore = currentScore;
                        bestRow = point.x;
                        bestCol = point.y;
                        bestMove = decision;
                    }
                    if (currentScore >= beta) {
                        return new int[]{currentScore, bestRow, bestCol, bestMove};
                    }
                    if (currentScore > alpha) {
                        alpha = currentScore;
                        bestRow = point.x;
                        bestCol = point.y;
                        bestMove = decision;
                    }
                } else {
                    currentScore = minimax(newboard, level + 1, myplayer, depth, alpha, beta)[0];
                    if (((bestRow == -1) && (bestCol == -1)) || (currentScore < bestScore)) {
                        bestScore = currentScore;
                        bestRow = point.x;
                        bestCol = point.y;
                        bestMove = decision;
                    }
                    if (currentScore <= alpha) {
                        return new int[]{currentScore, bestRow, bestCol, bestMove};
                    }
                    if (currentScore < beta) {
                        beta = currentScore;
                        bestRow = point.x;
                        bestCol = point.y;
                        bestMove = decision;
                    }
                }
            }
        }
        return new int[]{bestScore, bestRow, bestCol, bestMove};
    }
}

class homework {

    static String algorithm;
    static String player;
    static int[][] board;
    static int[][] points;
    static int n;

    public static void main(String[] args) throws java.lang.Exception {
        FileReader fileReader = new FileReader("input.txt");
        Scanner sc = new Scanner(fileReader);
        int i = 0, j = 0;
        n = sc.nextInt();
        algorithm = sc.next();
        player = sc.next();
        int depth = sc.nextInt();
        board = new int[n][n];
        points = new int[n][n];
        for (i = 0; i < n; i++) {
            for (j = 0; j < n; j++) {
                points[i][j] = sc.nextInt();
            }
        }
        for (i = 0; i < n; i++) {
            String a = sc.next();
            for (j = 0; j < n; j++) {
                char c = a.charAt(j);
                if (c == 'X') {
                    board[i][j] = 1;
                } else if (c == 'O') {
                    board[i][j] = 2;
                } else {
                    board[i][j] = 0;
                }
            }
        }
        Board b;
        if (player.equals("X")) {
            b = new Board(n, 1, 2, points);
        } else {
            b = new Board(n, 2, 1, points);
        }
        Point p = new Point(0, 0);
        int xx[] = new int[3];
        if (algorithm.equalsIgnoreCase("MINIMAX")) {
            if (player.equals("X")) {
                xx = b.minimax(board, 0, 1, depth);
            } else if (player.equals("O")) {
                xx = b.minimax(board, 0, 2, depth);
            }
        } else if (algorithm.equalsIgnoreCase("ALPHABETA")) {
            if (player.equals("X")) {
                xx = b.minimax(board, 0, 1, depth, -Integer.MAX_VALUE, Integer.MAX_VALUE);
            } else if (player.equals("O")) {
                xx = b.minimax(board, 0, 2, depth, -Integer.MAX_VALUE, Integer.MAX_VALUE);
            }
        }
        int raid = 0;
        int x = xx[1], y = xx[2];
        p = new Point(x, y);
        if (player.equals("X")) {
            raid = b.place(board, p, 1, 0, xx[3]);
        } else {
            raid = b.place(board, p, 2, 0, xx[3]);
        }
        try {
            File f = new File("output.txt");
            f.createNewFile();
            FileWriter fileWriter = new FileWriter(f);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            if (raid == 1) {
                bufferedWriter.write((char) (y + 65) + "" + (x + 1) + " Raid");
            } else {
                bufferedWriter.write((char) (y + 65) + "" + (x + 1) + " Stake");
            }
            bufferedWriter.write("\n");
            for (i = 0; i < n; i++) {
                for (j = 0; j < n; j++) {
                    if (board[i][j] == 0) {
                        bufferedWriter.write(".");
                    } else if (board[i][j] == 1) {
                        bufferedWriter.write("X");
                    } else if (board[i][j] == 2) {
                        bufferedWriter.write("O");
                    }
                }
                if (i != (n - 1)) {
                    bufferedWriter.write("\n");
                }
            }
            bufferedWriter.close();
        } catch (Exception ex) {
        }
    }
}
