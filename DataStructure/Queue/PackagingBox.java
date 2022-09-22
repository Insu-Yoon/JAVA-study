package DataStructure.Queue;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class PackagingBox {
    public static void main(String[] args) {
        Integer[] arr = new Integer[]{0,1,2,10,4,5,6,7,8,9};
        System.out.println(packagingBox(arr));
    }
    static public int packagingBox(Integer[] boxes) {
        Queue<Integer> box = new LinkedList<>(Arrays.asList(boxes));
        int time = 0; //1box/1time
        int cnt = 0; //한사람이 지나가면 cnt++
        int max = 0; //한번에 지나간 사람 수 최대치
        while (!box.isEmpty()) {
            time++; //매 사이클 시간이 흐름
            if (time< box.peek()) {//흐른시간 < 맨 앞사람 상자포장시간이면 cnt 초기화
                cnt = 0;
            }
            if (time >= box.peek()) { //time이 맨 앞사람의 상자수보다 크거나같으면
                box.poll();  //맨 앞사람을 보내고
                cnt++;       //보낸 사람수++
                time--;      //다음 사이클도 같은 시간에 대해 판단해야하므로 time--
            }
            if (cnt > max) max = cnt; //한번에 지나간 사람 최대치 갱신
        }
        return max;
    }
}