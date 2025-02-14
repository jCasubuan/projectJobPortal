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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
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
    private JScrollPane scrollPane;
    private boolean isMailOpen = false, isNotifOpen = false; 
    private DefaultListModel<String> notifModel;
    private JList<String> notifList;
    private Map<String, JFrame> openChats = new HashMap<>();
    private int chatCount = 0; // Tracks number of open chats
    private JLayeredPane layeredPane;
    private boolean isMailPanelVisible = false;


    
    public ClientInterface() {
        setSize(1500, 780);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
        try {
            ImageIcon icon = new ImageIcon("mainIcon.png");
            setIconImage(icon.getImage());
        } catch (Exception e) {
            System.err.println("Error loading icon: " + e.getMessage());
        }
        
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
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 75, 1500, 600);
        panel.add(scrollPane);
            
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
        
        txtSearch = new JTextField("Search for job title, keywords, or company");
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
                if (txtSearch.getText().equals("Search for job title, keywords, or company")) {
                    txtSearch.setText("");
                    txtSearch.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtSearch.getText().trim().isEmpty()) {
                    txtSearch.setText("Search for job title, keywords, or company");
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
        
    private JFrame activeChat = null; // Track active chat window

    private void createMailPanel(JLayeredPane layeredPane) {
        mailPanel = new JPanel();
        mailPanel.setBounds(1090, 55, 320, 550);
        mailPanel.setBackground(Color.WHITE);
        mailPanel.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
        mailPanel.setLayout(new BorderLayout());
        mailPanel.setVisible(false);
        layeredPane.add(mailPanel, JLayeredPane.POPUP_LAYER);

        JLabel lblMailHeader = new JLabel("Messages", SwingConstants.CENTER);
        lblMailHeader.setFont(new Font("Arial", Font.BOLD, 14));
        lblMailHeader.setOpaque(true);
        lblMailHeader.setBackground(new Color(240, 240, 240));
        lblMailHeader.setBorder(new LineBorder(Color.GRAY, 1));
        mailPanel.add(lblMailHeader, BorderLayout.NORTH);

        DefaultTableModel model = new DefaultTableModel(new String[]{"Sender", "Message"}, 0);

        // Sample Messages
        model.addRow(new Object[]{"Admin", "Welcome to JobVista!"});
        model.addRow(new Object[]{"HR Team", "Your interview is scheduled for..."});
        model.addRow(new Object[]{"Support", "Please update your profile information."});
        model.addRow(new Object[]{"Recruiter", "We found a job match for you!"});
        model.addRow(new Object[]{"System", "New job listings available!"});

        JTable messageTable = new JTable(model);
        messageTable.setFont(new Font("Arial", Font.PLAIN, 12));
        messageTable.setRowHeight(40);
        messageTable.setShowGrid(false);
        messageTable.setIntercellSpacing(new Dimension(0, 10));
        messageTable.setDefaultEditor(Object.class, null);

        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        leftRenderer.setHorizontalAlignment(SwingConstants.LEFT);
        messageTable.getColumnModel().getColumn(0).setCellRenderer(leftRenderer);

        DefaultTableCellRenderer messageRenderer = new DefaultTableCellRenderer();
        messageRenderer.setHorizontalAlignment(SwingConstants.LEFT);
        messageTable.getColumnModel().getColumn(1).setCellRenderer(messageRenderer);

        JScrollPane scrollPane = new JScrollPane(messageTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        mailPanel.add(scrollPane, BorderLayout.CENTER);

        messageTable.addMouseListener(new MouseAdapter() {
    public void mouseClicked(MouseEvent e) {
        int row = messageTable.getSelectedRow();
        if (row != -1) {
            String sender = messageTable.getValueAt(row, 0).toString();
            String message = messageTable.getValueAt(row, 1).toString();
            openChatPanel(sender, message);
            mailPanel.setVisible(false); // 🔹 Hide mail panel after selecting a message
            }
        }
    });

    }
    
    private void openChatPanel(String sender, String message) {
    // Check if chat is already open - if so, bring to front
    if (openChats.containsKey(sender)) {
        JFrame existingChat = openChats.get(sender);
        existingChat.toFront();
        existingChat.requestFocus();
        return;
    }

    // Handle maximum chat windows (3)
    if (openChats.size() >= 3) {
        // Get the oldest chat and remove it
        String oldestSender = openChats.keySet().iterator().next();
        JFrame oldestChat = openChats.remove(oldestSender);
        oldestChat.dispose();
    }

        // Calculate position for new chat window
        int chatWidth = 350;
        int spacing = 20;
        int offset = 100; // Offset from the right edge of the main window

        // Calculate base position (rightmost chat window)
        int baseX = getX() + getWidth() - offset; // Added offset here
        int chatX = baseX - chatWidth - (openChats.size() * (chatWidth + spacing));
        int chatY = getY() + getHeight() - 500;

        JFrame chatFrame = new JFrame("Chat with " + sender);
        chatFrame.setSize(chatWidth, 470);
        chatFrame.setLocation(chatX, chatY);
        chatFrame.setLayout(new BorderLayout());
        chatFrame.setAlwaysOnTop(true);
        chatFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        try {
            ImageIcon icon = new ImageIcon("mainIcon.png");
            chatFrame.setIconImage(icon.getImage());
        } catch (Exception e) {
            System.err.println("Error loading icon: " + e.getMessage());
        }

        // Chat header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(240, 240, 240));
        headerPanel.setBorder(new LineBorder(Color.GRAY, 1));

        JLabel lblChatHeader = new JLabel("Chat with " + sender, SwingConstants.CENTER);
        lblChatHeader.setFont(new Font("Arial", Font.BOLD, 14));
        lblChatHeader.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        headerPanel.add(lblChatHeader, BorderLayout.CENTER);

        // Close button
        JButton closeButton = new JButton("×");
        closeButton.setFont(new Font("Arial", Font.BOLD, 16));
        closeButton.setFocusPainted(false);
        closeButton.setBorderPainted(false);
        closeButton.setContentAreaFilled(false);
        closeButton.addActionListener(e -> {
            openChats.remove(sender);
            chatFrame.dispose();
            rearrangeChats();
        });
        headerPanel.add(closeButton, BorderLayout.EAST);

        chatFrame.add(headerPanel, BorderLayout.NORTH);

        // Chat messages area
        JPanel chatPanel = new JPanel();
        chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.Y_AXIS));
        chatPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        chatPanel.setBackground(Color.WHITE);

        JScrollPane scrollChat = new JScrollPane(chatPanel);
        scrollChat.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        chatFrame.add(scrollChat, BorderLayout.CENTER);

        // Function to add messages
        Consumer<String[]> addMessage = (data) -> {
            String senderName = data[0];
            String messageText = data[1];
            boolean isSender = senderName.equals("You");

            JPanel messageBubble = new JPanel(new FlowLayout(isSender ? FlowLayout.RIGHT : FlowLayout.LEFT));
            messageBubble.setOpaque(false);

            int preferredWidth = Math.min(200, messageText.length() * 7 + 20);
            JLabel lblMessage = new JLabel("<html><div style='width: " + preferredWidth + "px;'>" + messageText + "</div></html>");
            lblMessage.setFont(new Font("Arial", Font.PLAIN, 13));
            lblMessage.setOpaque(true);
            lblMessage.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));

            if (isSender) {
                lblMessage.setBackground(new Color(0, 132, 255));
                lblMessage.setForeground(Color.WHITE);
            } else {
                lblMessage.setBackground(new Color(240, 240, 240));
                lblMessage.setForeground(Color.BLACK);
            }

            lblMessage.setBorder(BorderFactory.createCompoundBorder(
                new EmptyBorder(2, 2, 2, 2),
                new LineBorder(lblMessage.getBackground(), 8, true)
            ));

            messageBubble.add(lblMessage);
            chatPanel.add(messageBubble);
            chatPanel.revalidate();
            chatPanel.repaint();

            // Auto-scroll to bottom
            SwingUtilities.invokeLater(() -> {
                JScrollBar vertical = scrollChat.getVerticalScrollBar();
                vertical.setValue(vertical.getMaximum());
            });
        };

        // Add initial message
        addMessage.accept(new String[]{sender, message});

        // Reply panel
        JPanel replyPanel = new JPanel(new BorderLayout(5, 0));
        replyPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        replyPanel.setBackground(Color.WHITE);

        JTextField txtReply = new JTextField();
        JButton btnSend = new JButton("Send");
        btnSend.setBackground(new Color(0, 132, 255));
        btnSend.setForeground(Color.WHITE);
        btnSend.setFocusPainted(false);

        btnSend.addActionListener(e -> {
            String reply = txtReply.getText().trim();
            if (!reply.isEmpty()) {
                addMessage.accept(new String[]{"You", reply});
                txtReply.setText("");
                txtReply.requestFocus();
            }
        });

        txtReply.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    btnSend.doClick();
                }
            }
        });

        replyPanel.add(txtReply, BorderLayout.CENTER);
        replyPanel.add(btnSend, BorderLayout.EAST);
        chatFrame.add(replyPanel, BorderLayout.SOUTH);

        // Window listener for cleanup
        chatFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                openChats.remove(sender);
                rearrangeChats();
            }
        });

        // Add to open chats and show
        openChats.put(sender, chatFrame);
        chatFrame.setVisible(true);
    }

    // Rearrange chat windows when one is closed
    private void rearrangeChats() {
        int chatWidth = 350;
        int spacing = 20;
        int offset = 100; // Same offset as in openChatPanel
        int baseX = getX() + getWidth() - offset;
        int index = 0;

        for (Map.Entry<String, JFrame> entry : openChats.entrySet()) {
            JFrame chatFrame = entry.getValue();
            int newX = baseX - chatWidth - (index * (chatWidth + spacing));
            chatFrame.setLocation(newX, chatFrame.getY());
            index++;
        }
    }


    private void closeChatPanel(String sender) {
        JFrame chatFrame = openChats.remove(sender);
        if (chatFrame != null) {
            chatFrame.dispose();
            chatCount--;
        }
    }

      
    private JPanel notificationContainer;
    
    private void createNotifPanel(JLayeredPane layeredPane) {
        notifPanel = new JPanel();
        notifPanel.setBounds(1090, 55, 320, 550);
        notifPanel.setBackground(Color.WHITE);
        notifPanel.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
        notifPanel.setLayout(new BorderLayout());
        notifPanel.setVisible(false);
        layeredPane.add(notifPanel, JLayeredPane.POPUP_LAYER);

        // Header Label
        JLabel lblNotifHeader = new JLabel("Notifications", SwingConstants.CENTER);
        lblNotifHeader.setFont(new Font("Arial", Font.BOLD, 14));
        lblNotifHeader.setOpaque(true);
        lblNotifHeader.setBackground(Color.WHITE);
        notifPanel.add(lblNotifHeader, BorderLayout.NORTH);

        // Initialize notification container
        notificationContainer = new JPanel();
        notificationContainer.setLayout(new BoxLayout(notificationContainer, BoxLayout.Y_AXIS));
        notificationContainer.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(notificationContainer);
        scrollPane.setBorder(null);
        notifPanel.add(scrollPane, BorderLayout.CENTER);

        // Add sample notifications to verify functionality
        addNotification("System Update", "New features available for your dashboard", "2 hours ago");
        addNotification("Meeting Reminder", "Team meeting scheduled for tomorrow", "5 hours ago");
    }

    private void addNotification(String title, String message, String time) {
        JPanel notificationPanel = new JPanel();
        notificationPanel.setLayout(new BorderLayout(5, 5));
        notificationPanel.setBackground(Color.WHITE);
        notificationPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)  // Reduced padding
        ));
        notificationPanel.setMaximumSize(new Dimension(320, 80));  // Control panel height

        notificationPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        notificationPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openDetailedNotification(title, message, time);
            }
        });

        JLabel titleLabel = new JLabel("🔔 " + title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 12));
        notificationPanel.add(titleLabel, BorderLayout.NORTH);

        JTextArea messageText = new JTextArea(message);
        messageText.setFont(new Font("Arial", Font.PLAIN, 11));  // Smaller font
        messageText.setLineWrap(true);
        messageText.setWrapStyleWord(true);
        messageText.setEditable(false);
        messageText.setBackground(Color.WHITE);

        // Limit visible lines to 2
        int lineHeight = messageText.getFontMetrics(messageText.getFont()).getHeight();
        messageText.setPreferredSize(new Dimension(250, lineHeight * 2));

        notificationPanel.add(messageText, BorderLayout.CENTER);

        JLabel timeLabel = new JLabel(time);
        timeLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        timeLabel.setForeground(Color.GRAY);
        notificationPanel.add(timeLabel, BorderLayout.EAST);

        notificationContainer.add(notificationPanel);
        notificationContainer.add(Box.createRigidArea(new Dimension(0, 1)));
        notificationContainer.revalidate();
        notificationContainer.repaint();
    }

    // Store the original content pane components
    private Component[] originalComponents;

    private void openDetailedNotification(String title, String message, String time) {
        // Store original components before removing them
        originalComponents = getContentPane().getComponents();

        JPanel detailedView = new JPanel();
        detailedView.setLayout(new BorderLayout(20, 20));
        detailedView.setBackground(Color.WHITE);
        detailedView.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        getContentPane().removeAll();
        getContentPane().add(detailedView);

        JPanel headerPanel = new JPanel(new BorderLayout(10, 10));
        headerPanel.setBackground(Color.WHITE);

        JButton backButton = new JButton("← Back");
        backButton.addActionListener(e -> restoreMainView());
        headerPanel.add(backButton, BorderLayout.WEST);

        JLabel titleLabel = new JLabel("🔔 " + title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        detailedView.add(headerPanel, BorderLayout.NORTH);

        JTextArea messageArea = new JTextArea(message);
        messageArea.setFont(new Font("Arial", Font.PLAIN, 16));
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        messageArea.setEditable(false);
        messageArea.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JScrollPane scrollPane = new JScrollPane(messageArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        detailedView.add(scrollPane, BorderLayout.CENTER);

        JLabel timeLabel = new JLabel("Received: " + time);
        timeLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        timeLabel.setForeground(Color.GRAY);
        detailedView.add(timeLabel, BorderLayout.SOUTH);

        revalidate();
        repaint();
    }

    private void restoreMainView() {
        getContentPane().removeAll();

        // Restore all original components
        for (Component component : originalComponents) {
            getContentPane().add(component);
        }

        revalidate();
        repaint();
    }
    
        private JPanel createProfilePanel() {
        JPanel panel = new JPanel(null);
        panel.setBackground(Color.WHITE);

        JButton btnBack = new JButton("< Back");
        btnBack.setBackground(Color.WHITE);
        btnBack.setForeground(new Color(0, 119, 212));
        btnBack.setFont(new Font("Arial", Font.BOLD, 12));
        btnBack.setBounds(20, 20, 90, 30);
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
        
        JPanel pnlProfilePage = new JPanel();
        pnlProfilePage.setLayout(null);
        pnlProfilePage.setBorder(new LineBorder(Color.GRAY, 1));
        pnlProfilePage.setBounds(300, 20, 850, 700);
        panel.add(pnlProfilePage);
        
        JLabel lblFirstName = new JLabel("firstName");
        lblFirstName.setFont(new Font("Arial", Font.BOLD, 27));      
        FontMetrics metricFirst = lblFirstName.getFontMetrics(lblFirstName.getFont());
        int fNameWidth = metricFirst.stringWidth(lblFirstName.getText());       
        lblFirstName.setBounds(75, 80, fNameWidth + 20, 40);
        pnlProfilePage.add(lblFirstName);
        
        JLabel lblLastName = new JLabel("lastName");
        lblLastName.setFont(new Font("Arial", Font.BOLD, 25));
        FontMetrics metricLast = lblLastName.getFontMetrics(lblLastName.getFont());
        int lNameWidth = metricLast.stringWidth(lblLastName.getText());
        lblLastName.setBounds(220, 80, lNameWidth + 20, 40);
        pnlProfilePage.add(lblLastName);
        
        JPanel pnlProfile = new JPanel();
        pnlProfile.setBounds(600, 50, 160, 160);
        pnlProfile.setBackground(Color.red);
        pnlProfilePage.add(pnlProfile);

        btnLogOut = new JButton("Log out");
        btnLogOut.setBackground(Color.WHITE);
        btnLogOut.setForeground(new Color(0, 119, 212));
        btnLogOut.setFont(new Font("Arial", Font.BOLD, 12));
        btnLogOut.setBounds(1370, 670, 80, 30);
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
            
            ImageIcon originalIcon = new ImageIcon("mainIcon.png");
               
            Image scaledImage = originalIcon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);
            
            int confirm = JOptionPane.showConfirmDialog(
                    this, 
                    "Are you sure you want to log out?", 
                    "Log out", 
                    JOptionPane.YES_OPTION, 
                    JOptionPane.QUESTION_MESSAGE, 
                    scaledIcon
            );

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

