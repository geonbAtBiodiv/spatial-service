package au.org.ala

import java.lang.annotation.Documented
import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

/**
 * Annotation to check if a valid api key has been provided
 * or user has logged in
 */
@Target([ElementType.TYPE, ElementType.METHOD])
@Retention(RetentionPolicy.RUNTIME)
@Documented

/**
 * Minimum ApiKey check or login user
 */
@interface RequirePermission {
}