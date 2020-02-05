package com.bp.act;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ActApplicationTests {

    @Test
    public void contextLoads() throws FileNotFoundException {
        File file = new File("C:/命令.txt");
        System.out.println(file);
        InputStream in = new FileInputStream(file);
        System.out.println(in);

    }

}
