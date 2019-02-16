import java.util.*;

/*
 * Program class to handle pseudo-random numbers being accessed in the the SRPN program
 */

public class RandomNum {

    List<Integer> randNumList = new ArrayList<>();
    int count = -1;

    public RandomNum() {
        randNumList.add(1804289383);
        randNumList.add(846930886);
        randNumList.add(1681692777);
        randNumList.add(1714636915);
        randNumList.add(1957747793);
        randNumList.add(424238335);
        randNumList.add(719885386);
        randNumList.add(1649760492);
        randNumList.add(596516649);
        randNumList.add(1189641421);
        randNumList.add(1025202362);
        randNumList.add(1350490027);
        randNumList.add(783368690);
        randNumList.add(1102520059);
        randNumList.add(2044897763);
        randNumList.add(1967513926);
        randNumList.add(1365180540);
        randNumList.add(1540383426);
        randNumList.add(304089172);
        randNumList.add(1303455736);
        randNumList.add(35005211);
        randNumList.add(521595368);

    }

    public int getNum() {
        count++;
        return randNumList.get(count % 22);
    }

}
