package com.karimzai.greenatomtask.Core.Application.Features.File.Command.UploadFileCommand;

import br.com.fluentvalidator.context.ValidationResult;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

class UploadFileCommandValidatorTest {
    @Test
    void testCommandIsValid() {
        // Arrange
        UploadFileCommand command = new UploadFileCommand(
                "test", "test", "test");
        UploadFileCommandValidator validator = new UploadFileCommandValidator();

        // Act
        final ValidationResult validationResult = validator.validate(command);

        // Assert
        assertTrue(validationResult.isValid());
        assertThat(validationResult.getErrors(), empty());
    }

    @Test
    void testCommandContentIsEmpty() {
        // Arrange
        UploadFileCommand command = new UploadFileCommand(
                "", "test", "test");
        UploadFileCommandValidator validator = new UploadFileCommandValidator();
        String errorMessage = "Пустой файл.";

        // Act
        ValidationResult validationResult = validator.validate(command);

        // Assert
        assertFalse(validationResult.isValid());
        assertThat(validationResult.getErrors(), not(empty()));
        assertThat(validationResult.getErrors(), hasSize(1));
        assertThat(validationResult.getErrors(), hasItem(hasProperty("message", containsString(errorMessage))));
    }

    @Test
    void testCommandTitleIsEmpty() {
        // Arrange
        UploadFileCommand command = new UploadFileCommand(
                "test", "", "test");
        UploadFileCommandValidator validator = new UploadFileCommandValidator();
        String errorMessage = "Длина заголовок файла должна быть от 1 до 60.";

        // Act
        ValidationResult validationResult = validator.validate(command);

        // Assert
        assertFalse(validationResult.isValid());
        assertThat(validationResult.getErrors(), not(empty()));
        assertThat(validationResult.getErrors(), hasSize(1));
        assertThat(validationResult.getErrors(), hasItem(hasProperty("message", containsString(errorMessage))));
    }

    @Test
    void testCommandDescriptionIsEmpty() {
        // Arrange
        UploadFileCommand command = new UploadFileCommand(
                "test", "test", "");
        UploadFileCommandValidator validator = new UploadFileCommandValidator();
        String errorMessage = "Длина описание файла должна быть от 1 до 256.";

        // Act
        ValidationResult validationResult = validator.validate(command);

        // Assert
        assertFalse(validationResult.isValid());
        assertThat(validationResult.getErrors(), not(empty()));
        assertThat(validationResult.getErrors(), hasSize(1));
        assertThat(validationResult.getErrors(), hasItem(hasProperty("message", containsString(errorMessage))));
    }

    @Test
    void testAllFieldsAreInvalid() {
        // Arrange
        UploadFileCommand command = new UploadFileCommand(
                "", "", "");
        UploadFileCommandValidator validator = new UploadFileCommandValidator();
        List<String> errorMessages = List.of(
                "Длина описание файла должна быть от 1 до 256.",
                "Длина заголовок файла должна быть от 1 до 60.",
                "Пустой файл.");

        // Act
        ValidationResult validationResult = validator.validate(command);

        // Assert
        assertFalse(validationResult.isValid());
        assertThat(validationResult.getErrors(), not(empty()));
        assertThat(validationResult.getErrors(), hasSize(3));
        for (String errorMessage : errorMessages) {
            assertThat(validationResult.getErrors(), hasItem(hasProperty("message", containsString(errorMessage))));
        }
    }
}