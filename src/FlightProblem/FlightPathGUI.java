package FlightProblem;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class FlightPathGUI extends JFrame {
    int size;
    Point[][] mat;
    Main.Node[][] flightMat;
    List<String> allPaths;
    JTextArea textArea;

    public FlightPathGUI(int size, Main.Node[][]flightMat, List<String> allPaths ) {
        this.size = size;
        mat = new Point[size][size];
        this.flightMat = flightMat;
        this.allPaths = allPaths;
        init();
        repaint();
    }

    public void init() {
        this.setSize(500, 500); // setting the size of the wind
        this.setVisible(true); // setting the visibility.
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // when we close the window the Thread will die.
        this.setLocationRelativeTo(null); // setting the window in the center of the screen.
        this.getContentPane().setBackground(Color.YELLOW);
        int location = 150;
        int offSetX;
        int offSetY = 0;
        for (int i = 0; i < size; i++) {
            if (i > 0)
                offSetY += 50;
            offSetX = 0;
            for (int j = 0; j < size; j++) {
                mat[i][j] = new Point(location + offSetX, location + offSetY);
                offSetX += 50;
            }
        }
        String ans="";
        // setting all the paths on the GUI:
        for (int i=0;i<allPaths.size();i++){
            if(i!=0 && i%2==0)
                ans+="\n";
            ans+=allPaths.get(i)+" ";
        }
        System.out.println(ans);
        textArea = new JTextArea(ans);
        textArea.setFont(new Font("Serif", Font.ITALIC, 10));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setOpaque(false);
        textArea.setVisible(true);
        this.add(textArea);
        textArea.setLineWrap(false);


    }

    public void paint(Graphics g) { // drawing points and lines between them.
        super.paint(g);
        Point prev;
        for (int i = 0; i < size; i++) { // drawing points and horizontal lines
            prev = null;
            for (int j = 0; j < size; j++) {

                if (prev != null) {
                    g.drawLine((int) mat[i][j].getX(), (int) mat[i][j].getY(), (int) prev.getX(), (int) prev.getY());
                }

                g.fillOval((int) mat[i][j].getX(), (int) mat[i][j].getY(), 10, 10);
                prev = mat[i][j];
            }
        }
        for (int i = 0; i < size; i++) { // drawing vertical lines
            prev = null;
            for (int j = 0; j < size; j++) {
                if (prev != null) {
                    g.drawLine((int) mat[j][i].getX(), (int) mat[j][i].getY(), (int) prev.getX(), (int) prev.getY());
                }
                prev = mat[j][i];
            }
        }

        // priniting the shortest paths
        g.setColor(Color.white);
        int x=0,y=0,counter=0;
        for (int i = 0; i < size; i++){
            counter=0;
            for (int j = 0; j < size; j++){
                if(flightMat[i][j].isInShortPath && counter>0 && flightMat[i][j].pred.contains("R")){
                    g.drawLine((int) mat[i][j].getX(), (int) mat[i][j].getY(), x,y);
                    counter=0;
                }
                if(flightMat[i][j].isInShortPath){
                    x= (int) mat[i][j].getX();
                    y= (int) mat[i][j].getY();
                    counter++;
                }
            }
        }

        for (int i = 0; i < size; i++){
            counter=0;
            for (int j = 0; j < size; j++){
                if(flightMat[j][i].isInShortPath && counter>0 && flightMat[j][i].pred.contains("D")){
                    g.drawLine((int) mat[j][i].getX(), (int) mat[j][i].getY(), x,y);
                    counter=0;
                }
                if(flightMat[i][j].isInShortPath){
                    x= (int) mat[j][i].getX();
                    y= (int) mat[j][i].getY();
                    counter++;
                }
            }
        }
    }


}


