package cristinapalmisani.BEU2W3L1.payloads.authors;

import jakarta.validation.constraints.*;

public record NewAuthorDTO(
		@NotEmpty(message = "Il nome è obbligatorio")
		@Size(min = 3, max = 30, message = "Nome deve avere minimo 3 caratteri, massimo 30")
		String name,
		@NotEmpty(message = "Il cognome è obbligatorio")
		String surname,
		@NotEmpty(message = "L'email è obbligatoria")
		@Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "L'email inserita non è valida")
		String email,
		@NotNull(message = "La data di nascita è obbligatoria")
		String dateOfBirth
) {
}
