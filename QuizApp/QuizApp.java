//package QuizApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class QuizApp {
    JFrame frame;
    JPanel panel;
    JLabel label;
    JButton button;
    int currentQuestion = 0;
    int score = 0;
    String category = "";

    // Example Questions (you can add more)
    String[][] gkQuestions = {
            {"Which is the largest planet?", "Earth", "Jupiter", "Mars", "Venus", "Jupiter"},
            {"Who invented the light bulb?", "Newton", "Edison", "Einstein", "Tesla", "Edison"},
            {"Which is the national animal of India?", "Lion", "Tiger", "Elephant", "Horse", "Tiger"}
            // 👉 Add more until 10
    };

    String[][] currentAffairsQuestions = {
            {"Who is the current UN Secretary General?", "Ban Ki-moon", "Antonio Guterres", "Kofi Annan", "None", "Antonio Guterres"},
            {"Which country hosted the 2024 Olympics?", "China", "France", "USA", "Japan", "France"},
            {"Which is the current AI trend?", "Blockchain", "Neural Networks", "Cloud Storage", "All of the above", "All of the above"}
            // 👉 Add more until 10
    };

    public QuizApp() {
        showWelcomeScreen();
    }

    // Welcome Screen
    private void showWelcomeScreen() {
        frame = new JFrame("Quiz Application");
        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        label = new JLabel("Welcome to the Quiz!", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 22));
        panel.add(label, BorderLayout.CENTER);

        button = new JButton("Continue");
        button.addActionListener(e -> showCategoryScreen());
        panel.add(button, BorderLayout.SOUTH);

        frame.add(panel);
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    // Category Selection
    private void showCategoryScreen() {
        frame.getContentPane().removeAll();
        frame.repaint();

        panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));

        label = new JLabel("Choose a Category", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(label);

        JButton gkButton = new JButton("General Knowledge");
        gkButton.addActionListener(e -> { category = "GK"; currentQuestion = 0; score = 0; showQuestion(); });
        panel.add(gkButton);

        JButton caButton = new JButton("Current Affairs");
        caButton.addActionListener(e -> { category = "CA"; currentQuestion = 0; score = 0; showQuestion(); });
        panel.add(caButton);

        frame.add(panel);
        frame.revalidate();
    }

    // Show Question
    private void showQuestion() {
        frame.getContentPane().removeAll();
        frame.repaint();

        String[][] questions = category.equals("GK") ? gkQuestions : currentAffairsQuestions;

        if (currentQuestion >= questions.length) {
            showResult();
            return;
        }

        panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1));

        label = new JLabel("Q" + (currentQuestion + 1) + ": " + questions[currentQuestion][0]);
        panel.add(label);

        for (int i = 1; i <= 4; i++) {
            String option = questions[currentQuestion][i];
            JButton optionButton = new JButton(option);
            optionButton.addActionListener(e -> {
                if (option.equals(questions[currentQuestion][5])) {
                    score++;
                }
                currentQuestion++;
                showQuestion();
            });
            panel.add(optionButton);
        }

        frame.add(panel);
        frame.revalidate();
    }

    // Show Result
    private void showResult() {
        frame.getContentPane().removeAll();
        frame.repaint();

        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        label = new JLabel("Your Score: " + score, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 22));
        panel.add(label, BorderLayout.CENTER);

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));
        panel.add(exitButton, BorderLayout.SOUTH);

        frame.add(panel);
        frame.revalidate();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(QuizApp::new);
    }
}
