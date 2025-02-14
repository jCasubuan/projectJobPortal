/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectjobportal.accountSetup;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.LineBorder;

/**
 *
 * @author jcasu
 */

public class WelcomePage extends JFrame implements ActionListener{
    private JLabel  lblLogo, lblJobVista, lblTagline;
    private JButton btnSignIn, btnSignUp;
    private ImageIcon finalMainIcon;
    
    public WelcomePage() {
         
        setTitle("JobVista");       
        setSize(500, 600);
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
        try {
            ImageIcon icon = new ImageIcon("mainIcon.png");
            setIconImage(icon.getImage());
        } catch (Exception e) {
            System.err.println("Error loading icon: " + e.getMessage());
        }
        
      
        ImageIcon icon = new ImageIcon("mainIcon.png");
        finalMainIcon = new ImageIcon(icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
        lblLogo = new JLabel(finalMainIcon);
        lblLogo.setBounds(190, 80, 100, 100);
        add(lblLogo);
        
        lblJobVista = new JLabel("JobVista");
        lblJobVista.setFont(new Font("Arial", Font.CENTER_BASELINE, 45));
        lblJobVista.setBounds(147, 200, 500, 40);
        add(lblJobVista);

        // Tagline
        lblTagline = new JLabel("\"Your gateway to Career Opportunities\"", SwingConstants.CENTER);
        lblTagline.setFont(new Font("Arial", Font.ITALIC, 16));
        lblTagline.setForeground(Color.DARK_GRAY);  // Subtle color for tagline
        lblTagline.setBounds(50, 250, 400, 30);  // Just below the welcome text
        add(lblTagline);
        
        btnSignIn = new JButton("Sign in");
        btnSignIn.setBorder(new LineBorder(new Color(0, 119, 212), 2, true));
        btnSignIn.setBackground(new Color(0, 119, 212));
        btnSignIn.setForeground(Color.white);
        btnSignIn.setFont(new Font("Arial", Font.BOLD, 16));
        btnSignIn.setBounds(150, 350, 200, 40);  // Centered horizontally
        btnSignIn.setBorderPainted(false);
        btnSignIn.setFocusPainted(false);
        btnSignIn.setOpaque(true);
        add(btnSignIn);
        
        btnSignUp = new JButton("Sign up");
        btnSignUp.setBorder(new LineBorder(new Color(0, 119, 212), 2, true));
        btnSignUp.setBackground(new Color(0, 119, 212));
        btnSignUp.setForeground(Color.white);
        btnSignUp.setFont(new Font("Arial", Font.BOLD, 16));
        btnSignUp.setBounds(150, 410, 200, 40);  // Slightly below Sign In button
        btnSignUp.setBorderPainted(false);
        btnSignUp.setFocusPainted(false);
        btnSignUp.setOpaque(true);
        add(btnSignUp);
        
        btnSignIn.addActionListener(this);
        btnSignUp.addActionListener(this);
                
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSignIn){
            new SignInPage().setVisible(true);
            dispose();
        }
        else if (e.getSource()  == btnSignUp){
            new SignUpPage().setVisible(true);
            dispose();
        }
        
    }
    
}
