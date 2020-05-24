package com.kurylchyk.controller.validation;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
public interface Validation {
    List<String> validate();
    void  setAsRequestAttribute(HttpServletRequest req);

}
