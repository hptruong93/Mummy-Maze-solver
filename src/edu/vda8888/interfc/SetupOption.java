package interfc;

import java.awt.Color;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;

import process.Keyd;
import process.MummyRed;
import process.MummyWhite;
import process.Scorpion;
import process.ScorpionRed;
import process.State;
import vda8888.interfc.MainFrame;

public class SetupOption extends javax.swing.JFrame {

    private static final Color WALL_COLOR = Color.BLACK;
    private static final Color HUMAN = Color.BLUE;
    private static final Color MUMMY_WHITE = Color.WHITE;
    private static final Color MUMMY_RED = Color.RED;
    private static final Color SK = Color.DARK_GRAY;
    private static final Color SK_RED = Color.PINK;
    private static final Color EXIT = Color.ORANGE;
    private static final Color KEY = Color.GREEN;
    private static final Color TRAP = Color.BLACK;
    private int invoker;
    private static int x, y; //i,e. invoker coordinate
    private static JButton[] unit;
    private State state;

    public static SetupOption getInstance(MainFrame mainFrame, int position) {
        SetupOption setupOption = new SetupOption(mainFrame, position);
        return setupOption;
    }

    public static short editDigit(int x, int digit, int editValue) {
        String tam = "";
        while (x != 0) {
            int i = x % 2;
            tam = i + tam;
            x = x / 2;
        }
        if (tam.length() < digit) {
            while (tam.length() < digit) {
                tam = "0" + tam;
            }
        }
        String valueTam = "" + editValue;
        String tam1 = tam.substring(0, tam.length() - digit);
        String tam2 = tam.substring(tam.length() - digit + 1);
        tam = tam1.concat(valueTam).concat(tam2);
        return (short) Integer.parseInt(tam, 2);
    }

    public static void editButton(int xC, int yC, String position, int value) { //Known unit's position
        //      1111  up, right, down, left   
        JButton tam1 = new JButton();
        Color [] editing = {tam1.getBackground(),WALL_COLOR,KEY};
        int yy = MainFrame.getYY();
        switch (position) {
            case "left": {
                //1
                int i = 2 * (xC + 1);
                int number = ((i - 2) / 2 + 1) * yy + (i - 2) / 2 * (yy + 1) + yC;
                MainFrame.wall[number].setBackground(editing[value]);
                break;
            }
            case "right": {
                //3
                int i = 2 * (xC + 1);
                int number = ((i - 2) / 2 + 1) * yy + (i - 2) / 2 * (yy + 1) + yC + 1;
                MainFrame.wall[number].setBackground(editing[value]);
                break;
            }
            case "down": {
                // 2
                int i = 2 * (xC + 1) + 1;
                int number = (i - 1) / 2 * (2 * yy + 1) + yC;
                MainFrame.wall[number].setBackground(editing[value]);
                break;
            }
            case "up": {
                //4
                int i = 2 * (xC + 1) - 1;
                int number = (i - 1) / 2 * (2 * yy + 1) + yC;
                MainFrame.wall[number].setBackground(editing[value]);
                break;
            }
        }
    }

    @SuppressWarnings({"unchecked", "unchecked"})
    private void initSetup(int a, int b) {
//        if (b == 0) {
//            this.cbLeftWall.setSelected(true);
//            this.cbLeftWall.setEnabled(false);
//        }
//        if (b == MainFrame.getYY() - 1) {
//            this.cbRightWall.setSelected(true);
//            this.cbRightWall.setEnabled(false);
//        }
//        if (a == 0) {
//            this.cbTopWall.setSelected(true);
//            this.cbTopWall.setEnabled(false);
//        }
//        if (a == MainFrame.getXX() - 1) {
//            this.cbBottomWall.setSelected(true);
//            this.cbBottomWall.setEnabled(false);
//        }

        DefaultComboBoxModel model = new DefaultComboBoxModel();
        for (int i = 0; i < unit.length; i++) {
            model.addElement("" + i);
        }
        cbbAffectedUnit.setModel(model);
        cbbAffectedUnit.setEnabled(false);

        model = new DefaultComboBoxModel();
        model.addElement("Left");
        model.addElement("Bottom");
        model.addElement("Right");
        model.addElement("Top");
        cbbAffectedSide.setModel(model);
        cbbAffectedSide.setEnabled(false);
        this.resetUnit(x, y);
    }

    /**
     * Creates new form SetupOption
     */
    protected SetupOption(MainFrame mainFrame, int position) {
        initComponents();
        state = MainFrame.getSState();
        this.invoker = position;
        if (MainFrame.getYY() == 0) {
            y = 0;
        } else {
            x = position / MainFrame.getYY();
        }
        y = position - x * MainFrame.getYY();
        unit = mainFrame.getUnit();
        this.initSetup(x, y);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        rbMummyWhite = new javax.swing.JRadioButton();
        rbMummyRed = new javax.swing.JRadioButton();
        rbSk = new javax.swing.JRadioButton();
        rbTrap = new javax.swing.JRadioButton();
        rbHuman = new javax.swing.JRadioButton();
        rbSkRed = new javax.swing.JRadioButton();
        jPanel3 = new javax.swing.JPanel();
        cbExit = new javax.swing.JCheckBox();
        cbKey = new javax.swing.JCheckBox();
        jLabel4 = new javax.swing.JLabel();
        cbbAffectedUnit = new javax.swing.JComboBox();
        cbbAffectedSide = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        bAdd = new javax.swing.JButton();
        bCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Setup Option");
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        jLabel1.setText("What do you want to add?");

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel3.setText("Threats");

        jLabel5.setText("and Human");

        buttonGroup1.add(rbMummyWhite);
        rbMummyWhite.setText("White Mummy");

        buttonGroup1.add(rbMummyRed);
        rbMummyRed.setText("Red Mummy");

        buttonGroup1.add(rbSk);
        rbSk.setText("Scorpion");

        buttonGroup1.add(rbTrap);
        rbTrap.setText("Trap");

        buttonGroup1.add(rbHuman);
        rbHuman.setText("Human");

        buttonGroup1.add(rbSkRed);
        rbSkRed.setText("Red Scorpion");
        rbSkRed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbSkRedActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rbHuman)
                    .addComponent(rbTrap)
                    .addComponent(rbSkRed)
                    .addComponent(rbSk)
                    .addComponent(rbMummyRed)
                    .addComponent(rbMummyWhite))
                .addContainerGap(39, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(rbMummyWhite)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rbMummyRed)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rbSk)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rbSkRed)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rbTrap)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rbHuman)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        cbExit.setText("Exit");
        cbExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbExitActionPerformed(evt);
            }
        });

        cbKey.setText("Key");
        cbKey.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbKeyActionPerformed(evt);
            }
        });

        jLabel4.setText("Path");

        jLabel6.setText("Side:");

        jLabel7.setText("Affected unit");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbExit)
                    .addComponent(cbKey)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbbAffectedSide, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbbAffectedUnit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbKey)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(cbbAffectedUnit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbbAffectedSide, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addComponent(cbExit)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        bAdd.setText("Add");
        bAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAddActionPerformed(evt);
            }
        });

        bCancel.setText("Cancel");
        bCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(69, 69, 69)
                            .addComponent(bAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(bCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(42, 42, 42)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(50, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jPanel2, jPanel3});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bAdd)
                    .addComponent(bCancel))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static int digit(int x, int n) { //return the nth digit
        //return ((x / (((int) Math.pow(10, n-1))))) % 10;
        return (x >> (n - 1)) % 2;
    }

    private void resetUnit(int x, int y) {
        int position = x + y * MainFrame.getYY();
        //Reset the unit
        // Reset wall
        // 1111  up, right, down, left     
        state.getMaze()[x][y][0] = 0;
        try { //Left unit has a right wall
            int tam = digit(state.getMaze()[x][y - 1][0], 3);
            state.getMaze()[x][y][0] = editDigit(state.getMaze()[x][y][0], 1, tam); //This has a left wall
//            this.cbLeftWall.setSelected(tam == 1);
            editButton(x, y, "left", tam);
        } catch (ArrayIndexOutOfBoundsException e) { /* Do nothing */

        }

        try { //Right unit has a left wall
            int tam = digit(state.getMaze()[x][y + 1][0], 1);
            state.getMaze()[x][y][0] = editDigit(state.getMaze()[x][y][0], 3, tam); //This has a right wall
//            this.cbRightWall.setSelected(tam == 1);
            editButton(x, y, "right", tam);
        } catch (ArrayIndexOutOfBoundsException e) { /* Do nothing */

        }

        try { //Top unit has a bottom wall
            int tam = digit(state.getMaze()[x - 1][y][0], 2);
            state.getMaze()[x][y][0] = editDigit(state.getMaze()[x][y][0], 4, tam); //This has a top wall
//            this.cbTopWall.setSelected(tam == 1);
            editButton(x, y, "up", tam);
        } catch (ArrayIndexOutOfBoundsException e) { /* Do nothing */

        }

        try { //Bottom unit has a top wall
            int tam = digit(state.getMaze()[x + 1][y][0], 4);
            state.getMaze()[x][y][0] = editDigit(state.getMaze()[x][y][0], 2, tam); //This has a bottom wall
//            this.cbBottomWall.setSelected(tam == 1);
            editButton(x, y, "down", tam);
        } catch (ArrayIndexOutOfBoundsException e) { /* Do nothing */

        }

        //Reset Mummy White for that unit
        for (int i = 0; i < MainFrame.getMummyWhiteCount(); i++) {
            if ((state.getMummyWhite()[i].getX() == x)
                    && (state.getMummyWhite()[i].getY() == y)) {
                state.getMummyWhite()[i] =
                        new MummyWhite(state.getMummyWhite()[MainFrame.getMummyWhiteCount() - 1].getX(),
                        state.getMummyWhite()[MainFrame.getMummyWhiteCount() - 1].getY());
                MainFrame.setMummyWhiteCount(MainFrame.getMummyWhiteCount() - 1);
                unit[position].setBackground(bCancel.getBackground());
                break;
            }
        }
        //Reset Mummy Red for that unit
        for (int i = 0; i < MainFrame.getMummyRedCount(); i++) {
            if ((state.getMummyRed()[i].getX() == x)
                    && (state.getMummyRed()[i].getY() == y)) {
                state.getMummyRed()[0] = new MummyRed(state.getMummyRed()[0].getX(), state.getMummyRed()[0].getY());
                state.getMummyRed()[i] =
                        new MummyRed(state.getMummyRed()[MainFrame.getMummyRedCount() - 1].getX(),
                        state.getMummyRed()[MainFrame.getMummyRedCount() - 1].getY());
                MainFrame.setMummyRedCount(MainFrame.getMummyRedCount() - 1);
                unit[position].setBackground(bCancel.getBackground());
                break;
            }
        }
        //Reset Scorpion for that unit
        for (int i = 0; i < MainFrame.getSkCount(); i++) {
            if ((state.getSk()[i].getX() == x) && (state.getSk()[i].getY() == y)) {
                state.getSk()[i] =
                        new Scorpion(state.getSk()[MainFrame.getSkCount() - 1].getX(),
                        state.getSk()[MainFrame.getSkCount() - 1].getY());
                MainFrame.setSkCount(MainFrame.getSkCount() - 1);
                unit[position].setBackground(bCancel.getBackground());
                break;
            }
        }
        // Reset Red Scorpion for that unit
        for (int i = 0; i < MainFrame.getSkRedCount(); i++) {
            if ((state.getSkRed()[i].getX() == x) && (state.getSkRed()[i].getY() == y)) {
                state.getSkRed()[i] =
                        new ScorpionRed(state.getSkRed()[MainFrame.getSkRedCount() - 1].getX(),
                        state.getSkRed()[MainFrame.getSkRedCount() - 1].getY());
                MainFrame.setSkRedCount(MainFrame.getSkRedCount() - 1);
                unit[position].setBackground(bCancel.getBackground());
                break;
            }
        }

        //Reset Key for that unit
        String[] sides = {"left","down","right","up"};
        for (int i = 0; i < MainFrame.getKeyCount(); i++) {
            if ((state.getKey()[i].getX() == x) && (state.getKey()[i].getY() == y)) {
                int xWall = state.getKey()[i].getxWall();
                int yWall = state.getKey()[i].getyWall();
                String affectedPosition = sides[MainFrame.getSState().getKey()[i].getSide()-1];
                int currentState = digit(state.getMaze()[xWall][yWall][0],MainFrame.getSState().getKey()[i].getSide());
                editButton(xWall,yWall,affectedPosition,currentState);
                
                state.getKey()[i] =
                        new Keyd(state.getKey()[MainFrame.getKeyCount() - 1].getX(),
                        state.getKey()[MainFrame.getKeyCount() - 1].getY());
                MainFrame.setKeyCount(MainFrame.getKeyCount() - 1);
                unit[position].setBackground(bCancel.getBackground());
                break;
            }
        }

        //Reset Human for that unit
        if ((state.getHuman().getX() == x) && (state.getHuman().getY() == y) && ((rbMummyWhite.isSelected())
                || (rbMummyRed.isSelected()) || (rbSk.isSelected()))) {
            state.getHuman().setX(-1);
            state.getHuman().setY(-1);
        }

        //Reset Exit for that unit
        if ((MainFrame.getXExit() == x) && (MainFrame.getYExit() == y)) {
            this.cbExit.setSelected(true);
        }
    }

    private static int toNumber(boolean bo) {
        if (bo) {
            return 1;
        } else {
            return 0;
        }
    }

    private void addFeatures(int position) {
        //Add features
        //Path
        
        int editValue = toNumber(rbTrap.isSelected());
        state.getMaze()[x][y][0] = editDigit(state.getMaze()[x][y][0], 5, editValue);
        if (rbTrap.isSelected()) {//Trap
            unit[position].setBackground(TRAP);
        } else {
            unit[position].setBackground(bCancel.getBackground());
        }
        
//        if (rbTrap.isSelected()) {//Trap
//            state.getMaze()[x][y][0] = editDigit(state.getMaze()[x][y][0], 5, 1);
//            unit[position].setBackground(TRAP);
//        } 
        if (cbKey.isSelected()) {//Key
            String[] side = {"left","down","right","up"};
            state.getMaze()[x][y][0] = editDigit(state.getMaze()[x][y][0], 6, 1);
            MainFrame.setKeyCount(MainFrame.getKeyCount() + 1);
            int selected = cbbAffectedUnit.getSelectedIndex();
            int xWall = selected / MainFrame.getYY();
            int yWall = selected - xWall * MainFrame.getYY();
            state.getKey()[MainFrame.getKeyCount() - 1] = new Keyd(x, y, xWall, yWall, cbbAffectedSide.getSelectedIndex() + 1);
            unit[position].setBackground(KEY);
            editButton(xWall, yWall, side[cbbAffectedSide.getSelectedIndex()], 
                    2*digit(state.getMaze()[xWall][yWall][0],cbbAffectedSide.getSelectedIndex()+1));
        }
        if (cbExit.isSelected()) {//Exit
            unit[position].setBackground(EXIT);
            if (MainFrame.getXExit() != -1) {
                int exitPosition = MainFrame.deduceUnit(MainFrame.getXExit(), MainFrame.getYExit());
                JButton tam1 = new JButton();
                unit[exitPosition].setBackground(tam1.getBackground());
            }
            MainFrame.setXExit(x);
            MainFrame.setYExit(y);
            unit[position].setBackground(EXIT);
        }

        //Threats & Human
        if (rbMummyWhite.isSelected()) {//Mummy White
            unit[position].setBackground(MUMMY_WHITE);
            MainFrame.setMummyWhiteCount(MainFrame.getMummyWhiteCount() + 1);
            state.getMummyWhite()[MainFrame.getMummyWhiteCount() - 1] = new MummyWhite(x, y);
        }
        
        if (rbMummyRed.isSelected()) {// Mummy Red
            unit[position].setBackground(MUMMY_RED);
            MainFrame.setMummyRedCount(MainFrame.getMummyRedCount() + 1);
            state.getMummyRed()[MainFrame.getMummyRedCount() - 1] = new MummyRed(x, y);
        }
        
        if (rbSk.isSelected()) {// Scorpion
            unit[position].setBackground(SK);
            MainFrame.setSkCount(MainFrame.getSkCount() + 1);
            state.getSk()[MainFrame.getSkCount() - 1] = new Scorpion(x, y);
        }
        if (rbSkRed.isSelected()) {//Scorpion Red
            unit[position].setBackground(SK_RED);
            MainFrame.setSkRedCount(MainFrame.getSkRedCount() + 1);
            state.getSkRed()[MainFrame.getSkRedCount() - 1] = new ScorpionRed(x, y);
        }
        if (rbHuman.isSelected()) {//Human
            if (state.getHuman().getX() != -1) {
                int humanPosition = MainFrame.deduceUnit(state.getHuman().getX(), state.getHuman().getY());
                JButton tam1 = new JButton();
                unit[humanPosition].setBackground(tam1.getBackground());
            }
            unit[position].setBackground(HUMAN);
            state.getHuman().setX(x);
            state.getHuman().setY(y);
        }
    }

    private void bAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAddActionPerformed
        int position = x + y * MainFrame.getYY();
        this.resetUnit(x, y);
        this.addFeatures(this.invoker);
        MainFrame.setState(state);
        this.setVisible(false);
    }//GEN-LAST:event_bAddActionPerformed

    private void bCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCancelActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_bCancelActionPerformed

    private void cbExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbExitActionPerformed
    }//GEN-LAST:event_cbExitActionPerformed

    private void cbKeyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbKeyActionPerformed
        cbbAffectedUnit.setEnabled(cbKey.isSelected());
        cbbAffectedSide.setEnabled(cbKey.isSelected());
    }//GEN-LAST:event_cbKeyActionPerformed

    private void rbSkRedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbSkRedActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rbSkRedActionPerformed
    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(SetupOption.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(SetupOption.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(SetupOption.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(SetupOption.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new SetupOption().setVisible(true);
//            }
//        });
//    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bAdd;
    private javax.swing.JButton bCancel;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JCheckBox cbExit;
    private javax.swing.JCheckBox cbKey;
    private javax.swing.JComboBox cbbAffectedSide;
    private javax.swing.JComboBox cbbAffectedUnit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JRadioButton rbHuman;
    private javax.swing.JRadioButton rbMummyRed;
    private javax.swing.JRadioButton rbMummyWhite;
    private javax.swing.JRadioButton rbSk;
    private javax.swing.JRadioButton rbSkRed;
    private javax.swing.JRadioButton rbTrap;
    // End of variables declaration//GEN-END:variables
}
