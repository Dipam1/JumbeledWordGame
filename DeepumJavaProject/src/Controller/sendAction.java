/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DatabaseConnection.DBConnection;
import View.GameUserInterface;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;

/**
 *
 * @author deepu
 */
public class sendAction implements ActionListener {

    private String word;
    private JTextField answer;
    private JFrame frame;
    private JLabel points;
    private Container container;

    public sendAction(String word, JTextField answer, JLabel points, Container container, JFrame frame) {
        this.container = container;
        this.frame = frame;
        this.word = word;
        this.answer = answer;
        this.points = points;
        System.out.println(this.word);
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        String compare = answer.getText();
        container.removeAll();
        try {
            Connection con = DBConnection.getConnection();
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM scores ORDER BY id DESC LIMIT 1");
            rs.next();
            int score = 0;
            if (compare.equals(this.word)) {
                container.setBackground(Color.green);
                score = rs.getInt("Score") + 1;
            } else {
                container.setBackground(Color.red);
                score = rs.getInt("Score");
            }
            int totalPlayed = rs.getInt("Total_Played") + 1;
            PreparedStatement sender = con.prepareStatement("UPDATE scores\n"
                    + "SET Score = ?, Total_Played=?,Ratio=? order by id desc limit 1");
            sender.setInt(1, score);
            sender.setInt(2, totalPlayed);
            sender.setDouble(3, (score / (double) totalPlayed));
            container.removeAll();
            sender.execute();
            con.close();

        } catch (SQLException ex) {
            System.out.println(ex);
        }
        container.removeAll();
        GameUserInterface.frameComponents(container, frame);
        frame.setVisible(true);

    }
}
