import java.io.*;
import java.util.Arrays;
import java.util.List;

/*
 * Program class for an SRPN calculator.
 */

public class SRPN {

    //Stack that stores the numbers that are used for calculations
    RpnStack stack = new RpnStack();
    //Boolean variable that stores whether or not the current command is a comment
    boolean comment = false;
    //Used to return random numbers
    RandomNum randNum = new RandomNum();

    public void processCommand(String s) {
        /* Processes strings that are passed to it,
        pushes valid numbers to the stack and passes on symbols to be handled */
        if (!comment) {
            //If not currently in a comment
            if (isNumeric(s)) {
                //If the command is a number
                    if (s.charAt(0) == '0') {
                        //If the number defined as octal then check if it is a valid octal number
                        if (isOctal(s)) {
                            //If a valid octal number then push it to the stack and convert to decimal
                            stack.push((int) Long.parseLong(s, 8));
                        } else {
                            //If not an octal number and has more than 2 characters, push nothing to the stack
                            if (s.length() <= 2) {
                                //If the number has 2 or less characters, push it to the stack
                                stack.push(Long.parseLong(s));
                            }
                        }
                    } else {
                        //If not an octal number, simply push it to the stack
                        stack.push(Long.parseLong(s));
                    }
            } else {
                //If the command is not a number, iterate through the characters and handle each one
                for (char c : s.toCharArray()) {
                    handleSymbols(c);
                }
            }

        } else {
            if (s.charAt(0) == '#') {
                //If at the end of a comment
                comment = false;
            }
        }
    }

    public void processLine(String s) {

        //Creates an array of commands that are separated by spaces
        String comArray[] = s.split(" ");

        for (String com : comArray) {
            if (com.length() == 1 || isNumeric(com)) {
                //If it is a single symbol or if it is a number then simply process the command
                processCommand(com);
            } else {
                //The command contains both operations and operands, so calculate based on priorities
                List<String> commandList;

                commandList = Arrays.asList(com.replaceAll("[-?0-9]", ""));
                /*Uses regex to replace all numbers in a list with a blank space and turns the remaining characters into a list
                 so that they can be executed by priority */
                String numArray[] = com.split("[^-?0-9]");
                //Uses regex to get all the numbers in the string and adds them to an array
                for (String num : numArray) {
                    //Processes all numbers one by one
                    processCommand(num);
                }
                for (String command : commandList) {
                    //Processes each command one by one
                        processCommand(command);
                }
            }
        }
    }

    public void handleSymbols(char s) {
        //Performs different actions based on the character passed
        switch (s) {
            case '*':
                stack.performOperation('*');
                break;
            case '/':
                stack.performOperation('/');
                break;
            case '+':
                stack.performOperation('+');
                break;
            case '-':
                stack.performOperation('-');
                break;
            case '%':
                stack.performOperation('%');
                break;
            case '^':
                stack.performOperation('^');
                break;
            case '#':
                //Start of a comment
                comment = true;
                break;
            case 'd':
                stack.displayStack();
                break;
            case 'r':
                //Checks to make sure the stack is not full so the next random number is not generated
                if (stack.size() <= 22) {
                    stack.push(randNum.getNum());
                } else {
                    System.out.println("Stack overflow.");
                }
                break;
            case '=':
                stack.peek();
                break;
            default:
                //The character is not one that can be used to operate, so returns an error message
                System.out.println("Unrecognised operator or operand \"" + s + "\".");
                break;
        }
    }

    public boolean isNumeric(String s) {
        /* Attempts to parse a long, if it fails return false since it is not a long,
        otherwise return true */
        try {
            Long.parseLong(s);
            return true;
        } catch(NumberFormatException exp) {
            return false;
        }
    }

    public boolean isOctal(String s) {
        /* Attempts to parse an integer as an octal, if it fails return false since it is not an octal
        otherwise return true */
        try {
            Integer.parseInt(s , 8);
            return true;
        } catch(NumberFormatException exp) {
            return false;
        }
    }

    public static void main(String[] args) {
        SRPN srpn = new SRPN();

        //To read the data that has been inputted
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            //Keep on accepting input from the command-line
            while(true) {
                String command = reader.readLine();

                //Close on an End-of-file (EOF) (Ctrl-D on the terminal)
                if(command == null)
                {
                    //Exit code 0 for a graceful exit
                    System.exit(0);
                }

                //Otherwise, (attempt to) process the character
                srpn.processLine(command);
            }
        } catch(IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

}
