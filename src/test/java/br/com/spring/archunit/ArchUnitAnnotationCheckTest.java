package br.com.spring.archunit;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noFields;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import javax.persistence.Entity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

class ArchUnitAnnotationCheckTest {

  private JavaClasses importedClasses;

  @BeforeEach
  public void setup() {
    importedClasses = new ClassFileImporter()
        .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
        .importPackages("br.com.spring");
  }

  @Test
  void fieldInjectionNotUseAutowiredAnnotation() {
    noFields()
        .should().beAnnotatedWith(Autowired.class)
        .check(importedClasses);
  }

  @Test
  void repositoriesShouldBeAnnotatedWithRepository() {
    classes()
        .that().haveSimpleNameEndingWith("Repository")
        .should().beAnnotatedWith(Repository.class)
        .check(importedClasses);
  }

  @Test
  void mapperShouldBeAnnotatedWithComponent() {
    classes()
            .that().haveSimpleNameEndingWith("Mapper")
            .should().beAnnotatedWith(Component.class)
            .check(importedClasses);
  }

  @Test
  void serviceClassesShouldHaveSpringServiceAnnotation() {
    classes()
        .that().haveSimpleNameEndingWith("Service")
        .should().beAnnotatedWith(Service.class)
        .check(importedClasses);
  }

  @Test
  void serviceClassesShouldHaveSpringConfigurationAnnotation() {
    classes()
        .that().haveSimpleNameEndingWith("Config")
        .should().beAnnotatedWith(Configuration.class)
        .check(importedClasses);
  }

  @Test
  void serviceClassesShouldHaveSpringJPAEntityAnnotation() {
    classes()
        .that().haveSimpleNameEndingWith("Entity")
        .should().beAnnotatedWith(Entity.class)
        .check(importedClasses);
  }

  @Test
  void serviceClassesShouldHaveSpringControllerAnnotation() {
    classes()
        .that().haveSimpleNameEndingWith("Controller")
        .should().beAnnotatedWith(RestController.class)
        .check(importedClasses);
  }
}