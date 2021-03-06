package process;

public class Human extends LivingThings {

    public Human(int x, int y) {
        super(x, y);
    }

    protected static State keyCheck(State state, LivingThings objects) {
        if (LivingThings.digit(state.getMaze()[objects.getX()][objects.getY()][0], 6)
                == 1) { //Key check
            for (int i = 0; i < state.getKey().length; i++) {
                if (state.getKey()[i].samePlace(objects)) {
                    state.setMaze(state.getKey()[i].changeState(state.getMaze()));
                }
            }
        }
        return state;
    }
    
    // Breadth-first search
    protected State attemptMove(State state, short stateSide, int position) {
        State tam = state.cloneState();

        //attempt move up
        if (eligibleHumanMove(tam.getMaze(), tam.getHuman().getX(), tam.getHuman().getY(),
                tam.getHuman().getX() - 1, tam.getHuman().getY())) { //wall check
            int newX = tam.getHuman().getX() - 1;
            tam.setHuman(new Human(newX, tam.getHuman().getY()));
            tam = keyCheck(tam, tam.getHuman());
            tam = tam.getHuman().testOfMummy(tam);
            if (tam.getStep() != -1) { //check if get killed
                if (fillMaze(tam, stateSide, position)) {
                    // Exit check
                    int xx = tam.getHuman().getX();
                    int yy = tam.getHuman().getY();
                    if ((xx == MainProcess.getxExit()) && (yy == MainProcess.getyExit())) {
                        return tam;
                    }
                }
            }
        }

        tam = state.cloneState();
        //attempt move down
        if (eligibleHumanMove(tam.getMaze(), tam.getHuman().getX(), tam.getHuman().getY(),
                tam.getHuman().getX() + 1, tam.getHuman().getY())) { //wall check
            int newX = tam.getHuman().getX() + 1;
            tam.setHuman(new Human(newX, tam.getHuman().getY()));
            tam = keyCheck(tam, tam.getHuman());
            tam = tam.getHuman().testOfMummy(tam);
            if (tam.getStep() != -1) { //check if get killed
                if (fillMaze(tam, stateSide, position)) {
                    //Exit check 
                    int xx = tam.getHuman().getX();
                    int yy = tam.getHuman().getY();
                    if ((xx == MainProcess.getxExit()) && (yy == MainProcess.getyExit())) {
                        return tam;
                    }
                }
            }
        }

        tam = state.cloneState();
        //attempt move left
        if (eligibleHumanMove(tam.getMaze(), tam.getHuman().getX(), tam.getHuman().getY(),
                tam.getHuman().getX(), tam.getHuman().getY() - 1)) { //wall check
            int newY = tam.getHuman().getY() - 1;
            tam.setHuman(new Human(tam.getHuman().getX(), newY));
            tam = keyCheck(tam, tam.getHuman());
            tam = tam.getHuman().testOfMummy(tam);
            if (tam.getStep() != -1) { //check if get killed
                if (fillMaze(tam, stateSide, position)) {
                    //Exit check
                    int xx = tam.getHuman().getX();
                    int yy = tam.getHuman().getY();
                    if ((xx == MainProcess.getxExit()) && (yy == MainProcess.getyExit())) {
                        return tam;
                    }
                }
            }
        }

        tam = state.cloneState();
        //attempt move right
        if (eligibleHumanMove(tam.getMaze(), tam.getHuman().getX(), tam.getHuman().getY(),
                tam.getHuman().getX(), tam.getHuman().getY() + 1)) { //wall check
            int newY = tam.getHuman().getY() + 1;
            tam.setHuman(new Human(tam.getHuman().getX(), newY));
            tam = keyCheck(tam, tam.getHuman());
            tam = tam.getHuman().testOfMummy(tam);
            if (tam.getStep() != -1) { //check if get killed
                if (fillMaze(tam, stateSide, position)) {
                    //Exit check
                    int xx = tam.getHuman().getX();
                    int yy = tam.getHuman().getY();
                    if ((xx == MainProcess.getxExit()) && (yy == MainProcess.getyExit())) {
                        return tam;
                    }
                }
            }
        }

        tam = state.cloneState();
        //attempt stay
        if (eligibleHumanMove(tam.getMaze(), tam.getHuman().getX(), tam.getHuman().getY(),
                tam.getHuman().getX(), tam.getHuman().getY())) { //wall check
            tam.getHuman().setX(tam.getHuman().getX());
            //No Keycheck required
            tam = tam.getHuman().testOfMummy(tam);
            if (tam.getStep() != -1) { //check if get killed
                if (fillMaze(tam, stateSide, position)) {
                    //Exit check is not required
                }
            }
        }

        return null;
    }

    protected State testOfMummy(State state) {

        State temp = state;

        // Creating temporary saving arrays
        short[][][] mazee = temp.getMaze().clone();
        for (int i = 0; i < mazee.length; i++) {
            for (int j = 0; j < mazee[0].length; j++) {
                System.arraycopy(temp.getMaze()[i][j], 0, mazee[i][j], 0, mazee[0][0].length);
            }
        }
        MummyWhite[] updateWhite = temp.getMummyWhite().clone();
        MummyRed[] updateRed = temp.getMummyRed().clone();
        Scorpion[] updateSk = temp.getSk().clone();
        ScorpionRed[] updateSkRed = temp.getSkRed().clone();

        // Move
        for (int i = 0; i < updateWhite.length; i++) {
            Mummy compare = new Mummy(updateWhite[i].getX(), updateWhite[i].getY());
            updateWhite[i] = updateWhite[i].mummyWhiteMove(temp.getMaze(), temp.getHuman());
            temp.setMummyWhite(updateWhite);
            if (temp.getMummyWhite()[i].samePlace(this)) {
                temp.setStep((short) -1);
                return temp;
            } else {
                if (!compare.samePlace(updateWhite[i])) { //i.e. Mummy did move
                    temp = keyCheck(temp, updateWhite[i]);
                }
            }
        }
        for (int i = 0; i < updateRed.length; i++) {
            Mummy compare = new Mummy(updateRed[i].getX(), updateRed[i].getY());
            updateRed[i] = updateRed[i].mummyRedMove(temp.getMaze(), temp.getHuman());
            temp.setMummyRed(updateRed);
            if (this.samePlace(temp.getMummyRed()[i])) {
                temp.setStep((short) -1);
                return temp;
            } else {
                if (!compare.samePlace(updateRed[i])) { //i.e. Mummy did move
                    temp = keyCheck(temp, updateRed[i]);
                }
            }
        }
        for (int i = 0; i < updateSk.length; i++) {
            Mummy compare = new Mummy(updateSk[i].getX(), updateSk[i].getY());
            updateSk[i] = updateSk[i].scorpionMove(temp.getMaze(), temp.getHuman());
            temp.setSk(updateSk);
            if (this.samePlace(temp.getSk()[i])) {
                temp.setStep((short) -1);
                return temp;
            } else {
                if (!compare.samePlace(updateSk[i])) { //i.e. Mummy did move
                    temp = keyCheck(temp, updateSk[i]);
                }
            }
        }
        for (int i = 0; i < updateSkRed.length; i++) {
            Mummy compare = new Mummy(updateSkRed[i].getX(), updateSkRed[i].getY());
            updateSkRed[i] = updateSkRed[i].scorpionMove(temp.getMaze(), temp.getHuman());
            temp.setSkRed(updateSkRed);
            if (this.samePlace(temp.getSkRed()[i])) {
                temp.setStep((short) -1);
                return temp;
            } else {
                if (!compare.samePlace(updateSkRed[i])) { //i.e. Mummy did move
                    temp = keyCheck(temp, updateSkRed[i]);
                }
            }
        }
        temp.setMaze(mazee);
        temp.setMummyWhite(updateWhite);
        temp.setMummyRed(updateRed);
        temp.setSk(updateSk);
        temp.setSkRed(updateSkRed);

        //Check for fighting
        temp = fightCheck(temp);
        updateWhite = temp.getMummyWhite();
        updateRed = temp.getMummyRed();
        updateSk = temp.getSk();
        updateSkRed = temp.getSkRed();

        // Move (2nd step for Mummy)
        for (int i = 0; i < updateWhite.length; i++) {
            Mummy compare = new Mummy(updateWhite[i].getX(), updateWhite[i].getY());
            updateWhite[i] = updateWhite[i].mummyWhiteMove(temp.getMaze(), temp.getHuman());
            temp.setMummyWhite(updateWhite);
            if (temp.getMummyWhite()[i].samePlace(this)) {
                temp.setStep((short) -1);
                return temp;
            } else {
                if (!compare.samePlace(updateWhite[i])) { //i.e. Mummy did move
                    temp = keyCheck(temp, updateWhite[i]);
                }
            }
        }
        for (int i = 0; i < updateRed.length; i++) {
            Mummy compare = new Mummy(updateRed[i].getX(), updateRed[i].getY());
            updateRed[i] = updateRed[i].mummyRedMove(temp.getMaze(), temp.getHuman());
            temp.setMummyRed(updateRed);
            if (this.samePlace(temp.getMummyRed()[i])) {
                temp.setStep((short) -1);
                return temp;
            } else {
                if (!compare.samePlace(updateRed[i])) { //i.e. Mummy did move
                    temp = keyCheck(temp, updateRed[i]);
                }
            }
        }

        //Check for fighting again
        temp = fightCheck(temp);

        temp.setMaze(mazee);
        temp.setMummyWhite(updateWhite);
        temp.setMummyRed(updateRed);
        temp.setSk(updateSk);
        temp.setSkRed(updateSkRed);
        return temp;
    }

    private static State fightCheck(State state) {
        MummyWhite[] updateWhite = state.getMummyWhite();
        MummyRed[] updateRed = state.getMummyRed();
        Scorpion[] updateSk = state.getSk();
        ScorpionRed[] updateSkRed = state.getSkRed();

        //White vs White
        updateWhite = (MummyWhite[]) mummyFight(updateWhite, updateWhite);
        //White vs Red --> White win
        updateRed = (MummyRed[]) mummyFight(updateWhite, updateRed);
        //White vs Scorpion --> White win
        updateSk = (Scorpion[]) mummyFight(updateWhite, updateSk);
        //White vs RedScorpion --> White win
        updateSkRed = (ScorpionRed[]) mummyFight(updateWhite, updateSkRed);

        //Red vs Red
        updateRed = (MummyRed[]) mummyFight(updateRed, updateRed);
        //Red vs Scorpion --> Red win
        updateSk = (Scorpion[]) mummyFight(updateRed, updateSk);
        //Red vs ScorpionRed --> Red win
        updateSkRed = (ScorpionRed[]) mummyFight(updateRed, updateSkRed);

        //Scorpion vs Scorpion
        updateSk = (Scorpion[]) mummyFight(updateSk, updateSk);
        //Scorpion vs ScorpionRed --> Scorpion win
        updateSkRed = (ScorpionRed[]) mummyFight(updateSk, updateSkRed);

        state.setMummyWhite(updateWhite);
        state.setMummyRed(updateRed);
        state.setSk(updateSk);
        state.setSkRed(updateSkRed);
        return state;
    }

    private static Mummy[] mummyFight(Mummy[] mummyType1, Mummy[] mummyType2) {//First type win
        Mummy[] mm1 = mummyType1;
        Mummy[] mm2 = mummyType2;
        if ((mm1.length == 0) || (mm2.length == 0)) { //No conflict for void arrays
            return mm2;
        }
        if (mm1.getClass() != mm2.getClass()) {
            for (int i = 0; i < mm1.length; i++) {
                for (int j = 0; j < mm2.length; j++) {
                    if (mm1[i].samePlace(mm2[j])) { //Delete second type
                        mm2 = deleteMummy(mm2, j);
                    }
                }
            }
        } else {
            for (int i = 0; i < mm1.length; i++) {
                for (int j = i + 1; j < mm2.length; j++) {
                    if (mm1[i].samePlace(mm2[j])) { //Delete second type
                        mm2 = deleteMummy(mm2, j);
                    }
                }
            }
        }
        return mm2;
    }

    private static Mummy[] deleteMummy(Mummy[] mummy, int i) {

        for (int j = i; j < mummy.length - 1; j++) {
            mummy[j] = mummy[j + 1];
        }
        Class invoker = mummy[i].getClass();
        Mummy[] tam = null;
//        for (int j = 0; j < tam.length; j++) {
//            tam[j] = mummy[j];
//        }
        if (invoker.equals(MummyWhite.class)) {
            tam = new MummyWhite[mummy.length - 1];
            for (int j = 0; j < mummy.length - 1; j++) {
                tam[j] = new MummyWhite(mummy[j].getX(), mummy[j].getY());
            }
        } else if (invoker.equals(MummyRed.class)) {
            tam = new MummyRed[mummy.length - 1];
            for (int j = 0; j < mummy.length - 1; j++) {
                tam[j] = new MummyRed(mummy[j].getX(), mummy[j].getY());
            }
        } else if (invoker.equals(Scorpion.class)) {
            tam = new Scorpion[mummy.length - 1];
            for (int j = 0; j < mummy.length - 1; j++) {
                tam[j] = new Scorpion(mummy[j].getX(), mummy[j].getY());
            }
        } else if (invoker.equals(ScorpionRed.class)) {
            tam = new ScorpionRed[mummy.length - 1];
            for (int j = 0; j < mummy.length - 1; j++) {
                tam[j] = new ScorpionRed(mummy[j].getX(), mummy[j].getY());
            }
        }
        return tam;
    }

    private static boolean fillMaze(State state, short stateSide, int position) {
        int side = (stateSide + 1) % 2; //Change side
        state.setStep((short) (state.getStep() + 1)); //step++
        for (int i = 1; i < state.getMaze()[state.getHuman().getX()][state.getHuman().getY()].length; i++) {//adding marker
            if (state.getMaze()[state.getHuman().getX()][state.getHuman().getY()][i] < 1) {//check if can add marker
                state.getMaze()[state.getHuman().getX()][state.getHuman().getY()][i] = state.getStep(); //add marker to maze
                break;
            }
        }
        StaticState staticState = new StaticState(state);
        if (!staticState.existing()) {
            MainProcess.stateSideCount[side]++; //Increase count 
            MainProcess.mang[side][MainProcess.stateSideCount[side]] = state; //add new State to the other side
            MainProcess.getExistedState().add(staticState); //add new state to existing States
            return true;
        } else {
            return false;
        }
    }

    private boolean eligibleHumanMove(short[][][] maze, int x, int y, int newX, int newY) {
        if (Mummy.eligibleLivingThingsMove(maze, x, y, newX, newY)) {
            return !(LivingThings.digit(maze[newX][newY][0], 5) == 1);
        } else {
            return false;
        }
    }
}
