package com.karimzai.greenatomtask.Core.Application.Features.File.Query.GetPagedFileList;

import br.com.fluentvalidator.AbstractValidator;
import org.springframework.stereotype.Component;

@Component
public class GetPagedFileListQueryValidator extends AbstractValidator<GetPagedFileListQuery> {
    @Override
    public void rules() {
        ruleFor(GetPagedFileListQuery::getPage)
                .must(pageNumber -> pageNumber > 0)
                .withMessage("Номер страницы должен быть больше нуля.");

        ruleFor(GetPagedFileListQuery::getSize)
                .must(pageSize -> pageSize > 0 && pageSize <= 20)
                .withMessage("Размер страницы не должен быть меньше или равен нулю и не должен быть больше 20.");
    }
}
