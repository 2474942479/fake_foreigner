package edu.zsq.utils.result;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 张
 */
public class ResponseUtil {

    public static void out(HttpServletResponse response, MyResultUtils myResultUtils) {
        ObjectMapper mapper = new ObjectMapper();
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        try {
            mapper.writeValue(response.getWriter(), myResultUtils);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
