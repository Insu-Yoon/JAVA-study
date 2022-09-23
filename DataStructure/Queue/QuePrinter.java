package DataStructure.Queue;

import java.util.LinkedList;
import java.util.Queue;

public class QuePrinter {
    public static void main(String[] args) {

    }

    public int queuePrinter(int bufferSize, int capacities, int[] documents) {
        // TODO: bufferSize-칸 /capacities - 모든 칸 용량/ documents - 각 문서의 용량을 담은 배열
        // 프린터의 작업목록? bufferSize 이 queue// 대기하고있는 목록 queue -doc
        
        // 1초 -> 첫 문서 추가 ->2초 다음 문서 들어올수있나 판단 후 첫 문서 이동, 다음문서 추가 -> ...
        //bufferSize 만큼 시간 추가, 큐에 입력, 다음 입력 받을때 poll안된 값들 + 입력이 cap보다 크면 +1초 - 다음사이클
        Queue<Integer> queprinter = new LinkedList<>(); //목록내부 큐
        Queue<Integer> quewaiting = new LinkedList<>(); //대기중인 큐
        for (int i = 0; i < bufferSize; i++) queprinter.add(0); //버퍼 0으로 꽉
        // 8칸 문서 a가들어가면 한칸씩 앞으로/// 제일 앞에 입력 
        for (int i = 0; i < documents.length; i++) quewaiting.add(documents[i]); // 문서 목록
        int time = 0; //한 사이클당 time++
        int cap = 0;  //printer queue의 현재 용량 - quewating에서 queprinter로 데이터 넘길 때 용량 판단용
        while (!queprinter.isEmpty()) { // 10 10문서가 있으면
            time++; //사이클 시작하면서 time++
            cap -= queprinter.poll(); // head poll     {0,0,0,0,문서c}        넵
            if (quewaiting.isEmpty()) continue;
           // {5,0,0,4}   <-  {} x
            cap += peek 
            작업목록.add(대기목록poll);
            time++
            // 대기목록이 비었으면 위의 동작만 반복 대기목록이 비었다 = 더이상 작업목록에 추가할 문서가 없다
            if (cap + (quewaiting.peek()) <= capacities) {//최대 용량보다 작거나 같으면
                cap += quewaiting.peek();                 //peek으로 용량 파악해서 cap에 더해주고
                queprinter.add(quewaiting.poll());        //poll해서 queprinter에 add     작업목록엔 문서 b가 끄트머리에 추가되고, 대기목록에선 b가 삭제됐다 문서 c
            } else {//용량초과인 상황                                      //최대 용량을 초과하면
                queprinter.add(0); //그냥 넘기면 6칸짜린데 {10,0,0,0,0}                        //queprinter에 0을 add
            }                                             //(queprinter에 입력할 시 bufferSize만큼 대기해야 하기 때문에, 빈공간으로 두지않는다)
        }
        return time;
    }
}
