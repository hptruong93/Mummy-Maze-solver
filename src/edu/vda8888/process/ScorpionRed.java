package process;

public class ScorpionRed extends Mummy{
    
    public ScorpionRed(int x, int y) {
        super(x,y);
    }
    
    protected ScorpionRed scorpionMove(short[][][] maze, Human man) {//Horizontal then vertical
        // This method was written using a different logic than the current one
        ScorpionRed tam = new ScorpionRed(this.getX(), this.getY());
        
        if (tam.samePlace(man)) {
            return tam;
        } else {
            Mummy.setAttempt(0);
            Mummy.setCount(0);
            while ((Mummy.getAttempt() < 5) && (Mummy.getCount() < 1)) {
                while (this.getX() != man.getX()) {
                    tam = tam.mummyMoveVertical(maze, man);
                    if (!(Mummy.getCount() < 1)) {
                        return tam;
                    }
                    if (Mummy.getAttempt() > 4) break;
                }
                if (tam.samePlace(man)) {
                    return tam;
                }
                tam = tam.mummyMoveHorizontal(maze, man);
                if ((tam.samePlace(man)) || (!(Mummy.getCount() < 1))) {
                    return tam;
                }
            }
            return tam;
        }
    }

    @Override //Same thing but only 1 move per turn
    protected ScorpionRed mummyMoveVertical(short[][][] maze, Human man) {
        if (Mummy.getCount() == 1) {
            return this;
        }
        { //Horizontal move
            int newX;
            newX = this.getX() + sign(man.getX() - this.getX());
            int newY = this.getY();
            if (eligibleLivingThingsMove(maze, this.getX(), this.getY(), newX, newY)) {
                this.move(newX, newY);
                Mummy.setCount(Mummy.getCount()+1);
                if (Mummy.getCount() == 1) {
                    return this;
                }
            } else {
                Mummy.setAttempt(Mummy.getAttempt()+1);
            }
        }
        return this;
    }

    @Override //Same thing but only 1 move per turn
    protected ScorpionRed mummyMoveHorizontal(short[][][] maze, Human man) {
        if (Mummy.getCount() == 1) { return this; }
        { //Vertical move
            int newX = this.getX();
            int newY = this.getY() + sign(man.getY() - this.getY());
            if (eligibleLivingThingsMove(maze,this.getX(),this.getY(),newX, newY)) {
                this.move(newX, newY);
                Mummy.setCount(Mummy.getCount()+1);
                if (Mummy.getCount() == 1) { return this;}
            } else {
                Mummy.setAttempt(Mummy.getAttempt()+1);
            }
        }
        return this;
    }
    
}
