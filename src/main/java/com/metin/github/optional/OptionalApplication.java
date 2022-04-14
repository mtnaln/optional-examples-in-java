package com.metin.github.optional;

import com.metin.github.optional.model.Customer;
import com.metin.github.optional.model.Student;
import com.metin.github.optional.service.StudentService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.Optional;

@SpringBootApplication
public class OptionalApplication implements CommandLineRunner {

    private final StudentService studentService;

    public OptionalApplication(StudentService studentService) {
        this.studentService = studentService;
    }

    public static void main(String[] args) {
        SpringApplication.run(OptionalApplication.class, args);
    }

    @Override
    public void run(String... args) {

        Student student = new Student(1L, null, null, Arrays.asList("Math", "C++", "Java"), 3.9);

        // empty (taking empty object)
        Optional<Object> empty = Optional.empty();
        System.out.println(empty);

        // of --> it throws NullPointerException (if you are sure object is NOT NULL, you can use it)
        // Optional<String> surnameOptionalOf = Optional.of(student.getSurname());
        // System.out.println(surnameOptionalOf);

        // ofNullable --> (if you are NOT sure object is NOT NULL, you can use it)
        Optional<String> surnameOptionalOfNullable = Optional.ofNullable(student.getSurname());
        System.out.println(surnameOptionalOfNullable);

        // get() method
        // it throws NoSuchElementException because student.getSurname() is null
        // System.out.println(surnameOptionalOfNullable.get());

        // isPresent(...) method
        // surnameOptionalOfNullable.isPresent() is false
        // so System.out.println(surnameOptionalOfNullable.get()); line will NOT run
        if (surnameOptionalOfNullable.isPresent()) {
            System.out.println(surnameOptionalOfNullable.get());
        }

        // orElse(...) method
        // if student.getSurname() is null then "default value..." else return student.getSurname() value
        String value = surnameOptionalOfNullable.orElse("default value...");
        System.out.println(value);

        // orElse(...) method (alternative example)
        // if student.getSurname() is null then null else return student.getSurname() value
        String value2 = surnameOptionalOfNullable.orElse(null);
        System.out.println(value2);

        student.setSurname("Alnıaçık");
        surnameOptionalOfNullable = Optional.ofNullable(student.getSurname());

        // student.getSurname() is NOT null so return student.getSurname() value
        String value3 = surnameOptionalOfNullable.orElse(null);
        System.out.println(value3);


        Optional<String> nameOptionalOfNullable = Optional.ofNullable(student.getName());

        // orElseThrow(...) method
        // String value4 = nameOptionalOfNullable.orElseThrow(()-> new NoSuchElementException("NAME is NULL"));
        // System.out.println(value4);

        // orElseGet(...) method
        System.out.println(nameOptionalOfNullable.map(t -> t.toUpperCase()).orElseGet(() -> "default value..."));
        System.out.println(surnameOptionalOfNullable.map(t -> t.toUpperCase()).orElseGet(() -> "default value..."));


        // No value present if value NOT exist
        // Student student = studentService.getStudents().stream().filter(t -> t.getId().equals(7L)).findFirst().get();
        // System.out.println(student1);

        // value exists
        Student student1 = studentService.getStudents().stream().filter(t -> t.getId().equals(1L)).findFirst().get();
        System.out.println(student1);

        // orElse
        Student student2 = studentService.getStudents().stream().filter(t -> t.getId().equals(7L)).findFirst().orElse(null);
        System.out.println(student2);

        // more safe tech --> name property getter returns Optional<String>
        Customer customer = new Customer(null, null);
        System.out.println(customer.getName().orElseGet(() -> "NAME is NULL"));

        // less safe tech
        System.out.println(customer.getSurname());
    }
}
