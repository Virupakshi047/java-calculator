import java.util.*;


public class Application {
    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }
    private static boolean isValidChar(char c) {
        return Character.isDigit(c) || isOperator(c) ||
                c == '(' || c == ')' || Character.isWhitespace(c);
    }
        public static boolean isValidExpression(String expression){
            if(expression==null||expression.trim().isEmpty()){
                return false;
            }

            try{
                Stack<Character> stack = new Stack<>()  ;
                for(char c : expression.toCharArray()){
                    if(c=='('){
                        stack.push(c);
                    } else if (c==')'){
                        if(stack.isEmpty()){
                            return false;
                        }
                        stack.pop();
                    }
                }
                if(!stack.isEmpty()){
                    return false;
                }
                char lastChar = ' ';
                for (int i = 0; i < expression.length(); i++) {
                    char c = expression.charAt(i);

                    // Valid characters check
                    if (!isValidChar(c)) {
                        return false;
                    }

                    // Check for consecutive operators
                    if (isOperator(c) && isOperator(lastChar)) {
                        return false;
                    }

                    lastChar = c;
                }
                char firstChar = expression.trim().charAt(0);
                if (isOperator(firstChar) && firstChar != '-') {
                    return false;
                }
                if (isOperator(expression.trim().charAt(expression.trim().length() - 1))) {
                    return false;
                }
                return true;
            } catch (Exception e){
                return false;
            }
        }

    private static int getPrecedence(char operator) {
        switch (operator) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            default:
                return 0;
        }
    }

    public static String toPostfix(String expression) {
        if (!isValidExpression(expression)) {
            throw new IllegalArgumentException("Invalid expression");
        }

        StringBuilder postfix = new StringBuilder();
        Stack<Character> operatorStack = new Stack<>();

        // Remove spaces
        expression = expression.replaceAll("\\s+", "");

        StringBuilder number = new StringBuilder();

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (Character.isDigit(c)) {
                number.append(c);
                // If it's the last character or next char is not a digit
                if (i == expression.length() - 1 || !Character.isDigit(expression.charAt(i + 1))) {
                    postfix.append(number).append(" ");
                    number = new StringBuilder();
                }
            }
            else if (c == '(') {
                operatorStack.push(c);
            }
            else if (c == ')') {
                while (!operatorStack.isEmpty() && operatorStack.peek() != '(') {
                    postfix.append(operatorStack.pop()).append(" ");
                }
                if (!operatorStack.isEmpty()) {
                    operatorStack.pop(); // Remove '('
                }
            }
            else if (isOperator(c)) {
                // Handle negative numbers
                if (c == '-' && (i == 0 || expression.charAt(i-1) == '(')) {
                    number.append(c);
                    continue;
                }

                while (!operatorStack.isEmpty() && operatorStack.peek() != '(' &&
                        getPrecedence(operatorStack.peek()) >= getPrecedence(c)) {
                    postfix.append(operatorStack.pop()).append(" ");
                }
                operatorStack.push(c);
            }
        }

        while (!operatorStack.isEmpty()) {
            postfix.append(operatorStack.pop()).append(" ");
        }

        return postfix.toString().trim();
    }

    public static int calculate(String expression) {
        return 0;
    }



    public static void main(String[] args) {
        System.out.println("Enter the expression");
        Scanner input = new Scanner(System.in);
        String expression = input.nextLine();
        if(isValidExpression(expression)){
            System.out.println("Its valid");
          System.out.println(toPostfix(expression));
        }else{
            System.out.println("Not valid");
        }
    }
}
