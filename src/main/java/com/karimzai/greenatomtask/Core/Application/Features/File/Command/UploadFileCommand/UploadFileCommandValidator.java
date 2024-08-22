package com.karimzai.greenatomtask.Core.Application.Features.File.Command.UploadFileCommand;

import br.com.fluentvalidator.AbstractValidator;
import org.springframework.stereotype.Component;

public class UploadFileCommandValidator extends AbstractValidator<UploadFileCommand> {
    @Override
    public void rules() {
        ruleFor(UploadFileCommand::getTitle)
                .must(title -> !title.isEmpty() && title.length() <= 60)
                .withMessage("Длина заголовок файла должна быть от 1 до 60.");

        ruleFor(UploadFileCommand::getDescription)
                .must(description -> !description.isEmpty() && description.length() <= 256)
                .withMessage("Длина описание файла должна быть от 1 до 256.");

        ruleFor(UploadFileCommand::getContent)
                .must(description -> !description.isEmpty())
                .withMessage("Пустой файл.");
    }
}
