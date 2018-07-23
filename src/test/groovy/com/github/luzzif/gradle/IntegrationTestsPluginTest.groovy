package com.github.luzzif.gradle

import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

import static org.junit.Assert.assertNotNull
import static org.junit.Assert.assertNull
import static org.junit.Assert.assertTrue

class IntegrationTestsPluginTest {

    @Rule
    public final TemporaryFolder testProjectDir = new TemporaryFolder()
    private File buildFile

    @Before
    void setup() {
        buildFile = testProjectDir.newFile "build.gradle"
        buildFile << "plugins { id 'com.github.luzzif.integration-tests' }"
    }

    @Test
    void testUndefinedIntegrationTestsDirectory() {

        BuildResult result = GradleRunner.create()
                .withPluginClasspath()
                .withProjectDir(testProjectDir.getRoot())
                .build()
        assertNull result.task(":integrationTest")

    }

    @Test
    void testNoSource() throws IOException {

        buildFile << "\nintegrationTests { location = 'test' }"
        BuildResult result = GradleRunner.create()
                .withPluginClasspath()
                .withProjectDir(testProjectDir.getRoot())
                .withArguments("integrationTest")
                .build()

        println result.output
        assertNotNull result.task(":integrationTest").outcome == TaskOutcome.NO_SOURCE

    }

}
