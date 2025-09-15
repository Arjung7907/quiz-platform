import javax.swing.*;
import java.awt.*;

public class QuizApp {
    JFrame frame;
    JPanel panel;
    JLabel label, progressLabel;
    JButton button;
    JTextField usernameField;
    JPasswordField passwordField;
    int currentQuestion = 0, score = 0;
    String category = "";

    // ---------------- GK QUESTIONS ----------------
    String[][] gkQuestions = {
            {"Which is the largest planet?", "Earth", "Jupiter", "Mars", "Venus", "Jupiter"},
            {"Who invented the light bulb?", "Newton", "Edison", "Einstein", "Tesla", "Edison"},
            {"Which is the national animal of India?", "Lion", "Tiger", "Elephant", "Horse", "Tiger"},
            {"Which is the longest river in the world?", "Amazon", "Nile", "Ganga", "Yangtze", "Nile"},
            {"What is the capital of Australia?", "Sydney", "Melbourne", "Canberra", "Perth", "Canberra"},
            {"Which gas do plants absorb?", "Oxygen", "Carbon Dioxide", "Nitrogen", "Hydrogen", "Carbon Dioxide"},
            {"Who wrote 'Romeo and Juliet'?", "Shakespeare", "Milton", "Wordsworth", "Chaucer", "Shakespeare"},
            {"What is H2O commonly called?", "Hydrogen", "Water", "Oxygen", "Salt", "Water"},
            {"Which is the fastest land animal?", "Cheetah", "Horse", "Tiger", "Leopard", "Cheetah"},
            {"Who painted the Mona Lisa?", "Van Gogh", "Picasso", "Leonardo da Vinci", "Rembrandt", "Leonardo da Vinci"}
    };

    // ---------------- CURRENT AFFAIRS QUESTIONS ----------------
    String[][] currentAffairsQuestions = {
            {"Who is the current UN Secretary General?", "Ban Ki-moon", "Antonio Guterres", "Kofi Annan", "None", "Antonio Guterres"},
            {"Which country hosted the 2024 Olympics?", "China", "France", "USA", "Japan", "France"},
            {"Which is the current AI trend?", "Blockchain", "Neural Networks", "Cloud Storage", "All of the above", "All of the above"},
            {"Which country won the 2023 Cricket World Cup?", "India", "England", "Australia", "Pakistan", "Australia"},
            {"Who is the current President of the USA?", "Donald Trump", "Barack Obama", "Joe Biden", "Kamala Harris", "Joe Biden"},
            {"Which company launched ChatGPT?", "Google", "OpenAI", "Microsoft", "Meta", "OpenAI"},
            {"Which country recently landed on the Moon (Chandrayaan-3)?", "China", "Russia", "India", "USA", "India"},
            {"Which tech giant owns YouTube?", "Meta", "Amazon", "Google", "Apple", "Google"},
            {"G20 Summit 2023 was hosted by?", "India", "Japan", "Brazil", "Germany", "India"},
            {"Which country will host the FIFA World Cup 2026?", "USA, Canada & Mexico", "Qatar", "France", "Germany", "USA, Canada & Mexico"}
    };

    public QuizApp() {
        showLoginScreen();
    }

    // ---------------- LOGIN SCREEN ----------------
    private void showLoginScreen() {
        frame = new JFrame(" QuizApp - Login");
        frame.setSize(450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.setBackground(new Color(52, 152, 219));

        JLabel userLabel = new JLabel(" Username:");
        userLabel.setFont(new Font("Arial", Font.BOLD, 16));
        userLabel.setForeground(Color.WHITE);
        usernameField = new JTextField();

        JLabel passLabel = new JLabel(" Password:");
        passLabel.setFont(new Font("Arial", Font.BOLD, 16));
        passLabel.setForeground(Color.WHITE);
        passwordField = new JPasswordField();

        JButton loginButton = createStyledButton("Login", new Color(46, 204, 113));
        loginButton.addActionListener(e -> checkLogin());

        JButton exitButton = createStyledButton("Exit", new Color(231, 76, 60));
        exitButton.addActionListener(e -> System.exit(0));

        panel.add(userLabel); panel.add(usernameField);
        panel.add(passLabel); panel.add(passwordField);
        panel.add(loginButton); panel.add(exitButton);

        frame.add(panel);
        frame.setVisible(true);
    }

    private void checkLogin() {
        String user = usernameField.getText();
        String pass = new String(passwordField.getPassword());

        if (user.equals("arjun@123") && pass.equals("123456")) {
            frame.getContentPane().removeAll();
            frame.repaint();
            showWelcomeScreen(user);
        } else {
            JOptionPane.showMessageDialog(frame, "Invalid username or password!", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ---------------- WELCOME SCREEN ----------------
    private void showWelcomeScreen(String username) {
        frame.setTitle("Quiz Application");

        panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(155, 89, 182));

        label = new JLabel("Welcome, " + username + " ", SwingConstants.CENTER);
        label.setFont(new Font("Verdana", Font.BOLD, 26));
        label.setForeground(Color.WHITE);

        button = createStyledButton("Continue", new Color(241, 196, 15));
        button.addActionListener(e -> showCategoryScreen());

        panel.add(label, BorderLayout.CENTER);
        panel.add(button, BorderLayout.SOUTH);

        frame.add(panel);
        frame.revalidate();
        frame.repaint();
    }

    // ---------------- CATEGORY SCREEN ----------------
    private void showCategoryScreen() {
        frame.getContentPane().removeAll();
        frame.repaint();

        panel = new JPanel(new GridLayout(3, 1, 15, 15));
        panel.setBackground(new Color(230, 126, 34));
        panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        label = new JLabel("Choose a Category", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 22));
        label.setForeground(Color.WHITE);
        panel.add(label);

        JButton gkButton = createStyledButton("General Knowledge", new Color(26, 188, 156));
        gkButton.addActionListener(e -> { category = "GK"; currentQuestion = 0; score = 0; showQuestion(); });

        JButton caButton = createStyledButton("Current Affairs", new Color(52, 152, 219));
        caButton.addActionListener(e -> { category = "CA"; currentQuestion = 0; score = 0; showQuestion(); });

        panel.add(gkButton);
        panel.add(caButton);

        frame.add(panel);
        frame.revalidate();
    }

    // ---------------- SHOW QUESTION ----------------
    private void showQuestion() {
        frame.getContentPane().removeAll();
        frame.repaint();

        String[][] questions = category.equals("GK") ? gkQuestions : currentAffairsQuestions;

        if (currentQuestion >= questions.length) {
            showResult();
            return;
        }

        panel = new JPanel(new GridLayout(6, 1, 10, 10));
        panel.setBackground(new Color(236, 240, 241));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        progressLabel = new JLabel("Question " + (currentQuestion + 1) + " of " + questions.length, SwingConstants.CENTER);
        progressLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        panel.add(progressLabel);

        label = new JLabel("Q" + (currentQuestion + 1) + ": " + questions[currentQuestion][0], SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(label);

        for (int i = 1; i <= 4; i++) {
            String option = questions[currentQuestion][i];
            JButton optionButton = createStyledButton(option, new Color(189, 195, 199));
            optionButton.addActionListener(e -> {
                if (option.equals(questions[currentQuestion][5])) score++;
                currentQuestion++;
                showQuestion();
            });
            panel.add(optionButton);
        }

        frame.add(panel);
        frame.revalidate();
    }

    // ---------------- RESULT SCREEN WITH CHART ----------------
private void showResult() {
    frame.getContentPane().removeAll();
    frame.repaint();

    panel = new JPanel(new BorderLayout());
    panel.setBackground(new Color(46, 204, 113));
    panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

    // Score calculation
    int totalQuestions = category.equals("GK") ? gkQuestions.length : currentAffairsQuestions.length;
    int percentage = (score * 100) / totalQuestions;

    // Title
    JLabel resultTitle = new JLabel("Quiz Result", SwingConstants.CENTER);
    resultTitle.setFont(new Font("Verdana", Font.BOLD, 26));
    resultTitle.setForeground(Color.WHITE);
    panel.add(resultTitle, BorderLayout.NORTH);

    // Center area with chart + details
    JPanel centerPanel = new JPanel(new GridLayout(3, 1, 10, 10));
    centerPanel.setBackground(new Color(46, 204, 113));

    JLabel scoreLabel = new JLabel("Your Score: " + score + " / " + totalQuestions, SwingConstants.CENTER);
    scoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
    scoreLabel.setForeground(Color.WHITE);
    centerPanel.add(scoreLabel);

    // Progress Bar
    JProgressBar progressBar = new JProgressBar(0, 100);
    progressBar.setValue(percentage);
    progressBar.setStringPainted(true);
    progressBar.setFont(new Font("Arial", Font.BOLD, 16));
    progressBar.setForeground(Color.YELLOW);
    progressBar.setBackground(Color.WHITE);
    centerPanel.add(progressBar);

    // Feedback text
    JLabel feedback = new JLabel("", SwingConstants.CENTER);
    feedback.setFont(new Font("Arial", Font.BOLD, 20));
    feedback.setForeground(Color.WHITE);
    if (percentage >= 70) {
        feedback.setText("Excellent Performance!");
    } else if (percentage >= 50) {
        feedback.setText("Good Job!");
    } else {
        feedback.setText("Needs Improvement!");
    }
    centerPanel.add(feedback);

    panel.add(centerPanel, BorderLayout.CENTER);

    // Exit Button
    JButton exitButton = createStyledButton("Exit", new Color(192, 57, 43));
    exitButton.addActionListener(e -> System.exit(0));
    panel.add(exitButton, BorderLayout.SOUTH);

    frame.add(panel);
    frame.revalidate();
}


    // ---------------- STYLED BUTTON FACTORY ----------------
    private JButton createStyledButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 16));
        btn.setBackground(bg);
        btn.setForeground(Color.BLACK);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return btn;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(QuizApp::new);
    }
}
