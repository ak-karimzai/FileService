package com.karimzai.greenatomtask.Core.Application.Features.File.Query.GetFileById;

import br.com.fluentvalidator.context.ValidationResult;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

class GetFileByIdQueryValidatorTest {
    @Test
    void testIdIsValid() {
        // Arrange
        GetFileByIdQuery query = new GetFileByIdQuery(UUID.randomUUID().toString());
        GetFileByIdQueryValidator getFileByIdQueryValidator = new GetFileByIdQueryValidator();

        // Act
        ValidationResult validationResult = getFileByIdQueryValidator.validate(query);

        // Assert
        assertTrue(validationResult.isValid());
        assertThat(validationResult.getErrors(), empty());
    }

    @Test
    void testIdIsInvalid() {
        // Arrange
        GetFileByIdQuery query = new GetFileByIdQuery(new String(new char[14]).replace("\0", "d"));
        GetFileByIdQueryValidator getFileByIdQueryValidator = new GetFileByIdQueryValidator();
        String errorMessage = "неверный идентификатор файла.";

        // Act
        ValidationResult validationResult = getFileByIdQueryValidator.validate(query);

        // Assert
        assertFalse(validationResult.isValid());
        assertThat(validationResult.getErrors(), not(empty()));
        assertThat(validationResult.getErrors(), hasSize(1));
        assertThat(validationResult.getErrors(), hasItem(hasProperty("message", containsString(errorMessage))));
    }
}