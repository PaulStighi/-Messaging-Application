package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MessagePane extends JPanel implements MessageListener {

    private final ChatClient client;
    private final String username;

    private DefaultListModel<String> listModel = new DefaultListModel<>();
    private JList<String> messageList = new JList<>(listModel);
    private JTextField inputField = new JTextField();
   //constructor
    public MessagePane(ChatClient client, String username) {
        this.client = client;
        this.username = username;

        client.addMessageListener(this);
// layout for the message
        setLayout(new BorderLayout());
        add(new JScrollPane(messageList), BorderLayout.CENTER);
        add(inputField, BorderLayout.SOUTH);

        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String text = inputField.getText();
                    client.msg(username, text);
                    listModel.addElement("You: " + text);
                    inputField.setText("");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    @Override
    //displays the message and who sends the message
    
    public void onMessage(String fromUsername, String msgBody) {
        if (username.equalsIgnoreCase(fromUsername)) {
            String line = fromUsername + ": " + msgBody;
            listModel.addElement(line);
        }
    }
}