import java.awt.*;

public class Score extends Rectangle {

    static int GAME_WIDTH;
    static int GAME_HEIGHT;
    int player1;
    int player2;


    Score(int GAME_WIDTH, int GAME_HEIGHT){
        Score.GAME_WIDTH = GAME_WIDTH;
        Score.GAME_HEIGHT = GAME_HEIGHT;
    }

    public void draw(Graphics g){
        g.setColor(Color.GREEN);
        g.setFont(new Font("Papyrus",Font.PLAIN, 60));

        g.drawLine( 500, 0,  500, GAME_HEIGHT);

        g.drawString(String.valueOf(player1/10) + String.valueOf(player1%10),(500)-85, 50);
        g.drawString(String.valueOf(player2/10) + String.valueOf(player2%10),(500)+20, 50);
    }
}
