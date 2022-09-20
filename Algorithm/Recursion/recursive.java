import java.util.Arrays;

public class recursive {
    public static void main(String[] args) {
        int[] arr = new int[]{1,2,3,4,5};//배열의 모든 요소의 합을 재귀로 구하자
        System.out.println(arrSum(arr));
    }
    public static int arrSum(int[] arr) { // arr = {5};
        if (arr.length == 0) return 0;
        //arr의 길이는 4이므로, 조건을 만족하지 않는다.
        int[] tail = Arrays.copyOfRange(arr, 1, arr.length);
        //tail = {}
        return arr[0] + arrSum(tail);
        // 5 + arrSum({});
    }
}
//첫 요소의 값을 더하고, 나머지를 다시 arrSum 메서드에 입력
// => 코드의 흐름은 아래처럼 된다
//arrSum({1,2,3,4,5}) == 1 + arrSum({2,3,4,5})
//== 1 + 2 + arrSum({3,4,5}) ... == 1+2+3+4+5 +arrSum({})
// arr.length 가 0이면 0을 리턴하므로, 최종 리턴은 1+2+3+4+5+0;