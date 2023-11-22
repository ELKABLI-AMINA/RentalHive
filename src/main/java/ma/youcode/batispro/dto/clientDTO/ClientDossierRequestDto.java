package ma.youcode.batispro.dto.clientDTO;

import javax.validation.constraints.NotBlank;

public record ClientDossierRequestDto(
        @NotBlank String firstName,
        @NotBlank String lastName,
        @NotBlank String email,
        @NotBlank String address,
        @NotBlank String phone,
        @NotBlank String cin
) {
}
