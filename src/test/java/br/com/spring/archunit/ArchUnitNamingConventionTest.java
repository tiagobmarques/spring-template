package br.com.spring.archunit;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

class ArchUnitNamingConventionTest {

    @Test
    void classesShouldHaveSpecificSuffixes() {
        JavaClasses importedClasses = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages("br.com.spring");

        classes()
                .should().haveSimpleNameEndingWith("Application")
                .orShould().haveSimpleNameEndingWith("Repository")
                .orShould().haveSimpleNameEndingWith("Controller")
                .orShould().haveSimpleNameEndingWith("Service")
                .orShould().haveSimpleNameEndingWith("Config")
                .orShould().haveSimpleNameEndingWith("Entity")
                .orShould().haveSimpleNameEndingWith("Builder")
                .orShould().haveSimpleNameEndingWith("Request")
                .orShould().haveSimpleNameEndingWith("Response")
                .orShould().haveSimpleNameEndingWith("Mapper")
                .check(importedClasses);
    }
}
