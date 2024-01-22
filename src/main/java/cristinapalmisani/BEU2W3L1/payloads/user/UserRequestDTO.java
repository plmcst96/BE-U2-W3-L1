package cristinapalmisani.BEU2W3L1.payloads.user;

import jakarta.validation.constraints.*;

public record UserRequestDTO(
        @NotEmpty(message = "This items cannot be empty")
        @Size(min = 3, max = 30, message = "Name must be between 3 or 30 chars")
        String name,
        @NotEmpty(message = "This items cannot be empty")
        @Size(min = 3, max = 30, message = "Surname must be between 3 or 30 chars")
        String surname,
        @NotEmpty(message = "This items cannot be empty")
        @Size(min = 3, max = 30, message = "Username must be between 3 or 30 chars")
        String username,
        @NotEmpty(message = "This items cannot be empty")
        @Email
        @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "The email insert is wrong!")
        String email) {
}
