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
        public int calculate(String expression){

            return  0;
        }
    public static void main(String[] args) {
        System.out.println("Enter the expression");
        Scanner input = new Scanner(System.in);
        String expression = input.nextLine();
        if(isValidExpression(expression)){
            System.out.println("Its valid");
        }else{
            System.out.println("Not valid");
        }
    }
}
