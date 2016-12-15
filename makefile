default: build

.PHONY: build

clean:
	gradle clean
	rm -rf build/intermediates *.zip

build:
	gradle build

install:
	gradle installDebug

test:
	gradle build installDebug

pkg: package
package:
	zip -r Project_KalevKaldaSikes_100425828.zip \
		app build gradle tool res src \
		gradle.properties build.gradle gradlew gradlew.bat \
		local.properties makefile settings.gradle \
		mobile-exam.iml
