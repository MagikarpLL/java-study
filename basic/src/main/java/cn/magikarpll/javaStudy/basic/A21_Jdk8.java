package cn.magikarpll.javaStudy.basic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;

import java.time.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class A21_Jdk8 {
}


/**
 * Optional用法学习
 */
@Data
class Student {

    private String name;
    private int age;
    private Integer score;

    public Student() {
    }

    public static Student buildStudent(String name, int age, Integer score) {
        Student student = new Student();
        student.setName(name);
        student.setAge(age);
        student.setScore(score);
        return student;
    }

    public List<Student> initData() {
        Student s1 = buildStudent("张三", 19, 80);
        Student s2 = buildStudent("李四", 19, 50);
        Student s3 = buildStudent("王五", 23, null);
        Student s4 = buildStudent("赵六", 16, 90);
        Student s5 = buildStudent("钱七", 18, 99);
        Student s6 = buildStudent("孙八", 20, 40);
        Student s7 = buildStudent("吴九", 21, 88);

        return Arrays.asList(s1, s2, s3, null, s4, s5, s6, s7);
    }

    @Test
    public void beforeJava8() {
        List<Student> studentList = initData();
        for (Student student : studentList) {
            if (student != null) {
                if (student.getAge() >= 18) {
                    Integer score = student.getScore();
                    if (score != null && score > 80) {
                        System.out.println("入选：" + student.getName());
                    }
                }
            }
        }
    }

    @Test
    /**
     * 使用Of遇上空指针还是会报错，并没有达到目的
     */
    public void useJava8() {
        List<Student> studentList = initData();
        for (Student student : studentList) {
            Optional<Student> optionalStudent = Optional.of(student);
            Integer score = optionalStudent.filter(s -> s.getAge() >= 18).map(Student::getScore).orElse(0);
            if (score > 80) {
                System.out.println("入选: " + student.getName());
            }
        }
    }

    @Test
    /**
     * 使用ofNullable对可能为空的数据结构做包装，
     * 这样遇上空指针就不会报错，并达到了目的
     */
    public void useJava82() {
        List<Student> studentList = initData();
        for (Student student : studentList) {
            Optional<Student> optionalStudent = Optional.ofNullable(student);
            Integer score = optionalStudent.filter(s -> s.getAge() >= 18).map(Student::getScore).orElse(0);
            if (score > 80) {
                System.out.println("入选: " + student.getName());
            }
        }
    }

}

/**
 * java8 stream api学习
 */
//class TestStream{
//
//
//}

/**
 * java8 Locale time api 学习
  */
class TestLocaleTime{

    public static void main(String[] args) {
        // Get the system clock as UTC offset
        final Clock clock = Clock.systemUTC();
        System.out.println( clock.instant() );
        System.out.println( clock.millis() );

        // Get the local date and local time
        final LocalDate date = LocalDate.now();
        final LocalDate dateFromClock = LocalDate.now( clock );

        System.out.println( date );
        System.out.println( dateFromClock );

        // Get the local date and local time
        final LocalTime time = LocalTime.now();
        final LocalTime timeFromClock = LocalTime.now( clock );

        System.out.println( time );
        System.out.println( timeFromClock );

        // Get duration between two dates
        final LocalDateTime from = LocalDateTime.of( 2014, Month.APRIL, 16, 0, 0, 0 );
        final LocalDateTime to = LocalDateTime.of( 2015, Month.APRIL, 16, 23, 59, 59 );

        final Duration duration = Duration.between( from, to );
        System.out.println( "Duration in days: " + duration.toDays() );
        System.out.println( "Duration in hours: " + duration.toHours() );
    }


}