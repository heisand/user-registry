package no.cancerregistry.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import no.cancerregistry.model.UpdateUserRoleDTO;
import no.cancerregistry.model.UserRoleDTO;

public class DateRangeValidator implements ConstraintValidator<ValidDateRange, Object> {

    @Override
    public boolean isValid(Object request, ConstraintValidatorContext context) {
        if (request == null) { return true; }

        if (request instanceof UserRoleDTO) {
            UserRoleDTO dto = (UserRoleDTO) request;
            return  dto.getValidTo() != null && dto.getValidTo().isAfter(dto.getValidFrom());
        } else if (request instanceof UpdateUserRoleDTO) {
            UpdateUserRoleDTO dto = (UpdateUserRoleDTO) request;
            return dto.getValidTo() != null && dto.getValidTo().isAfter(dto.getValidFrom());
        } else { return true; }
    }
}