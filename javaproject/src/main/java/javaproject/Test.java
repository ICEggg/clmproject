package javaproject;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        List<Person> list = new ArrayList<>();
        list.add(new Person(4,"",14));
        list.add(new Person(3,"ccc",13));
        list.add(new Person(1,"aaa",11));
        list.add(new Person(2,"bbb",12));

//        List<Person> sortlist = list.stream().sorted(Comparator.comparing(Person::getName)).collect(Collectors.toList());
//        for (Person person : sortlist) {
//            System.out.println(person.toString());
//        }
        //相当于下面这个
//        List<Person> sortlist = list.stream().sorted(Comparator.comparing(new Function<Person, String>() {
//                    @Override
//                    public String apply(Person person) {
//                        return person.getName();
//                    }
//                })
//        ).collect(Collectors.toList());

//        list.sort(new Comparator<Person>() {
//            @Override
//            public int compare(Person one, Person two) {
//                //one-two是升序
//                //two-one是降序
//                return one.getId()-two.getId();
//            }
//        });


        for (Person person : list) {
            System.out.println(person.toString());
        }

    }
}
