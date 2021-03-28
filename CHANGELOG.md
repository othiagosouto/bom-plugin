# Changelog

## [Unreleased]
### Added
### Modified
### Removed

## Version 0.5.0 - (2021-03-28)]
### Added
- Added developerId, developerName, developerEmail, scmConnection, scmDeveloperConnection, scmUrl fields to `BomMetadata`.
- Added `licenses` tag to generated `pom.xml` when the user fills licenses info in `BomMetadata`.
- Added `developers` tag to generated `pom.xml` when the user fills developer info in `BomMetadata`.
- Added `scm` tag to generated `pom.xml` when the user fills scm info in `BomMetadata`.

## Version 0.4.1 - (2021-03-26)]
### Fixed
- Artifacts from `bomConfiguration` weren't available to `implementation` configuration.

## Version 0.4 - (2021-03-22)
### Added
- `bomConfiguration` to map dependencies related to the bill fo materials
- apply detekt plugin 1.16.0

### Removed
- Use of `implementation` configuration to collect dependencies used to create the `pom.xml` and dependencies

## Version 0.3 - (2021-03-22)
### Added
- Create task that generates pom.xml
- Create task that generates Bom.kt classes including all dependencies marked as `implementation`
