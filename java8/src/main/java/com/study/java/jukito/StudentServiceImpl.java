package com.study.java.jukito;

import com.study.java.lombok.Student;

/**
 * @author Jeffrey
 * @since 2017/04/07 13:44
 */
public class StudentServiceImpl implements StudentService{

    @Override
    public Student createStudent(String name, Integer age) {
        return new Student(name, age);
    }
}
