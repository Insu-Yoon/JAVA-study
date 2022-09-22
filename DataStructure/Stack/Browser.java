package DataStructure.Stack;

import java.util.ArrayList;
import java.util.Stack;

public class Browser {
    public static void main(String[] args) {

    }
    public ArrayList<Stack> browserStack(String[] actions, String start) {
        Stack<String> prevStack = new Stack<>();
        Stack<String> nextStack = new Stack<>();
        Stack<String> current = new Stack<>();
        ArrayList<Stack> result = new ArrayList<>();
        //만약 start의 인자로 알파벳 대문자가 아닌 다른 데이터가 들어온다면 false를 리턴합니다? return 타입이 ArrayList인데?
        // TODO:

        current.push(start);
        for (int i = 0; i < actions.length; i++) {
            switch (actions[i]) {
                case "-1":
                    if (prevStack.empty()) break;
                    nextStack.push(current.pop());
                    current.push(prevStack.pop());
                    break;
                case "1":
                    if (nextStack.empty()) break;
                    prevStack.push(current.pop());
                    current.push(nextStack.pop());
                    break;
                default:
                    nextStack.clear();
                    prevStack.push(current.pop());
                    current.push(actions[i]);
                    break;
            }
        }
        result.add(prevStack);
        result.add(current);
        result.add(nextStack);
        return result;
    }
}