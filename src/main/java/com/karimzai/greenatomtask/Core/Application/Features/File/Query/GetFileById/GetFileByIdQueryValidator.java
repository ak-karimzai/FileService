package com.karimzai.greenatomtask.Core.Application.Features.File.Query.GetFileById;

import br.com.fluentvalidator.AbstractValidator;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class GetFileByIdQueryValidator extends AbstractValidator<GetFileByIdQuery> {
    @Override
    public void rules() {
        ruleFor(GetFileByIdQuery::getFileId)
                .must(this::validateUUID)
                .withMessage("неверный идентификатор файла.");
    }

    private boolean validateUUID(String id) {
        try {
            UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }
}
