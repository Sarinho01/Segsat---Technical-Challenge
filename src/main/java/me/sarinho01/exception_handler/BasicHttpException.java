package me.sarinho01.exception_handler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Objects;

@Getter
@AllArgsConstructor
public class BasicHttpException extends RuntimeException {
    private final String title;
    private final String message;
    private final HttpStatus httpStatus;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        BasicHttpException that = (BasicHttpException) o;
        return Objects.equals(title, that.title) && Objects.equals(message, that.message)
                && httpStatus == that.httpStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, message, httpStatus);
    }

    @Override
    public String toString() {
        return "BasicHttpException{" + "title='" + title + '\'' + ", message='" + message + '\'' + ", httpStatus="
                + httpStatus + '}';
    }
}