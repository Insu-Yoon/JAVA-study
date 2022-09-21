import java.util.*;
public class stringifyJSON {
  //Object 타입의 data를 입력받아, 경우에 따라 String으로 반환
  public String stringify(Object data){
    //Integer 일 때
    if(data instanceof Integer){
      return String.valueOf(data);
    }
    //문자열 일 때
    if(data instanceof String){
      return String.format("\"%s\"",data);
    }
    //Boolean 일 때
    if(data instanceof Boolean){
      return String.valueOf(data);
    }
    //배열 일 때
    //여기서 고생한 이유 : head tail에 너무 집착함. 
    //시도해 본 코드
//  if(data instanceof Object[]){
//    Object[] obj = (Object[]) data;
//    Object[]tail={};
//    String head="";
//    if (obj.length == 0) return "[]";
//    if (obj.length == 1) return "["+stringify(obj[0])+"]";
//    head = stringify(obj[0]);
//    tail = Arrays.copyOfRange(obj, 1, obj.length);
//    return head+","+stringify(tail);
//    생각난 모든 방법을 동원해봤지만 모든 test case를 만족시킬 순 없었다..
    if(data instanceof Object[]){
      //obj 에 data 할당
      Object[] obj = (Object[]) data;
      //obj 전체를 순회하며 각 index 에 대해 stringify() 재귀호출
      //만약 이번 인덱스에 들어온 요소가 배열이다 -> stringify(배열)
      //-> for문으로 들어가 첫 항부터 순서대로 stringify()
      for(int i=0;i< obj.length;i++){
        obj[i] = stringify(obj[i]);
      }
      //호출이 종료될 때 Arrays.toString()을 통해 obj 배열을 string으로 변환후 반환
      //n번째 요소가 배열이다?
      //-> stringify(배열)로 들어와 아래 return까지 실행되고 해당 String을 obj[n]에 반환
      return Arrays.toString(obj).replaceAll(" ","");
    }
    //해시맵 일 때
    if(data instanceof HashMap){
      //data 를 hashMap 에 할당
      HashMap<?,?> hashMap = (HashMap<?, ?>) data;
      //결과를 받을 Hashmap result 생성
      HashMap<String,String> result = new LinkedHashMap<>();
      //entrySet 을 순회하며 getKey, getValue -> stringify후 key와 value에 할당
      for(Map.Entry<?,?> s:hashMap.entrySet()){
        String key = stringify(s.getKey());
        String value = stringify(s.getValue());
        result.put(key,value);
      }
      //result 를 String.valueOf로 출력
      //최초 출력은 "a"="apple" -> =을 :로 replace
      //공백도 제거 " " -> ""
      return String.valueOf(result).replaceAll("=",":").replaceAll(" ","");
    }
    //해당되는 사항이 없을 시 String "null" 리턴
    return "null";
  }
}
