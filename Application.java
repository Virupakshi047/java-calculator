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
                if (i == expression.length() - 1 || !Character.isDigit(expression.charAt(i + 1))) {
                    postfix.append(number).append(" ");
                    number = new StringBuilder();
                }
            }
            else if (c == '(') {
                operatorStack.push(c);
            }
            else if (c == ')') {
                // If we have a negative sign right before (, handle unary minus
                if (!number.toString().isEmpty()) {
                    postfix.append(number).append(" ");
                    number = new StringBuilder();
                }

                while (!operatorStack.isEmpty() && operatorStack.peek() != '(') {
                    postfix.append(operatorStack.pop()).append(" ");
                }
                if (!operatorStack.isEmpty()) {
                    operatorStack.pop(); // Remove '('
                }
            }
            else if (isOperator(c)) {
                // Handle unary minus
                if (c == '-' && (i == 0 || expression.charAt(i-1) == '(' || isOperator(expression.charAt(i-1)))) {
                    if (i < expression.length() - 1 && expression.charAt(i+1) == '(') {
                        postfix.append("0 "); // Add 0 before unary minus
                        operatorStack.push(c);
                    } else {
                        number.append(c);
                    }
                    continue;
                }

                if (!number.toString().isEmpty()) {
                    postfix.append(number).append(" ");
                    number = new StringBuilder();
                }

                while (!operatorStack.isEmpty() && operatorStack.peek() != '(' &&
                        getPrecedence(operatorStack.peek()) >= getPrecedence(c)) {
                    postfix.append(operatorStack.pop()).append(" ");
                }
                operatorStack.push(c);
            }
        }

        if (!number.toString().isEmpty()) {
            postfix.append(number).append(" ");
        }

        while (!operatorStack.isEmpty()) {
            postfix.append(operatorStack.pop()).append(" ");
        }

        return postfix.toString().trim();
    }


    public static void main(String[] args) {
        System.out.println("Enter the expression");
        Scanner input = new Scanner(System.in);
        String expression = input.nextLine();
        if(isValidExpression(expression)){
            System.out.println("Its valid");
          expression = toPostfix(expression);
        }else{
            System.out.println("Not valid");
        }
    }
}
