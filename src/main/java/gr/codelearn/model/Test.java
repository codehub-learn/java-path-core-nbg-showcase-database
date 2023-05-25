package gr.codelearn.model;

import lombok.*;

import java.util.Objects;

@Data
@AllArgsConstructor
@Builder
public class Test {
    private Long id;
    private String firstname;
    private String lastname;

    public Test(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }
}
