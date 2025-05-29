package com.grannsnack.GrannSnack.WebController;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * This class is responsible for displaying our custom-made error pages.
 * @Author Joel Seger
 */
@Controller
public class CustomErrorController implements ErrorController {

    /**
     * This method is responsible for returning the right error page if something goes bad.
     * @param request gets the current error status code from the system.
     * @return the appropriate error page depending on what error status the system encountered.
     */
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            if (statusCode == 404) {
                return "error/404";
            } else if (statusCode == 500) {
                return "error/500";
            } else if (statusCode == 403) {
                return "error/403";
            }
        }
        return "error/error";
    }
}
