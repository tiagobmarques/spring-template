package br.com.spring.archunit;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

public class ArchUnitLayerAccessTest {

  private JavaClasses importedClasses;

  @BeforeEach
  public void setup() {
    importedClasses = new ClassFileImporter()
        .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
        .importPackages("br.com.spring");
  }

  @Test
  void layeredArchitectureShouldBeRespected() {
    layeredArchitecture()
        .consideringAllDependencies()
        .layer("application").definedBy("..application..")
        .layer("domain").definedBy("..domain..")
        .layer("infrastructure").definedBy("..infrastructure..")
        .whereLayer("application").mayNotBeAccessedByAnyLayer()
        .whereLayer("domain").mayOnlyBeAccessedByLayers("application")
        .whereLayer("infrastructure").mayOnlyBeAccessedByLayers("domain")
        .check(importedClasses);
  }
}