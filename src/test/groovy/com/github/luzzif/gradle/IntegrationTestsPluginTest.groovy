package com.github.luzzif.gradle

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

import static org.junit.Assert.*

class IntegrationTestsPluginTest {

    @Rule
    public final TemporaryFolder testProjectDir = new TemporaryFolder()
    private File buildFile

    @Before
    void setup() throws Exception {
        buildFile = testProjectDir.newFile "build.gradle"
        buildFile << "plugins { id 'com.github.luzzif.integration-tests' }"
    }

    @Test
    void testBuildNoSource() {

        buildFile << "\nintegrationTests { location = 'test' }"
        BuildResult result = GradleRunner
                .create()
                .withPluginClasspath()
                .withProjectDir(testProjectDir.getRoot())
                .withArguments("integrationTest")
                .build()

        assertNotNull result.task(":integrationTest")
        assertTrue result.task(":integrationTest").outcome == TaskOutcome.NO_SOURCE

    }

    @Test
    void testUndefinedExtension() {

        Project project = ProjectBuilder
                .builder()
                .withProjectDir(testProjectDir.root)
                .build()

        assertFalse project.plugins.contains("java")
        assertFalse project.tasks.contains("integrationTest")

    }

}
