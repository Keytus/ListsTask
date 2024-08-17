import com.task.MyLinkedList;
import org.junit.Assert;
import org.junit.Test;

public class MyLinkedListTest {
    @Test
    public void addAndGetTest(){
        MyLinkedList<String> myList = new MyLinkedList<>();
        String[] expectedResult = {"First","Second","Third","Fourth"};

        for(String string: expectedResult){
            myList.add(string);
        }

        for(int i = 0;i < myList.size();i++){
            Assert.assertEquals(expectedResult[i], myList.get(i));
        }
    }

    @Test
    public void addInCenterIndexTest(){
        MyLinkedList<String> myList = new MyLinkedList<>();
        String[] expectedResult = {"First","Second","Third","Fourth"};

        myList.add(expectedResult[0]);
        myList.add(expectedResult[1]);
        myList.add(expectedResult[3]);
        myList.add(expectedResult[2], 2);

        for(int i = 0;i < myList.size();i++){
            Assert.assertEquals(expectedResult[i], myList.get(i));
        }
    }

    @Test
    public void addInHeadIndexTest(){
        MyLinkedList<String> myList = new MyLinkedList<>();
        String[] expectedResult = {"First","Second","Third","Fourth"};

        myList.add(expectedResult[1]);
        myList.add(expectedResult[2]);
        myList.add(expectedResult[3]);
        myList.add(expectedResult[0], 0);

        for(int i = 0;i < myList.size();i++){
            Assert.assertEquals(expectedResult[i], myList.get(i));
        }
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void getArrayIndexOutOfBoundsExceptionTest(){
        MyLinkedList<Double> myList = new MyLinkedList<>();
        myList.add(4.3);
        myList.get(100);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getNegativeValueIllegalArgumentExceptionTest(){
        MyLinkedList<Integer> myList = new MyLinkedList<>();
        myList.add(1);
        myList.add(2);
        myList.get(-2);
    }

    @Test
    public void removeByIndexTest(){
        MyLinkedList<String> myList = new MyLinkedList<>();
        String[] strings = {"First","Second","Third","Fourth", "Fifth"};
        String[] expectedResult = {"Second","Fourth"};

        for(String string: strings){
            myList.add(string);
        }
        myList.remove(strings.length - 1);
        myList.remove(0);
        myList.remove(1);

        for(int i = 0;i < myList.size();i++){
            Assert.assertEquals(expectedResult[i], myList.get(i));
        }
    }

    @Test
    public void removeByElementTest(){
        MyLinkedList<Integer> myList = new MyLinkedList<>();
        Integer[] integers = {6,5,4,3,2,1};
        Integer[] expectedResult = {6,5,4,3,1};

        for (Integer integer: integers) {
            myList.add(integer);
        }
        myList.remove(Integer.valueOf(2));

        for(int i = 0;i < myList.size();i++){
            Assert.assertEquals(expectedResult[i], myList.get(i));
        }
    }

    @Test
    public void removeNotInListElementTest(){
        MyLinkedList<String> myList = new MyLinkedList<>();
        String[] strings = {"First","Second","Third"};

        for(String string: strings){
            myList.add(string);
        }
        boolean result = myList.remove("Fourth");

        Assert.assertFalse(result);
    }

    @Test
    public void setTest(){
        MyLinkedList<String> myList = new MyLinkedList<>();
        String[] strings = {"First","None","Third"};
        String[] expectedResult = {"First","Second","Third"};

        for(String string: strings){
            myList.add(string);
        }
        myList.set(expectedResult[1], 1);

        for(int i = 0;i < myList.size();i++){
            Assert.assertEquals(expectedResult[i], myList.get(i));
        }
    }

    @Test
    public void subListRemoveTest(){
        MyLinkedList<Integer> myList = new MyLinkedList<>();
        MyLinkedList<Integer> subList;
        Integer[] integers = {1,2,3,4,5,6};
        Integer[] listExpectedResult = {1,2,3,5,6};
        Integer[] subListExpectedResult = {3,5};

        for (Integer integer: integers) {
            myList.add(integer);
        }
        subList = myList.subList(2,4);
        subList.remove(1);

        for(int i = 0;i < subList.size();i++){
            Assert.assertEquals(subListExpectedResult[i], subList.get(i));
        }

        for(int i = 0;i < myList.size();i++){
            Assert.assertEquals(listExpectedResult[i], myList.get(i));
        }
    }

    @Test
    public void subListAddInTargetIndexTest() {
        MyLinkedList<String> myList = new MyLinkedList<>();
        MyLinkedList<String> subList;
        String[] strings = {"First","Second","Fourth","Fifth"};
        String newString = "Third";
        String[] listExpectedResult = {"First","Second","Third","Fourth","Fifth"};
        String[] subListExpectedResult = {"First","Second","Third","Fourth"};

        for (String string: strings) {
            myList.add(string);
        }
        subList = myList.subList(0,2);
        subList.add(newString, 2);

        for(int i = 0;i < subList.size();i++){
            Assert.assertEquals(subListExpectedResult[i], subList.get(i));
        }

        for(int i = 0;i < myList.size();i++){
            Assert.assertEquals(listExpectedResult[i], myList.get(i));
        }
    }

    @Test
    public void subListAddTest() {
        MyLinkedList<String> myList = new MyLinkedList<>();
        MyLinkedList<String> subList;
        String[] strings = {"First","Second","Fourth","Fifth"};
        String newString = "Third";
        String[] listExpectedResult = {"First","Second","Third","Fourth","Fifth"};
        String[] subListExpectedResult = {"First","Second","Third"};

        for (String string: strings) {
            myList.add(string);
        }
        subList = myList.subList(0,1);
        subList.add(newString);

        for(int i = 0;i < subList.size();i++){
            Assert.assertEquals(subListExpectedResult[i], subList.get(i));
        }

        for(int i = 0;i < myList.size();i++){
            Assert.assertEquals(listExpectedResult[i], myList.get(i));
        }
    }

    @Test
    public void subListSetTest() {
        MyLinkedList<String> myList = new MyLinkedList<>();
        MyLinkedList<String> subList;
        String[] strings = {"First","Second","None","Fourth","Fifth"};
        String newString = "Third";
        String[] listExpectedResult = {"First","Second","Third","Fourth","Fifth"};
        String[] subListExpectedResult = {"Third","Fourth"};

        for (String string: strings) {
            myList.add(string);
        }
        subList = myList.subList(2,3);
        subList.set(newString, 0);

        for(int i = 0;i < subList.size();i++){
            Assert.assertEquals(subListExpectedResult[i], subList.get(i));
        }

        for(int i = 0;i < myList.size();i++){
            Assert.assertEquals(listExpectedResult[i], myList.get(i));
        }
    }
    @Test
    public void subListOfSubListTest() {
        MyLinkedList<Integer> myList = new MyLinkedList<>();
        MyLinkedList<Integer> subList;
        MyLinkedList<Integer> subSubList;
        Integer[] integers = {1,2,3,4,-5,6,7,8,9};
        Integer newInteger = 5;
        Integer[] listExpectedResult = {1,2,3,4,5,6,7,8,9};
        Integer[] subListExpectedResult = {2,3,4,5,6};
        Integer[] subSubListExpectedResult = {4,5};

        for (Integer integer: integers) {
            myList.add(integer);
        }
        subList = myList.subList(1,5);
        subSubList = subList.subList(2,3);
        subSubList.set(newInteger, 1);

        for(int i = 0;i < subSubList.size();i++){
            Assert.assertEquals(subSubListExpectedResult[i], subSubList.get(i));
        }

        for(int i = 0;i < subList.size();i++){
            Assert.assertEquals(subListExpectedResult[i], subList.get(i));
        }

        for(int i = 0;i < myList.size();i++){
            Assert.assertEquals(listExpectedResult[i], myList.get(i));
        }
    }

}
