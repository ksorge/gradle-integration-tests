package com.github.luzzif.gradle

import org.gradle.api.NonNullApi
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginConvention
import org.gradle.api.tasks.testing.Test

@NonNullApi
class IntegrationTestsPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {

        def extension = project.extensions.create(
                "integrationTests",
                IntegrationTestsExtension.class
        )

        project.plugins.apply "java"
        def javaConvention = project.convention.getPlugin JavaPluginConvention.class
        javaConvention.sourceSets {
            integrationTest {
                compileClasspath += main.output + test.output
                runtimeClasspath += main.output + test.output
                java.srcDir "src/${extension.location}/${extension.language}"
                resources.srcDir "src/${extension.location}/resources"
            }
        }
        project.configurations {
            integrationTestCompile.extendsFrom compile, testCompile
            integrationTestRuntime.extendsFrom runtime, testRuntime
        }

        def tasks = project.tasks
        def task = tasks.create("integrationTest", Test, {

            group = 'verification'
            description = 'Runs integration tests.'
            testClassesDirs = javaConvention
                    .sourceSets
                    .integrationTest
                    .output
                    .classesDirs
            classpath = javaConvention
                    .sourceSets
                    .integrationTest
                    .runtimeClasspath

        })
        tasks.getByName("check").dependsOn task
        task.mustRunAfter tasks.getByName("test")

    }

}
