package views;
/* =============================================
   Rosanne's attempt to make the GUI ( ͡ಥ ͜ʖ ͡ಥ)
 ============================================ */
import controllers.*;
import model.Player;
import model.PlayerPiece;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.*;
import java.util.List;

public class GameView {

    private JFrame frame;
    private JPanel leftPanel;
    public JPanel topPanel;
    private JPanel bottomPanel = new JPanel();

    public JTextArea turnTextArea;
    public JTextArea cardTextArea;
    public JTextField inputArea;
    public JTextArea gameplayTextArea;
    public Queue<String> answers = new PriorityQueue<>();
    public Queue<String> arrowResponse = new PriorityQueue<>();
    private boolean scrollToggle = true;

    private int result;
    private boolean startGameButtonPressed = false;

    private GameControl gameControl;

    //X and Y locations of the players
    private static Map<String, Integer> playerPieceXs;
    private static Map<String, Integer> playerPieceYs;

    int playersPlaying = 0;

    public GameView(GameControl gc, Map<String, Integer> xs, Map<String, Integer> ys) {
        gameControl = gc;
        playerPieceXs = xs;
        playerPieceYs = ys;
    }

    /**
     * The method initialises the main frame and from here calls other methods to 
     * create buttons and panels etc.
     * 
     * After everything is build and players are chosen the method calls the start game of 
     * the controller and the game begins from there.
     */
    public void createMainWindow() {
        frame = new JFrame("-Murder Madness-");
        frame.getContentPane().setBackground(Color.LIGHT_GRAY);
        frame.setMinimumSize(new Dimension(830, 800));
        frame.setLayout(new BorderLayout());
        frame.setResizable(false); // main window can be resized :o
        frame.setUndecorated(true); // takes off the close button and other stuff from frame

        setMenuBar(frame); //Sets the menu bar at the top of the window, its used to quit the game
        dialogBox(frame); // The pop up dialogBox
        setPanels(frame); //Sets the panels which have buttons, board and gameplay text area

        /* display the main window ========== */
        frame.pack();
        frame.setVisible(true);

        //Welcome box with instructions
        welcomeDialogBox(frame);

        //Starts the game in game control as everything has been built at this stage
        gameControl.startGame();

    }

    /**
     * The welcome dialog box which includes all the instructions of the game.
     * @param frame
     */
    public void welcomeDialogBox(JFrame frame) {
        JOptionPane.showMessageDialog(
                frame,
                "Welcome to MurderMadness - this is a game about finding the murderer,\n" +
                        "the murder weapon and the murder estate, in order to win!\n\n" +
                "The characters are:\nLucilla - Yellow circle\nBert - Blue circle\n" +
                        "Maline - Magenta circle\nPercy - Purple circle\n\n" + "The weapons are:\n" +
                "Broom - Maroon square\nScissors - Dark Green square\nKnife - Dark Blue square\n" +
                "Shovel - Dark Purple square\niPad - Dark Cyan square\n\n" +
                "The Estates (labelled w/ characters):\nHaunted House - H\nManic Manor - M\nVila Celia - V\n" +
                "Calamity Castle - C\nPeril Palace - P\n\n" + "You are able to move around the board using the arrow\n" +
                "keys in the left panel and read game play\ndisplay to keep up with the game.\n\nGood luck! (❋•‿•❋)",
                "Welcome & Instructions!",
                JOptionPane.PLAIN_MESSAGE
        );
    }

    /**
     * Pop up dialog box which gets user input asking how many players are playing,
     * also lets the player choose the names of their names and characters.
     * @param frame
     */
    public void dialogBox(JFrame frame) {
        //Building all the JComponents
        JRadioButton lucillaButton = new JRadioButton("Lucilla");
        JRadioButton bertButton = new JRadioButton("Bert");
        JRadioButton malineButton = new JRadioButton("Maline");
        JRadioButton percyButton = new JRadioButton("Percy");

        JButton confirmPlayer = new JButton("Confirm Player");
        JButton startGame = new JButton("Start Game");
        startGame.setVisible(true);
        startGame.setPreferredSize(new Dimension(40,20));
        startGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                startGameButtonPressed = true;
                JOptionPane.getRootFrame().dispose();
            }
        });

        ButtonGroup playerButtons = new ButtonGroup();
        Integer[] numPlayersOptions = { 2, 3, 4 };
        JComboBox numPlayers = new JComboBox(numPlayersOptions);

        JTextField playerNameInput = new JTextField();

        lucillaButton.setEnabled(false);
        bertButton.setEnabled(false);
        malineButton.setEnabled(false);
        percyButton.setEnabled(false);
        playerNameInput.setEnabled(false);
        confirmPlayer.setEnabled(false);
        startGame.setEnabled(false);

        JButton confirmNumPlayers = new JButton("Confirm");
        confirmNumPlayers.setVisible(true);
        confirmNumPlayers.setPreferredSize(new Dimension(40, 20));
        confirmNumPlayers.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                numPlayers.setEnabled(false);
                confirmNumPlayers.setEnabled(false);
                lucillaButton.setEnabled(true);
                bertButton.setEnabled(true);
                malineButton.setEnabled(true);
                percyButton.setEnabled(true);
                playerNameInput.setEnabled(true);
                confirmPlayer.setEnabled(true);
                playersPlaying = (Integer) numPlayers.getSelectedItem();
            }
        });


        lucillaButton.setActionCommand("Lucilla");
        lucillaButton.setVisible(true);
        lucillaButton.setPreferredSize(new Dimension(40, 20));
        lucillaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                //System.out.println("Got to accuse");
            }
        });
        playerButtons.add(lucillaButton);

        bertButton.setActionCommand("Bert");
        bertButton.setVisible(true);
        bertButton.setPreferredSize(new Dimension(40, 20));
        bertButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                //System.out.println("Got to accuse");
            }
        });
        playerButtons.add(bertButton);

        malineButton.setActionCommand("Maline");
        malineButton.setVisible(true);
        malineButton.setPreferredSize(new Dimension(40, 20));
        malineButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                //System.out.println("Got to accuse");
            }
        });
        playerButtons.add(malineButton);

        percyButton.setActionCommand("Percy");
        percyButton.setVisible(true);
        percyButton.setPreferredSize(new Dimension(40, 20));
        percyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                //System.out.println("Got to accuse");
            }
        });
        playerButtons.add(percyButton);

        confirmPlayer.setVisible(true);
        confirmPlayer.setPreferredSize(new Dimension(40, 20));
        confirmPlayer.addActionListener(new ActionListener() {
            int counter = 0;
            List<String> tempPlayerNames = new ArrayList<>();
            public void actionPerformed(ActionEvent ev) {
                if (playerButtons.getSelection() == null || playerNameInput.getText().equals("") ||
                        tempPlayerNames.contains(playerNameInput.getText())) {
                    JOptionPane.showMessageDialog(frame, "Please enter a player name and select a character!",
                            "Error!", JOptionPane.ERROR_MESSAGE);
                }
                if(playerButtons.getSelection() != null && !playerNameInput.getText().equals("") &&
                        !tempPlayerNames.contains(playerNameInput.getText())) {
                    String selectedButton = playerButtons.getSelection().getActionCommand();
                    Player newPlayer = new Player(playerNameInput.getText());
                    PlayerPiece newPlayerPiece = new PlayerPiece(selectedButton, playerPieceYs.get(selectedButton),
                            playerPieceXs.get(selectedButton));
                    newPlayer.addPlayerPiece(newPlayerPiece);
                    gameControl.addPlayers(newPlayer);
                    tempPlayerNames.add(playerNameInput.getText());
                    counter++;
                    playerButtons.getSelection().setEnabled(false);
                    playerButtons.clearSelection();
                    playerNameInput.setText("");
                    if (counter == playersPlaying) {
                        confirmPlayer.setEnabled(false);
                        startGame.setEnabled(true);
                        tempPlayerNames.clear();
                    }
                }
            }
        });

        //Adding all the Jcomponents to the inputs array to get it ready to output into a dialog box
        final JComponent[] inputs = new JComponent[] {
                new JLabel("How many players are playing?"),
                numPlayers,
                confirmNumPlayers,
                new JLabel("Choose a player piece:"),
                lucillaButton,
                bertButton,
                malineButton,
                percyButton,
                new JLabel("Player name:"),
                playerNameInput,
                confirmPlayer,
                startGame
        };

        //Creating the JOptionPane
        result = JOptionPane.showOptionDialog(null, inputs, "Set up players",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{}, null);
        while (result == JOptionPane.CLOSED_OPTION && !startGameButtonPressed) {
            JOptionPane.showOptionDialog(null, inputs, "Set up players",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{}, null);
        }
    }


    /**
     * Menubar to allow use to quit the game from.
     * @param frame
     */
    public void setMenuBar(JFrame frame) {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Game");
        JMenuItem quit = new JMenuItem("Quit");

        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int exitChoice = JOptionPane.showConfirmDialog(
                        null,
                        "Are you sure you want to exit?",
                        "Exit Murder Madness",
                        JOptionPane.YES_NO_OPTION
                );
                if (exitChoice == 0) {
                    System.exit(0);
                }
            }
        });

        menuBar.setBorder(BorderFactory.createEmptyBorder()); // get rid of ugly bottom border
        menu.add(quit);
        menuBar.add(menu);

        frame.add(menuBar, BorderLayout.NORTH);
    }

    /**
     * Making and splitting the panels for the board, buttons and the game text output area
     * @param frame
     */
    public void setPanels(JFrame frame) {
        JSplitPane vertSplit = new JSplitPane();
        // Left panel here...
        leftPanel = new JPanel();
        addButtons();
        leftPanel.setBackground(Color.WHITE);
        Border border = BorderFactory.createLineBorder(Color.LIGHT_GRAY);
        Border borderBottomPanel = BorderFactory.createEmptyBorder(24,0,10,0);
        leftPanel.setBorder(border);
        JSplitPane rightPanel = new JSplitPane();

        vertSplit.setOrientation(JSplitPane.HORIZONTAL_SPLIT); // it's the other tf!?!?
        vertSplit.setDividerSize(5);
        vertSplit.setDividerLocation(200);
        vertSplit.setContinuousLayout(false);
        vertSplit.setLeftComponent(leftPanel);
        vertSplit.setRightComponent(rightPanel);
        vertSplit.setEnabled(false); // to stop resizing

        // the right panel will now split horizontally AGAIN (¬､¬) ...

        // B= new GameBoardView(gameControl);
        topPanel = new GameBoardView(gameControl);
        gameControl.board.setGameView(this);
        topPanel.setBackground(Color.DARK_GRAY);
        Border borderTwo = BorderFactory.createLineBorder(Color.DARK_GRAY, 10);
        topPanel.setBorder(borderTwo);
        // Text field for gameplay ou
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(borderBottomPanel);

        rightPanel.setOrientation(JSplitPane.VERTICAL_SPLIT);
        rightPanel.setDividerSize(5);
        rightPanel.setDividerLocation(620);
        rightPanel.setContinuousLayout(false);
        rightPanel.setTopComponent(topPanel);
        rightPanel.setBottomComponent(bottomPanel);
        rightPanel.setEnabled(false);

        frame.add(vertSplit, BorderLayout.CENTER);

    }

    /**
     * Adding buttons to the left panel which control the game
     */
    private void addButtons() {

        JPanel suggestAccuse = new JPanel();
        suggestAccuse.setBorder(BorderFactory.createBevelBorder(0));
        suggestAccuse.setLayout(new BorderLayout(1, 1));
        suggestAccuse.setSize(new Dimension(122, 72));
        suggestAccuse.setPreferredSize(new Dimension(122, 72));
        suggestAccuse.setBackground(Color.DARK_GRAY);
        suggestAccuse.setLocation(39, 190);
        suggestAccuse.setLayout(new GridLayout(2,1, 0, 1));

        JPanel arrows = new JPanel();
        arrows.setBorder(BorderFactory.createBevelBorder(0));
        arrows.setLayout(new BorderLayout(1, 1));
        arrows.setSize(new Dimension(150, 90));
        arrows.setPreferredSize(new Dimension(150, 90));
        arrows.setLocation(25, 290);
        arrows.setBackground(Color.DARK_GRAY);
        arrows.setLayout(new GridLayout(2,3, 1, 1));

        JButton accuse = new JButton("Accuse");
        accuse.setVisible(true);
        accuse.setPreferredSize(new Dimension(120, 35));
        accuse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                answers.add("accuse");
            }
        });

        JButton suggest =new JButton("Suggest");
        suggest.setVisible(true);
        suggest.setPreferredSize(new Dimension(120, 35));
        suggest.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                answers.add("suggest");
            }
        });

        JButton arrowUp =new JButton("↑");
        arrowUp.setVisible(true);
        arrowUp.setPreferredSize(new Dimension(50, 44));
        arrowUp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                arrowResponse.add("↑");
            }
        });

        JButton arrowLeft =new JButton("←");
        arrowLeft.setVisible(true);
        arrowLeft.setPreferredSize(new Dimension(50, 44));
        arrowLeft.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                arrowResponse.add("←");
            }
        });

        JButton arrowRight =new JButton("→");
        arrowRight.setVisible(true);
        arrowRight.setPreferredSize(new Dimension(50, 44));
        arrowRight.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                arrowResponse.add("→");
            }
        });


        JButton arrowDown =new JButton("↓");
        arrowDown.setVisible(true);
        arrowDown.setPreferredSize(new Dimension(50, 44));
        arrowDown.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                arrowResponse.add("↓");
            }
        });

        JButton empty1 =new JButton("");
        empty1.setVisible(false);

        JButton empty2 =new JButton("");
        empty2.setVisible(false);

        //Adding buttons to the panels
        suggestAccuse.add(accuse);
        suggestAccuse.add(suggest);
        arrows.add(empty1);
        arrows.add(arrowUp);
        arrows.add(empty2);
        arrows.add(arrowLeft);
        arrows.add(arrowDown);
        arrows.add(arrowRight);

        JPanel header = new JPanel();
        header.setSize(new Dimension(170, 50));
        header.setPreferredSize(new Dimension(170, 30));
        header.setBackground(Color.DARK_GRAY);
        header.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        header.setLocation(15, 45);

        JLabel murderMadness = new JLabel();
        murderMadness.setText("Murder Madness");
        murderMadness.setHorizontalTextPosition(JLabel.CENTER);
        murderMadness.setVerticalTextPosition(JLabel.CENTER);
        murderMadness.setForeground(Color.WHITE);
        murderMadness.setFont(new Font("MV Boli", Font.PLAIN, 20));

        header.add(murderMadness);

        //Updating the frame
        arrows.setVisible(true);
        suggestAccuse.setVisible(true);
        frame.add(suggestAccuse);
        frame.add(arrows);
        frame.add(header);
        makeJTextArea();
    }

    /**
     * This method changed if the user is able to scroll the game text area
     * or will the game automatically continously keep scrolling to the bottom
     */
    private void toggleScroll() {
        this.scrollToggle = !this.scrollToggle;
    }

    /**
     * returns the value of the boolean field of toggle scroll
     * Wasn't able to just call the field from the action method.
     * @return
     */
    private boolean getScrollToggle() {
        return this.scrollToggle;
    }

    /**
     * Making the J text areas to output the user's hand, game play info
     * Along with the text field which lets the user do inputs.
     */
    private void makeJTextArea() {
        //players turn
        turnTextArea = new JTextArea();
        turnTextArea.setBounds(10, 120, 180, 40);
        turnTextArea.setBackground(new Color(230,230,230));
        turnTextArea.setBorder(BorderFactory.createBevelBorder(0));
        turnTextArea.setEditable(false);
        frame.add(turnTextArea);

        //Player's hand's label's panel
        JPanel cardHeader = new JPanel(null);
        cardHeader.setSize(new Dimension(100, 20));
        cardHeader.setBorder(BorderFactory.createEmptyBorder());
        cardHeader.setBackground(Color.WHITE);
        cardHeader.setLocation(10, 410);
        cardHeader.setPreferredSize(new Dimension(100, 20));

        //Label for player's hand's label
        JLabel cardLabel = new JLabel();
        cardLabel.setText("Player's hand:");
        cardLabel.setHorizontalTextPosition(JLabel.LEFT);
        cardLabel.setVerticalTextPosition(JLabel.CENTER);
        Dimension cardLabelSize = cardLabel.getPreferredSize();
        cardLabel.setBounds(2, 0, cardLabelSize.width, cardLabelSize.height);
        cardHeader.add(cardLabel);
        frame.add(cardHeader);

        //Player's hand's output area
        cardTextArea = new JTextArea();
        cardTextArea.setBounds(10, 435, 180, 180);
        cardTextArea.setBackground(new Color(230,230,230));
        cardTextArea.setBorder(BorderFactory.createBevelBorder(0));
        cardTextArea.setEditable(false);
        frame.add(cardTextArea);

        //Input text area's panel for the label
        JPanel inputHeader = new JPanel(null);
        inputHeader.setSize(new Dimension(100, 20));
        inputHeader.setBorder(BorderFactory.createEmptyBorder());
        inputHeader.setBackground(Color.WHITE);
        inputHeader.setLocation(10, 645);
        inputHeader.setPreferredSize(new Dimension(100, 20));

        //Input text area's label
        JLabel inputLabel = new JLabel();
        inputLabel.setText("Input Text:");
        inputLabel.setHorizontalTextPosition(JLabel.LEFT);
        inputLabel.setVerticalTextPosition(JLabel.CENTER);
        Dimension inputLabelSize = inputLabel.getPreferredSize();
        inputLabel.setBounds(2, 0, inputLabelSize.width, inputLabelSize.height);
        inputHeader.add(inputLabel);
        frame.add(inputHeader);

        //Gameplay's label's panel
        JPanel gamePlayHeader = new JPanel(null);
        gamePlayHeader.setSize(new Dimension(100, 15));
        gamePlayHeader.setBorder(BorderFactory.createEmptyBorder());
        gamePlayHeader.setBackground(Color.WHITE);
        gamePlayHeader.setLocation(228, 652);
        gamePlayHeader.setPreferredSize(new Dimension(100, 15));

        //Gameplay's label
        JLabel gameplayLabel = new JLabel();
        gameplayLabel.setText("Gameplay:");
        gameplayLabel.setHorizontalTextPosition(JLabel.LEFT);
        gameplayLabel.setVerticalTextPosition(JLabel.CENTER);
        Dimension gameplayHeaderPreferredSize = gameplayLabel.getPreferredSize();
        gameplayLabel.setBounds(2, 0, gameplayHeaderPreferredSize.width, gameplayHeaderPreferredSize.height);
        gamePlayHeader.add(gameplayLabel);
        frame.add(gamePlayHeader);

        //Panel for the scroll toggle buttons
        JPanel scrollButtonPanel = new JPanel(null);
        scrollButtonPanel.setSize(new Dimension(145, 20));
        scrollButtonPanel.setBorder(BorderFactory.createEmptyBorder());
        scrollButtonPanel.setBackground(Color.WHITE);
        scrollButtonPanel.setLocation(660, 650);
        scrollButtonPanel.setPreferredSize(new Dimension(145, 20));

        //Scroll toggle button
        JButton scrollButton = new JButton("Scroll [ON/OFF]");
        scrollButton.setVisible(true);
        scrollButton.setSize(new Dimension(145, 20));
        scrollButton.setPreferredSize(new Dimension(145, 20));
        scrollButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                toggleScroll();
            }
        });
        scrollButtonPanel.add(scrollButton);
        frame.add(scrollButtonPanel);

        //player turn text area's label's panel
        JPanel turnHeader = new JPanel(null);
        turnHeader.setSize(new Dimension(100, 15));
        turnHeader.setBorder(BorderFactory.createEmptyBorder());
        turnHeader.setBackground(Color.WHITE);
        turnHeader.setLocation(10, 105);
        turnHeader.setPreferredSize(new Dimension(100, 15));

        //player turn text area label
        JLabel turnLabel = new JLabel();
        turnLabel.setText("Player:");
        turnLabel.setHorizontalTextPosition(JLabel.LEFT);
        turnLabel.setVerticalTextPosition(JLabel.CENTER);
        Dimension turnLabelPreferredSize = turnLabel.getPreferredSize();
        turnLabel.setBounds(2, 0, turnLabelPreferredSize.width, turnLabelPreferredSize.height);
        turnHeader.add(turnLabel);
        frame.add(turnHeader);

        //input area text field
        inputArea = new JTextField();
        inputArea.setBounds(10, 670, 180, 25);
        inputArea.setBackground(Color.WHITE);
        inputArea.setPreferredSize(new Dimension(180,25));
        inputArea.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                answers.add(inputArea.getText());
                inputArea.setText("");
            }
        });
        frame.add(inputArea);


        //gameplay output text field
        gameplayTextArea = new JTextArea();
        gameplayTextArea.setLocation(10, 60);
        gameplayTextArea.setBounds(10, 40, 580, 100);
        gameplayTextArea.setBackground(new Color(230,230,230));
        gameplayTextArea.setBorder(BorderFactory.createBevelBorder(0));
        gameplayTextArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(gameplayTextArea);
        scroll.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
            public void adjustmentValueChanged(AdjustmentEvent e) {
                if (getScrollToggle()){
                    e.getAdjustable().setValue(e.getAdjustable().getMaximum());
                }
            }
        });
        scroll.setPreferredSize(new Dimension(580, 100));
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setLocation(10, 10);
        bottomPanel.add(scroll, BorderLayout.CENTER);
    }

    /**
     * Replacing and adding string to the player turn text area
     * @param string
     */
    public void addToTurnTextArea(String string) {
        turnTextArea.selectAll();
        turnTextArea.replaceSelection(string);
    }

    /**
     * Updating the grid on the board with player locations
     */
    public void updateGameBoard() {
        topPanel.repaint();
        topPanel.revalidate();
    }


}