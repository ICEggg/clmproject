package org.clm.javaproject.callback;

public class Teacher implements CallBack{
    private Student student;

    public Teacher(Student student){
        this.student = student;
    }

    public void askQuestion(final String question){
        System.out.println("Teacher ask a question: "+ question);    //提出一个问题
        student.resolveQuestion(this,question);                      //询问学生
        System.out.println("Teacher: do someting else");             //忙自己的事
    }

    @Override              //重写回调函数
    public void tellAnswer(int answer){
        System.out.println("your answer is: " + answer);
    }

    public static void main(String[] args) {
        Student student = new Student();
        Teacher teacher = new Teacher(student);
        teacher.askQuestion("问题1");
    }
}
