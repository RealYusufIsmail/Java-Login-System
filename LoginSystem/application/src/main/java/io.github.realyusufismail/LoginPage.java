/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2021, YusufIsmail
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its
 *    contributors may be used to endorse or promote products derived from
 *    this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */

package io.github.realyusufismail;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

import static java.awt.Font.ITALIC;

public class LoginPage implements ActionListener {

    JFrame frame = new JFrame();
    JButton loginButton = new JButton("Login");
    JButton resetButton = new JButton("Reset");
    JButton registerButton = new JButton("Register");
    JTextField userNameField = new JTextField();
    JPasswordField userPasswordField = new JPasswordField();
    JLabel userNameLabel = new JLabel("User name");
    JLabel userPasswordLabel = new JLabel("Password");
    JLabel MessageLabel = new JLabel();
    HashMap<String, String> loginInfo;

    LoginPage(HashMap<String, String> loginInfoOriginal) {
        loginInfo = loginInfoOriginal;


        userNameLabel.setBounds(50,100,75,25);
        userPasswordLabel.setBounds(50,150,75,25);

        MessageLabel.setBounds(125,300,250,35);
        MessageLabel.setFont(new Font(null, ITALIC, 20));

        userNameField.setBounds(125,100,200,25);
        userPasswordField.setBounds(125,150,200,25);

        registerButton.setBounds(175,240,100,25);
        registerButton.setFocusable(false);
        registerButton.addActionListener(this);

        loginButton.setBounds(225,200,100,25);
        loginButton.setFocusable(false);
        loginButton.addActionListener(this);

        resetButton.setBounds(125,200,100,25);
        resetButton.setFocusable(false);
        resetButton.addActionListener(this);


        frame.add(userNameLabel);
        frame.add(userPasswordLabel);
        frame.add(MessageLabel);
        frame.add(userNameField);
        frame.add(userPasswordField);
        frame.add(loginButton);
        frame.add(resetButton);
        frame.add(registerButton);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(420,420);
        frame.setLayout(null);
        frame.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == resetButton) {
            userNameField.setText("");
            userPasswordField.setText("");
        }

        if(e.getSource() == loginButton) {

            String userName = userNameField.getText();
            String password = String.valueOf(userPasswordField.getPassword());


            if(loginInfo.containsKey(userName)) {
                if(loginInfo.get(userName).equals(password)) {
                    MessageLabel.setForeground(Color.CYAN);
                    MessageLabel.setText("Login successful");
                    frame.dispose();
                    WelcomePage welcomePage = new WelcomePage(userName);
                } else {
                    MessageLabel.setForeground(Color.RED);
                    MessageLabel.setText("password is incorrect");
                }
            }
            else {
                MessageLabel.setForeground(Color.RED);
                MessageLabel.setText("User name is incorrect");
            }
        }
        if(e.getSource() == resetButton) {
            RegisterPage registerPage = new RegisterPage();
        }
    }
}
