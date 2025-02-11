/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectjobportal.accountInterface;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import projectjobportal.accountSetup.SignInPage;

/**
 *
 * @author jcasu
 */
public class ClientInterface extends JFrame implements ActionListener{
    private JLabel hdrlogo, hdrJobVista, lblHome, lblNew, lblCopyright, lblPrivacyCenter, lblTerms, lblSearchIC;
    private JPanel hdrPanel, homePanel, profilePanel, cards, mailPanel, notifPanel;   
    private ImageIcon logoIC, finalLogoIC, notifIC, finalNotifIC, profileIC, finalProfileIC, mailIC, finalMailIC, searchIC, finalSearchIC;
    private JButton btnMail, btnNotif, btnProfile, btnLogOut;
    private JTextField txtSearch;
    private CardLayout cardLayout;
    private boolean isMailOpen = false, isNotifOpen = false; 
    
    public ClientInterface() {
        setSize(1500, 780);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(1500, 780));
        add(layeredPane, BorderLayout.CENTER);
        
        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);
        cards.setBounds(0, 0, 1500, 780);
        layeredPane.add(cards, JLayeredPane.DEFAULT_LAYER);
        
        homePanel = createHomePanelSetup();
        profilePanel = createProfilePanel();
        
        cards.add(homePanel, "HomeSetup");
        cards.add(profilePanel, "ProfileSetup");       
        
        createMailPanel(layeredPane);
        createNotifPanel(layeredPane);

        addGlobalMouseListener(layeredPane);
        
        setVisible(true);
        
        }
    
        private void addGlobalMouseListener(JLayeredPane layeredPane) {
        layeredPane.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Check if click is outside mail and notif panels
                if (mailPanel != null && notifPanel != null) {
                    Point click = e.getPoint();
                    if (!mailPanel.getBounds().contains(click) && 
                        !btnMail.getBounds().contains(SwingUtilities.convertPoint(layeredPane, click, btnMail)) &&
                        !notifPanel.getBounds().contains(click) && 
                        !btnNotif.getBounds().contains(SwingUtilities.convertPoint(layeredPane, click, btnNotif))) {
                        
                        mailPanel.setVisible(false);
                        notifPanel.setVisible(false);
                        isMailOpen = false;
                        isNotifOpen = false;
                    }
                }
            }
        });
    }
    
        private JPanel createHomePanelSetup(){
            JPanel panel = new JPanel(null);
        
        hdrPanel = new JPanel();
        hdrPanel.setBorder(new LineBorder(Color.GRAY, 1));
        hdrPanel.setBounds(0, 0, 1500, 76);
        hdrPanel.setLayout(null);
        panel.add(hdrPanel);
        
        logoIC = new ImageIcon("mainIcon.png");
        finalLogoIC = new ImageIcon(logoIC.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));            
        hdrlogo = new JLabel(finalLogoIC);
        hdrlogo.setBounds(15, 15, 40, 40);
        hdrPanel.add(hdrlogo);
        
        hdrJobVista = new JLabel("JobVista");
        hdrJobVista.setBounds(50, 20, 125, 35);
        hdrJobVista.setFont(new Font("Arial", Font.CENTER_BASELINE, 25));
        hdrJobVista.setHorizontalAlignment(SwingConstants.CENTER);
        hdrPanel.add(hdrJobVista);
        
        lblHome = new JLabel("Home");
        lblHome.setBounds(190, 33, 50, 15);
        lblHome.setFont(new Font("Arial", Font.PLAIN, 15));
        lblHome.setHorizontalAlignment(SwingConstants.CENTER);
        hdrPanel.add(lblHome);
        
        lblNew = new JLabel("What's new?");
        lblNew.setBounds(255, 33, 90, 15);
        lblNew.setFont(new Font("Arial", Font.PLAIN, 15));
        lblNew.setHorizontalAlignment(SwingConstants.CENTER);
        hdrPanel.add(lblNew);
        
        searchIC = new ImageIcon("searchIcon.png");
        finalSearchIC = new ImageIcon(searchIC.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        lblSearchIC = new JLabel(finalSearchIC);
        lblSearchIC.setBounds(550, 29, 20, 20);
        hdrPanel.add(lblSearchIC);
        
        txtSearch = new JTextField("Search for jobs...");
        txtSearch.setBounds(580, 18, 400, 40);
        txtSearch.setFont(new Font("Arial", Font.PLAIN, 14));
        txtSearch.setForeground(Color.GRAY);
        txtSearch.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
        txtSearch.setBorder(BorderFactory.createCompoundBorder(txtSearch.getBorder(), new EmptyBorder(5, 10, 5, 10)));
        hdrPanel.add(txtSearch);
        
        // Placeholder Effect
        txtSearch.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtSearch.getText().equals("Search for jobs...")) {
                    txtSearch.setText("");
                    txtSearch.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtSearch.getText().trim().isEmpty()) {
                    txtSearch.setText("Search for jobs...");
                    txtSearch.setForeground(Color.GRAY);
                }
            }
        });

        txtSearch.addActionListener(e -> JOptionPane.showMessageDialog(null, "Searching for: " + txtSearch.getText()));
        
        mailIC = new ImageIcon("mailIcon.jpg");
        finalMailIC = new ImageIcon(mailIC.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));              
        btnMail = new JButton(finalMailIC);
        btnMail.setBounds(1240, 28, 30, 30);
        btnMail.setBorderPainted(false);
        btnMail.setFocusPainted(false);
        hdrPanel.add(btnMail);

        notifIC = new ImageIcon("notifIcon.png");
        finalNotifIC = new ImageIcon(notifIC.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));   
        btnNotif = new JButton(finalNotifIC);
        btnNotif.setBounds(1320, 30, 25, 25);
        btnNotif.setBorderPainted(false);
        btnNotif.setFocusPainted(false);
        hdrPanel.add(btnNotif);

        profileIC = new ImageIcon("profileIcon.jpg");
        finalProfileIC = new ImageIcon(profileIC.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));     
        btnProfile = new JButton(finalProfileIC);
        btnProfile.setBounds(1390, 29, 25, 25);
        btnProfile.setBorderPainted(false);
        btnProfile.setFocusPainted(false);
        hdrPanel.add(btnProfile);
        
        
        lblCopyright = new JLabel("© 2025 JobVista");
        lblCopyright.setFont(new Font("Arial", Font.PLAIN, 12));
        lblCopyright.setBounds(30, 700, 120, 12);
        panel.add(lblCopyright);
        
        lblPrivacyCenter = new JLabel("Privacy Policy");
        lblPrivacyCenter.setFont(new Font("Arial", Font.PLAIN, 12));
        lblPrivacyCenter.setBounds(140, 700, 120, 12);
        panel.add(lblPrivacyCenter);
        
        lblTerms = new JLabel("Terms");
        lblTerms.setFont(new Font("Arial", Font.PLAIN, 12));
        lblTerms.setBounds(245, 700, 120, 12);
        panel.add(lblTerms);

        btnMail.addActionListener(this);
        btnNotif.addActionListener(this);
        btnProfile.addActionListener(this);
        
        return panel;
    }   
        
        private void createMailPanel(JLayeredPane layeredPane) {
        mailPanel = new JPanel();
        mailPanel.setBounds(1090, 55, 320, 550); // 📌 Position under Mail Button
        mailPanel.setBackground(Color.WHITE);
        mailPanel.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
        mailPanel.setLayout(new BorderLayout());
        mailPanel.setVisible(false);  // 🔹 Initially Hidden
        layeredPane.add(mailPanel, JLayeredPane.POPUP_LAYER);

        JLabel lblMailHeader = new JLabel("Inbox", SwingConstants.CENTER);
        lblMailHeader.setFont(new Font("Arial", Font.BOLD, 14));
        lblMailHeader.setOpaque(true);
        lblMailHeader.setBackground(Color.WHITE);
        mailPanel.add(lblMailHeader, BorderLayout.NORTH);

        JTextArea txtMailContent = new JTextArea("📩 No new messages...");
        txtMailContent.setEditable(false);
        txtMailContent.setFont(new Font("Arial", Font.PLAIN, 12));
        mailPanel.add(txtMailContent, BorderLayout.CENTER);
    }
      
        private void createNotifPanel(JLayeredPane layeredPane) {
        notifPanel = new JPanel();
        notifPanel.setBounds(1090, 55, 320, 550); // 📌 Position under Notification Button
        notifPanel.setBackground(Color.WHITE);
        notifPanel.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
        notifPanel.setLayout(new BorderLayout());
        notifPanel.setVisible(false);  // 🔹 Initially Hidden
        layeredPane.add(notifPanel, JLayeredPane.POPUP_LAYER);

        JLabel lblNotifHeader = new JLabel("Notifications", SwingConstants.CENTER);
        lblNotifHeader.setFont(new Font("Arial", Font.BOLD, 14));
        lblNotifHeader.setOpaque(true);
        lblNotifHeader.setBackground(Color.WHITE);
        notifPanel.add(lblNotifHeader, BorderLayout.NORTH);

        JTextArea txtNotifContent = new JTextArea("🔔 No new notifications...");
        txtNotifContent.setEditable(false);
        txtNotifContent.setFont(new Font("Arial", Font.PLAIN, 12));
        notifPanel.add(txtNotifContent, BorderLayout.CENTER);
    }
    
        private JPanel createProfilePanel() {
        JPanel panel = new JPanel(null);
        panel.setBackground(Color.WHITE);

        JButton btnBack = new JButton("Back");
        btnBack.setBackground(Color.WHITE);
        btnBack.setForeground(new Color(0, 119, 212));
        btnBack.setFont(new Font("Arial", Font.BOLD, 12));
        btnBack.setBounds(20, 20, 80, 30);
        btnBack.setFocusPainted(false);
        btnBack.addActionListener(e -> {
            // Close any open panels before switching back to home
            mailPanel.setVisible(false);
            notifPanel.setVisible(false);
            isMailOpen = false;
            isNotifOpen = false;

            // Switch to home panel
            cardLayout.show(cards, "HomeSetup");
        });
        panel.add(btnBack);

        JLabel lblProfile = new JLabel("Profile Page", SwingConstants.CENTER);
        lblProfile.setFont(new Font("Arial", Font.BOLD, 24));
        lblProfile.setBounds(600, 300, 300, 50);
        panel.add(lblProfile);
        
        btnLogOut = new JButton("Log out");
        btnLogOut.setBackground(Color.WHITE);
        btnLogOut.setForeground(new Color(0, 119, 212));
        btnLogOut.setFont(new Font("Arial", Font.BOLD, 12));
        btnLogOut.setBounds(30, 590, 80, 30);
        btnLogOut.setFocusPainted(false);
        panel.add(btnLogOut);
        
        btnLogOut.addActionListener(this);
        
        
        return panel;
    }
   
  
    
    @Override
    public void actionPerformed(ActionEvent e) {
             if (e.getSource() == btnMail) {
            isMailOpen = !isMailOpen;
            mailPanel.setVisible(isMailOpen);
            if (isMailOpen) {
                notifPanel.setVisible(false);
                isNotifOpen = false;
            }
            
        } else if (e.getSource() == btnNotif) {
            isNotifOpen = !isNotifOpen;
            notifPanel.setVisible(isNotifOpen);
            if (isNotifOpen) {
                mailPanel.setVisible(false);
                isMailOpen = false;
            }
        } else if (e.getSource() == btnProfile) {
            
            mailPanel.setVisible(false);
            notifPanel.setVisible(false);
            isMailOpen = false;
            isNotifOpen = false;
        
            cardLayout.show(cards, "ProfileSetup");
        }
        else if (e.getSource() == btnLogOut){
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to log out?", "Log out", JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION){
              new SignInPage().setVisible(true);
                dispose();
            }
            
        }
    }
        
    
    
    
    
    public static void main(String[] args) {
        
        
        SwingUtilities.invokeLater(() -> {
            ClientInterface client = new ClientInterface();
        });
    }

    
    
}

