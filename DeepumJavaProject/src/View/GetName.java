/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.GetNameController;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.*;

/**
 *
 * @author deepu
 */
public class GetName implements Runnable {

    @Override
    public void run() {
        JFrame frame = new JFrame("Ask and Tell");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(300, 150));
        frameComponents(frame.getContentPane(), frame);
        frame.pack();
        frame.setVisible(true);

    }

    public static void frameComponents(Container container, JFrame frame) {
        container.setLayout(new GridLayout(2, 2));
        container.add(new JLabel("Name: "));
        JTextArea getName = new JTextArea();
        container.add(getName);
        container.add(new JLabel(""));
        JButton send = new JButton("Send");
        container.add(send);

        send.addActionListener(new GetNameController(getName, frame));
    }

}
