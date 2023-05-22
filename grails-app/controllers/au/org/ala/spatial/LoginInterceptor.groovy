package au.org.ala.spatial

import au.org.ala.spatial.RequireAdmin
import au.org.ala.spatial.RequireLogin
import au.org.ala.spatial.RequirePermission
import au.org.ala.spatial.SkipSecurityCheck
import au.org.ala.spatial.SpatialConfig
import au.org.ala.web.AuthService
import com.google.common.base.Strings
import grails.converters.JSON

/**
 * Copy and simplify from plugin "ala-ws-security-plugin:2.0"
 * Remove IP/method/controller whitelist support
 * collecting api_key from POST body (backward compatiblity support)
 *
 * TODO use ALA standard apiKey method: store apiKey in hearder
 */
import groovy.transform.CompileStatic
@CompileStatic
class LoginInterceptor {
    static final int STATUS_UNAUTHORISED = 401
    static final int STATUS_FORBIDDEN = 403

    AuthService authService
    SpatialConfig spatialConfig

    LoginInterceptor() {
        match controller: '*'
    }

    boolean before() {
//        if (!spatialConfig.security.oidc.enabled) {
//            return true
//        }
//
//        def isAdmin = authService.userInRole(spatialConfig.auth.admin_role)
//
//        def controller = grailsApplication.getArtefactByLogicalPropertyName("Controller", controllerName)
//        Class controllerClass = controller?.clazz
//        def method = controllerClass?.getMethod(actionName ?: "index", [] as Class[])
//
//        if (method?.isAnnotationPresent(SkipSecurityCheck)) {
//            return true
//        }
//
//        //Calculating the required permission.
//        def permissionLevel = null
//        //Permission on method has the top priority
//        if (method?.isAnnotationPresent(RequirePermission.class)) {
//            permissionLevel = RequirePermission
//        } else if (method?.isAnnotationPresent(RequireLogin.class)) {
//            permissionLevel = RequireLogin
//        } else if (method?.isAnnotationPresent(RequireAdmin.class)) {
//            permissionLevel = RequireAdmin
//        }
//
//        if (Objects.isNull(permissionLevel)) {
//            if (controllerClass?.isAnnotationPresent(RequirePermission.class)) {
//                permissionLevel = RequirePermission
//            } else if (controllerClass?.isAnnotationPresent(RequireLogin.class)) {
//                permissionLevel = RequireLogin
//            } else if (controllerClass?.isAnnotationPresent(RequireAdmin.class)) {
//                permissionLevel = RequireAdmin
//            }
//        }
//
//        //Permission check
//        def role  // if require a certain level of ROLE
//        if (permissionLevel == RequirePermission) {
//            if (authService.getUserId()) {
//                return true
//            } else {
//                return accessDenied(STATUS_UNAUTHORISED, 'Forbidden, ApiKey or user login required!')
//            }
//        } else if (permissionLevel == RequireAdmin) {
//            role = spatialConfig.auth.admin_role
//        } else if (permissionLevel == RequireLogin) {
//            RequireLogin requireAuthentication = method.getAnnotation(RequireLogin.class)
//            role = requireAuthentication?.role()
//        } else {
//            return true
//        }
//
//        if (authService.getUserId()) {
//            //Check role
//            if (!Strings.isNullOrEmpty(role)) {
//                if (false && !authService.userInRole(role)) {
//                    return accessDenied(STATUS_FORBIDDEN, 'Forbidden, require a user with role: ' + role)
//                }
//            }
//            return true
//        } else {
//            return accessDenied(STATUS_UNAUTHORISED, 'Forbidden, user login required!')
//        }
        return true
    }


    boolean after() { true }

    void afterView() {
        // no-op
    }

    boolean accessDenied(status, message) {
        log.debug("Access denied : " + controllerName + "->" + actionName ?: "index")

        if (!request.getHeader("accept")?.toLowerCase()?.contains("application/json")) {
            redirect(absolute: false, uri: authService.loginUrl(request))

            return false
        } else {
            Map error = [error: message]
            render error as JSON, status: status
            return false
        }
    }
}
