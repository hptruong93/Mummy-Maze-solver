package interfc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import vda8888.interfc.MainFrame;

public class WallEdit implements ActionListener {

    private short[][][] maze;
    private JButton[] wall;
    private int x, y;

    public WallEdit(short[][][] maze, JButton[] wall, int x, int y) {
        this.maze = MainFrame.getSState().getMaze();
        this.wall = wall;
        this.x = x;
        this.y = y;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        int invoker = 0;
        for (int i = 0; i < this.wall.length; i++) {
            if (button == this.wall[i]) {
                invoker = i;
                break;
            }
        }
        int xButton = 0, yButton = 0, relativePosition = 0;
        String side = "";

        //Find x, y & side
        for (int i = 0; i < this.x + 1; i++) {
            if ((i + 1) * (2 * this.y + 1) > invoker) {
                relativePosition = invoker - i * (2 * y + 1);
                if (i < this.x) {
                    xButton = i;
                } else {
                    xButton = i - 1;
                    side = "down";
                }
                if (relativePosition >= this.y) {
                    yButton = (relativePosition - this.y);
                    if (yButton == this.y) {
                        yButton--;
                        side = "right";
                    }
                } else {
                    yButton = relativePosition;
                }
                break;
            }
        }
        if (side.equals("")) {
            if (relativePosition < this.y) {
                side = "up";
            } else {
                side = "left";
            }
        }
        //Edit the unit
        int editedValue = 0;
        switch (side) {
            case "left": {
                int editValue = (SetupOption.digit(maze[xButton][yButton][0], 1) + 1) % 2;
                editedValue = 2*editValue;
                maze[xButton][yButton][0] = SetupOption.editDigit(maze[xButton][yButton][0], 1, editValue);
                SetupOption.editButton(xButton, yButton, side, editValue);
                try {
                    maze[xButton][yButton - 1][0] = SetupOption.editDigit(maze[xButton][yButton - 1][0], 3, editValue);
                    SetupOption.editButton(xButton, yButton - 1, "right", editValue);
                } catch (ArrayIndexOutOfBoundsException er) {
                }
                break;
            }
            case "down": {
                int editValue = (SetupOption.digit(maze[xButton][yButton][0], 2) + 1) % 2;
                editedValue = 2*editValue;
                maze[xButton][yButton][0] = SetupOption.editDigit(maze[xButton][yButton][0], 2, editValue);
                SetupOption.editButton(xButton, yButton, side, editValue);
                try {
                    SetupOption.editButton(xButton + 1, yButton, "up", editValue);
                    maze[xButton + 1][yButton][0] = SetupOption.editDigit(maze[xButton + 1][yButton][0], 4, editValue);
                } catch (ArrayIndexOutOfBoundsException er) {
                }
                break;
            }
            case "right": {
                int editValue = (SetupOption.digit(maze[xButton][yButton][0], 3) + 1) % 2;
                editedValue = 2*editValue;
                maze[xButton][yButton][0] = SetupOption.editDigit(maze[xButton][yButton][0], 3, editValue);
                SetupOption.editButton(xButton, yButton, side, editValue);
                try {
                    SetupOption.editButton(xButton, yButton + 1, "left", editValue);
                    maze[xButton][yButton + 1][0] = SetupOption.editDigit(maze[xButton][yButton + 1][0], 1, editValue);
                } catch (ArrayIndexOutOfBoundsException er) {
                }
                break;
            }
            case "up": {
                int editValue = (SetupOption.digit(maze[xButton][yButton][0], 4) + 1) % 2;
                editedValue = 2*editValue;
                maze[xButton][yButton][0] = SetupOption.editDigit(maze[xButton][yButton][0], 4, editValue);
                SetupOption.editButton(xButton, yButton, side, editValue);
                try {
                    SetupOption.editButton(xButton - 1, yButton, "down", editValue);
                    maze[xButton - 1][yButton][0] = SetupOption.editDigit(maze[xButton - 1][yButton][0], 2, editValue);
                } catch (ArrayIndexOutOfBoundsException er) {
                }
                break;
            }
        }
        String[] sides = {"left","down","right","up"};
        for (int i = 0; i < MainFrame.getKeyCount(); i++) {
            int xWall = MainFrame.getSState().getKey()[i].getxWall();
            int yWall = MainFrame.getSState().getKey()[i].getyWall();
            if ((xWall == xButton) && 
                    (yWall == yButton) && 
                    (sides[MainFrame.getSState().getKey()[i].getSide()-1].equals(side))) {
                SetupOption.editButton(xWall, yWall, side, editedValue);
            }
        }
        MainFrame.getSState().setMaze(maze);
    }
}
