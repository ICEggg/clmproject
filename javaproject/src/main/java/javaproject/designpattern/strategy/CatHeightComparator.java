package javaproject.designpattern.strategy;


/**
 * 猫的高度比较器
 */
public class CatHeightComparator implements Comparator<Cat> {
    @Override
    public int compare(Cat c1, Cat c2) {
        if(c1.height>c2.height) return 1;
        else if(c1.height<c2.height) return -1;
        else return 0;
    }

}
