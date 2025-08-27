// ChatBotApp.java
//--------------------------------------------------------------
// 1. Custom Exception
class QuestionNotFoundException extends Exception {
    public QuestionNotFoundException(String message) {
        super(message);
    }
}

//--------------------------------------------------------------
// 2. ChatBot class
class ChatBot {
    private final String[] questions = {
        "hi", "hello", "how are you", "what is your name", "bye"
    };
    private final String[] answers = {
        "Hello! How can I help you?",
        "Hi there! Nice to meet you.",
        "I'm just a chatbot, but I'm doing great! How about you?",
        "I am ChatBot, your virtual assistant.",
        "Goodbye! Have a nice day!"
    };

    public String getResponse(String userInput) throws QuestionNotFoundException {
        userInput = userInput.toLowerCase().trim();
        for (int i = 0; i < questions.length; i++) {
            if (questions[i].equals(userInput)) {
                return answers[i];
            }
        }
        throw new QuestionNotFoundException(
            "I don't understand that. Try asking something else!");
    }
}

//--------------------------------------------------------------
// 3. Main Program
public class ChatBotApp {
    public static void main(String[] args) {
        java.util.Scanner sc = new java.util.Scanner(System.in);
        ChatBot bot = new ChatBot();

        System.out.println("\nChatBot: Hello! Type 'bye' to exit.");
        while (true) {
            System.out.print("You: ");
            String input = sc.nextLine();

            try {
                String response = bot.getResponse(input);
                System.out.println("\nChatBot: " + response);
                if (input.equalsIgnoreCase("bye")) {
                    break;               // exit after farewell
                }
            } catch (QuestionNotFoundException e) {
                System.out.println("\nChatBot: " + e.getMessage());
            }
        }
        sc.close();
    }
}


