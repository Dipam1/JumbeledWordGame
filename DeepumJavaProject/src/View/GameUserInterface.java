/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.sendAction;
import DatabaseConnection.DBConnection;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 *
 * @author deepu
 */
public class GameUserInterface implements Runnable {

    @Override
    public void run() {
        JFrame frame = new JFrame("Ask and Tell");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setPreferredSize(new Dimension(800, 400));
        frameComponents(frame.getContentPane(), frame);
        frame.pack();
        frame.setVisible(true);

    }

    public static void frameComponents(Container container, JFrame aframe) {

        container.setLayout(new GridLayout(5, 2));
        JLabel title = new JLabel("GUESS THE WORD GAME");
        title.setFont(new Font("Serif", Font.BOLD, 24));
        title.setForeground(Color.BLUE);
        container.add(title);
        JLabel points = new JLabel("Points: " + getScoreOfUser());
        container.add(points);
        container.add(new JLabel("Your Word is:"));
        String word = "--", meaning = "";
        try {
            while (!word.matches("[a-zA-Z]+")) {
                ResultSet rs = getRow();
                rs.next();
                word = rs.getString("word");
                meaning = "<html>" + rs.getString("definition") + "</html>";
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        container.add(new JLabel(jumble(word)));
        container.add(new JLabel("It means:"));
        container.add(new JLabel(meaning));
        container.add(new JLabel("Enter Your Guess"));
        JTextField answer = new JTextField();
        container.add(answer);
        container.add(new JLabel());
        JButton send = new JButton("Send");
        container.add(send);
        send.addActionListener(new sendAction(word, answer, points, container, aframe));
    }

    public static ResultSet getRow() throws SQLException {
        Random random = new Random();
        Connection con = DBConnection.getConnection();
        int a = random.nextInt(38604) + 1;
        ResultSet rs = con.createStatement().executeQuery("SELECT * FROM entries where SN=" + a);
        return rs;
    }

    public static String getScoreOfUser() {
        try {
            Connection con = DBConnection.getConnection();
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM scores ORDER BY id DESC LIMIT 1");
            rs.next();
            String sendScore = "" + rs.getInt("Score") + "/" + rs.getInt("Total_Played");
            return sendScore;
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return null;
    }

    public static String jumble(String word) {
        ArrayList<Character> chars = new ArrayList<Character>(word.length());
        for (char c : word.toCharArray()) {
            chars.add(c);
        }
        Collections.shuffle(chars);
        char[] shuffled = new char[chars.size()];
        for (int i = 0; i < shuffled.length; i++) {
            shuffled[i] = chars.get(i);
        }
        String shuffledWord = new String(shuffled);
        return shuffledWord;
    }

}
