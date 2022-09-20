public class recusiveGugudan {
    static int i=1;
    public static void main(String[] args) {
        gugudan(2);
        gugudan(4);
    }
    static void gugudan(int n){ // 구구단은 정수형 입력을 받고, 해당 정수에 1~9까지 곱한 결과를 출력한다
        //출력할 값 : n*1, n*2, ... n*9
        //i 는 클래스에서 스태틱으로 선언. 따로 gugudan() 메서드의 입력으로 받는 방법도 있다
        if(i==10){
            i = 1;//여러번 사용하려면 1로 초기화하고 리턴
            return;
        }
        System.out.println(n+" * "+i+" = "+n*i);
        i++;
        gugudan(n);
    }
}
