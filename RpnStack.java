import java.util.Stack;

/*
 * Program class to handle all operations related to the stack for the SRPN program
 */

public class RpnStack {

    Stack<Integer> stack;

    public RpnStack() {
        //Creates a new stack to store the numbers for SRPN calculation
        stack = new Stack<>();
    }

    public void push(long n) {
        /*If the stack isn't full (above 22 numbers), add the number passed to the stack,
        otherwise, output stack overflow */
        if (stack.size() <= 22) {
            stack.push(handleSaturation(n));
        } else {
            System.out.println("Stack overflow.");
        }
    }

    public void performOperation(char op) {
        //Handles the operations passed to the function
        if (stack.size() > 1) {
            /*If the stack has at least 2 numbers in it, then it can continue, otherwise
            output stack underflow since 2 numbers would be attempted to be taken */
            int num2 = stack.pop();
            int num1 = stack.pop();
            /*Get the 2 numbers that are to be operated on in the correct order,
            the first number popped is the second number used in a sum */
            switch (op) {
                //Perform different operations based on the operator passed
                case '*':
                    push((long) num1 * num2);
                    break;
                case '/':
                    if (num2 != 0) {
                        //Cannot divide by zero
                        push((long) num1 / num2);
                    } else {
                        System.out.println("Divide by 0.");
                        stack.push(num1);
                        stack.push(num2);
                        //Operation was not performed, add numbers back onto stack
                    }
                    break;
                case '+':
                    push((long) num1 + num2);
                    break;
                case '-':
                    push((long) num1 - num2);
                    break;
                case '%':
                    if (num1 != 0 && num2 != 0) {
                        push((long) num1 % num2);
                    } else {
                        if (num1 == 0) {
                            System.out.println("Divide by 0.");
                            stack.push(num1);
                            stack.push(num2);
                            //Operation was not performed, add numbers back onto stack
                        } else {
                            System.out.println("Floating point exception");
                            System.exit(0);
                        }
                    }
                    break;
                case '^':
                    if (num2 < 0) {
                        System.out.println("Negative power.");
                        stack.push(num1);
                        stack.push(num2);
                        //Operation was not performed, add numbers back onto stack
                    } else {
                        push((long) Math.pow(num1, num2));
                    }
                    break;
            }
        } else {
            System.out.println("Stack underflow.");
            //Stack does not have enough numbers to perform an operation, inform user
        }
    }

    public void peek() {
        //Returns the top value of the stack without removing it, if it is empty, inform user
        if (!stack.empty()) {
            System.out.println(stack.peek());
        } else {
            System.out.println("Stack empty.");
        }
    }

    public void displayStack() {
        //Outputs the contents of the stack, if the stack is empty, output int min value
        if (stack.empty()) {
            System.out.println(Integer.MIN_VALUE);
        } else {
            Object objectArray[] = stack.toArray();
            //Converts the stack to an array so that it can be iterated through and outputted
            for (int i = 0; i <= stack.size() - 1; i++) {
                System.out.println(objectArray[i]);
            }
        }
    }

    public int size() {
        return stack.size();
    }



    private int handleSaturation(long l) {
        //Limits a long to an integers max values so that overflow or underflow of an integer does not occur
        if (l > Integer.MAX_VALUE) {
            //If the number is larger than the max value, return the max value
            return Integer.MAX_VALUE;
        } else if (l < Integer.MIN_VALUE) {
            //If the number is smaller than the min value, return the min value
            return Integer.MIN_VALUE;
        }

        return (int) l;
        //If the number is within the bounds of a integer simply return it as an integer
    }

}
