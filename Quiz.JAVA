package quiz;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Quiz {
    private final List<Question> questions;
    private int currentQuestionIndex;
    private int score;
    private Timer timer;

    public Quiz() {
        this.questions = new ArrayList<>();
        this.currentQuestionIndex = 0;
        this.score = 0;
        initializeQuestions();
    }

    private void initializeQuestions() {
        // Sample questions and options (you can add more questions as needed)
        List<String> options1 = List.of("A. Option A", "B. Option B", "C. Option C", "D. Option D");
        Question q1 = new Question("What is the capital of France?", options1, 0); // Correct option index: 0

        List<String> options2 = List.of("A. Java", "B. Python", "C. C++", "D. JavaScript");
        Question q2 = new Question("Which programming language is used for Android app development?", options2, 0); // Correct option index: 0

        List<String> options3 = List.of("A. Red", "B. Blue", "C. Green", "D. Yellow");
        Question q3 = new Question("What color do you get by mixing blue and yellow?", options3, 1); // Correct option index: 1

        questions.add(q1);
        questions.add(q2);
        questions.add(q3);
    }

    public void startQuiz() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Quiz!");
        System.out.println("You will have 10 seconds to answer each question.");

        for (Question question : questions) {
            displayQuestion(question);

            // Start timer for 10 seconds
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    System.out.println("\nTime's up! Moving to the next question.");
                    timer.cancel(); // Cancel timer
                    showNextQuestion();
                }
            }, 10000); // 10 seconds delay

            // Wait for user input
            int selectedOption = scanner.nextInt();
            timer.cancel(); // Cancel timer

            // Validate user input
            if (selectedOption >= 1 && selectedOption <= 4) {
                // Convert to zero-based index
                int userAnswerIndex = selectedOption - 1;

                // Check if answer is correct
                if (userAnswerIndex == question.getCorrectOptionIndex()) {
                    System.out.println("Correct answer!");
                    score++;
                } else {
                    System.out.println("Incorrect answer.");
                }
            } else {
                System.out.println("Invalid option. Skipping question.");
            }
        }

        // Display final score and summary
        System.out.println("\nQuiz finished!");
        System.out.println("Your final score: " + score + "/" + questions.size());

        // Optional: Display correct answers for each question
        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            System.out.println("Question " + (i + 1) + ": " + q.getQuestion());
            System.out.println("Correct answer: " + q.getOptions().get(q.getCorrectOptionIndex()));
        }

        scanner.close();
    }

    private void displayQuestion(Question question) {
        System.out.println("\nQuestion: " + question.getQuestion());
        List<String> options = question.getOptions();
        for (int i = 0; i < options.size(); i++) {
            System.out.println(options.get(i));
        }
        System.out.print("Enter your choice (1-4): ");
    }

    private void showNextQuestion() {
        currentQuestionIndex++;
        if (currentQuestionIndex < questions.size()) {
            displayQuestion(questions.get(currentQuestionIndex));
        }
    }

    public static void main(String[] args) {
        Quiz quiz = new Quiz();
        quiz.startQuiz();
    }
}
