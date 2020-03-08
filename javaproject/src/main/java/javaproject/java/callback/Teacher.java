package javaproject.java.callback;


/**
 * 回调函数的例子：
 * 比如：老师问学生一个问题，然后学生要花时间想答案，或者先做自己的事，再想答案，
 * 不知道这中间要花多少时间，
 * 所以老师不能在这一直等学生想出答案，这段时间做自己的事，
 * 当学生做完事情，想出答案后，自己告诉老师
 */
public class Teacher implements CallBack {
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
