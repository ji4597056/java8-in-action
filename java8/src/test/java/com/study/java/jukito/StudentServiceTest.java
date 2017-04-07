package com.study.java.jukito;

import com.google.inject.Inject;
import com.study.java.lombok.Student;
import org.jukito.All;
import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jeffrey
 * @since 2017/04/07 13:45
 */
@RunWith(JukitoRunner.class)
public class StudentServiceTest {

    @Inject
    StudentService studentService;

    public static class Module extends JukitoModule {

        protected void configureTest() {
            bindMany(StudentService.class, StudentServiceImpl.class, StudentServiceImpl2.class);
        }
    }

    @Test
    public void createStudent(@All StudentService studentService) throws Exception {
        Student student = studentService.createStudent("张三", 12);
        System.out.println(student);
    }
}