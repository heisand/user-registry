package no.cancerregistry.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import no.cancerregistry.model.UserRoleDTO;

public class DateRangeValidator implements ConstraintValidator<ValidDateRange, UserRoleDTO> {

    @Override
    public boolean isValid(UserRoleDTO request, ConstraintValidatorContext context) {
        if (request == null) { return true; }
        return request.getValidTo() != null && request.getValidTo().isAfter(request.getValidFrom());
    }
}