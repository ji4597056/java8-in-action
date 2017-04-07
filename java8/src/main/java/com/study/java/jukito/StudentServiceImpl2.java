package com.study.java.jukito;

import com.study.java.lombok.Student;

/**
 * @author Jeffrey
 * @since 2017/04/07 14:18
 */
public class StudentServiceImpl2 implements StudentService{

    @Override
    public Student createStudent(String name, Integer age) {
        return new Student(name, age + 1);
    }
}
