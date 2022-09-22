package DataStructure.Queue;

import java.util.LinkedList;
import java.util.Queue;

public class QuePrinter {
    public static void main(String[] args) {

    }
    public int queuePrinter(int bufferSize, int capacities, int[] documents) {
        // TODO: bufferSize-칸 /capacities - 모든 칸 용량/ documents - 각 문서의 용량을 담은 배열
        // 1초 -> 첫 문서 추가 ->2초 다음 문서 들어올수있나 판단 후 첫 문서 이동, 다음문서 추가 -> ...
        //bufferSize 만큼 시간 추가, 큐에 입력, 다음 입력 받을때 poll안된 값들 + 입력이 cap보다 크면 +1초 - 다음사이클
        Queue<Integer> queprinter = new LinkedList<>(); //목록내부 큐
        Queue<Integer> quewaiting = new LinkedList<>(); //대기중인 큐
        for (int i = 0; i < bufferSize; i++) queprinter.add(0); //버퍼 0으로 꽉
        for (int i = 0; i < documents.length; i++) quewaiting.add(documents[i]); // 문서 목록
        int time = 0;
        int cap = 0;
        int temp = 0;
        while (!queprinter.isEmpty() || !quewaiting.isEmpty()) {
            time++;
            cap -= queprinter.poll();
            if (quewaiting.isEmpty()) continue;
            if (cap + (quewaiting.peek()) <= capacities) {
                cap += quewaiting.peek();
                queprinter.add(quewaiting.poll());
            } else {
                queprinter.add(0);
            }
        }
        return time;
    }
}
