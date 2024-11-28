package quizeGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuizApp {

    private static final int TOTAL_QUESTIONS = 4;  // Number of questions
    private static final int TIMER_LIMIT = 10;     // Timer limit per question (seconds)

    private JFrame frame;
    private JPanel questionPanel, resultPanel;
    private JLabel questionLabel, timerLabel, resultLabel;
    private JRadioButton[] optionButtons;
    private ButtonGroup optionGroup;
    private JButton submitButton;
    private Timer timer;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private int timeLeft = TIMER_LIMIT;
    private int[] userAnswers = new int[TOTAL_QUESTIONS]; // Stores the selected option for each question

    // Quiz Data: Questions, Options, and Correct Answers
    private String[][] questions = {
        {"What is the capital of France?", "Paris", "London", "Berlin", "Madrid"},
        {"Which planet is known as the Red Planet?", "Earth", "Mars", "Venus", "Jupiter"},
        {"Who wrote 'Romeo and Juliet'?", "Shakespeare", "Dickens", "Austen", "Hemingway"},
        {"What is the largest ocean on Earth?", "Atlantic", "Pacific", "Indian", "Arctic"}
    };
    private int[] answers = {0, 1, 0, 1}; // Correct answers' indices

    public QuizApp() {
        // Initialize frame
        frame = new JFrame("Quiz Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 350);
        frame.setLayout(new BorderLayout());

        // Set up the question panel
        questionPanel = new JPanel();
        questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.Y_AXIS));
        frame.add(questionPanel, BorderLayout.CENTER);

        // Question Label
        questionLabel = new JLabel("", SwingConstants.CENTER);
        questionLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        questionPanel.add(questionLabel);

        // Options buttons
        optionButtons = new JRadioButton[4];
        optionGroup = new ButtonGroup();
        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JRadioButton();
            optionButtons[i].setFont(new Font("Arial", Font.PLAIN, 14));
            optionGroup.add(optionButtons[i]);
            questionPanel.add(optionButtons[i]);
        }

        // Timer Label
        timerLabel = new JLabel("Time: " + TIMER_LIMIT, SwingConstants.CENTER);
        timerLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        frame.add(timerLabel, BorderLayout.NORTH);

        // Submit Button
        submitButton = new JButton("Submit Answer");
        submitButton.setEnabled(false); // Initially disabled until an option is selected
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitAnswer();
            }
        });
        frame.add(submitButton, BorderLayout.SOUTH);

        // Set up the result panel (hidden initially)
        resultPanel = new JPanel();
        resultLabel = new JLabel("", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        resultPanel.add(resultLabel);

        // Start quiz
        displayQuestion();
        frame.setVisible(true);
    }

    private void displayQuestion() {
        if (currentQuestionIndex >= TOTAL_QUESTIONS) {
            showResults();
            return;
        }

        // Reset timer and options
        timeLeft = TIMER_LIMIT;
        submitButton.setEnabled(false);
        timerLabel.setText("Time: " + timeLeft);

        // Get the current question and options
        String[] currentQuestion = questions[currentQuestionIndex];
        questionLabel.setText(currentQuestion[0]);
        for (int i = 0; i < 4; i++) {
            optionButtons[i].setText(currentQuestion[i + 1]);
            optionButtons[i].setSelected(false);
        }

        // Start the timer
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeLeft--;
                timerLabel.setText("Time: " + timeLeft);
                if (timeLeft == 0) {
                    submitAnswer();
                }
            }
        });
        timer.start();
    }

    private void submitAnswer() {
        // Stop the timer
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }

        // Get the selected option
        int selectedOption = -1;
        for (int i = 0; i < 4; i++) {
            if (optionButtons[i].isSelected()) {
                selectedOption = i;
                break;
            }
        }

        // Save the user's answer and update the score if correct
        if (selectedOption != -1) {
            userAnswers[currentQuestionIndex] = selectedOption;
            if (selectedOption == answers[currentQuestionIndex]) {
                score++;
            }
        }

        currentQuestionIndex++;
        if (currentQuestionIndex < TOTAL_QUESTIONS) {
            displayQuestion();
        } else {
            showResults();
        }
    }

    private void showResults() {
        // Switch to result panel
        frame.getContentPane().removeAll();
        frame.add(resultPanel, BorderLayout.CENTER);

        // Display the final score
        resultLabel.setText("Your Score: " + score + "/" + TOTAL_QUESTIONS);
        StringBuilder resultDetails = new StringBuilder("<html><br>Correct/Incorrect Answers:<br>");
        for (int i = 0; i < TOTAL_QUESTIONS; i++) {
            String result = (userAnswers[i] == answers[i]) ? "Correct" : "Incorrect";
            resultDetails.append("Q").append(i + 1).append(": ").append(result).append("<br>");
        }
        resultDetails.append("</html>");
        resultLabel.setText(resultDetails.toString());

        // Show retry button
        JButton retryButton = new JButton("Retry");
        retryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                score = 0;
                currentQuestionIndex = 0;
                displayQuestion();
                frame.getContentPane().removeAll();
                frame.add(questionPanel, BorderLayout.CENTER);
                frame.add(timerLabel, BorderLayout.NORTH);
                frame.add(submitButton, BorderLayout.SOUTH);
                frame.revalidate();
            }
        });
        resultPanel.add(retryButton);
        frame.revalidate();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new QuizApp();
            }
        });
    }
}
