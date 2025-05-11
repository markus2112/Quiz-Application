package quiz.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main extends JFrame implements ActionListener {

    private CardLayout card;
    private JPanel mainPanel;

    // Login components
    private JTextField nameField, rollField, emailField, deptField, classField, sectionField;
    private String userName = "";

    // Quiz state
    private int score = 0;
    private int currentQuestion = 0;
    private final String[][] questions = {
            {"Java is a ?", "Programming Language", "Operating System", "Browser", "IDE", "A"},
            {"Which company developed Java?", "Oracle", "Sun Microsystems", "Google", "IBM", "B"},
            {"Which keyword is used to inherit a class in Java?", "implement", "extends", "inherits", "super", "B"},
            {"Java is:", "Compiled", "Interpreted", "Both", "None", "C"},
            {"Which of these is not a Java keyword?", "class", "interface", "object", "extends", "C"},
            {"Which method is used to start a thread in Java?", "start()", "run()", "init()", "execute()", "A"},
            {"What is the default value of a boolean in Java?", "true", "false", "null", "0", "B"},
            {"Which of the following is used to handle exceptions in Java?", "try", "catch", "throw", "throws", "B"},
            {"What does JVM stand for?", "Java Visual Machine", "Java Virtual Machine", "Java Variable Machine", "Java Value Machine", "B"},
            {"Which of the following is a wrapper class in Java?", "Integer", "Double", "Character", "All of the above", "D"}
    };

    // Quiz components
    private JRadioButton opt1, opt2, opt3, opt4;
    private ButtonGroup options;
    private JLabel qLabel;
    private JButton quizNextBtn;

    // Score screen
    private JLabel scoreLabel;

    public Main() {
        setTitle("Quiz Application");
        setSize(800, 600);
        setLocation(300, 100);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        card = new CardLayout();
        mainPanel = new JPanel(card);
        add(mainPanel);

        initLoginScreen();
        initRulesScreen();
        initQuizScreen();
        initScoreScreen();

        setVisible(true);
    }

    // 1. LOGIN SCREEN
    private void initLoginScreen() {
        JPanel loginPanel = new JPanel(null);
        loginPanel.setBackground(Color.WHITE);

        JLabel heading = new JLabel("QUIZ APPLICATION");
        heading.setBounds(250, 50, 300, 30);
        heading.setFont(new Font("Viner Hand ITC", Font.BOLD, 24));
        loginPanel.add(heading);

        JLabel nameLabel = new JLabel("Enter Your Name:");
        nameLabel.setBounds(250, 120, 200, 25);
        loginPanel.add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(250, 150, 300, 30);
        loginPanel.add(nameField);

        JLabel rollLabel = new JLabel("Enter Roll No:");
        rollLabel.setBounds(250, 180, 200, 25);
        loginPanel.add(rollLabel);

        rollField = new JTextField();
        rollField.setBounds(250, 210, 300, 30);
        loginPanel.add(rollField);

        JLabel emailLabel = new JLabel("Enter Email ID:");
        emailLabel.setBounds(250, 240, 200, 25);
        loginPanel.add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(250, 270, 300, 30);
        loginPanel.add(emailField);

        JLabel deptLabel = new JLabel("Enter Department:");
        deptLabel.setBounds(250, 300, 200, 25);
        loginPanel.add(deptLabel);

        deptField = new JTextField();
        deptField.setBounds(250, 330, 300, 30);
        loginPanel.add(deptField);

        JLabel classLabel = new JLabel("Enter Class:");
        classLabel.setBounds(250, 360, 200, 25);
        loginPanel.add(classLabel);

        classField = new JTextField();
        classField.setBounds(250, 390, 300, 30);
        loginPanel.add(classField);

        JLabel sectionLabel = new JLabel("Enter Section:");
        sectionLabel.setBounds(250, 420, 200, 25);
        loginPanel.add(sectionLabel);

        sectionField = new JTextField();
        sectionField.setBounds(250, 450, 300, 30);
        loginPanel.add(sectionField);

        JButton nextBtn = new JButton("Next");
        nextBtn.setBounds(350, 490, 100, 30);
        nextBtn.setBackground(new Color(22,99,54));
        nextBtn.setForeground(Color.WHITE);
        nextBtn.addActionListener(e -> {
            // Validate all fields
            String inputName = nameField.getText().trim();
            String inputRoll = rollField.getText().trim();
            String inputEmail = emailField.getText().trim();
            String inputDept = deptField.getText().trim();
            String inputClass = classField.getText().trim();
            String inputSection = sectionField.getText().trim();

            if (!inputName.isEmpty() && !inputRoll.isEmpty() && !inputEmail.isEmpty() && !inputDept.isEmpty() && !inputClass.isEmpty() && !inputSection.isEmpty()) {
                userName = inputName;
                card.show(mainPanel, "Rules");
            }
        });
        loginPanel.add(nextBtn);

        mainPanel.add(loginPanel, "Login");
    }

    // 2. RULES SCREEN
    private void initRulesScreen() {
        JPanel rulesPanel = new JPanel(null);

        JLabel heading = new JLabel();
        heading.setText("Welcome " + userName + " to QUIZ TEST");
        heading.setBounds(100, 80, 600, 30);
        heading.setFont(new Font("Viner Hand ITC", Font.BOLD, 22));
        heading.setForeground(new Color(22,99,54));
        rulesPanel.add(heading);

        JLabel rules = new JLabel("<html>"
                + "1. All Questions are compulsory.<br><br>"
                + "2. There are 10 questions.<br><br>"
                + "3. Each question carry 2 marks.<br><br>"
                + "4. No cheating.<br><br>"
                + "5. Click 'Start' to begin.<br><br>"
                + "</html>");
        rules.setBounds(70, 130, 650, 250);
        rules.setFont(new Font("Tahoma", Font.PLAIN, 16));
        rules.setForeground(new Color(22,99,54));
        rulesPanel.add(rules);

        JButton startBtn = new JButton("Start");
        startBtn.setBounds(350, 400, 100, 30);
        startBtn.setBackground(new Color(22,99,54));
        startBtn.setForeground(Color.WHITE);
        startBtn.addActionListener(e -> card.show(mainPanel, "Quiz"));
        rulesPanel.add(startBtn);

        mainPanel.add(rulesPanel, "Rules");
    }

    // 3. QUIZ SCREEN
    private void initQuizScreen() {
        JPanel quizPanel = new JPanel(null);

        qLabel = new JLabel();
        qLabel.setBounds(50, 50, 700, 30);
        qLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        quizPanel.add(qLabel);

        opt1 = new JRadioButton();
        opt2 = new JRadioButton();
        opt3 = new JRadioButton();
        opt4 = new JRadioButton();

        opt1.setBounds(100, 100, 600, 30);
        opt2.setBounds(100, 150, 600, 30);
        opt3.setBounds(100, 200, 600, 30);
        opt4.setBounds(100, 250, 600, 30);

        options = new ButtonGroup();
        options.add(opt1);
        options.add(opt2);
        options.add(opt3);
        options.add(opt4);

        quizPanel.add(opt1);
        quizPanel.add(opt2);
        quizPanel.add(opt3);
        quizPanel.add(opt4);

        quizNextBtn = new JButton("Next");
        quizNextBtn.setBounds(350, 350, 100, 30);
        quizNextBtn.setBackground(new Color(22,99,54));
        quizNextBtn.setForeground(Color.WHITE);
        quizNextBtn.addActionListener(this);
        quizPanel.add(quizNextBtn);

        mainPanel.add(quizPanel, "Quiz");

        loadQuestion();
    }

    private void loadQuestion() {
        if (currentQuestion < questions.length) {
            qLabel.setText("Q" + (currentQuestion + 1) + ": " + questions[currentQuestion][0]);
            opt1.setText("A. " + questions[currentQuestion][1]);
            opt2.setText("B. " + questions[currentQuestion][2]);
            opt3.setText("C. " + questions[currentQuestion][3]);
            opt4.setText("D. " + questions[currentQuestion][4]);
            options.clearSelection();
        } else {
            card.show(mainPanel, "Score");
            scoreLabel.setText("Thank you, " + userName + ". Your score: "
                    + score + " / " + questions.length);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String correct = questions[currentQuestion][5];
        String selected = "";
        if (opt1.isSelected()) selected = "A";
        else if (opt2.isSelected()) selected = "B";
        else if (opt3.isSelected()) selected = "C";
        else if (opt4.isSelected()) selected = "D";

        if (selected.equals(correct)) score++;
        currentQuestion++;
        loadQuestion();
    }

    // 4. SCORE SCREEN
    private void initScoreScreen() {
        JPanel scorePanel = new JPanel(null);

        scoreLabel = new JLabel();
        scoreLabel.setBounds(150, 150, 600, 40);
        scoreLabel.setFont(new Font("Tahoma", Font.BOLD, 22));
        scorePanel.add(scoreLabel);

        JButton exitBtn = new JButton("Exit");
        exitBtn.setBounds(350, 250, 100, 30);
        exitBtn.setBackground(new Color(22,99,54));
        exitBtn.setForeground(Color.WHITE);
        exitBtn.addActionListener(e -> System.exit(0));
        scorePanel.add(exitBtn);

        mainPanel.add(scorePanel, "Score");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}
