package com.karimzai.greenatomtask.Core.Application.Features.File.Query.GetPagedFileList;

import br.com.fluentvalidator.context.ValidationResult;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

class GetPagedFileListQueryValidatorTest {
    @Test
    void queryIsValid() {
        // Arrange
        GetPagedFileListQuery query = new GetPagedFileListQuery(1, 10, false);
        GetPagedFileListQueryValidator validator = new GetPagedFileListQueryValidator();

        // Act
        ValidationResult validationResult = validator.validate(query);

        // Assert
        assertTrue(validationResult.isValid());
        assertThat(validationResult.getErrors(), empty());
    }

    @Test
    void pageIsInvalid() {
        // Arrange
        GetPagedFileListQuery query = new GetPagedFileListQuery(0, 10, true);
        GetPagedFileListQueryValidator validator = new GetPagedFileListQueryValidator();

        // Act
        ValidationResult validationResult = validator.validate(query);

        // Assert
        assertFalse(validationResult.isValid());
        assertThat(validationResult.getErrors(), not(empty()));
        assertThat(validationResult.getErrors(), hasSize(1));
        assertThat(validationResult.getErrors(), hasItem(hasProperty("message", containsString("Номер страницы должен быть больше нуля."))));
    }

    @Test
    void sizeIsInvalid() {
        // Arrange
        GetPagedFileListQuery query = new GetPagedFileListQuery(1, 21, true);
        GetPagedFileListQueryValidator validator = new GetPagedFileListQueryValidator();

        // Act
        ValidationResult validationResult = validator.validate(query);

        // Assert
        assertFalse(validationResult.isValid());
        assertThat(validationResult.getErrors(), not(empty()));
        assertThat(validationResult.getErrors(), hasSize(1));
        assertThat(validationResult.getErrors(), hasItem(hasProperty("message", containsString("Размер страницы не должен быть меньше или равен нулю и не должен быть больше 20."))));
    }
}