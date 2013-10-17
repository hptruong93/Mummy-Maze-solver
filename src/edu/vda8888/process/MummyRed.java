/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package process;

/**
 *
 * @author HP
 */
public class MummyRed extends Mummy{
    
    public MummyRed(int x, int y) {
        super(x,y);
    }
    
    protected MummyRed mummyRedMove(short [][][]maze,Human man) {// Vertical then horizontal
        
        MummyRed tam = new MummyRed(this.getX(),this.getY());
        
        if (tam.samePlace(man)) {
            return tam;
        } else {
            Mummy.setAttempt(0);
            Mummy.setCount(0);
            while ((Mummy.getAttempt() < 5) && (Mummy.getCount() < 1)) {
                while (tam.getX() != man.getX()) {
                    tam = (MummyRed) tam.mummyMoveVertical(maze, man);
                    if (!(Mummy.getCount() < 1)) {
                        return tam;
                    }
                    if (Mummy.getAttempt() > 4) break;
                }
                if (tam.samePlace(man)) {
                    return tam;
                }
                tam = (MummyRed) tam.mummyMoveHorizontal(maze, man);
                if ((tam.samePlace(man)) || (!(Mummy.getCount() < 1))) {
                    return tam;
                }
            } 
            return tam;
        }
    }
}
