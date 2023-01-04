package course.springdata.jpaintro.entity;

import lombok.*;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class User {
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    private byte age;
    @NonNull
    private String username;
    @NonNull
    private String password;
}
