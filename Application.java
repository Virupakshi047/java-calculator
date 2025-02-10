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

    private static int evaluate(int a, int b, char operator) {
        try {
            switch (operator) {
                case '+': return a + b;
                case '-': return a - b;
                case '*': return a * b;
                case '/':
                    if (b == 0) {
                        throw new ArithmeticException("Division by zero is not allowed");
                    }
                    return a / b;
                default:
                    throw new IllegalArgumentException("Invalid operator: " + operator);
            }
        } catch (ArithmeticException e) {
            System.out.println(e.getMessage()); // Print error message
            return 0; // Return a default value (could also throw an exception based on requirement)
        }
    }


    public static int calculate(String postfixExpression) {
        if (postfixExpression == null || postfixExpression.trim().isEmpty()) {
            throw new IllegalArgumentException("Empty expression");
        }

        Stack<Integer> stack = new Stack<>();
        String[] tokens = postfixExpression.split("\\s+");

        for (String token : tokens) {
            if (token.isEmpty()) continue; // Skip empty tokens

            if ("+-*/".contains(token)) {
                if (stack.size() < 2) {
                    // Handle unary minus case
                    if (token.equals("-") && stack.size() == 1) {
                        int operand = stack.pop();
                        stack.push(-operand);
                    } else {
                        throw new IllegalArgumentException("Invalid expression");
                    }
                } else {
                    int op2 = stack.pop();
                    int op1 = stack.pop();
                    stack.push(evaluate(op1, op2, token.charAt(0))); // Use evaluate method
                }
            } else {
                try {
                    stack.push(Integer.parseInt(token));
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Invalid token: " + token);
                }
            }
        }

        if (stack.size() != 1) {
            throw new IllegalArgumentException("Invalid expression");
        }

        return stack.peek();
    }



    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the expression: "); // Use print instead of println
        String expression = input.nextLine();

        if (isValidExpression(expression)) {
            expression = toPostfix(expression);
            System.out.println("Output: "+calculate(expression));
        } else {
            System.out.println("Not valid enter again");
        }
    }

}
