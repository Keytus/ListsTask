import com.task.MyArrayList;
import org.junit.Assert;
import org.junit.Test;


public class MyArrayListTest {

    @Test
    public void addAndGetTest(){
        MyArrayList<Integer> myList = new MyArrayList<>();
        Integer[] expectedResult = {234,645,456,15,546};

        for (Integer integer: expectedResult) {
            myList.add(integer);
        }

        for(int i = 0;i < myList.size();i++){
            Assert.assertEquals(expectedResult[i], myList.get(i));
        }
    }

    @Test
    public void addInTargetIndexTest(){
        MyArrayList<String> myList = new MyArrayList<>();
        String[] expectedResult = {"First","Second","Third","Fourth"};

        myList.add(expectedResult[0]);
        myList.add(expectedResult[2]);
        myList.add(expectedResult[3]);
        myList.add(expectedResult[1], 1);

        for(int i = 0;i < myList.size();i++){
            Assert.assertEquals(expectedResult[i], myList.get(i));
        }
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void getArrayIndexOutOfBoundsExceptionTest(){
        MyArrayList<Double> myList = new MyArrayList<>();
        myList.add(4.3);
        myList.get(100);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getNegativeValueIllegalArgumentExceptionTest(){
        MyArrayList<Integer> myList = new MyArrayList<>();
        myList.add(1);
        myList.add(2);
        myList.get(-2);
    }

    @Test
    public void removeByIndexTest(){
        MyArrayList<String> myList = new MyArrayList<>();
        String[] strings = {"First","Second","Third","Fourth"};
        String[] expectedResult = {"Second","Fourth"};

        for(String string: strings){
            myList.add(string);
        }
        myList.remove(2);
        myList.remove(0);

        for(int i = 0;i < myList.size();i++){
            Assert.assertEquals(expectedResult[i], myList.get(i));
        }
    }

    @Test
    public void removeByElementTest(){
        MyArrayList<Integer> myList = new MyArrayList<>();
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
        MyArrayList<String> myList = new MyArrayList<>();
        String[] strings = {"First","Second","Third"};

        for(String string: strings){
            myList.add(string);
        }
        boolean result = myList.remove("Fourth");

        Assert.assertFalse(result);
    }

    @Test
    public void setTest(){
        MyArrayList<String> myList = new MyArrayList<>();
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
        MyArrayList<Integer> myList = new MyArrayList<>();
        MyArrayList<Integer> subList;
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
        MyArrayList<String> myList = new MyArrayList<>();
        MyArrayList<String> subList;
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
        MyArrayList<String> myList = new MyArrayList<>();
        MyArrayList<String> subList;
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
        MyArrayList<String> myList = new MyArrayList<>();
        MyArrayList<String> subList;
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
        MyArrayList<Integer> myList = new MyArrayList<>();
        MyArrayList<Integer> subList;
        MyArrayList<Integer> subSubList;
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
