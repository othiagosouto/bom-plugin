#!/bin/bash
page="$(curl https://plugins.gradle.org/plugin/dev.thiagosouto.plugins.bom-plugin | grep latest)"

if [[ $page =~ (Version )([^ ]*) ]]; then
  published_version=${BASH_REMATCH[2]}
  version_to_publish=$1
  if [ "$published_version" == "$version_to_publish" ]; then
    echo "Version already published, skipping publish step"
  else
    echo "$(./gradlew publishPlugins -Pgradle.publish.key="$GRADLE_KEY" -Pgradle.publish.secret="$GRADLE_SECRET")"
  fi
else
  echo "failed to retrieve version from https://plugins.gradle.org/plugin/dev.thiagosouto.plugins.bom-plugin"
fi
