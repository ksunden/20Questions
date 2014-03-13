import java.awt.*;
import javax.swing.*;

public class AppFrame extends JFrame
{
    private JLabel text;
    private JTextField input;
    private JButton send;

    public AppFrame()
    {
        super("20 Questions");
        init();
    }
    
    private void init()
    {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        this.setSize(370, 200);

        JPanel panel = new JPanel();
        text = new JLabel("Received messages go here");
        input = new JTextField(30);
        send = new JButton("Send Response");

        panel.add(text);
        panel.add(input);
        panel.add(send);

        this.add(Box.createVerticalGlue());
        this.add(panel);
        this.add(Box.createVerticalGlue());
    }

    public static void main(String[] args)
    {
        AppFrame frame = new AppFrame();
        frame.setVisible(true);
    }
}
