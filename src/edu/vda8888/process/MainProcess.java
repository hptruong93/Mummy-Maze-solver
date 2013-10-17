package process;

import interfc.WelcomeScreen;

import java.awt.EventQueue;
import java.awt.Point;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import vda8888.interfc.MainFrame;

public class MainProcess {

    private static short xExit, yExit;
    // 11 1111 last four digits: up, right, down, left
    // first two digits: key, traps
    private short step;
    private static short stateSide;
    protected static short[] stateSideCount = new short[2];
    private static ArrayList<StaticState> existedState;
    protected static State[][] mang = new State[2][1000]; //State arrays
    private static Human[] trace;

    public MainProcess(State state, short xxExit, short yyExit, Point invokedPosition) {

        File debugFile = new File("Debug Log");
        try {
            //These lines are for creating data to debug
            debugFile.createNewFile();
            FileWriter fw = new FileWriter("Debug Log.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            for (int i = 0; i < state.getMaze().length; i++) {
                for (int j = 0; j < state.getMaze()[0].length; j++) {
                    bw.write("mazee[" + i + "][" + j + "][0]=" + state.getMaze()[i][j][0] + ";");
                    bw.write("\n");
                }
            }

            StaticState tam = new StaticState(state);
            state.setStep((short) 1);
            state.getMaze()[state.getHuman().getX()][state.getHuman().getY()][1] = 1;
            stateSide = 0;
            stateSideCount[stateSide] = 0; //It's count - 1 for the array sake
            stateSideCount[(short) ((stateSide + 1) % 2)] = -1;
            mang[0][0] = state;
            xExit = xxExit;
            yExit = yyExit;

            mang[0][0] = state;
            existedState = new ArrayList<>();
            existedState.add(tam);

            breadth_firstSearch(bw);
            bw.write((existedState.size())); //Indicate how large the search is
            printResult(tam, invokedPosition);

            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(MainProcess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        short[][][] mazee = new short[8][8][10000];

        mazee[0][0][0] = 9;
        mazee[0][1][0] = 8;
        mazee[0][2][0] = 8;
        mazee[0][3][0] = 8;
        mazee[0][4][0] = 8;
        mazee[0][5][0] = 8;
        mazee[0][6][0] = 14;
        mazee[0][7][0] = 13;
        mazee[1][0][0] = 7;
        mazee[1][1][0] = 1;
        mazee[1][2][0] = 4;
        mazee[1][3][0] = 3;
        mazee[1][4][0] = 0;
        mazee[1][5][0] = 0;
        mazee[1][6][0] = 12;
        mazee[1][7][0] = 5;
        mazee[2][0][0] = 9;
        mazee[2][1][0] = 2;
        mazee[2][2][0] = 0;
        mazee[2][3][0] = 8;
        mazee[2][4][0] = 4;
        mazee[2][5][0] = 1;
        mazee[2][6][0] = 0;
        mazee[2][7][0] = 4;
        mazee[3][0][0] = 3;
        mazee[3][1][0] = 8;
        mazee[3][2][0] = 0;
        mazee[3][3][0] = 0;
        mazee[3][4][0] = 0;
        mazee[3][5][0] = 0;
        mazee[3][6][0] = 0;
        mazee[3][7][0] = 4;
        mazee[4][0][0] = 9;
        mazee[4][1][0] = 4;
        mazee[4][2][0] = 3;
        mazee[4][3][0] = 0;
        mazee[4][4][0] = 0;
        mazee[4][5][0] = 2;
        mazee[4][6][0] = 0;
        mazee[4][7][0] = 4;
        mazee[5][0][0] = 1;
        mazee[5][1][0] = 0;
        mazee[5][2][0] = 8;
        mazee[5][3][0] = 0;
        mazee[5][4][0] = 0;
        mazee[5][5][0] = 12;
        mazee[5][6][0] = 1;
        mazee[5][7][0] = 4;
        mazee[6][0][0] = 1;
        mazee[6][1][0] = 2;
        mazee[6][2][0] = 0;
        mazee[6][3][0] = 0;
        mazee[6][4][0] = 0;
        mazee[6][5][0] = 0;
        mazee[6][6][0] = 4;
        mazee[6][7][0] = 5;
        mazee[7][0][0] = 7;
        mazee[7][1][0] = 11;
        mazee[7][2][0] = 2;
        mazee[7][3][0] = 2;
        mazee[7][4][0] = 2;
        mazee[7][5][0] = 2;
        mazee[7][6][0] = 2;
        mazee[7][7][0] = 6;

        Keyd[] keye = new Keyd[0];
//        keye[0] = new Keyd(1,1,1,1,3);
        MummyWhite[] mummyWhitee = new MummyWhite[0];
//        mummyWhitee[0] = new MummyWhite(5, 4);
        MummyRed[] mummyRede = new MummyRed[1];
        mummyRede[0] = new MummyRed(7, 3);
        Scorpion[] ske = new Scorpion[0];
        ScorpionRed[] skRede = new ScorpionRed[1];
        skRede[0] = new ScorpionRed(3, 4);
        Human human = new Human(4, 5);
        mazee[human.getX()][human.getY()][1] = 1; //Mark the first step
        State goo = new State(mazee, keye, mummyWhitee, mummyRede, ske, skRede, human, (short) 1);
        Point point = new Point(30, 20);
        MainProcess thunghiem = new MainProcess(goo, (short) 0, (short) 0, point);
    }

    private static void breadth_firstSearch(BufferedWriter bw) {
        while (true) {
            if (stateSideCount[stateSide] == -1) {
                JOptionPane.showMessageDialog(null, "Cannot find a solution!");
                WelcomeScreen.welcome.setVisible(true);
                System.exit(0);
            }
            for (int i = 0; i <= stateSideCount[stateSide]; i++) {
                try {
                    bw.write(mang[stateSide][i].getHuman().getX() + "," + mang[stateSide][i].getHuman().getY() + "\n");
                } catch (IOException ex) {
                    Logger.getLogger(MainProcess.class.getName()).log(Level.SEVERE, null, ex);
                }
                State tam = mang[stateSide][i].getHuman().attemptMove(mang[stateSide][i], stateSide, i);
                if (tam != null) { //Exit condition
                    //Trace back
                    int xx = tam.getHuman().getX();
                    int yy = tam.getHuman().getY();
                    int j = tam.getStep();
                    trace = new Human[j];
                    trace[j - 1] = new Human(xx, yy);
//                  The following lines are so useful for debugging xD
//                  They print out the final non-static state
                    try {
                        bw.write("\n");
                        for (int a = 0; a < tam.getMaze().length; a++) {
                            for (int b = 0; b < tam.getMaze()[0].length; b++) {
                                bw.write("[" + a + "]" + "[" + b + "]= ");
                                for (int c = 1; c < 6; c++) {
                                    bw.write(tam.getMaze()[a][b][c] + ",");
                                }
                                bw.write("\n");
                            }
                            bw.write("\n");
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(MainProcess.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    for (j -= 1; j > 0; j--) { //k here is the currentStep being traced
                        if (traceStep(tam, j, xx - 1, yy)) {
                            xx--;
                            trace[j - 1] = new Human(xx, yy);
                        } else if (traceStep(tam, j, xx + 1, yy)) {
                            xx++;
                            trace[j - 1] = new Human(xx, yy);
                        } else if (traceStep(tam, j, xx, yy - 1)) {
                            yy--;
                            trace[j - 1] = new Human(xx, yy);
                        } else if (traceStep(tam, j, xx, yy + 1)) {
                            yy++;
                            trace[j - 1] = new Human(xx, yy);
                        } else if (traceStep(tam, j, xx, yy)) {
                            trace[j - 1] = new Human(xx, yy);
                        }
                    }
                    return;
                }
            }
            stateSideCount[stateSide] = -1; //Reset count for the used one
            stateSide = (short) ((stateSide + 1) % 2); //Change side
        }
    }

    private static void printResult(StaticState state, final Point position) {

        final StaticState[] resultState = new StaticState[trace.length];
        resultState[0] = state;

        short[][][] tam;
        for (int i = 0; i < trace.length - 1; i++) {
            trace[i].move(trace[i + 1].getX(), trace[i + 1].getY());
            tam = new short[resultState[i].getStaticMaze().length][resultState[i].getStaticMaze()[0].length][1];
            for (int j = 0; j < tam.length; j++) {
                for (int k = 0; k < tam[0].length; k++) {
                    tam[j][k][0] = resultState[i].getStaticMaze()[j][k];
                }
            }
            State stateTam = new State(tam, resultState[i].getKey(), resultState[i].getMummyWhite(), resultState[i].getMummyRed(),
                    resultState[i].getSk(), resultState[i].getSkRed(), trace[i], (short) 0);
            stateTam = Human.keyCheck(stateTam, stateTam.getHuman());
            stateTam = stateTam.getHuman().testOfMummy(stateTam);
            resultState[i + 1] = new StaticState(stateTam);
        }


        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainFrame resultFrame = new MainFrame(resultState, 0, xExit, yExit, position);
                resultFrame.setVisible(true);
            }
        });
    }

    private static boolean traceStep(State state, int currentStep, int findX, int findY) {
        try {
            int test = state.getMaze()[findX][findY][0];
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
        for (int i = 1; i < 15; i++) {
            try {
                int test = state.getMaze()[findX][findY][i];
            } catch (ArrayIndexOutOfBoundsException e) {
                return false;
            }
            if (state.getMaze()[findX][findY][i] == currentStep) {
                return true;
            }
        }
        return false;
    }

    public static int getTraceLength() {
        return trace.length;
    }

    //Getter & Setter Auto Generated Code
    public static short getxExit() {
        return xExit;
    }

    public static short getyExit() {
        return yExit;
    }

    public short getStep() {
        return step;
    }

    public static short getStateSide() {
        return stateSide;
    }

    public static short[] getStateSideCount() {
        return stateSideCount;
    }

    public static State[][] getMang() {
        return mang;
    }

    public static void setxExit(short xExit) {
        MainProcess.xExit = xExit;
    }

    public static void setyExit(short yExit) {
        MainProcess.yExit = yExit;
    }

    public void setStep(short step) {
        this.step = step;
    }

    public static void setStateSide(short stateSide) {
        MainProcess.stateSide = stateSide;
    }

    public static void setStateSideCount(short[] stateSideCount) {
        MainProcess.stateSideCount = stateSideCount;
    }

    public static void setMang(State[][] mang) {
        MainProcess.mang = mang;
    }

    public static ArrayList<StaticState> getExistedState() {
        return existedState;
    }

    public static void setExistedState(ArrayList<StaticState> existedState) {
        MainProcess.existedState = existedState;
    }
}
