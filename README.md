# java-calculator
# Expression Evaluator (Infix to Postfix & Evaluation)

This Java program evaluates mathematical expressions entered by the user.  
It supports **infix notation** conversion to **postfix** (Reverse Polish Notation) and evaluates the result.  

## Features
- **Validates expressions** (checks for balanced parentheses, correct operator usage, etc.).
- **Converts infix expressions to postfix notation** using the **Shunting Yard algorithm**.
- **Evaluates postfix expressions** using a **stack-based approach**.
- **Handles division by zero** gracefully.
- **Supports unary minus** (negative numbers).

## How It Works
1. The user enters an infix expression.
2. The program **validates** the expression.
3. If valid, it converts it to **postfix notation**.
4. The **postfix expression** is evaluated and the result is displayed.

---

## Functions Explained

### **1. `isOperator(char c): boolean`**
- **Checks if a character is a mathematical operator** (`+`, `-`, `*`, `/`).

### **2. `isValidChar(char c): boolean`**
- **Checks if a character is valid** (digits, operators, parentheses, spaces).

### **3. `isValidExpression(String expression): boolean`**
- **Validates the mathematical expression**, ensuring:
  - Balanced parentheses.
  - Correct operator placement.
  - No consecutive operators (except unary minus).
  - Expression is not empty or null.

### **4. `getPrecedence(char operator): int`**
- **Assigns precedence values** to operators:
  - `+` and `-` have precedence `1`.
  - `*` and `/` have precedence `2`.

### **5. `toPostfix(String expression): String`**
- **Converts an infix expression to postfix notation** using the **Shunting Yard algorithm**.
- Uses a **stack to store operators** and maintains correct precedence.

### **6. `evaluate(int a, int b, char operator): int`**
- **Performs arithmetic operations** (`+`, `-`, `*`, `/`).
- **Handles division by zero** with a `try-catch` block.

### **7. `calculate(String postfixExpression): int`**
- **Evaluates a postfix expression** using a **stack**:
  - Pushes numbers onto the stack.
  - Pops operands when an operator is encountered and applies the operation.
  - Returns the final result.

### **8. `main(String[] args): void`**
- **Takes user input, processes it, and prints the result**.
- Calls `isValidExpression()`, `toPostfix()`, and `calculate()`.

---


### **Examples**

## Sample Code output
### 1.3+5*2
![image](https://github.com/user-attachments/assets/00e70c39-511c-4145-a770-4e9ca5d85a2b)
### 2.2-3/3-3
![image](https://github.com/user-attachments/assets/6e7d167a-748d-421e-bf05-4f69cc444b55)
### (2*2+1)/7+3-1
![image](https://github.com/user-attachments/assets/275926d6-d6f5-4596-87ed-29fc94b434bb)
### 3.B
![image](https://github.com/user-attachments/assets/9702a97e-3d29-4661-8741-34a5b00f685b)
### 4.-(-)
![image](https://github.com/user-attachments/assets/2b9a1f7d-7f8a-4bb2-8cd7-3235832f9f53)
### 5.7/3-2+1
![image](https://github.com/user-attachments/assets/457b0a30-e196-46e8-bfd8-8f1b59ee3ee9)
### 6.(5+3*2)+(2*4/2)
![image](https://github.com/user-attachments/assets/b74ad7f3-0177-4517-b091-d4b7b549cbb7)


---

## Improvements
- Add support for **floating-point numbers**.
- Implement **modulus (`%`) and exponentiation (`^`) operators**.
- Handle **implicit multiplication** (e.g., `2(3+4)` should be valid).

---

## Author
**K Virupakshi**
