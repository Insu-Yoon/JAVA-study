import java.lang.reflect.Array;
import java.util.Arrays;

public class recursiveFactorial {
    int[] a =new int[]{1,2,3,4};
    int t = a.length;
    public static void main(String[] args) {
        System.out.println("5! = " + Factorial(5));
    }

    public static int Factorial(int i) { // 정수를 입력받고 해당 정수의 팩토리얼을 정수로 리턴
        if (i == 1) return 1;
        else return i * Factorial(--i);
    }
}