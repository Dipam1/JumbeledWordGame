/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DatabaseConnection.DBConnection;
import View.GameUserInterface;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;

/**
 *
 * @author deepu
 */
public class GetNameController implements ActionListener {

    JTextArea getName;
    JFrame frame;

    public GetNameController(JTextArea getName, JFrame frame) {
        this.getName = getName;
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {

        try {
            Connection con = DBConnection.getConnection();
            String name = this.getName.getText();
            PreparedStatement sender = con.prepareStatement("INSERT INTO `scores`(`Name`, `Score`,Total_Played,Ratio) VALUES ('" + name + "','0','0','0')");
            sender.execute();
            con.close();

            GameUserInterface ui = new GameUserInterface();
            SwingUtilities.invokeLater(ui);

            this.frame.setVisible(false);
            this.frame.dispose();

        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

}
