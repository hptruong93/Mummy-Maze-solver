package vda8888.interfc;

import interfc.SetupOption;
import interfc.WelcomeScreen;

import java.awt.Color;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import process.Human;
import process.Keyd;
import process.MainProcess;
import process.MummyRed;
import process.MummyWhite;
import process.Scorpion;
import process.ScorpionRed;
import process.State;
import process.StaticState;
import interfc.WallEdit;

public class MainFrame extends JFrame implements ActionListener { //i.e. Maze Setup

    private static final int UNIT_WIDTH = 46;
    private static final int UNIT_HEIGHT = 46;
    private static final Color HUMAN_COLOR = Color.BLUE;
    private static final Color MUMMY_WHITE = Color.WHITE;
    private static final Color MUMMY_RED = Color.RED;
    private static final Color EXIT = Color.ORANGE;
    private static final Color SK = Color.DARK_GRAY;
    private static final Color SK_RED = Color.PINK;
    private static final Color KEY = Color.GREEN;
    private static final Color TRAP = Color.BLACK;
    private String jobPerforming;
    private int tracingState;
    private JButton[] unit;
    public static JButton[] wall;
    private static int x, y;
    private JButton bSetup, bCancel, bReset, bNext, bPrevious;
    private static State state;
    private static int mummyWhiteCount;
    private static int mummyRedCount;
    private static int skCount;
    private static int skRedCount;
    private static int keyCount;
    private static int xExit, yExit;
    private static StaticState[] resultState;
    private ActionListener wallEdit;

    public MainFrame(int numberOfRows, int numberOfColumns, Point position) {
        setupFrame(true, position);
        this.setLocation(position);
        x = numberOfRows;
        y = numberOfColumns;
        state = new State(new short[x][y][255], new Keyd[10], new MummyWhite[10], new MummyRed[10],
                new Scorpion[10], new ScorpionRed[10], new Human(-1, -1), (short) 1);
        initComponents(x, y, "solve");
        xExit = yExit = -1;
        mummyWhiteCount = mummyRedCount = skCount = skRedCount = keyCount = 0;
    }

    public MainFrame(StaticState[] resultState, int tracing, short xExit, short yExit, Point position) {
        setupFrame(false, position);
        MainFrame.resultState = resultState;
        x = resultState[tracing].getStaticMaze().length;
        y = resultState[tracing].getStaticMaze()[0].length;
        this.tracingState = tracing;
        initComponents(x, y, "trace");
        MainFrame.xExit = xExit;
        MainFrame.yExit = yExit;
        printResult(resultState, tracing);
    }

    private void setupFrame(boolean show, Point position) {
        setSize(500, 300);
        setVisible(show);
        setLocation(position);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
    }

    private void initComponents(int a, int b, String task) {
        switch (task) {
            case "solve":
                this.setTitle("Setup the Maze!");
                break;
            case "trace":
                this.setTitle("Solution Maze.");
                break;
        }

        jobPerforming = task;
        //Menu Setup
        JMenuBar menuBar = new JMenuBar();

        JMenu mFile = new JMenu("File");
        JMenuItem miExit = new JMenuItem("Exit");
        miExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.ALT_MASK));
        miExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        mFile.add(miExit);
        menuBar.add(mFile);


        JMenu mEdit = new JMenu("Edit");
        JMenuItem miReset = new JMenuItem("Reset");
        miReset.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_MASK));
        miReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetAll();
            }
        });
        mEdit.add(miReset);
        menuBar.add(mEdit);

        this.setJMenuBar(menuBar);


        //Button Setup
        this.bNext = new JButton("Next");
        this.bNext.addActionListener(this);

        this.bPrevious = new JButton("Previous");
        this.bPrevious.addActionListener(this);

        if (this.tracingState == 0) {
            this.bPrevious.setEnabled(false);
        } else if (this.tracingState == MainProcess.getTraceLength() - 1) {
            this.bNext.setEnabled(false);
        } else {
            this.bPrevious.setEnabled(true);
            this.bNext.setEnabled(true);
        }


        this.bSetup = new JButton("Setup and Solve!");
        this.bSetup.addActionListener(this);
        this.bSetup.setForeground(Color.red);

        this.bCancel = new JButton("Cancel");
        if (task.equals("trace")) {
            this.bCancel.setText("Return to Setup");
        }
        this.bCancel.addActionListener(this);

        this.bReset = new JButton("Reset");
        this.bReset.addActionListener(this);
        //Unit setup
        this.unit = new JButton[a * b];
        for (int i = 0; i < this.unit.length; i++) {
            this.unit[i] = new JButton();
            this.unit[i].setText("" + i);
            this.unit[i].addActionListener(this);
        }
        //Wall setup
        wall = new JButton[2 * a * b + a + b];
        for (int i = 0; i < wall.length; i++) {
            wall[i] = new JButton("Wall " + i);
            wall[i].setSize(UNIT_WIDTH, UNIT_HEIGHT);
        }

        state.setMaze(new short[a][b][255]);
        this.setBorderColor(a, b);

        wallEdit = new WallEdit(state.getMaze(), wall, x, y);
        if (task.equals("solve")) {
            for (int i = 0; i < wall.length; i++) {
                wall[i].addActionListener(wallEdit);
            }
        }

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        //Layout Setup
        {//Hortizontal

            ParallelGroup parallel = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
            SequentialGroup horizontal = layout.createSequentialGroup();
            horizontal.addGap(16); //Left Margin

            ParallelGroup setupAndCancel = layout.createParallelGroup(GroupLayout.Alignment.TRAILING);
            setupAndCancel.addComponent(this.bCancel);
            switch (task) {
                case "solve":
                    setupAndCancel.addComponent(this.bSetup, 121, 121, 121);
                    setupAndCancel.addComponent(this.bReset, 121, 121, 121);
                    break;
                case "trace":
                    setupAndCancel.addGroup(layout.createSequentialGroup()
                            .addComponent(this.bPrevious)
                            .addComponent(this.bNext));
                    break;
            }


            ParallelGroup column = layout.createParallelGroup(GroupLayout.Alignment.LEADING);

            for (int i = 2 * a + 1; i > 0; i--) {
                SequentialGroup row = layout.createSequentialGroup();
                if (i % 2 != 0) {
                    row.addGap(UNIT_WIDTH / 3);
                    int initial = ((i - 1) / 2) * (2 * b + 1);
                    for (int j = initial; j < initial + b; j++) {
                        row.addComponent(wall[j], UNIT_WIDTH, UNIT_WIDTH, UNIT_WIDTH).addGap(UNIT_WIDTH / 3);
                    }
                } else if (i % 2 == 0) {
                    int initial = ((i - 2) / 2 + 1) * b + ((i - 2) / 2) * (b + 1);
                    for (int j = initial; j < initial + b; j++) {
                        row.addComponent(wall[j], UNIT_WIDTH / 3, UNIT_WIDTH / 3, UNIT_WIDTH / 3);
                        row.addComponent(unit[(i - 2) / 2 * b + j - initial], UNIT_WIDTH, UNIT_WIDTH, UNIT_WIDTH);
                    }
                    row.addComponent(wall[initial + b], UNIT_WIDTH / 3, UNIT_WIDTH / 3, UNIT_WIDTH / 3);
                }
                column.addGroup(row);
            }
            setupAndCancel.addGroup(column);
            horizontal.addGroup(setupAndCancel);

            horizontal.addContainerGap(16, Short.MAX_VALUE); //Right Margin
            layout.setHorizontalGroup(parallel.addGroup(horizontal));
        }

        {//Vertical
            ParallelGroup parallel1 = layout.createParallelGroup(GroupLayout.Alignment.LEADING); //
            SequentialGroup vertical = layout.createSequentialGroup();
            vertical.addGap(16); //Top Margin

            for (int i = 1; i < 2 * a + 2; i++) {
                if (i % 2 != 0) {
                    ParallelGroup row = layout.createParallelGroup(GroupLayout.Alignment.BASELINE);
                    row.addGap(UNIT_HEIGHT / 3);
                    int initial = ((i - 1) / 2) * (2 * b + 1) + b - 1;
                    for (int j = initial; j >= initial - b + 1; j--) {
                        row.addComponent(wall[j], UNIT_HEIGHT / 3, UNIT_HEIGHT / 3, UNIT_HEIGHT / 3);
                        row.addGap(UNIT_HEIGHT / 3);
                    }
                    vertical.addGroup(row);
                } else if (i % 2 == 0) {
                    ParallelGroup row = layout.createParallelGroup(GroupLayout.Alignment.BASELINE);
                    int initial = ((i - 2) / 2 + 1) * b + ((i - 2) / 2 * (b + 1)) + b - 1;
                    row.addComponent(wall[initial + 1], UNIT_HEIGHT, UNIT_HEIGHT, UNIT_HEIGHT);
                    for (int j = initial; j >= initial - b + 1; j--) {
                        row.addComponent(unit[i / 2 * b - 1 - (initial - j)], UNIT_HEIGHT, UNIT_HEIGHT, UNIT_HEIGHT);
                        row.addComponent(wall[j], UNIT_HEIGHT, UNIT_HEIGHT, UNIT_HEIGHT);
                    }
                    vertical.addGroup(row);
                }
            }
            vertical.addGap(18);
            switch (task) {
                case "solve":
                    vertical.addComponent(this.bSetup);
                    vertical.addComponent(this.bReset);
                    break;
                case "trace":
                    vertical.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(this.bNext)
                            .addComponent(this.bPrevious));
                    break;
            }
            vertical.addComponent(this.bCancel);

            vertical.addContainerGap(16, Short.MAX_VALUE); // Bottom Margin
            parallel1.addGroup(vertical);

            layout.setVerticalGroup(parallel1);
        }

        pack();
    }

    private void setBorderColor(int a, int b) {//rows/columns
        //Set Color Border Wall
        for (int i = 0; i < b; i++) { //Top and Bottom row
            SetupOption.editButton(0, i, "up", 1);
            state.getMaze()[0][i][0] = SetupOption.editDigit(state.getMaze()[0][i][0], 4, 1);
            SetupOption.editButton(a - 1, i, "down", 1);
            state.getMaze()[a - 1][i][0] = SetupOption.editDigit(state.getMaze()[a - 1][i][0], 2, 1);
        }
        for (int i = 0; i < a; i++) { //Two sides
            SetupOption.editButton(i, 0, "left", 1);
            state.getMaze()[i][0][0] = SetupOption.editDigit(state.getMaze()[i][0][0], 1, 1);
            SetupOption.editButton(i, b - 1, "right", 1);
            state.getMaze()[i][b - 1][0] = SetupOption.editDigit(state.getMaze()[i][b - 1][0], 3, 1);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        switch (jobPerforming) {
            case "solve":
                int position = 0;
                for (int i = 0; i < unit.length; i++) {
                    if (button.equals(unit[i])) {
                        position = i;
                        break;
                    }
                }
                if ((button != this.bSetup) && (button != this.bCancel) && (button != this.bReset)) {
                    JFrame setupOption = SetupOption.getInstance(this, position);
                    setupOption.setVisible(true);
                } else if (button == this.bCancel) {
                    this.setVisible(false);
                    WelcomeScreen.welcome.setVisible(true);
                } else if (button == this.bSetup) {
                    if ((xExit == -1) && (yExit == -1)) {
                        JOptionPane.showMessageDialog(null, "No exit found. Please choose an exit!", "Missing Information!", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if ((state.getHuman().getX() == -1) && (state.getHuman().getY() == -1)) {
                        JOptionPane.showMessageDialog(null, "No human found. Please add a human!", "Missing Information!", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    this.setVisible(false);
                    MummyWhite[] finalMummyWhite = new MummyWhite[mummyWhiteCount];
                    System.arraycopy(state.getMummyWhite(), 0, finalMummyWhite, 0, mummyWhiteCount);

                    MummyRed[] finalMummyRed = new MummyRed[mummyRedCount];
                    System.arraycopy(state.getMummyRed(), 0, finalMummyRed, 0, mummyRedCount);

                    Scorpion[] finalSk = new Scorpion[skCount];
                    System.arraycopy(state.getSk(), 0, finalSk, 0, skCount);

                    ScorpionRed[] finalSkRed = new ScorpionRed[skRedCount];
                    System.arraycopy(state.getSkRed(), 0, finalSkRed, 0, skRedCount);

                    Keyd[] finalKey = new Keyd[keyCount];
                    System.arraycopy(state.getKey(), 0, finalKey, 0, keyCount);

                    State tam = new State(state.getMaze(), finalKey, finalMummyWhite, finalMummyRed,
                            finalSk, finalSkRed, state.getHuman(), (short) 1);

                    WindowEvent exit = new WindowEvent(this, WindowEvent.WINDOW_CLOSED);
                    Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(exit);

                    MainProcess solve = new MainProcess(tam, (short) xExit, (short) yExit, this.getLocation());
                } else if (button == this.bReset) {
                    this.resetAll();
                }
                break;
            case "trace":
                if (button == this.bCancel) {
                    this.setVisible(false);
//                    WindowEvent exit = new WindowEvent(this,WindowEvent.WINDOW_CLOSED);
//                    Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(exit);
                    this.dispose();
                    MainFrame setupFrame = new MainFrame(x, y, this.getLocation());
                } else if (button == this.bNext) {
                    for (int i = 0; i < wall.length; i++) {
                        wall[i].setBackground(bReset.getBackground());
                    }
                    this.setBorderColor(x, y);
                    for (int i = 0; i < unit.length; i++) {
                        unit[i].setBackground(bReset.getBackground());
                    }

                    this.tracingState++;
                    this.printResult(resultState, this.tracingState);
                    this.bPrevious.setEnabled(true);
                    if (this.tracingState == MainProcess.getTraceLength() - 1) {
                        this.bNext.setEnabled(false);
                    } else {
                        this.bNext.setEnabled(true);
                    }
                } else if (button == this.bPrevious) {
                    for (int i = 0; i < wall.length; i++) {
                        wall[i].setBackground(bReset.getBackground());
                    }
                    this.setBorderColor(x, y);
                    for (int i = 0; i < unit.length; i++) {
                        unit[i].setBackground(bReset.getBackground());
                    }
                    this.tracingState--;
                    this.printResult(resultState, this.tracingState);
                    this.bNext.setEnabled(true);
                    if (this.tracingState == 0) {
                        this.bPrevious.setEnabled(false);
                    } else {
                        this.bPrevious.setEnabled(true);
                    }
                }
                break;
        }
    }

    private void resetAll() {
        for (int i = 0; i < unit.length; i++) {
            this.unit[i].setBackground(bSetup.getBackground()); //Default Button Color 
        }
        state.setMaze(new short[x][y][255]);
        state.setKey(new Keyd[255]);
        keyCount = 0;
        state.setMummyWhite(new MummyWhite[255]);
        mummyWhiteCount = 0;
        state.setMummyRed(new MummyRed[255]);
        mummyRedCount = 0;
        state.setSk(new Scorpion[255]);
        skCount = 0;
        state.setSkRed(new ScorpionRed[255]);
        skRedCount = 0;
        state.setHuman(new Human(-1, -1));
        xExit = yExit = -1;
        wallEdit = new WallEdit(state.getMaze(), wall, x, y);
        for (int i = 0; i < wall.length; i++) {
            wall[i].setBackground(bReset.getBackground());
            ActionListener[] tam = wall[i].getActionListeners();
            for (int j = 0; j < tam.length; j++) {
                wall[i].removeActionListener(tam[j]);
            }
            wall[i].addActionListener(wallEdit);
        }
        this.setBorderColor(x, y);
    }

    public static int deduceUnit(int a, int b) {
        return a * y + b;
    }

    private void printResult(StaticState[] resultState, int tracing) {//Highlight
        String[] sides = {"left", "down", "right", "up"};
//      Walls & Traps
        for (int i = 0; i < resultState[tracing].getStaticMaze().length; i++) {
            for (int j = 0; j < resultState[tracing].getStaticMaze().length; j++) {
//                    1111 last four digits: up, right, down, left
                for (int k = 1; k < 5; k++) {
                    int editValue = SetupOption.digit(resultState[tracing].getStaticMaze()[i][j], k);
                    SetupOption.editButton(i, j, sides[k - 1], editValue);
                }
                if (SetupOption.digit(resultState[tracing].getStaticMaze()[i][j], 5) == 1) {
                    int tam = deduceUnit(i, j);
                    this.unit[tam].setBackground(TRAP);
                }
            }
        }
        this.unit[deduceUnit(xExit, yExit)].setBackground(EXIT);

        //Key
        for (int i = 0; i < resultState[tracing].getKey().length; i++) {
            int xWall = resultState[tracing].getKey()[i].getxWall();
            int yWall = resultState[tracing].getKey()[i].getyWall();
            String affectedSide = sides[resultState[tracing].getKey()[i].getSide()-1];
            int currentState = SetupOption.digit(resultState[tracing].getStaticMaze()[xWall][yWall],
                    MainFrame.getSState().getKey()[i].getSide());
            SetupOption.editButton(xWall, yWall, affectedSide, 2*currentState);
            
            int tam = deduceUnit(resultState[tracing].getKey()[i].getX(),
                    resultState[tracing].getKey()[i].getY());
            this.unit[tam].setBackground(KEY);
        }

        //LivingThings
        //Mummy White
        for (int i = 0; i < resultState[tracing].getMummyWhite().length; i++) {
            int tam = deduceUnit(resultState[tracing].getMummyWhite()[i].getX(),
                    resultState[tracing].getMummyWhite()[i].getY());
            this.unit[tam].setBackground(MUMMY_WHITE);
        }
        //Mummy Red
        for (int i = 0; i < resultState[tracing].getMummyRed().length; i++) {
            int tam = deduceUnit(resultState[tracing].getMummyRed()[i].getX(),
                    resultState[tracing].getMummyRed()[i].getY());
            this.unit[tam].setBackground(MUMMY_RED);
        }
        //Scorpion
        for (int i = 0; i < resultState[tracing].getSk().length; i++) {
            int tam = deduceUnit(resultState[tracing].getSk()[i].getX(),
                    resultState[tracing].getSk()[i].getY());
            this.unit[tam].setBackground(SK);
        }
        //Scorpion Red
        for (int i = 0; i < resultState[tracing].getSkRed().length; i++) {
            int tam = deduceUnit(resultState[tracing].getSkRed()[i].getX(),
                    resultState[tracing].getSkRed()[i].getY());
            this.unit[tam].setBackground(SK_RED);
        }
        //Human
        this.unit[deduceUnit(resultState[tracing].getHuman().getX(),
                resultState[tracing].getHuman().getY())].setBackground(HUMAN_COLOR);
        this.setBorderColor(x, y);
    }

    //Getter and Setter Auto Generated Code
    public static int getXX() {//Naming problem
        return x;
    }

    public static int getYY() {//Naming problem
        return y;
    }

    public static int getMummyWhiteCount() {
        return mummyWhiteCount;
    }

    public static int getMummyRedCount() {
        return mummyRedCount;
    }

    public static int getSkCount() {
        return skCount;
    }

    public static int getXExit() {
        return xExit;
    }

    public static int getYExit() {
        return yExit;
    }

    public static void setMummyWhiteCount(int mummyWhiteCount) {
        MainFrame.mummyWhiteCount = mummyWhiteCount;
    }

    public static void setMummyRedCount(int mummyRedCount) {
        MainFrame.mummyRedCount = mummyRedCount;
    }

    public static void setSkCount(int skCount) {
        MainFrame.skCount = skCount;
    }

    public static void setXExit(int xExit) {
        MainFrame.xExit = xExit;
    }

    public static void setYExit(int yExit) {
        MainFrame.yExit = yExit;
    }

    public JButton[] getUnit() {
        return unit;
    }

    public static int getKeyCount() {
        return keyCount;
    }

    public static void setKeyCount(int keyCount) {
        MainFrame.keyCount = keyCount;
    }

    public static int getSkRedCount() {
        return skRedCount;
    }

    public static void setSkRedCount(int skRedCount) {
        MainFrame.skRedCount = skRedCount;
    }

    public static int getxExit() {
        return xExit;
    }

    public static int getyExit() {
        return yExit;
    }

    public static void setxExit(int xExit) {
        MainFrame.xExit = xExit;
    }

    public static void setyExit(int yExit) {
        MainFrame.yExit = yExit;
    }

    public static State getSState() { //Naming problem
        return state;
    }

    public static void setState(State state) {
        MainFrame.state = state;
    }
}
